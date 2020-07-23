<?php
require_once("ConexionSQL.php");
$id = $_GET["numero"];

    $mysqli = sqlsrv_connect(Server() , connectionInfo());
    $res = "SELECT * FROM  proyecto.produccion WHERE numero_op = '".$id."'";
    $result = sqlsrv_query($mysqli, $res);
    
    while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
       
      $arreglo2= array();
    
        $arreglo2[] = $row["cantidad"];
    
    
    
            
        }
        echo json_encode($arreglo2);

        
?>