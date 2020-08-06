<?php
require_once("ConexionSQL.php");

 $nombre = $_GET["nombre"];
 $apellido = $_GET["apellido"];
 $pass = $_GET["pass"];
 $cedula = $_GET["cedula"];
 $rol = $_GET["rol"];

 $dato = password_hash($pass, PASSWORD_DEFAULT);
 
 if($rol == "administrador"){
   $rol = $roladmin = 1;
   $cargo = "administrador";
 }else if($rol == "analista"){
    $rol =  $rolana = 2;
    $cargo = "analista";
 }else if ($rol == "operador"){
  $rol =  $rolop = 3;
  $cargo = "operador";
 }
$mysqli = sqlsrv_connect(Server() , connectionInfo());
$res = "INSERT INTO proyecto.usuarios (nomusuario,apeusuario,password,cedula,rol,cargo) VALUES ('".$nombre."','".$apellido."','".$dato."','".$cedula."','".$rol."','".$cargo."')";
$resultado = sqlsrv_query($mysqli, $res);

?>
