<?php
require_once("ConexionSQL.php");
$ID = $_GET["id"];
$mysqli = sqlsrv_connect(Server() , connectionInfo());
$sql_statement = "SELECT * FROM proyecto.operador  WHERE id= '.$ID.'";
$result = sqlsrv_query($mysqli, $sql_statement);

while ($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)){ 
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
    $mysqli = sqlsrv_connect(Server() , connectionInfo());
    $res = "INSERT INTO proyecto.operador (id,nombre) VALUES ('".$id."','".$nom."')";
   $resultado = sqlsrv_query($mysqli, $res);
 
  }
}
?>