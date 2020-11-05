<?php
require_once("ConexionSQL.php");


$op = $_GET["op"];
$nombre = $_GET["nombre"];
$fecha = $_GET["fecha"];
$actual = $_GET["hora"];
if(isset($op) && isset($nombre)&& isset($fecha) && isset($actual)){
$a = new comienzo();
$a->inicio($nombre,$fecha,$op,$actual);
}

class comienzo{
  function inicio($nombre,$fecha,$op,$actual){
      sleep(1);
      $mysqli = sqlsrv_connect(Server() , connectionInfo());
      $CONSULTID = "SELECT DISTINCT id FROM proyecto.operador WHERE nombre='".$nombre."' AND numero_op='".$op."' AND inicial = '".$fecha."'  AND hora_final = '".$actual."' ";
      $llaves = sqlsrv_query($mysqli, $CONSULTID);
      while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {
         $ID = $row["id"];
        
      }  

      $CONSULTAREA = "SELECT DISTINCT tarea FROM proyecto.operador WHERE nombre='".$nombre."' AND numero_op='".$op."' AND inicial = '".$fecha."' AND hora_final = '".$actual."' ";
      $llaves = sqlsrv_query($mysqli, $CONSULTAREA);
      while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {

        $tarea = $row["tarea"];
      }

      $a = new produc();
      $a->produccion($op,$ID,$fecha,$nombre,$tarea,$actual);

    }
}

class produc{
  function produccion($op,$ID,$fecha,$nombre,$tarea,$actual){

    $mysqli = sqlsrv_connect(Server() , connectionInfo());
      $consulta = "SELECT cod_producto FROM proyecto.produccion WHERE numero_op = '".$op."'";
    $result = sqlsrv_query($mysqli, $consulta);  
  
      while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
        
        $cod = $row["cod_producto"];

      }

      $c = new HORA();
      $c->EFICACIA($ID,$op,$cod,$fecha,$nombre,$tarea,$actual);
  }
} 

class HORA{
  function EFICACIA($ID,$op,$cod,$fecha,$nombre,$tarea,$actual){

    //  
    $mysqli = sqlsrv_connect(Server() , connectionInfo());
    $search = "SELECT operador.cantidad,operador.tarea as tarea,cantidadbase, extandar
    FROM proyecto.produccion INNER JOIN proyecto.tarea ON proyecto.produccion.numero_op = proyecto.tarea.numero_op  INNER JOIN proyecto.operador ON proyecto.operador.tarea = proyecto.tarea.tarea
      WHERE proyecto.tarea.tarea='".$tarea."' AND proyecto.operador.inicial='".$fecha."' AND proyecto.operador.nombre='".$nombre."'
       AND proyecto.produccion.numero_op='".$op."' ";
    $res = sqlsrv_query($mysqli, $search);  
    
    $cant = array();
    $extandar = array();
    while($listo = sqlsrv_fetch_array($res, SQLSRV_FETCH_ASSOC)) {

     $cant[] = $listo["cantidad"];
      $extandar[] = round($listo["extandar"] *3600) / $listo["cantidadbase"];
    
    }

    for ($i=0; $i < count($cant); $i++) { 

       $array[] = $cant[$i] * $extandar[$i];
    }

    /*CONVERTIDO A HORA EFICACIA */
     $datosa = array_sum($array);

    $d = new entradasalida();
    $d->totaltime($ID,$datosa,$op,$cod,$fecha,$nombre,$actual,$tarea);
  }
}

class entradasalida{
  function totaltime($ID,$datosa,$op,$cod,$fecha,$nombre,$actual,$tarea){
    $mysqli = sqlsrv_connect(Server() , connectionInfo());
   $search = "SELECT hora_inicial,hora_final FROM proyecto.operador WHERE id = '".$ID."' AND tarea='".$tarea."' AND inicial='".$fecha."' AND hora_final='".$actual."' ";
    $res = sqlsrv_query($mysqli, $search);  
    while($listo = sqlsrv_fetch_array($res, SQLSRV_FETCH_ASSOC)) {
      $inicial = $listo["hora_inicial"]->format('H:i:s');
      $final = $listo["hora_final"]->format('H:i:s');

      

    }

    $e = new EFICIENCIA();
    $e->eficiencias($datosa,$ID,$op,$cod,$fecha,$nombre,$inicial,$final,$actual,$tarea);
  }
}

class EFICIENCIA{
  function eficiencias($datosa,$ID,$op,$cod,$fecha,$nombre,$inicial,$final,$actual,$tarea){

        /* YA ESTA LISTO */
    $mysqli = sqlsrv_connect(Server() , connectionInfo());
    $sql_statements = "SELECT SUM(DATEDIFF(SECOND, '00:00:00', CONVERT(time, tiempo_descanso))) AS tiempo_descanso FROM proyecto.motivo_paro WHERE  numero_op= '".$op."' AND id= '".$ID."' AND fecha='".$fecha."'";
    $ensayo = sqlsrv_query($mysqli, $sql_statements);
    while($row = sqlsrv_fetch_array($ensayo, SQLSRV_FETCH_ASSOC)) {
        $TIMEPAROSUM= $row["tiempo_descanso"] ;
       
    }  
     $promedioSUM = gmdate('H:i:s',$TIMEPAROSUM);


     $datetime1 = new DateTime($final);
      $datetime2 = new DateTime($inicial);
      $interval = $datetime1->diff($datetime2);
      $inicia = $interval->format('%H:%I:%S');

     $datetime1 = new DateTime($inicia);
     $datetime2 = new DateTime($promedioSUM);
     $interval = $datetime1->diff($datetime2);
      $real = $interval->format('%H:%I:%S');


    // PASAR DE HORAS REALES LABORADAS A SEGUNDOS
    list($horas, $minutos, $segundos) = explode(':', $real);
    $datest = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;

    $formula =  $datosa / $datest * 100;
    $formulas = round($formula);

    $dates = tiempo_produccido($datest);

   $dato = tiempo_estimado($datosa);

       $f = new EFICACIA();
       $f->eficacias($ID,$dato,$op,$cod,$formulas,$dates,$fecha,$nombre,$inicia,$actual);

      
  }

}

class EFICACIA{
    function eficacias($ID,$dato,$op,$cod,$formulas,$dates,$fecha,$nombre,$inicia,$actual){
        
          // TIEMPO EXTANDAR PROGRAMADA
        list($hours, $minutes, $segund) = explode(':', $inicia);
         $timeprogramado = ($hours * 3600 ) + ($minutes * 60 ) + $segund;
      // TIEMPO REAL LABORADO
      list($hours, $minutes, $segund) = explode(':', $dates);
       $timeproduccido = ($hours * 3600 ) + ($minutes * 60 ) + $segund;
  
       $eficacia = $timeproduccido / $timeprogramado  * 100;
        $eficacias = round($eficacia);

      $a = new CONSOLIDADO();
      $a->run($eficacias,$formulas,$dato,$dates,$fecha,$nombre,$op,$inicia,$actual);

    }
}

  
  
class CONSOLIDADO{
    function run($eficacias,$formulas,$dato,$dates,$fecha,$nombre,$op,$inicia,$actual){
         echo "FECHA : ".$fecha;echo "<br>";
         echo "NUMERO OP : ".$op;echo "<br>";
         echo "NOMBRE : ".$nombre;echo "<br>";echo "<br>";
         echo "TIEMPO HABIL  SIN TIEMPO DE PARO : ".$inicia; echo "<br>";
         echo "TIEMPO ESTIMADO : ".$dato;echo "<br>";
         echo "TIEMPO PRODUCCIDO CON TIEMPO DE PARO : ".$dates;echo "<br>";echo "<br>";

         echo "% EFICIENCIA : ".$formulas;echo "<br>";
         echo "% PRODUCTIVIDAD : ".$eficacias;echo "<br>";
         $form =substr($formulas, 0,3);
         /* */
         $mysqli = sqlsrv_connect(Server() , connectionInfo());
         $res = "INSERT INTO proyecto.promedio (fecha,hora,OP,Descripcion,tiempo_habil,timepo_estimado,tiempo_produccido,eficiencia,produccion)
         VALUES ('".$fecha."','".$actual."','".$op."','".$nombre."','".$inicia."','".$dato."','".$dates."','".$form."','".$eficacias."')";
         sqlsrv_query($mysqli, $res); 
 
        

    }
}

/* FUNCIONES PARA PASAR SEGUNDOS A HORAS FORMATO 24 HOURS */
function tiempo_habil($segundos) { 
  $h = floor($segundos / 3600); 
  $m = floor(($segundos % 3600) / 60); 
  $s = $segundos - ($h * 3600) - ($m * 60); 
  $variable = $h+$m+$s;
  $time = gmdate('H:i:s',$variable);
  return $time ; 
}


function diferencia_horas($segundos) { 
  $h = floor($segundos / 3600); 
  $m = floor(($segundos % 3600) / 60); 
  $s = $segundos - ($h * 3600) - ($m * 60); 
  $variable = $h+$m+$s;
  $time = gmdate('H:i:s',$variable);
  return $time ; 
}



function tiempo_real_produccido($segundos) { 
  $h = floor($segundos / 3600); 
  $m = floor(($segundos % 3600) / 60); 
  $s = $segundos - ($h * 3600) - ($m * 60); 
  $variable = $h+$m+$s;
  $time = gmdate('H:i:s',$variable);
  return $time ; 
}

function tiempo_estimado($segundos) { 
  $h = floor($segundos / 3600); 
  $m = floor(($segundos % 3600) / 60); 
  $s = $segundos - ($h * 3600) - ($m * 60); 
  $variable = $h+$m+$s;
  $time = gmdate('H:i:s',$variable);
  return $time ; 
}

function tiempo_produccido($segundos) { 
$h = floor($segundos / 3600); 
$m = floor(($segundos % 3600) / 60); 
$s = $segundos - ($h * 3600) - ($m * 60); 
return sprintf('%02d:%02d:%02d', $h, $m, $s); 
}
?>