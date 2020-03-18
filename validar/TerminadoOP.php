<?php
 $id = $_GET["numero"];
 $totales = 0;

 
 $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
 $res = "UPDATE produccion SET cantidadpentiente='". $totales."' WHERE numero_id = '".$id."'";
 
 $result = mysqli_query($mysqli, $res);
 
?>