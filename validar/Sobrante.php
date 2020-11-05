<?php
require_once("ConexionSQL.php");
error_reporting(0);
 $cod = $_GET["cod"];
 $orden = $_GET["op"];
 $tarea = $_GET["tarea"];
 $mysqli = sqlsrv_connect(Server() , connectionInfo());
 $res = "SELECT * FROM proyecto.tarea WHERE id  = '".$cod."' AND tarea ='".$tarea."' AND numero_op ='".$orden."'";
 $result = sqlsrv_query($mysqli, $res);
 while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {

       

       $arreglo2[] = $row["cantidadpentiente"];
 

         
     }
     echo json_encode($arreglo2);

     sqlsrv_close( $mysqli );
?>