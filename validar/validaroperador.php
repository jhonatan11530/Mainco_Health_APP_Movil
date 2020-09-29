<?php
require_once("ConexionSQL.php");
error_reporting(0);
$ID = $_GET["id"];
$mysqli = sqlsrv_connect(Server() , connectionInfo());
  $sql_statement = "SELECT * FROM proyecto.operador WHERE id= '".$ID."'";
  $result = sqlsrv_query($mysqli, $sql_statement);
while ($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)){ 
  $arreglito = array();
    $arreglito[] = $row["numero_op"];

  }

if($arreglito != null){
  echo json_encode($arreglito);
  }
if($arreglito == null){

  }
  sqlsrv_close( $mysqli );
?>