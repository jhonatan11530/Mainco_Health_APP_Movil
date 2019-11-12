<?php


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
$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$res = "INSERT INTO usuarios (nomusuario,apeusuario,password,cedula,rol,cargo) VALUES ('".$nombre."','".$apellido."','".$dato."','".$cedula."','".$rol."','".$cargo."')";
$resultado = mysqli_query($mysqli, $res);

?>
