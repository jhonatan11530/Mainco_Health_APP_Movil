<?php
require_once("ConexionSQL.php");
$op = $_GET["op"];
 $cod = $_GET["cod"];
 $totales = $_GET["totales"];
 
 $mysqli = sqlsrv_connect(Server() , connectionInfo());
 $res = "UPDATE proyecto.tarea SET cantidadpentiente='". $totales."' WHERE numero_op = '".$op."' AND id = '".$cod."' ";
 $result = sqlsrv_query($mysqli, $res);
 sqlsrv_close( $mysqli );
?>