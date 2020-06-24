<?php
 $op = $_GET["op"];
 $tarea = $_GET["tarea"];
 $totales = $_GET["totales"];

 
 $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
 $res = "UPDATE tarea SET cantidadpentiente='". $totales."' WHERE tarea = '".$tarea."' AND numero_op = '".$op."' ";
 
 $result = mysqli_query($mysqli, $res);
 
?>