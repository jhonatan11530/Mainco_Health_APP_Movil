<?php
require_once("ConexionSQL.php");
error_reporting(0);
 $op = $_GET["op"];
 $cod = $_GET["cod"];
 $mysqli = sqlsrv_connect(Server() , connectionInfo());
 $res = "SELECT * FROM proyecto.produccion WHERE numero_op  = '".$op."' AND cod_producto ='".$cod."'";
 $result = sqlsrv_query($mysqli, $res);
 while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
   $arreglo2= array();
       

       $arreglo2[] = $row["autorizado"];
 

         
     }
     echo json_encode($arreglo2);

     sqlsrv_close( $mysqli );
?>