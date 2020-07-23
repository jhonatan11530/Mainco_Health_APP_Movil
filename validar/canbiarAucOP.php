<?php
require_once("ConexionSQL.php");
    $op = $_GET["op"];
    $cantidad = $_GET["cantidad"];
    $item = $_GET["item"];
    
    $mysqli = sqlsrv_connect(Server() , connectionInfo());
    $search = "UPDATE proyecto.produccion SET cantidad='".$cantidad."' WHERE numero_op='".$op."' AND cod_producto= '".$item."'";
    $res = sqlsrv_query($mysqli, $search);

    ?>