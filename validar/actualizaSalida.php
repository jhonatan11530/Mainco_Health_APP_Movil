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
  $sql_statement = "UPDATE operador SET cantidad='".$cantidad."',no_conforme='".$fallas."',cantidad_fallas='".$noconforme."',tarea='".$tarea."',final='".$final."',hora_final='".$finalh."',numero_op='".$op."' WHERE id= '".$ID."'";
  $result = mysqli_query($mysqli, $sql_statement);


  $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statement = "SELECT * FROM operador WHERE id= '".$ID."'";
  $resultado = mysqli_query($mysqli, $sql_statement);
  
$arreglo = array();
while ($row = mysqli_fetch_array($resultado)){ 
    $arreglito = array();
  
    $arreglito[]=$row["id"];
    $arreglito[]=$row["nombre"];
    $arreglito[]=$row["tarea"];
    $arreglito[]=$row["inicial"];
    $arreglito[]=$row["hora_inicial"];
    $arreglito[]=$row["final"];
    $arreglito[]=$row["hora_final"];

    $arreglo[] = $arreglito;
  }
echo json_encode($arreglo);
?>