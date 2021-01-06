<?php
require_once("../ConexionSQL.php");

$connectionInfo  = sqlsrv_connect(Server() , connectionInfo());
if( $connectionInfo ) {
    $conecto[] = "Correcto.";
    echo json_encode($conecto);
}else{

    $fallo[] = "Incorrecto.";
    echo json_encode($fallo);

    die( print_r( sqlsrv_errors(), true));
}
?>