<?php
require_once("../ConexionSQL.php");
error_reporting(0);
 $id = $_REQUEST["id"];

      $mysqli = sqlsrv_connect(Server() , connectionInfo());
      $res = "SELECT inicial FROM proyecto.operador WHERE id = '".$id."'";
      $result = sqlsrv_query($mysqli, $res);
    
        while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
          $arreglo2= array();
          
          $arreglo2[] = $row["inicial"];
            
        }

echo json_encode($arreglo2); 
sqlsrv_close( $mysqli );



    
?>