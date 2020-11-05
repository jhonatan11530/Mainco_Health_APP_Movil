<?php
require_once("ConexionSQL.php");
$id = $_GET["id"];
$op = $_GET["op"];
$totales = 0;

$mysqli = sqlsrv_connect(Server() , connectionInfo());
$res = "UPDATE proyecto.tarea SET cantidadpentiente='". $totales."' WHERE  numero_op = '".$op."' AND id = '".$id."' ";
$result = sqlsrv_query($mysqli, $res);
sqlsrv_close( $mysqli );
?>