<?php


 $id = $_REQUEST["cedula"];
 $pass = $_REQUEST["pass"];

 $dato = password_hash($pass, PASSWORD_BCRYPT);

$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");

$res = "SELECT cedula,password FROM usuarios WHERE cedula = '".$id."' AND password = '".$dato."'";

  $result = mysqli_query($mysqli, $res);

  if($id != null && $dato != null){

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");

    $res = "SELECT password FROM usuarios WHERE cedula = '".$id."'";
    
      $result = mysqli_query($mysqli, $res);
    
    
     
        while($row = mysqli_fetch_array($result)) {
          $arreglo2= array();
          
          $arreglo2[] = $row["password"];
            
        }

if (password_verify($pass, $arreglo2[0])) {
  
  
  $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");

$res = "SELECT * FROM usuarios ";

  $result = mysqli_query($mysqli, $res);


   	
    while($row = mysqli_fetch_array($result)) {
      $arreglo = array();
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
 
} else {
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

  }


    
?>
