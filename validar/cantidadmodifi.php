<?php
 $id = $_GET["numero"];
 $totales = $_GET["totales"];

 
 $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
 $res = "UPDATE tarea SET cantidadpentiente='". $totales."' WHERE tarea = '".$id."'";
 
 $result = mysqli_query($mysqli, $res);
 
?>