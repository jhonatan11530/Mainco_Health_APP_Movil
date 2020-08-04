<?php
require_once("ConexionSQL.php");
 $op = $_GET["op"];

  $mysqli = sqlsrv_connect(Server() , connectionInfo());
  $sql_statement = "SELECT DISTINCT cod_producto FROM proyecto.produccion WHERE numero_op = '".$op."' ";
  $result = sqlsrv_query($mysqli, $sql_statement);

  $output = array();
  while($e=sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)){
  $output[]=$e; 
  }	

  echo json_encode($output); 
?>

