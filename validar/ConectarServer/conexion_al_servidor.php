<?php
  require_once("../ConexionSQL.php");

$conn = sqlsrv_connect( Server() , connectionInfo());
$output[] =  http_response_code();
if( $conn ) {
    echo json_encode($output);
}else{
     echo "Conexión no se pudo establecer.<br />";
     die( print_r( sqlsrv_errors(), true));
}

?>