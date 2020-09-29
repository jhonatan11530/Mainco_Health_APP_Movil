<?php
require_once("ConexionSQL.php");
$id = $_GET["id"];
$op = $_GET["op"];
$nombre = $_GET["nombre"];
$ip = $_GET["ip"];
$hora = $_GET["hora"];
$fecha = $_GET["fecha"];

$mysqli = sqlsrv_connect(Server() , connectionInfo());
$sql_statement = "INSERT INTO [proyecto].[proyecto].[error](code,op,nombre_dispositivo,dirrecion_ip,hora,fecha) VALUES('".$id."','".$op."','".$nombre."','".$ip."','".$hora ."','".$fecha."')";
$result = sqlsrv_query($mysqli, $sql_statement);
sqlsrv_close( $mysqli );

?>