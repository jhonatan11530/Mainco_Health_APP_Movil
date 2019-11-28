<?php
 $id = $_GET["op"];
 $pen = $_GET["canpen"];

 $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
 $res = "UPDATE tarea SET cantidadpentiente='". $pen."' WHERE numero_op = '".$id."'";
 
 $result = mysqli_query($mysqli, $res);
 
?>