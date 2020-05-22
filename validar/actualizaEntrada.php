<?php

$ID = $_GET["id"];
$op = $_GET["op"];
$inicial = $_GET["Finicial"];
$inicialh = $_GET["Hinicial"];


$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
 $sql_statement = "UPDATE ignore operador SET inicial='".$inicial."',hora_inicial='".$inicialh."',numero_op='".$op."' WHERE id= '".$ID."' ";
  $result = mysqli_query($mysqli, $sql_statement);

?>
