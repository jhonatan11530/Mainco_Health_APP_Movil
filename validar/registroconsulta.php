<?php

 $id = $_GET["id"];


 
$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$res = "SELECT * FROM  operador WHERE id = '".$id."'";

$result = mysqli_query($mysqli, $res);


$arreglo = array();
while($row = mysqli_fetch_array($result)) {
  $arreglo2= array();
      
      $arreglo2[] = $row["id"];
      $arreglo2[] = $row["numero_op"];
      $arreglo2[] = $row["nombre"];
      $arreglo2[] = $row["tarea"];
      $arreglo2[] = $row["cantidad"];
      $arreglo2[] = $row["inicial"];
      $arreglo2[] = $row["hora_inicial"];
      $arreglo2[] = $row["final"];
      $arreglo2[] = $row["hora_final"];
      $arreglo2[] = $row["tiempo_paro"];
      $arreglo2[] = $row["motivo_paro"];
      
       $arreglo[] = $arreglo2;
        
    }
    echo json_encode($arreglo);



?>
