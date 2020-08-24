<?php
require_once("ConexionSQL.php");
$op = $_GET["numero"];
$tarea = $_GET["tarea"]; 

$mysqli = sqlsrv_connect(Server() , connectionInfo());
$res = "SELECT cantidadpentiente FROM [proyecto].[proyecto].[tarea] WHERE numero_op='".$op."' AND tarea='".$tarea."' ";
$result = sqlsrv_query($mysqli, $res);


while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
  $arreglo2= array();
      

      $arreglo2[] = $row["cantidadpentiente"];


        
    }
    echo json_encode($arreglo2);

?>