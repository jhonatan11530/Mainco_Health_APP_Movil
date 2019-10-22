<?php
$ID = $_GET["id"];


  $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statement = "SELECT nombre FROM operador WHERE id= '.$ID.'";
  $result = mysqli_query($mysqli, $sql_statement);
  
  $arreglo = array();
while ($row = mysqli_fetch_array($result)){ 
    $arreglito = array();
    $arreglito[]="OPERADOR : ".$row["nombre"];

  }
echo json_encode($arreglito);

/*
$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$sql_statement = "SELECT * FROM operador WHERE id= '.$ID.'";
$result = mysqli_query($mysqli, $sql_statement);

while ($row = mysqli_fetch_array($result)){ 
  $arreglito = array();
  $arreglito[0]= $row["id"];
  $arreglito[1]= $row["nombre"];
  $arreglito[2]= $row["numero_op"];
  $arreglito[3]= $row["tarea"];
  $arreglito[4]= $row["cantidad"];
  $arreglito[5]= $row["no_conforme"];
  $arreglito[6]= $row["cantidad_fallas"];
  $arreglito[7]= $row["inicial"];
  $arreglito[8]= $row["hora_inicial"];
  $arreglito[9]= $row["final"];
  $arreglito[10]= $row["hora_final"];

}
echo json_encode($arreglito);
echo $arreglito[5];

if($arreglito[0] != "null" && $arreglito[1] != "null" && $arreglito[2] != "null" && $arreglito[3] != "null" && $arreglito[4] != "null" && $arreglito[5] != "null" && $arreglito[6] != "null" && $arreglito[7] != "null" && $arreglito[8] != "null" && $arreglito[9] != "null"&& $arreglito[10] != "null"){
 
  echo "asdsadsa";

  $res = "INSERT INTO operador (id,nombre) VALUES ('".$arreglito[0]."','".$arreglito[1]."')";
  $resultado = mysqli_query($mysqli, $res);

}

if($arreglito[0] == "null" && $arreglito[1] == "null" && $arreglito[2] == "null" && $arreglito[3] == "null" && $arreglito[4] == "null" && $arreglito[5] == "null" && $arreglito[6] == "null" && $arreglito[7] == "null" && $arreglito[8] == "null" && $arreglito[9] == "null" && $arreglito[10] != "null"){

}
*/
?>