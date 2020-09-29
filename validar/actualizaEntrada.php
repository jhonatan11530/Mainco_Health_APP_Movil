<?php
require_once("ConexionSQL.php");
$ID = $_GET["id"];
$op = $_GET["op"];
$inicial = $_GET["Finicial"];
$inicialh = $_GET["Hinicial"];

$mysql = sqlsrv_connect(Server() , connectionInfo());
$sql_statements = "SELECT llaves FROM proyecto.operador WHERE id= '".$ID."'";
$llaves = sqlsrv_query($mysql, $sql_statements);
while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {
  $llave = $row["llaves"];
}
$mysql = sqlsrv_connect(Server() , connectionInfo());
 $sql_statement = "UPDATE proyecto.operador SET inicial='".$inicial."',hora_inicial='".$inicialh."',numero_op='".$op."' WHERE id= '".$ID."' AND llaves='".$llave."' ";
  $result = sqlsrv_query($mysql, $sql_statement);

  sqlsrv_close( $mysql );
?>
