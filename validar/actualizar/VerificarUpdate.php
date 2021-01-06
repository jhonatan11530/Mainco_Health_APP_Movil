<?php
require_once("../ConexionSQL.php");
error_reporting(0);
set_time_limit(10);

$ID = $_GET["id"];
$op = $_GET["op"];
$tarea = $_GET["tarea"];
$hora = $_GET["hora"];


$mysql = sqlsrv_connect(Server() , connectionInfo());
  $sql_statements = "SELECT llaves FROM proyecto.operador WHERE id= '".$ID."' AND numero_op = '".$op."' AND tarea='".$tarea."' AND hora_final='".$hora."'";
  $llaves = sqlsrv_query($mysql, $sql_statements);
  while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {
     $llave = $row["llaves"];
  }  
  $a = new ejemplo();
$a->ejecutar($ID,$op,$tarea,$llave);



class ejemplo{
  function ejecutar($ID,$op,$tarea,$llave){
  
      sleep(1);
      $mysql = sqlsrv_connect(Server() , connectionInfo());
      $sql_statements = "SELECT * FROM proyecto.operador WHERE id='".$ID."'  AND numero_op = '".$op."' AND llaves='".$llave."' ";
      $llaves = sqlsrv_query($mysql, $sql_statements);
      while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {
        $cant = $row["cantidad"];

        $fecha = $row["inicial"];
        $nombre = $row["nombre"];
      }  

      echo "CANTIDAD EN OP : ".$cant;
      echo "<br>";
      echo "<br>";

      $a = new produc();
      $a->produccion($cant,$op,$ID,$tarea,$llave,$fecha,$nombre);

    }
}

class produc{
  function produccion($cant,$op,$ID,$tarea,$llave,$fecha,$nombre){

    $mysql = sqlsrv_connect(Server() , connectionInfo());
      $consulta = "SELECT cod_producto FROM proyecto.produccion WHERE numero_op = '".$op."'";
      $result = sqlsrv_query($mysql, $consulta);  
  
      while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
        
        $cod = $row["cod_producto"];

      }
      echo "CODIGO PRODUCTO :".$cod;
      echo "<br>";
      echo "<br>";


      $b = new tarea();
      $b->extand($cant,$cod,$ID,$tarea,$op,$llave,$fecha,$nombre);
  }
} 
  
class tarea{
  function extand($cant,$cod,$ID,$tarea,$op,$llave,$fecha,$nombre){

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
      $extandar[] = round($listo["extandar"] *3600) * $listo["cantidadbase"] ;
    }

    for ($i=0; $i < count($cant); $i++) { 

       $array[] =  $cant[$i] * $extandar[$i] ;
    }

    /*CONVERTIDO A HORA EFICACIA */
     $dato = array_sum($array);
     
    echo "TIEMPO ESTÁNDAR :".$dato;
    echo "<br>";
    echo "<br>";


    $c = new HORA();
    $c->EFICACIA($cant,$dato,$ID,$op,$llave,$cod,$tarea);
  }
}

class HORA{
  function EFICACIA($cant,$dato,$ID,$op,$llave,$cod,$tarea){

    /*CONVERTIDO A HORA EFICACIA */

    echo "TIEMPO EN HORAS QUE SE DEBE DEMORAR EN HACER LA OP: ".gmdate('H:i:s', $dato);
     
    $d = new entradasalida();
    $d->totaltime($ID,$dato,$op,$cant,$llave,$cod,$tarea);
  }
}

class entradasalida{
  function totaltime($ID,$dato,$op,$cant,$llave,$cod,$tarea){
    $mysql = sqlsrv_connect(Server() , connectionInfo());
   $search = "SELECT inicial,hora_inicial,hora_final FROM proyecto.operador WHERE id = '".$ID."' AND numero_op = '".$op."' AND llaves='".$llave."' ";
    $res = sqlsrv_query($mysql, $search);  
    
    while($listo = sqlsrv_fetch_array($res, SQLSRV_FETCH_ASSOC)) {
      $fecha = $listo["inicial"];
      $inicial = $listo["hora_inicial"]->format('H:i:s');
      $final = $listo["hora_final"]->format('H:i:s');

    }

    echo "<br>";  
    echo "<br>";
    echo "TIEMPO ENTRADA : ".$inicial;
    echo "<br>";
    echo "<br>";
    echo "TIEMPO SALIDA : ".$final;
    echo "<br>";
    echo "<br>";

    
    $e = new EFICIENCIA();
    $e->eficiencias($dato,$ID,$inicial,$final,$op,$cant,$llave,$cod,$tarea,$fecha);
  }
}

class EFICIENCIA{
  function eficiencias($dato,$ID,$inicial,$final,$op,$cant,$llave,$cod,$tarea,$fecha){

    /* YA ESTA LISTO */
    $mysql = sqlsrv_connect(Server() , connectionInfo());
    $sql_statements = "SELECT SUM(DATEDIFF(SECOND, '00:00:00', CONVERT(time, tiempo_descanso))) AS tiempo_descanso FROM proyecto.motivo_paro WHERE  numero_op= '".$op."' AND id= '".$ID."' AND fecha='".$fecha."'";
    $ensayo = sqlsrv_query($mysql, $sql_statements);
    while($row = sqlsrv_fetch_array($ensayo, SQLSRV_FETCH_ASSOC)) {
       $TIMEPAROSUM= $row["tiempo_descanso"] ;
    }  
      $promedioSUM = gmdate('H:i:s', $TIMEPAROSUM);
    if($promedioSUM != null){
  
  
    // RESTAR TIEMPO REAL PRODUCCIDA HORA INICIAL // HORA FINAL
    $datetime1 = new DateTime($final);
    $datetime2 = new DateTime($inicial);
    $interval = $datetime1->diff($datetime2);
    $hora = $interval->format('%H:%I:%S');
    
    // RESTAR TIEMPO PARO - REAL
    $datetime1 = new DateTime($hora);
    $datetime2 = new DateTime($promedioSUM);
    $interval = $datetime1->diff($datetime2);
     $totales = $interval->format('%H:%I:%S');
   
    // PASAR DE HORAS REALES LABORADAS A SEGUNDOS
    list($horas, $minutos, $segundos) = explode(':', $totales);
    $dates = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;

    $formula =  $dato / $dates * 100;
    $formulas = round($formula);
     echo "EFICIENCIA : ".$formulas;
     $formula= substr($formulas, 0,3);
       
     $search = "UPDATE proyecto.operador SET eficencia='".$formula."' WHERE id= '".$ID."' AND llaves='".$llave."'";
        $res = sqlsrv_query($mysql, $search);

       $f = new EFICACIA();
       $f->eficacias($ID,$dato,$op,$cant,$llave,$cod,$tarea,$dates);

   
  }
  }
}

class EFICACIA{
  function eficacias($ID,$dato,$op,$cant,$llave,$cod,$tarea,$dates){

    $programado = gmdate('H:i:s', $dates);
    /*------------------------------------------*/
    $mysql = sqlsrv_connect(Server() , connectionInfo());
    $sql_statements = "SELECT SUM(DATEDIFF(SECOND, '00:00:00', CONVERT(time, tiempo_descanso))) AS tiempo_descanso FROM proyecto.motivo_paro WHERE numero_op= '".$op."' AND id= '".$ID."'";
    $ensayo = sqlsrv_query($mysql, $sql_statements);
    while($row = sqlsrv_fetch_array($ensayo, SQLSRV_FETCH_ASSOC)) {
       $TIMEPAROSUM= $row["tiempo_descanso"] ; 
    }  
   
  /*------------------------------------------*/

    if($programado != null){

      $promedioSUM = gmdate('H:i:s', $TIMEPAROSUM);
      $verifi =  $dates + $TIMEPAROSUM;
      
      $diferencia = gmdate('H:i:s', $verifi);
        // TIEMPO EXTANDAR PROGRAMADA
      list($hours, $minutes, $segund) = explode(':', $programado);
       $timeprogramado = ($hours * 3600 ) + ($minutes * 60 ) + $segund;
    // TIEMPO REAL LABORADO
    list($hours, $minutes, $segund) = explode(':', $diferencia);
     $timeproduccido = ($hours * 3600 ) + ($minutes * 60 ) + $segund;

     $eficacia = round($timeprogramado / $timeproduccido * 100);
      $eficacias = round($eficacia);
      echo "<br>";
      echo "<br>";
     echo "EFICACIA : ".$eficacias ;

 $mysql = sqlsrv_connect(Server() , connectionInfo());
      $search = "UPDATE proyecto.operador SET eficacia='".$eficacias."' WHERE id= '".$ID."' AND llaves='".$llave."'";
      $res = sqlsrv_query($mysql, $search);
    $mysql->close();	


    }
  }
}
