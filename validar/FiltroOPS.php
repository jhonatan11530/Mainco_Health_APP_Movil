<?php
require_once("ConexionSQL.php");
 $op = $_GET["op"];

 // SELECT DISTINCT cod_producto FROM proyecto.produccion WHERE numero_op = '".$op."'
  $mysqli = sqlsrv_connect(Server() , connectionInfo());
  $sql_statement = "If Exists(SELECT DISTINCT cod_producto FROM proyecto.produccion WHERE numero_op = '".$op."') Begin
  SELECT DISTINCT cod_producto FROM proyecto.produccion  WHERE numero_op = '".$op."'
End
Else Begin
SELECT 'NO EXISTE UN ITEM PORFAVOR VERIFICAR' as cod_producto
End  ";
  $result = sqlsrv_query($mysqli, $sql_statement);

  $output = array();
  while($e=sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)){
  $output[]=$e; 
  
  }	
  $variable = json_encode($output); 
  if($variable!=null){
    echo $variable;
  }if($variable==null){
    $arr = array('cod_producto' => 'null');

    echo json_encode($arr);
  }
  
  sqlsrv_close( $mysqli );

?>

