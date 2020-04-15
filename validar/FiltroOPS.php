<?php
$op = $_GET["op"];

  $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statement = "SELECT DISTINCT cod_producto FROM  produccion WHERE numero_id = '".$op."' ";
  $result = mysqli_query($mysqli, $sql_statement);

  $output = array();
  $result=mysqli_query($mysqli, $sql_statement);
  while($e=mysqli_fetch_assoc($result)){
  $output[]=$e; 
  }	

  print(json_encode($output)); 
  $mysqli->close();	
?>