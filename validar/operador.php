<?php
error_reporting(0);
$ID = $_GET["id"];
$mysqli = mysqli_connect("localhost", "root", "", "proyecto");
  $sql_statement = "SELECT * FROM operador WHERE id= '".$ID."'";
  $result = mysqli_query($mysqli, $sql_statement);
  
  
while ($row = mysqli_fetch_array($result)){ 
  $arreglito = array();
    $arreglito[] = $row["nombre"];

  }

if($arreglito != null){
  echo json_encode($arreglito);
  }
if($arreglito == null){

  }

?>