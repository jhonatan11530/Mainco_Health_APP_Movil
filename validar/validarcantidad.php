<?php

$id = $_GET["numero"];
$ID = $_GET["id"];

$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$res = "SELECT * FROM  produccion WHERE numero_id = '".$id."'";
 
$result = mysqli_query($mysqli, $res);

while($row = mysqli_fetch_array($result)) {
  $arreglo2= array();
      

      $arreglo2[] = $row["cantidad"];

        
    }
    echo json_encode($arreglo2);
/*
   
$sql_statement = "SELECT  * FROM  tarea WHERE tarea= '".$ID."'";
$result = mysqli_query($mysqli, $sql_statement);

$arreglo = array();
while ($row = mysqli_fetch_array($result)){ 

  $arreglito[]= $row["cantidadpentiente"];

}
echo json_encode($arreglito);
*/
?>