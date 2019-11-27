<?php

$ID = $_GET["id"];

$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$res = "SELECT * FROM  tarea WHERE tarea= '".$ID."'";
$result = mysqli_query($mysqli, $res);


while ($row = mysqli_fetch_array($result)){ 
  $arreglo = array();
  $arreglo[]= $row["cantidadpentiente"];

}
echo json_encode($arreglo);

?>