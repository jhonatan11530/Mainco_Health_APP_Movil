<?php
require_once("ConexionSQL.php");
$mysqli = sqlsrv_connect(Server() , connectionInfo());
$sql_statement = "SELECT DISTINCT fallas FROM  proyecto.cantidaderror ";
$result = sqlsrv_query($mysqli, $sql_statement);
while($e=sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)){
$output[]=$e; 
}	

print_r(json_encode($output)); 
sqlsrv_close( $mysqli );



?>		