<?php
$ID = $_GET["id"];
$op = $_GET["op"];
$cantidad = $_GET["cantidad"];
$tarea = $_GET["tarea"];
$final = $_GET["Ffinal"];
$finalh = $_GET["Hfinal"];
$fallas = $_GET["fallas"];
$noconforme = $_GET["cantidaderror"];


 $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statement = "UPDATE IGNORE operador SET cantidad='".$cantidad."',no_conforme='".$fallas."',cantidad_fallas='".$noconforme."',tarea='".$tarea."',final='".$final."',hora_final='".$finalh."' WHERE id= '".$ID."'";
  $result = mysqli_query($mysqli, $sql_statement);


  $sql_statement = "SELECT * FROM operador WHERE id= '".$ID."'";
  $resultado = mysqli_query($mysqli, $sql_statement);
  

while ($row = mysqli_fetch_array($resultado)){ 
    $arreglito = array();
  
    $arreglito[0]=$row["id"];
    $arreglito[1]=$row["nombre"];
    $arreglito[2]=$row["cantidad"];
    $arreglito[3]=$row["tarea"];
    $arreglito[4]=$row["inicial"];
    $arreglito[5]=$row["hora_inicial"];
    $arreglito[6]=$row["final"];
    $arreglito[7]=$row["hora_final"];
    $arreglito[8]=$row["no_conforme"];

  }
  $sql_statement = "SELECT cantidad FROM produccion WHERE numero_id= '".$op."'";
  $resultado = mysqli_query($mysqli, $sql_statement);
  

while ($row = mysqli_fetch_array($resultado)){ 
    $arreglit = array();
    $arreglit[0]=$row["cantidad"];

  }
  $cantAC=$arreglit[0];
 $cant=$arreglito[2];
 $cantno=$arreglito[8];


 $divicion = $cantAC - $cantno ;
 $totales = $divicion / $cant * 100;
$porcentaje = round($totales);


  $workers=new eficencia();
  $workers->run($porcentaje,$ID);



 class eficencia {
  public function  run($porcentaje,$ID){
    sleep(1);

  $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statement = "UPDATE IGNORE operador SET eficacia='".$porcentaje."' WHERE id= '".$ID."'";
  $result = mysqli_query($mysqli, $sql_statement);
  }

 }

 
 
?>