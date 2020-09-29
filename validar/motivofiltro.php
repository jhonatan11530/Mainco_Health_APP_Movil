<?php
require_once("ConexionSQL.php");
$datos = $_REQUEST["motivo"];
$mysqli = sqlsrv_connect(Server() , connectionInfo());
$sql_statement = "SELECT paro FROM  proyecto.motivo WHERE id= '".$datos."'";
$result = sqlsrv_query($mysqli, $sql_statement);
while($e=sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)){
$output[]=$e; 
}	

print_r(json_encode($output)); 
sqlsrv_close( $mysqli );


?>