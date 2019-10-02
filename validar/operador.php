<?php
$ID = $_GET["id"];
$op = $_GET["op"];

  $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statement = "SELECT nombre FROM  operador WHERE  id= '".$ID."'AND numero_op='".$op."'";
  $result = mysqli_query($mysqli, $sql_statement);
  
$arreglo = array();
while ($row = mysqli_fetch_array($result)){ 
    $arreglito = array();
    $arreglito[]="OPERADOR : ".$row["nombre"];
    $arreglo[] = $arreglito;
  }
echo json_encode($arreglo);
?>