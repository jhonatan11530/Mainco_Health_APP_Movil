<?php

$ID = $_GET["id"];
$op = $_GET["op"];
$inicial = $_GET["Finicial"];
$inicialh = $_GET["Hinicial"];

$mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$sql_statements = "SELECT llaves FROM operador WHERE id= '".$ID."'";
$llaves = mysqli_query($mysql, $sql_statements);
while($row = mysqli_fetch_array($llaves)) {
  $llave = $row["llaves"];
}

$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
 $sql_statement = "UPDATE operador SET inicial='".$inicial."',hora_inicial='".$inicialh."',numero_op='".$op."' WHERE id= '".$ID."' AND llaves='".$llave."' ";
  $result = mysqli_query($mysqli, $sql_statement);

?>
