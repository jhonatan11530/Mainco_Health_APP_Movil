<?php
require_once("ConexionSQL.php");
 $op = $_GET["op"];
 $totales = $_GET["totales"];
 
 $mysqli = sqlsrv_connect(Server() , connectionInfo());
 $res = "UPDATE proyecto.tarea SET cantidadpentiente='". $totales."' WHERE numero_op = '".$op."' ";
 $result = sqlsrv_query($mysqli, $res);
 
?>