<?php
error_reporting(0);
INICIO();
function INICIO(){
  $serverName = "srv2008";
  $connectionInfo = array("Database"=>"proyecto","UID"=>"proyecto","PWD"=>"12345","CharacterSet"=>"UTF-8");
  $mysqli = sqlsrv_connect($serverName ,$connectionInfo);
  $sql_statement = "SELECT cod_producto,cantidad FROM  proyecto.produccion WHERE  cod_producto IS NOT NULL AND numero_op IS NOT NULL";
  $result = sqlsrv_query($mysqli, $sql_statement);
  $op = array();
  $cant = array();
  while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
    $op[] = $row["cod_producto"];
    $cant[] = $row["cantidad"];
    echo " NUMERO OP : ".$row["cod_producto"]." CANTIDAD : ".$row["cantidad"];
    echo "<br>";
  }
  for ($i=0; $i < count($op); $i++) { 
    $variable = $cant[$i];
    $ops = $op[$i];
    $mysqli = sqlsrv_connect($serverName ,$connectionInfo);
    $sql_statement = "UPDATE proyecto.tarea SET cantidadpentiente='".$variable."' WHERE numero_op='".$ops."'";
    $result = sqlsrv_query($mysqli, $sql_statement);
  }


}
/*while (true) {
  usleep(1000000);
  
}*/
?>