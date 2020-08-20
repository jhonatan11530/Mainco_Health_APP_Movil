<?php
require_once("ConexionSQL.php");
 $op = $_GET["op"];
 $mysqli = sqlsrv_connect(Server() , connectionInfo());
 $res = "SELECT autorizado FROM  proyecto.produccion WHERE cod_producto = '".$op."'";
 
 $result = sqlsrv_query($mysqli, $res);
 
 
 while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
   $arreglo2= array();
       

       $arreglo2[] = $row["autorizado"];
 

         
     }
     echo json_encode($arreglo2);
?>