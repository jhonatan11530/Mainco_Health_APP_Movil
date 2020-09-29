<?php
  require_once("ConexionSQL.php");
$mysqli = sqlsrv_connect(Server() , connectionInfo());
$sql_statement = "SELECT DISTINCT tarea FROM  proyecto.tarea ORDER BY tarea ASC";
$result = sqlsrv_query($mysqli, $sql_statement);
while($e=sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)){
$output[]=$e; 
}	
echo json_encode($output);
sqlsrv_close( $mysqli );
?>