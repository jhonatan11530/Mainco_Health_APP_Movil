<?php
require_once("ConexionSQL.php");
 $op = $_GET["op"];
 $cod = $_GET["codigo"];
 $tarea = $_GET["tarea"];
 $totales = $_GET["totales"];

 if($totales >= 0){
    $cero = 0;
 $mysqli = sqlsrv_connect(Server() , connectionInfo());
 $res = "UPDATE proyecto.tarea SET cantidadpentiente='". $totales."' WHERE  tarea !='".$tarea."' AND  numero_op = '".$cod."' AND cantidadpentiente !='". $cero."' ";
 $result = sqlsrv_query($mysqli, $res);

 $mysqli = sqlsrv_connect(Server() , connectionInfo());
 $res = "UPDATE proyecto.produccion SET autorizado='". $totales."' WHERE cod_producto= '".$op."' AND numero_op = '".$cod."' ";
 $result = sqlsrv_query($mysqli, $res);

}else if($totales < 0){
 
 $cero =0;

 $mysqli = sqlsrv_connect(Server() , connectionInfo());
 $res = "UPDATE proyecto.tarea SET cantidadpentiente='". $cero."' WHERE  tarea !='".$tarea."' AND  numero_op = '".$cod."' AND cantidadpentiente !='". $totales."' ";
 $result = sqlsrv_query($mysqli, $res);
}
sqlsrv_close( $mysqli );
?>