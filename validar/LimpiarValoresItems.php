<?php
 $op = $_GET["op"];
 $totales = 0;
 $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
 $res = "UPDATE tarea SET cantidadpentiente='". $totales."' WHERE numero_op = '".$op."' ";
 
 $result = mysqli_query($mysqli, $res);
 
?>