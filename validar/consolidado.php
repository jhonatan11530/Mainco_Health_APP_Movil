<?php
require_once("ConexionSQL.php");
date_default_timezone_set('America/Bogota');

$op = $_GET["op"];
$nombre = $_GET["nombre"];
$fecha = $_GET["fecha"];
if(isset($op) && isset($nombre)&& isset($fecha)){
$a = new comienzo();
$a->inicio($nombre,$fecha,$op);
}

class comienzo{
  function inicio($nombre,$fecha,$op){
      $mysqli = sqlsrv_connect(Server() , connectionInfo());
      $CONSULTID = "SELECT DISTINCT id FROM proyecto.operador WHERE nombre='".$nombre."' AND numero_op='".$op."' AND inicial = '".$fecha."' ";
      $llaves = sqlsrv_query($mysqli, $CONSULTID);
      while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {
         $ID = $row["id"];
        
      }  

      $CONSULTAREA = "SELECT DISTINCT tarea FROM proyecto.operador WHERE nombre='".$nombre."' AND numero_op='".$op."' AND inicial = '".$fecha."' ";
      $llaves = sqlsrv_query($mysqli, $CONSULTAREA);
      while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {

        $tarea = $row["tarea"];
      }

      $a = new produc();
      $a->produccion($op,$ID,$fecha,$nombre,$tarea);

    }
}

class produc{
  function produccion($op,$ID,$fecha,$nombre,$tarea){

    $mysqli = sqlsrv_connect(Server() , connectionInfo());
      $consulta = "SELECT cod_producto FROM proyecto.produccion WHERE numero_op = '".$op."'";
    $result = sqlsrv_query($mysqli, $consulta);  
  
      while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
        
        $cod = $row["cod_producto"];

      }

      $c = new HORA();
      $c->EFICACIA($ID,$op,$cod,$fecha,$nombre,$tarea);
  }
} 

class HORA{
  function EFICACIA($ID,$op,$cod,$fecha,$nombre,$tarea){

    /**/
    $mysqli = sqlsrv_connect(Server() , connectionInfo());
    $searchtotal = "SELECT operador.cantidad,operador.tarea as tarea,cantidadbase, extandar
    FROM proyecto.produccion INNER JOIN proyecto.tarea ON proyecto.produccion.cod_producto = proyecto.tarea.id  INNER JOIN proyecto.operador ON proyecto.operador.tarea = proyecto.tarea.tarea
      WHERE proyecto.operador.inicial='".$fecha."' AND proyecto.operador.nombre='".$nombre."' AND proyecto.produccion.numero_op='".$op."' AND proyecto.tarea.numero_op='".$op."'";
    $restotal = sqlsrv_query($mysqli, $searchtotal);  

    while($listo = sqlsrv_fetch_array($restotal, SQLSRV_FETCH_ASSOC)) {

     $cant[] = $listo["cantidad"];
      $extandar[] = round($listo["extandar"] *3600) / $listo["cantidadbase"];
    
    }
    for ($i=0; $i < count($cant); $i++) { 

       $array[] = $extandar[$i] * $cant[$i];
    }
    /*CONVERTIDO A HORA EFICACIA */
     $dato = array_sum($array);

    $d = new entradasalida();
    $d->totaltime($ID,$dato,$op,$cod,$fecha,$nombre,$tarea);
  }
}

class entradasalida{
  function totaltime($ID,$dato,$op,$cod,$fecha,$nombre,$tarea){
    $mysqli = sqlsrv_connect(Server() , connectionInfo());
   $search = "SELECT SUM(DATEDIFF(SECOND,hora_inicial,hora_final)) AS total FROM proyecto.operador WHERE id = '".$ID."' AND numero_op='".$op."' ";
    $res= sqlsrv_query($mysqli, $search);  
    while($listo = sqlsrv_fetch_array($res, SQLSRV_FETCH_ASSOC)) {
      $totalfechas =  $listo['total'];
      $real =gmdate("H:i:s", $totalfechas);

    }

    $sum_paro = "SELECT SUM(DATEDIFF(SECOND, '00:00:00', CONVERT(time, tiempo_descanso))) AS tiempo_descanso FROM proyecto.motivo_paro WHERE id='".$ID."' AND numero_op='".$op."'";
$ensayo = sqlsrv_query($mysqli, $sum_paro);
while($listo = sqlsrv_fetch_array($ensayo, SQLSRV_FETCH_ASSOC)) {
    $TIMEPAROSUM= $listo["tiempo_descanso"] ; 
    $promedioSUM = gmdate('H:i:s', $TIMEPAROSUM);
  }

                     // TIEMPO DIFERENCIA
                     list($hours, $minutes, $segund) = explode(':', $real);
                     $timeprogramado = ($hours * 3600 ) + ($minutes * 60 ) + $segund;
                 // TIEMPO REAL LABORADO
                 list($hours, $minutes, $segund) = explode(':', $promedioSUM);
                 $timeproduccido = ($hours * 3600 ) + ($minutes * 60 ) + $segund;
                 
                 
                 $habil = $timeprogramado - $timeproduccido;
                 $habiles = gmdate('H:i:s', $habil);

                 $estimado = gmdate('H:i:s', $dato);
                 

    $e = new EFICIENCIA();
    $e->eficiencias($nombre,$op,$fecha,$real,$estimado,$habiles);
  }
}

class EFICIENCIA{
  function eficiencias($nombre,$op,$fecha,$real,$estimado,$habiles){

        // PASAR TIEMPO DESCANSO EN SEGUNDOS ESTO SE SACA DEL PRODUCCIDO DEL DIA
        list($horas, $minutos, $segundos) = explode(':', $estimado);
        $timeestimado = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;
    
        // PASAR TIEMPO DESCANSO EN SEGUNDOS ESTO SE SACA DE SQL SERVER
        list($horas, $minutos, $segundos) = explode(':', $habiles);
        $timehabiles = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;

        $formulas = round($timeestimado / $timehabiles * 100);

       $f = new EFICACIA();
       $f->eficacias($nombre,$op,$fecha,$real,$estimado,$habiles,$formulas);

      
  }

}

class EFICACIA{
    function eficacias($nombre,$op,$fecha,$real,$estimado,$habiles,$formulas){
    

                // PASAR TIEMPO DESCANSO EN SEGUNDOS ESTO SE SACA DEL PRODUCCIDO DEL DIA
                list($horas, $minutos, $segundos) = explode(':', $real);
                $timereal = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;
            
                // PASAR TIEMPO DESCANSO EN SEGUNDOS ESTO SE SACA DE SQL SERVER
                list($horas, $minutos, $segundos) = explode(':', $habiles);
                $timehabiles = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;

                $eficacias = round($timehabiles / $timereal * 100);

        $f = new CONSOLIDADO();
        $f->run($nombre,$op,$fecha,$real,$estimado,$habiles,$formulas,$eficacias);
    }
}

  
  
class CONSOLIDADO{
    function run($nombre,$op,$fecha,$real,$estimado,$habiles,$formulas,$eficacias){
         echo "FECHA : ".$fecha;echo "<br>";
         echo "NUMERO OP : ".$op;echo "<br>";
         echo "NOMBRE : ".$nombre;echo "<br>";echo "<br>";
         echo "TIEMPO HABIL SIN TIEMPO DE PARO : ".$real; echo "<br>";
         echo "TIEMPO ESTIMADO : ".$estimado;echo "<br>";
         echo "TIEMPO PRODUCCIDO CON TIEMPO DE PARO : ".$habiles;echo "<br>";echo "<br>";

         echo "% EFICIENCIA : ".$formulas;echo "<br>";
         echo "% PRODUCTIVIDAD : ".$eficacias;echo "<br>";
         $form =substr($formulas, 0,3);

    $mysqli = sqlsrv_connect(Server() , connectionInfo());
    $sql_statements = "SELECT OP FROM proyecto.promedio WHERE  OP= '".$op."' AND fecha='".$fecha."'";
    $ensayo = sqlsrv_query($mysqli, $sql_statements);
    while($row = sqlsrv_fetch_array($ensayo, SQLSRV_FETCH_ASSOC)) {
      $NUMERO= $row["OP"];
       
    }  
    if($NUMERO !=""){

      // SOLO SE ACTUALIZA

         $mysqli = sqlsrv_connect(Server() , connectionInfo());
         $res = "UPDATE proyecto.promedio SET tiempo_habil='".$real."' , timepo_estimado='".$estimado."',
         tiempo_produccido='".$habiles."' , eficiencia='".$form."' , produccion='".$eficacias."' WHERE OP='".$op."' AND fecha='".$fecha."' ";
         sqlsrv_query($mysqli, $res);

    }else{
      
      // SOLO SE INSERTA

        $mysqli = sqlsrv_connect(Server() , connectionInfo());
         $res = "INSERT INTO proyecto.promedio (fecha,OP,Descripcion,tiempo_habil,timepo_estimado,tiempo_produccido,eficiencia,produccion)
         VALUES ('".$fecha."','".$op."','".$nombre."','".$real."','".$estimado."','".$habiles."','".$form."','".$eficacias."')";
         sqlsrv_query($mysqli, $res); 
  
    }

    }
}
