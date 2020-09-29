<?php
require_once("ConexionSQL.php");
error_reporting(0);
set_time_limit(10);

$ID = $_GET["id"];
$op = $_GET["op"];
$tarea = $_GET["tarea"];
/**/$cantidad = $_GET["cantidad"];
$no_conforme = $_GET["conforme"];
$motivo = $_GET["motivo"];
$final = $_GET["Ffinal"];
$hora_final = $_GET["Hfinal"];


if(isset($ID,$op,$tarea,$cantidad,$no_conforme,$motivo,$final,$hora_final)){


  $mysql = sqlsrv_connect(Server() , connectionInfo());
  $sql_statements = "SELECT llaves FROM proyecto.operador WHERE id= '".$ID."'";
  $llaves = sqlsrv_query($mysql, $sql_statements);
  while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {
    $llave = $row["llaves"];
  }  

  $mysql = sqlsrv_connect(Server() , connectionInfo());
  $sql_statement = "UPDATE proyecto.operador SET final='".$final."',cantidad='".$cantidad."',hora_final='".$hora_final."',tarea='".$tarea."',cantidad_fallas='".$no_conforme."',no_conforme='".$motivo."' 
  WHERE id= '".$ID."' AND numero_op = '".$op."' AND llaves='".$llave."' ";
  $result = sqlsrv_query($mysql, $sql_statement);


  $mysql = sqlsrv_connect(Server() , connectionInfo());
  $sql_statements = "SELECT AUTO FROM proyecto.motivo_paro WHERE id= '".$ID."'";
  $llaves = sqlsrv_query($mysql, $sql_statements);
  while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {
    $llaveS = $row["AUTO"];
  }  

  $a = new MOTIVO();
$a->run($ID,$op,$cantidad,$tarea,$llaveS);


$a = new ejemplo();
$a->ejecutar($ID,$op,$tarea,$llave);

}
else{
  echo "aqui no paso nada";
}
/*
$mysql = sqlsrv_connect(Server() , connectionInfo());
  $sql_statements = "SELECT llaves FROM proyecto.operador WHERE id= '".$ID."' AND numero_op = '".$op."' AND tarea='".$tarea."'";
  $llaves = sqlsrv_query($mysql, $sql_statements);
  while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {
     $llave = $row["llaves"];
  }  
  $a = new ejemplo();
$a->ejecutar($ID,$op,$tarea,$llave);
   
 
*/

class MOTIVO{
  function run($ID,$op,$cantidad,$tarea,$llaveS){
      sleep(1);

      $mysql = sqlsrv_connect(Server() , connectionInfo());
      $sql_statements = "SELECT cantidad FROM proyecto.motivo_paro WHERE id= '".$ID."' AND numero_op = '".$op."' AND AUTO='".$llaveS."' ";
      $llaves = sqlsrv_query($mysql, $sql_statements);
      while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {
        $cant = $row["cantidad"];
      } 
      if($cant ==0){
        $mysql = sqlsrv_connect(Server() , connectionInfo());
        $sql_statement = "UPDATE proyecto.motivo_paro SET cantidad='".$cantidad."' WHERE id= '".$ID."' AND numero_op = '".$op."' AND AUTO='".$llaveS."' ";
        $result = sqlsrv_query($mysql, $sql_statement);
      }else{

      }

      
    }
}

class ejemplo{
  function ejecutar($ID,$op,$tarea,$llave){
      sleep(1);
      $mysql = sqlsrv_connect(Server() , connectionInfo());
      $sql_statements = "SELECT * FROM proyecto.operador WHERE id='".$ID."' AND numero_op = '".$op."' AND llaves='".$llave."' ";
      $llaves = sqlsrv_query($mysql, $sql_statements);
      while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {
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
      $b->extand($cant,$cod,$ID,$tarea,$op,$llave,$cantFALLAS);
  }
} 
  
class tarea{
  function extand($cant,$cod,$ID,$tarea,$op,$llave,$cantFALLAS){
    $mysql = sqlsrv_connect(Server() , connectionInfo());
    $search = "SELECT extandar FROM proyecto.tarea WHERE numero_op = '".$cod."' AND tarea='".$tarea."' ";
    $res = sqlsrv_query($mysql, $search);  
    
    while($listo = sqlsrv_fetch_array($res, SQLSRV_FETCH_ASSOC)) {
          
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
   $listo = $extandar * 3600;
   $dato = $listo * $cant;

    echo "TIEMPO EN HORAS QUE SE DEBE DEMORAR EN HACER LA OP: ".$dato;
     
    $d = new entradasalida();
    $d->totaltime($ID,$dato,$op,$cant,$llave,$cantFALLAS,$cod,$tarea);
  }
}

class entradasalida{
  function totaltime($ID,$dato,$op,$cant,$llave,$cantFALLAS,$cod,$tarea){
    $mysql = sqlsrv_connect(Server() , connectionInfo());
   $search = "SELECT hora_inicial,hora_final FROM proyecto.operador WHERE id = '".$ID."' AND numero_op = '".$op."' AND llaves='".$llave."' ";
    $res = sqlsrv_query($mysql, $search);  
    
    while($listo = sqlsrv_fetch_array($res, SQLSRV_FETCH_ASSOC)) {
          
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
    $e->eficiencias($dato,$ID,$inicial,$final,$op,$cant,$llave,$cantFALLAS,$cod,$tarea);
  }
}

class EFICIENCIA{
  function eficiencias($dato,$ID,$inicial,$final,$op,$cant,$llave,$cantFALLAS,$cod,$tarea){
    /* YA ESTA LISTO */
    $mysql = sqlsrv_connect(Server() , connectionInfo());
    $sql_statements = "SELECT SUM(DATEDIFF(SECOND, '00:00:00', CONVERT(time, tiempo_descanso))) AS tiempo_descanso FROM proyecto.motivo_paro WHERE  numero_op= '".$op."' AND id= '".$ID."' AND tarea= '".$tarea."'";
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
    
    $formula =  $dato / $dates  * 100;
    $formulas = round($formula);


      echo "EFICIENCIA : ".$formulas;

      $search = "UPDATE proyecto.operador SET eficencia='".$formulas."' WHERE id= '".$ID."' AND llaves='".$llave."'";
      $res = sqlsrv_query($mysql, $search);

       $f = new EFICACIA();
       $f->eficacias($ID,$dato,$op,$cant,$llave,$cod,$tarea);

   
  }
  }
}

class EFICACIA{
  function eficacias($ID,$dato,$op,$cant,$llave,$cod,$tarea){
    /* YA ESTA LISTO NO MODIFICAR */
    $mysql = sqlsrv_connect(Server() , connectionInfo());
    $search = "SELECT programadas FROM proyecto.produccion WHERE numero_op = '".$op."' AND cod_producto='".$cod."' ";
    $res = sqlsrv_query($mysql, $search);  
    
    while($listo = sqlsrv_fetch_array($res, SQLSRV_FETCH_ASSOC)) {
          
       $programado = $listo["programadas"];
    }

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
     $mysql = sqlsrv_connect(Server() , connectionInfo());
    $search = "UPDATE proyecto.operador SET eficacia='".$eficacias."' WHERE id= '".$ID."' AND llaves='".$llave."'";
    $res = sqlsrv_query($mysql, $search);

    $mysql->close();	
    
    $g = new registro();
     $g->NuevoRegistro($ID);
    }
  }
}

class registro{
  function NuevoRegistro($ID){


  $sql_statement = "SELECT * FROM proyecto.operador  WHERE id= '.$ID.'";
  $result = sqlsrv_query($mysql, $sql_statement);

  while ($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)){ 
    $arreglito = array();
    $arreglito[0]= $row["id"];
    $arreglito[1]= $row["nombre"];
    $arreglito[2]= $row["tarea"];
    $arreglito[3]= $row["cantidad"];
    $arreglito[4]= $row["no_conforme"];
    $arreglito[5]= $row["cantidad_fallas"];
    $arreglito[6]= $row["inicial"];
    $arreglito[7]= $row["hora_inicial"];
    $arreglito[8]= $row["final"];
    $arreglito[9]= $row["hora_final"];

  }

  echo $id = $arreglito[0];
  echo $nom = $arreglito[1];

  if(isset($arreglito[2],$arreglito[3],$arreglito[4],$arreglito[5],$arreglito[6],$arreglito[7],$arreglito[8],$arreglito[9])){
    
    sleep(1);
    $mysql = sqlsrv_connect(Server() , connectionInfo());
    $res = "INSERT INTO proyecto.operador (id,nombre) VALUES ('".$id."','".$nom."')";
  $resultado = sqlsrv_query($mysql, $res);
}

  } 
}





?>
