<?php
$ID = $_GET["id"];
$op = $_GET["op"];
$inicial = $_GET["Finicial"];
$inicialh = $_GET["Hinicial"];


$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
 $sql_statement = "UPDATE IGNORE operador SET inicial='".$inicial."',hora_inicial='".$inicialh."',numero_op='".$op."' WHERE id= '".$ID."'";
  $result = mysqli_query($mysqli, $sql_statement);


  $sql_statement = "SELECT * FROM operador WHERE id= '".$ID."'";
  $resultado = mysqli_query($mysqli, $sql_statement);
  
$arreglo = array();
while ($row = mysqli_fetch_array($resultado)){ 
    $arreglito = array();
  
    $arreglito[]=$row["id"];
    $arreglito[]=$row["nombre"];
    $arreglito[]=$row["tarea"];
    $arreglito[]=$row["inicial"];
    $arreglito[]=$row["hora_inicial"];
    $arreglito[]=$row["final"];
    $arreglito[]=$row["hora_final"];
    $arreglo[] = $arreglito;
  }
echo json_encode($arreglo);

$sql_statement = "SELECT * FROM produccion WHERE numero_op= '".$op."'";
$resultado = mysqli_query($mysqli, $sql_statement);


while ($row = mysqli_fetch_array($resultado)){ 
  $arreglito = array();

  $arreglito[0]=$row["cantidad"];


}
$pendiente = $arreglito[0];

if($pendiente != null){
  $workers=new eficencia();
  $workers->run($pendiente,$ID);
}

class eficencia {
  public function  run($pendiente,$ID){
    sleep(1);

  $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statement = "UPDATE IGNORE operador SET cantidadreal='".$pendiente."' WHERE id= '".$ID."'";
  $result = mysqli_query($mysqli, $sql_statement);
  }

 }
?>