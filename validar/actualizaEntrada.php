<?php
$ID = $_GET["id"];
$op = $_GET["op"];
$inicial = $_GET["Finicial"];
$inicialh = $_GET["Hinicial"];


  $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statement = "UPDATE operador SET inicial='".$inicial."',hora_inicial='".$inicialh."' WHERE id= '".$ID."' AND numero_op='".$op."'";
  $result = mysqli_query($mysqli, $sql_statement);


  $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statement = "SELECT * FROM operador WHERE id= '".$ID."'AND numero_op='".$op."'";
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