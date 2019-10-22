<?php
$con = 0;
$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$sql_statement = "SELECT  * FROM  tarea WHERE 	cantidadpentiente = '".$con."'";
$result = mysqli_query($mysqli, $sql_statement);

$arreglo = array();
while ($row = mysqli_fetch_array($result)){ 

  $arreglito[]= $row["tarea"];

}
echo json_encode($arreglito);

?>