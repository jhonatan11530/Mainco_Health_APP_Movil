<?php

error_reporting(0);
 $id = $_REQUEST["cedula"];
 $pass = $_REQUEST["pass"];



$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");

$res = "SELECT cedula FROM usuarios WHERE cedula = '".$id."'";


  $result = mysqli_query($mysqli, $res);

  if($id != null){

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");

    $res = "SELECT cedula FROM usuarios WHERE cedula = '".$id."'";
    
      $result = mysqli_query($mysqli, $res);

        while($row = mysqli_fetch_array($result)) {
          $arreglo2= array();
          
          $arreglo2[] = $row["cedula"];
            
        }
        
        if($arreglo2 != null){
            echo json_encode($arreglo2);


            $dato = password_hash($pass, PASSWORD_BCRYPT);
            
        $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
        $res = "UPDATE usuarios SET password = '".$dato."' WHERE cedula = '".$id."'";
        $result = mysqli_query($mysqli, $res); 
    }
        
    }
    if($arreglo2 == null){
        $arreglo = array();
        while($row = mysqli_fetch_array($result)) {
          
          $arreglo2= array();
          
          $arreglo2[] = $row["id"];
          $arreglo2[] = $row["nomusuario"];
          $arreglo2[] = $row["apeusuario"];
          $arreglo2[] = $row["password"];
          $arreglo2[] = $row["cedula"];
          $arreglo2[] = $row["rol"];
           $arreglo[] = $arreglo2;
            
        }
        echo json_encode($arreglo);
}


      /*  $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
        $res = "UPDATE usuarios SET password = '".$comp."' WHERE cedula = '".$id."'";
        $result = mysqli_query($mysqli, $res); */

    
?>
