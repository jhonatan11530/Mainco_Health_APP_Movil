<?php
$ID = $_GET["id"];
$op = $_GET["numerico"];

$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$res = "SELECT cantidadpentiente FROM tarea WHERE tarea= '".$ID."' AND numero_op = '".$op."'";
$result = mysqli_query($mysqli, $res);

while ($row = mysqli_fetch_array($result)){ 
  $arreglo = array();
  $arreglo[]= $row["cantidadpentiente"];

}
echo json_encode($arreglo);

?>