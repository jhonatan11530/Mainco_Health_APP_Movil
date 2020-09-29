<?php
require_once("ConexionSQL.php");
error_reporting(0);
$op =$_GET["op"];
if(isset($op)){
$mysqli = sqlsrv_connect(Server() , connectionInfo());
$sql_statement = "SELECT SUM(cantidadpentiente) AS cantidadpentiente FROM proyecto.tarea WHERE numero_op='".$op."'";
$result = sqlsrv_query($mysqli, $sql_statement);
while($e=sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)){
$output[]=$e["cantidadpentiente"]; 
}	
$verifica = $output[0];

if($verifica > 0){
   $verdadero[] = TRUE;
 echo json_encode($verdadero);
}else{
   $falso[] = FALSE;
 echo json_encode($falso);
}
sqlsrv_close( $mysqli );
}  

?>
