<?php
$tarea = $_GET["tarea"];
$numero = $_GET["numero"];
$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$sql_statement = "SELECT  * FROM  tarea WHERE tarea= '".$tarea."' AND numero_op='".$numero."' ";
$result = mysqli_query($mysqli, $sql_statement);

$arreglo = array();
while ($row = mysqli_fetch_array($result)){ 

  $arreglito[]= $row["cantidadpentiente"];

}
echo json_encode($arreglito);

?>