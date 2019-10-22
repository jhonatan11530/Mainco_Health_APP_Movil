<?php
 $id = $_GET["numero"];
 $op = $_GET["id"];
 $pen = $_GET["canpen"];

 $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
 $res = "UPDATE operador SET cantidad='". $pen."' WHERE numero_op = '".$id."'AND id= '".$op."'";
 
 $result = mysqli_query($mysqli, $res);
 
?>