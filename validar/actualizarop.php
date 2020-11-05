<?php
require_once("ConexionSQL.php");
 $op = $_GET["op"];
 $cod = $_GET["codigo"];
 $totales = $_GET["totales"];
 if($totales >= 0){

 $mysqli = sqlsrv_connect(Server() , connectionInfo());
 $res = "UPDATE proyecto.produccion SET autorizado='". $totales."' WHERE cod_producto= '".$cod."' AND numero_op = '".$op."' ";
 $result = sqlsrv_query($mysqli, $res);

}else if($totales < 0){

    $totales =0;
    
    $mysqli = sqlsrv_connect(Server() , connectionInfo());
    $res = "UPDATE proyecto.produccion SET autorizado='". $totales."' WHERE cod_producto= '".$cod."' AND numero_op = '".$op."' ";
    $result = sqlsrv_query($mysqli, $res);
}
sqlsrv_close( $mysqli );
?>