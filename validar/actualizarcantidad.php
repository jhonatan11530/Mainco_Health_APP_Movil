<?php
 $id = $_GET["numero"];
 $op = $_GET["id"];
 $pen = $_GET["canpen"];
 $final = $_GET["Ffinal"];
 $finalh = $_GET["Hfinal"];
 $tarea = $_GET["tarea"];
 $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
 $res = "UPDATE operador SET cantidad='". $pen."',final='".$final."',hora_final='".$finalh."',tarea='".$tarea."' WHERE numero_op = '".$id."'AND id= '".$op."'";
 
 $result = mysqli_query($mysqli, $res);
 
?>