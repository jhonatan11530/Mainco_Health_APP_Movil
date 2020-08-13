<?php
require_once("ConexionSQL.php");
$limpio = $_REQUEST["id"];
$cero = 0;
$mysqli = sqlsrv_connect(Server() , connectionInfo());
  $sql_statement = "UPDATE proyecto.tarea SET cantidadpentiente= '".$cero."' WHERE  numero_op = '".$numeroops."' ";
  $resultado = sqlsrv_query($mysqli, $sql_statement);
?>