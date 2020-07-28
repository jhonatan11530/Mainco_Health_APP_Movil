<?php
require_once("ConexionSQL.php");
$OP = $_GET["op"];
$ID = $_GET["id"];
$paro = $_GET["paro"];
$motivo = $_GET["motivo"];
$fecha = $_GET["fecha"];
$hora = $_GET["hora"];
$tarea = $_GET["tarea"];


if(isset($OP,$ID,$paro,$motivo,$fecha,$hora,$tarea)){

$mysqli = sqlsrv_connect(Server() , connectionInfo());
$sql_statement = "INSERT INTO proyecto.motivo_paro (numero_op,id,tarea,tiempo_descanso,motivo_descanso,fecha,hora) VALUES ('".$OP."','".$ID."','".$tarea."','".$paro."','".$motivo."','".$fecha."','".$hora."')";
$result = sqlsrv_query($mysqli, $sql_statement);

}

?>