<?php
require_once("ConexionSQL.php");
error_reporting(0);
set_time_limit(1);
 $NOMBRE = $_GET["nombre"];
 $numeroops = $_GET["op"];

if (isset($NOMBRE,$numeroops)) { 

$a = new comienzo();
$a->inicio($NOMBRE,$numeroops);

}

class comienzo{
  function inicio($NOMBRE,$numeroops){

  $mysqli = sqlsrv_connect(Server() , connectionInfo());
  $sql_statement = "SELECT DISTINCT id,numero_op,inicial FROM proyecto.operador WHERE nombre = '".$NOMBRE."' AND numero_op = '".$numeroops."' ";
  $resultado = sqlsrv_query($mysqli, $sql_statement);
  while($row = sqlsrv_fetch_array($resultado, SQLSRV_FETCH_ASSOC)) {
     $ID = $row["id"];
     $op = $row["numero_op"];
     $inicial = $row["inicial"];

  } 

  $mysql = sqlsrv_connect(Server() , connectionInfo());
    $sql_statements = "SELECT *  FROM proyecto.produccion WHERE numero_op='".$op."'";
    $llaves = sqlsrv_query($mysql, $sql_statements);
    while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {

       $cod_producto =  $row["cod_producto"];

    }
    
    $mysql = sqlsrv_connect(Server() , connectionInfo());
    $sql_statements = "SELECT ROUND(`extandar`*3600) AS extandar FROM proyecto.tarea WHERE `numero_op`='".$cod_producto."'";
    $llaves = sqlsrv_query($mysql, $sql_statements);
    $extand = array();
    while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {
    
      $extand[] = $row["extandar"];
      
    } 

      $mysql = sqlsrv_connect(Server() , connectionInfo());
      $sql_statements = "SELECT cantidad FROM proyecto.operador WHERE id='".$ID."' AND numero_op = '".$op."'";
      $consultas = sqlsrv_query($mysql, $sql_statements);
      $cant = array();
      while($row = sqlsrv_fetch_array($consultas, SQLSRV_FETCH_ASSOC)) {

      $cant[] = $row["cantidad"];

      }
      $arrayResultados  = array();
      $suma = 0;

      for ($i = 0; $i < count($extand); $i++) {
        for ($f = 0; $f < count($cant); $f++) {
          if($i == $f){
             $arrayResultados[$i] = $extand[$i] * $cant[$f];
            
          break;
          
          }
          
        }
      }
      
      foreach ($arrayResultados as $numero) {
          $suma += $numero;
    }
    
    //TOTAL SUMADO TIME SEGUNDOS CONSOLIDADO
      $totalSegund = round($suma / 60); 

      $mysql = sqlsrv_connect(Server() , connectionInfo());
      $sql_statements = "SELECT TIMEDIFF(hora_final,hora_inicial) AS total FROM proyecto.operador WHERE id='".$ID."' AND numero_op = '".$op."'  ";
      $consutatime = sqlsrv_query($mysql, $sql_statements);
      $timerestan = array();
      while($row = sqlsrv_fetch_array($consutatime, SQLSRV_FETCH_ASSOC)) {

          $timerestan[] = $row["total"];

      }
       
      $total = array();
      for ($i = 0; $i < count($timerestan); $i++) {
        list($horas, $minutos, $segundos) = explode(':', $timerestan[$i]);
        $total[] = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;
      }
       $tiempoPorduc = array_sum($total);
      $hours = floor($tiempoPorduc / 3600);
      $minutes = floor(($tiempoPorduc / 60) % 60);
      $seconds = $tiempoPorduc % 60;

         $tiempoPorduccido = $hours.":".$minutes.":".$seconds;

      $mysql = sqlsrv_connect(Server() , connectionInfo());
      $sql_statements = "SELECT tiempo_descanso FROM proyecto.motivo_paro WHERE numero_op='".$op."' ";
      $consutatime = sqlsrv_query($mysql, $sql_statements);
      $numeroop = array();
      while($row = sqlsrv_fetch_array($consutatime, SQLSRV_FETCH_ASSOC)) {
       
         $numeroop[] = $row["tiempo_descanso"];
     
      }
     
      $NumeroOP = array();
      for ($i = 0; $i < count($numeroop); $i++) {
        list($horas, $minutos, $segundos) = explode(':', $numeroop[$i]);
          $NumeroOP[] = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;
      }
      $matriz2 = array();
      if ( count($total) == count($NumeroOP) )
    for ($i=0; $i < count($total); $i++){
        $matriz2[] = $total[$i] - $NumeroOP[$i];
      }

       $sumartotal =  array_sum($matriz2);
         $sumarsengund = $sumartotal / 60; 
            $real =  number_format((float)$sumarsengund, 1, '.', ''); 

    $horas = floor($sumartotal / 3600);
    $minutos = floor(($sumartotal - ($horas * 3600)) / 60);
    $segundos = $sumartotal - ($horas * 3600) - ($minutos * 60);
      $resultadominuto = $horas . ':' . $minutos . ":" . $segundos;

    // TOTAL PRODUCTIVIDAD 
    $productividad = round($sumartotal / $tiempoPorduc *100) ;

     //TOTAL EFICIENCIA CONSOLIDADO
     $eficiencias =  $totalSegund / $real * 100;

  if($eficiencias == INF && $productividad == 0){

    $a = new menor();
    $a->run($inicial,$numeroops,$NOMBRE,$tiempoPorduccido,$totalSegund,$resultadominuto);

  }
  if($eficiencias > 0 && $productividad > 0){

    $a = new mayor();
    $a->run($inicial,$numeroops,$NOMBRE,$tiempoPorduccido,$totalSegund,$resultadominuto,$eficiencias,$productividad);

  }


  }
}

class menor{
  function run($inicial,$numeroops,$NOMBRE,$tiempoPorduccido,$totalSegund,$resultadominuto){
      $productividad = 100;
      $eficiencia = 100;
      $mysqli = sqlsrv_connect(Server() , connectionInfo());
      $res = "INSERT INTO proyecto.promedio (fecha,OP,Descripcion,tiempo_habil,timepo_estimado,tiempo_produccido,eficiencia,produccion) 
      VALUES ('".$inicial."','".$numeroops."','".$NOMBRE."','".$tiempoPorduccido."','".$totalSegund."','".$resultadominuto."','".$eficiencia."','".$productividad."')";
      $resultado = sqlsrv_query($mysqli, $res);
  }
}

class mayor{
  function run($inicial,$numeroops,$NOMBRE,$tiempoPorduccido,$totalSegund,$resultadominuto,$eficiencias,$productividad){
    $eficiencia = round($eficiencias);
    $mysqli = sqlsrv_connect(Server() , connectionInfo());
    $res = "INSERT INTO proyecto.promedio (fecha,OP,Descripcion,tiempo_habil,timepo_estimado,tiempo_produccido,eficiencia,produccion) VALUES ('".$inicial."','".$numeroops."','".$NOMBRE."','".$tiempoPorduccido."','".$totalSegund."','".$resultadominuto."','".$eficiencia."','".$productividad."')";
    $resultado = sqlsrv_query($mysqli, $res);
  }
}

?>