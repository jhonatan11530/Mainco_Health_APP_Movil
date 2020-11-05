<?php
require_once("ConexionSQL.php");
 $cod = $_GET["cod"];
 $op = $_GET["op"];
 $mysqli = sqlsrv_connect(Server() , connectionInfo());
 $res = "SELECT autorizado FROM  proyecto.produccion WHERE cod_producto = '".$cod."' AND numero_op='".$op."' ";
 $result = sqlsrv_query($mysqli, $res);
 
 
 while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
   $arreglo2= array();
       

       $arreglo2[] = $row["autorizado"];
 

         
     }
     echo json_encode($arreglo2);
     sqlsrv_close( $mysqli );
?>