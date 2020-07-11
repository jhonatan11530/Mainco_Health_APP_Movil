<?php
$ID = $_GET["id"];
$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$sql_statement = "SELECT * FROM operador  WHERE id= '.$ID.'";
$result = mysqli_query($mysqli, $sql_statement);

while ($row = mysqli_fetch_array($result)){ 
  $arreglito = array();
  $arreglito[0]= $row["id"];
  $arreglito[1]= $row["nombre"];
  $arreglito[2]= $row["tarea"];
  $arreglito[3]= $row["cantidad"];
  $arreglito[4]= $row["no_conforme"];
  $arreglito[5]= $row["cantidad_fallas"];
  $arreglito[6]= $row["inicial"];
  $arreglito[7]= $row["hora_inicial"];
  $arreglito[8]= $row["final"];
  $arreglito[9]= $row["hora_final"];

}
 $can =json_encode($arreglito);


$id = $arreglito[0];
$nom = $arreglito[1];

if($arreglito[2] != null && $arreglito[3] != null && $arreglito[4] != null && $arreglito[5] != null && $arreglito[6] != null && $arreglito[7] != null && $arreglito[8] != null && $arreglito[9] != null){
  $workers=new nuevo();
  $workers->run($id,$nom);
}
else{

}


?>
<?php

class nuevo{
  public function run($id,$nom){

    sleep(1);
    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $res = "INSERT INTO operador (id,nombre) VALUES ('".$id."','".$nom."')";
   $resultado = mysqli_query($mysqli, $res);
 
  }
}
?>