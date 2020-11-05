<?php
require_once("ConexionSQL.php");
$op = $_GET["op"];
 $cod = $_GET["cod"];

  $mysqli = sqlsrv_connect(Server() , connectionInfo());
  $sql_statement = "SELECT DISTINCT tarea FROM proyecto.tarea WHERE numero_op = '".$op."' AND id = '".$cod."' ORDER BY tarea ASC";
  $result = sqlsrv_query($mysqli, $sql_statement);

  $output = array();
  while($e=sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)){
  $output[]=$e; 
  }	

  echo json_encode($output); 
  sqlsrv_close( $mysqli );
?>