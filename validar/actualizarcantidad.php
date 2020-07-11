<?php
error_reporting(0);

 $ID = $_GET["id"];
 $op = $_GET["op"];
 $tarea = $_GET["tarea"];
 $pen = $_GET["canpen"];
 $motivo = $_GET["motivo"];
 $malo = $_GET["malo"];
 $final = $_GET["Ffinal"];
 $finalh = $_GET["Hfinal"];


 if(isset($ID,$op,$tarea,$pen,$motivo,$malo,$final,$finalh)){


    $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $sql_statements = "SELECT llaves FROM operador WHERE id= '".$ID."'";
    $llaves = mysqli_query($mysql, $sql_statements);
    while($row = mysqli_fetch_array($llaves)) {
      $llave = $row["llaves"];
    }  
  

 $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
 $res = "UPDATE IGNORE operador SET cantidad='". $pen."',no_conforme='".$motivo."',cantidad_fallas='".$malo."',final='".$final."',hora_final='".$finalh."',tarea='".$tarea."'
  WHERE numero_op = '".$op."'AND id= '".$ID."' AND llaves='".$llave."' ";
 $result = mysqli_query($mysqli, $res);
 
 $a = new ejemplo();
$a->ejecutar($ID,$op,$tarea,$llave);
}
else{
  echo "aqui no paso nada";
}

class ejemplo{
  function ejecutar($ID,$op,$tarea,$llave){
      sleep(1);
      $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $sql_statements = "SELECT * FROM operador WHERE id='".$ID."' AND numero_op = '".$op."' AND llaves='".$llave."' ";
      $llaves = mysqli_query($mysql, $sql_statements);
      while($row = mysqli_fetch_array($llaves)) {
        $cant = $row["cantidad"];
        $cantFALLAS = $row["cantidad_fallas"];
      }  

      echo "CANTIDAD EN OP : ".$cant;
      echo "<br>";
      echo "<br>";

      $a = new produc();
      $a->produccion($cant,$op,$ID,$tarea,$llave,$cantFALLAS);

    }
}

class produc{
  function produccion($cant,$op,$ID,$tarea,$llave,$cantFALLAS){

      $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $consulta = "SELECT cod_producto FROM produccion WHERE numero_op = '".$op."'";
      $result = mysqli_query($mysqli, $consulta);  
  
      while($row = mysqli_fetch_array($result)) {
        
        $cod = $row["cod_producto"];

      }
      echo "CODIGO PRODUCTO :".$cod;
      echo "<br>";
      echo "<br>";


      $b = new tarea();
      $b->extand($cant,$cod,$ID,$tarea,$op,$llave,$cantFALLAS);
  }
} 
  
class tarea{
  function extand($cant,$cod,$ID,$tarea,$op,$llave,$cantFALLAS){

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "SELECT extandar FROM tarea WHERE numero_op = '".$cod."' AND tarea='".$tarea."' ";
    $res = mysqli_query($mysqli, $search);  
    
    while($listo = mysqli_fetch_array($res)) {
          
      $extandar = $listo["extandar"];
    
    }
    echo "TIEMPO ESTÁNDAR :".$extandar;
    echo "<br>";
    echo "<br>";


    $c = new HORA();
    $c->EFICACIA($cant,$extandar,$ID,$op,$llave,$cantFALLAS,$cod,$tarea);
  }
}

class HORA{
  function EFICACIA($cant,$extandar,$ID,$op,$llave,$cantFALLAS,$cod,$tarea){

    /*CONVERTIDO A HORA EFICACIA */
   $listo = round($extandar * 3600);
   $dato = $listo * $cant;

    echo "TIEMPO EN HORAS QUE SE DEBE DEMORAR EN HACER LA OP: ".$dato;
     
    $d = new entradasalida();
    $d->totaltime($ID,$dato,$op,$cant,$llave,$cantFALLAS,$cod,$tarea);
  }
}

class entradasalida{
  function totaltime($ID,$dato,$op,$cant,$llave,$cantFALLAS,$cod,$tarea){

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
   $search = "SELECT * FROM operador WHERE id = '".$ID."' AND numero_op = '".$op."' AND llaves='".$llave."' ";
    $res = mysqli_query($mysqli, $search);  
    
    while($listo = mysqli_fetch_array($res)) {
          
      $inicial = $listo["hora_inicial"];
      $final = $listo["hora_final"];

    }

    echo "<br>";  
    echo "<br>";
    echo "TIEMPO ENTRADA : ".$inicial;
    echo "<br>";
    echo "<br>";
    echo "TIEMPO SALIDA : ".$final;


    
    $e = new EFICIENCIA();
    $e->eficiencias($dato,$ID,$inicial,$final,$op,$cant,$llave,$cantFALLAS,$cod,$tarea);
  }
}

class EFICIENCIA{
  function eficiencias($dato,$ID,$inicial,$final,$op,$cant,$llave,$cantFALLAS,$cod,$tarea){
    /* YA ESTA LISTO */

    $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $sql_statements = "SELECT SEC_TO_TIME(SUM(TIME_TO_SEC(tiempo_descanso))) AS tiempo_descanso FROM motivo_paro WHERE  numero_op= '".$op."' AND id= '".$ID."' AND tarea= '".$tarea."'";
    $ensayo = mysqli_query($mysql, $sql_statements);
    while($row = mysqli_fetch_array($ensayo)) {
    $TIMEPAROSUM= $row["tiempo_descanso"] ;
    }  
     $promedioSUM = substr($TIMEPAROSUM, 0, 8);

     if($promedioSUM == null){
      $promedioSUM ="00:00:00";
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
    $formulas = round($formula );
    echo "<br>";
    echo "<br>";
    if($formulas >= 0){

      echo "EFICIENCIA : ".$formulas;

      $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $search = "UPDATE operador SET eficencia='".$formulas."' WHERE id= '".$ID."' AND llaves='".$llave."'";
      $res = mysqli_query($mysqli, $search);

      $f = new EFICACIA();
      $f->eficacias($ID,$dato,$op,$cant,$llave,$cod,$tarea);

    }
    if($formulas < 0){

      $formulas = 0;
      echo "EFICIENCIA : ".$formulas;
      $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $search = "UPDATE operador SET eficencia='".$formulas."' WHERE id= '".$ID."' AND llaves='".$llave."'";
      $res = mysqli_query($mysqli, $search);

       $f = new EFICACIA();
       $f->eficacias($ID,$dato,$op,$cant,$llave,$cod,$tarea);

    }
  }else if($promedioSUM != null){

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
    $formulas = round($formula );
    echo "<br>";
    echo "<br>";
    if($formulas >= 0){

      echo "EFICIENCIA : ".$formulas;

      $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $search = "UPDATE operador SET eficencia='".$formulas."' WHERE id= '".$ID."' AND llaves='".$llave."'";
      $res = mysqli_query($mysqli, $search);

      $f = new EFICACIA();
      $f->eficacias($ID,$dato,$op,$cant,$llave,$cod,$tarea);

    }
    if($formulas < 0){

      $formulas = 0;
      echo "EFICIENCIA : ".$formulas;
      $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $search = "UPDATE operador SET eficencia='".$formulas."' WHERE id= '".$ID."' AND llaves='".$llave."'";
      $res = mysqli_query($mysqli, $search);

       $f = new EFICACIA();
       $f->eficacias($ID,$dato,$op,$cant,$llave,$cod,$tarea);

    }
  }
  }
}

class EFICACIA{
  function eficacias($ID,$dato,$op,$cant,$llave,$cod,$tarea){
    /* YA ESTA LISTO NO MODIFICAR */
    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "SELECT programadas FROM produccion WHERE numero_op = '".$op."' AND cod_producto='".$cod."' ";
    $res = mysqli_query($mysqli, $search);  
    
    while($listo = mysqli_fetch_array($res)) {
          
      $programado = $listo["programadas"];
    }

    /*------------------------------------------*/

    $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $sql_statements = "SELECT SEC_TO_TIME(SUM(TIME_TO_SEC(tiempo_descanso))) AS tiempo_descanso FROM motivo_paro WHERE numero_op= '".$op."' AND id= '".$ID."' AND tarea= '".$tarea."'";
    $ensayo = mysqli_query($mysql, $sql_statements);
    while($row = mysqli_fetch_array($ensayo)) {
      $TIMEPAROSUM= $row["tiempo_descanso"] ; 
    }  
    $promedioSUM = substr($TIMEPAROSUM, 0, 8);

  /*------------------------------------------*/
  
    if($promedioSUM == null){

      $promedioSUM ="00:00:00";
      $datetime1 = new DateTime($promedioSUM);
      $datetime2 = new DateTime($programado);
      $interval = $datetime1->diff($datetime2);
      $diferencia = $interval->format('%H:%I:%S');
      
      // TIEMPO EXTANDAR PROGRAMADA
    list($hours, $minutes, $segund) = explode(':', $programado);
    $timeprogramado = ($hours * 3600 ) + ($minutes * 60 ) + $segund;

    // TIEMPO REAL LABORADO
    list($hours, $minutes, $segund) = explode(':', $diferencia);
    $timeproduccido = ($hours * 3600 ) + ($minutes * 60 ) + $segund;
 
    $eficacia = $timeproduccido / $timeprogramado  * 100;
    $eficacias = round($eficacia);

      echo "<br>";
      echo "<br>";
     echo "EFICACIA : ".$eficacias;

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "UPDATE operador SET eficacia='".$eficacias."' WHERE id= '".$ID."' AND llaves='".$llave."'";
    $res = mysqli_query($mysqli, $search);

    $mysqli->close();	

    }
    else if($promedioSUM != null){

      $datetime1 = new DateTime($promedioSUM);
      $datetime2 = new DateTime($programado);
      $interval = $datetime1->diff($datetime2);
      $diferencia = $interval->format('%H:%I:%S');

        // TIEMPO EXTANDAR PROGRAMADA
      list($hours, $minutes, $segund) = explode(':', $programado);
    $timeprogramado = ($hours * 3600 ) + ($minutes * 60 ) + $segund;

    // TIEMPO REAL LABORADO
    list($hours, $minutes, $segund) = explode(':', $diferencia);
    $timeproduccido = ($hours * 3600 ) + ($minutes * 60 ) + $segund;
 
      $eficacia = $timeproduccido / $timeprogramado  * 100;
      $eficacias = round($eficacia);
      echo "<br>";
      echo "<br>";
     echo "EFICACIA : ".$eficacias ;

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "UPDATE operador SET eficacia='".$eficacias."' WHERE id= '".$ID."' AND llaves='".$llave."'";
    $res = mysqli_query($mysqli, $search);

    $mysqli->close();	

    }
      
  }
}

  
  
  
  ?>