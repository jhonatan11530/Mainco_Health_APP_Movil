<?php
require_once("ConexionSQL.php");
error_reporting(0);
 $id = $_REQUEST["cedula"];
 $pass = $_REQUEST["pass"];



$mysqli = sqlsrv_connect(Server() , connectionInfo());
$res = "SELECT cedula FROM proyecto.usuarios WHERE cedula = '".$id."'";
$result = sqlsrv_query($mysqli, $res);

  if($id != null){

    $mysqli = sqlsrv_connect(Server() , connectionInfo());
    $res = "SELECT cedula FROM proyecto.usuarios WHERE cedula = '".$id."'";
      $result = sqlsrv_query($mysqli, $res);

        while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
          $arreglo2= array();
          
          $arreglo2[] = $row["cedula"];
            
        }
        
        if($arreglo2 != null){
            echo json_encode($arreglo2);


            $dato = password_hash($pass, PASSWORD_BCRYPT);
        $mysqli = sqlsrv_connect(Server() , connectionInfo());
        $res = "UPDATE proyecto.usuarios SET password = '".$dato."' WHERE cedula = '".$id."'";
        $result = sqlsrv_query($mysqli, $res); 
    }
        
    }
    if($arreglo2 == null){
        $arreglo = array();
        while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
          
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
sqlsrv_close( $mysqli );
?>
