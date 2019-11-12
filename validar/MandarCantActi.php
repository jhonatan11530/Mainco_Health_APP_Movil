
<?php
$ID = $_GET["id"];
$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statement = "SELECT cantidad FROM produccion WHERE numero_id= '".$ID."'";
  $resultado = mysqli_query($mysqli, $sql_statement);
  
$arreglo = array();
while ($row = mysqli_fetch_array($resultado)){ 
    $arreglito = array();
    $arreglito[0]=$row["cantidad"];


    $arreglo[] = $arreglito;
  }
  $cantAC=$arreglito[0];


  ?>
