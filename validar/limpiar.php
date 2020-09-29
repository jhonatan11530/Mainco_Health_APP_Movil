<?php
require_once("ConexionSQL.php");
$op = $_GET["id"];
$totales = 0;

$mysqli = sqlsrv_connect(Server() , connectionInfo());
$res = "UPDATE proyecto.tarea SET cantidadpentiente='". $totales."' WHERE  numero_op = '".$op."' ";
$result = sqlsrv_query($mysqli, $res);
sqlsrv_close( $mysqli );
?>