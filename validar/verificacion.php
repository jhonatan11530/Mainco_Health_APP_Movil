<?php
require_once("ConexionSQL.php");
error_reporting(0);

/*while (true) {
  usleep(1000000);
  
}*/
INICIO();
  function INICIO(){

  $mysqli = sqlsrv_connect(Server() , connectionInfo());
  $sql_statement = "SELECT cod_producto,cantidad FROM  proyecto.produccion WHERE  cod_producto IS NOT NULL AND numero_op IS NOT NULL";
  $result = sqlsrv_query($mysqli, $sql_statement);
  $op = array();
  $cant = array();
  while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
    $op[] = $row["cod_producto"];
    $cant[] = $row["cantidad"];
  }
echo $cantidadreal = $cant[0];
echo "<br>";
  for ($i=0; $i < count($op); $i++) { 
    $ops = $op[$i];
  $mysqli = sqlsrv_connect(Server() , connectionInfo());
  $sql_statement = "SELECT cantidadpentiente FROM  proyecto.tarea WHERE  numero_op='".$ops."' ";
  $result = sqlsrv_query($mysqli, $sql_statement);
  $ops = array();
  while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
    $ops[] = $row["cantidadpentiente"];
  }
  echo  $cantidadpendiente = $ops[0];
  echo "<br>";
  if($cantidadreal == $cantidadpendiente){

    echo "HOLA";
  }if ($cantidadpendiente < $cantidadreal) {
    echo "COMO ESTAS";
  } 
  
}

$cantidades = count($cant);
  if($cantidades > 0){

    for ($i=0; $i < count($op); $i++) { 
        $variable = $cant[$i];
        $ops = $op[$i];
        $mysqli = sqlsrv_connect(Server() , connectionInfo());
        $sql_statement = "UPDATE proyecto.tarea SET cantidadpentiente='".$variable."' WHERE numero_op='".$ops."'";
        $result = sqlsrv_query($mysqli, $sql_statement);
      }

  }elseif ($cantidades == 0) {
    for ($i=0; $i < count($op); $i++) { 
      $variable = $cant[$i];
      $ops = $op[$i];
      $mysqli = sqlsrv_connect(Server() , connectionInfo());
      $sql_statement = "UPDATE proyecto.tarea SET cantidadpentiente='".$variable."' WHERE numero_op='".$ops."'";
      $result = sqlsrv_query($mysqli, $sql_statement);
    }
  }

}

?>