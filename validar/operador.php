<?php
$ID = $_GET["id"];


$mysqli = mysqli_connect("localhost", "root", "", "proyecto");
  $sql_statement = "SELECT * FROM operador WHERE id= '".$ID."'";
  $result = mysqli_query($mysqli, $sql_statement);
  
  $arreglito = array();
while ($row = mysqli_fetch_array($result)){ 
  
    $arreglito[]="OPERADOR : ".$row["nombre"];

  }
echo json_encode($arreglito);


?>