<?php
require_once("ConexionSQL.php");
error_reporting(0);
 $id = $_REQUEST["cedula"];
 $pass = $_REQUEST["pass"];

 $dato = password_hash($pass, PASSWORD_BCRYPT);

  $mysqli = sqlsrv_connect(Server() , connectionInfo());
  $res = "SELECT cedula,password FROM proyecto.usuarios WHERE cedula = '".$id."' AND password = '".$dato."'";
  $result = sqlsrv_query($mysqli, $res);

  if($id != null && $dato != null){

      $mysqli = sqlsrv_connect(Server() , connectionInfo());
      $res = "SELECT password FROM proyecto.usuarios WHERE cedula = '".$id."'";
      $result = sqlsrv_query($mysqli, $res);
    
    
     
        while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
          $arreglo2= array();
          
          $arreglo2[] = $row["password"];
            
        }

if (password_verify($pass, $arreglo2[0])) {
  

  $mysqli = sqlsrv_connect(Server() , connectionInfo());
  $res = "SELECT * FROM proyecto.usuarios ";
  $result = sqlsrv_query($mysqli, $res);


   	
    while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
 
      $arreglo2= array();
      
      $arreglo2[] = $row["id"];
      $arreglo2[] = $row["nomusuario"];
      $arreglo2[] = $row["apeusuario"];
      $arreglo2[] = $row["password"];
      $arreglo2[] = $row["cedula"];
      $arreglo2[] = $row["rol"];
        
    }
    echo json_encode($arreglo2); 
 
}
sqlsrv_close( $mysqli );

  }


    
?>
