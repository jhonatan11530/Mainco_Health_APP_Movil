<?php
$OP = $_GET["op"];
$ID = $_GET["id"];
$paro = $_GET["paro"];
$motivo = $_GET["motivo"];
$fecha = $_GET["fecha"];
$hora = $_GET["hora"];



if(isset($_GET['op']) && isset($_GET['id']) && isset($_GET['paro']) && isset($_GET['motivo']) && isset($_GET['fecha'])&& isset($_GET['hora'])){

$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$sql_statement = "INSERT INTO motivo_paro (numero_op,id,tiempo_descanso,motivo_descanso,fecha,hora) VALUES ('".$OP."','".$ID."','".$paro."','".$motivo."','".$fecha."','".$hora."')";
$result = mysqli_query($mysqli, $sql_statement);

}

?>