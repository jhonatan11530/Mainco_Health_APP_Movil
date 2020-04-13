<?php
$hora = new DateTime("now", new DateTimeZone('Asia/Jayapura'));
echo $horas = $hora->format('h:i:s');
echo "<br>";
$horad = new DateTime("now", new DateTimeZone('America/Los_Angeles'));
echo $horat = $horad->format('h:i:s');


function RestarHoras($horas,$horat)
{
    $f1 = new DateTime($horas);
    $f2 = new DateTime($horat);
    $d = $f1->diff($f2);
    return $d->format('%H:%I:%S');
}
echo "<br>";
echo RestarHoras($horas,$horat); //Devolvera 04:00:00


$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
 $res = "SELECT * FROM operador ";
 
 $result = mysqli_query($mysqli, $res);
 
 
 while($row = mysqli_fetch_array($result)) {
   $arreglo2= array();
       

       $arreglo2[] = $row["cantidadpentiente"];
 

         
     }
     echo json_encode($arreglo2);
?>