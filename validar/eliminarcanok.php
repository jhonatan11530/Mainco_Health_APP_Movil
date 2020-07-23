<?php
require_once("ConexionSQL.php");
$tarea = $_GET["tarea"];
$numero = $_GET["numero"];
$mysqli = sqlsrv_connect(Server() , connectionInfo());
$sql_statement = "SELECT  * FROM  proyecto.tarea WHERE tarea= '".$tarea."' AND numero_op='".$numero."' ";
$result = sqlsrv_query($mysqli, $sql_statement);

$arreglo = array();
while ($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)){ 

  $arreglito[]= $row["cantidadpentiente"];

}
echo json_encode($arreglito);

?>