<?php
 $id = $_GET["numero"];

 $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
 $res = "SELECT cantidadpentiente FROM  tarea WHERE tarea = '".$id."'";
 
 $result = mysqli_query($mysqli, $res);
 
 
 while($row = mysqli_fetch_array($result)) {
   $arreglo2= array();
       

       $arreglo2[] = $row["cantidadpentiente"];
 

         
     }
     echo json_encode($arreglo2);
?>