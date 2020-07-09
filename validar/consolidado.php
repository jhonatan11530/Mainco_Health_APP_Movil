<?php
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

  $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statement = "SELECT DISTINCT id,numero_op,inicial FROM operador WHERE nombre = '".$NOMBRE."' AND numero_op = '".$numeroops."' ";
  $resultado = mysqli_query($mysqli, $sql_statement);
  while($row = mysqli_fetch_array($resultado)) {
     $ID = $row["id"];
     $op = $row["numero_op"];
     $inicial = $row["inicial"];

  } 

  $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $sql_statements = "SELECT *  FROM produccion WHERE numero_op='".$op."'";
    $llaves = mysqli_query($mysql, $sql_statements);
    while($row = mysqli_fetch_array($llaves)) {

       $cod_producto =  $row["cod_producto"];

    }
    
    $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $sql_statements = "SELECT ROUND(`extandar`*3600) AS `extandar` FROM `tarea` WHERE `numero_op`='".$cod_producto."'";
    $llaves = mysqli_query($mysql, $sql_statements);
    $extand = array();
    while($row = mysqli_fetch_array($llaves)) {
    
      $extand[] = $row["extandar"];
      
    } 

      $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $sql_statements = "SELECT cantidad FROM operador WHERE id='".$ID."' AND numero_op = '".$op."'";
      $consultas = mysqli_query($mysql, $sql_statements);
      $cant = array();
      while($row = mysqli_fetch_array($consultas)) {

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

      $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $sql_statements = "SELECT TIMEDIFF(hora_final,hora_inicial) AS total FROM operador WHERE id='".$ID."' AND numero_op = '".$op."'  ";
      $consutatime = mysqli_query($mysql, $sql_statements);
      $timerestan = array();
      while($row = mysqli_fetch_array($consutatime)) {

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

      $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $sql_statements = "SELECT tiempo_descanso FROM motivo_paro WHERE numero_op='".$op."' ";
      $consutatime = mysqli_query($mysql, $sql_statements);
      $numeroop = array();
      while($row = mysqli_fetch_array($consutatime)) {
       
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
      $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $res = "INSERT INTO promedio (fecha,OP,Descripcion,tiempo_habil,timepo_estimado,tiempo_produccido,eficiencia,produccion) 
      VALUES ('".$inicial."','".$numeroops."','".$NOMBRE."','".$tiempoPorduccido."','".$totalSegund."','".$resultadominuto."','".$eficiencia."','".$productividad."')";
      $resultado = mysqli_query($mysqli, $res);
  }
}

class mayor{
  function run($inicial,$numeroops,$NOMBRE,$tiempoPorduccido,$totalSegund,$resultadominuto,$eficiencias,$productividad){
    $eficiencia = round($eficiencias);
    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $res = "INSERT INTO promedio (fecha,OP,Descripcion,tiempo_habil,timepo_estimado,tiempo_produccido,eficiencia,produccion) VALUES ('".$inicial."','".$numeroops."','".$NOMBRE."','".$tiempoPorduccido."','".$totalSegund."','".$resultadominuto."','".$eficiencia."','".$productividad."')";
    $resultado = mysqli_query($mysqli, $res);
  }
}

?>