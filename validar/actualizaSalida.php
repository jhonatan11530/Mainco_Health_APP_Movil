<?php
$ID = $_GET["id"];
$op = $_GET["op"];
$cantidad = $_GET["cantidad"];
$tarea = $_GET["tarea"];
$final = $_GET["Ffinal"];
$finalh = $_GET["Hfinal"];
$fallas = $_GET["fallas"];
$noconforme = $_GET["cantidaderror"]; 


  
$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$sql_statement = "SELECT cantidad FROM operador WHERE id= '".$ID."'";
$result = mysqli_query($mysqli, $sql_statement);

while ($row = mysqli_fetch_array($result)){ 
  $arreglito = array();
  $arreglito[0]=$row["cantidad"];
}

 json_encode($arreglito[0]);
  $cantid=$arreglito[0];

if($cantid == 0){

  $sql_statement = "UPDATE  operador SET cantidad='".$cantidad."',no_conforme='".$fallas."',cantidad_fallas='".$noconforme."',tarea='".$tarea."',final='".$final."',hora_final='".$finalh."' WHERE id= '".$ID."'";
  $result = mysqli_query($mysqli, $sql_statement);

}else{
  $workers=new ejecutar();
  $workers->run($ID,$op);
}
 

?>

<?php

class ejecutar{
    public function run($ID,$op){
    
      $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
        $sql_statement = "SELECT * FROM operador WHERE numero_op= '".$op."'";
      $resultado = mysqli_query($mysqli, $sql_statement);

    while ($row = mysqli_fetch_array($resultado)){ 
    
        $arreglito = array();
      
        $arreglito[0]=$row["id"];
        $arreglito[1]=$row["nombre"];
        $arreglito[2]=$row["cantidad"];
        $arreglito[3]=$row["tarea"];
        $arreglito[4]=$row["inicial"];
        $arreglito[5]=$row["hora_inicial"];
        $arreglito[6]=$row["final"];
        $arreglito[7]=$row["hora_final"];
        $arreglito[8]=$row["no_conforme"];
    

      }
      
      $cant=$arreglito[2];
    
      $sql_statement = "SELECT cantidad FROM produccion WHERE numero_id= '".$op."'";
      $resultado = mysqli_query($mysqli, $sql_statement);
      
    
    while ($row = mysqli_fetch_array($resultado)){ 
        $arreglit = array();
        $arreglit[0]=$row["cantidad"];
    
      }
    $cantAC=$arreglit[0];
    
    $cantno=$arreglito[8];
    
    
    $dates = new DateTime($arreglito[5]);
     $Hini = $dates->format('H:i');

    $datest = new DateTime($arreglito[7]);
    $Hfini = $datest->format('H:i');

     $horai= substr($Hini ,0,2);

   $horaf =substr($Hfini,0,2);

    $fis =  $horaf / $horai;
     $fist = round($fis);

     $divicion = $cant - $cantno ;
    $totales = $divicion / $cantAC *100;
    // LA EFICACIA YA ESTA TERMINADA
     $porcentaje = round($totales);

if($porcentaje != null){
  $workers=new efica();
  $workers->run($porcentaje,$ID);
}



//-------TERMINANO

 $sql_statement = "SELECT * FROM tarea WHERE numero_op= '".$op."'";
 $res = mysqli_query($mysqli, $sql_statement);

 while ($row = mysqli_fetch_array($res)){ 
  $arreglit = array();
  $arreglit[0]=$row["extandar"];

}

$timeST=$arreglit[0];


  $sql_statement = "SELECT cantidad FROM produccion WHERE numero_id= '".$op."'";
$res = mysqli_query($mysqli, $sql_statement);

while ($row = mysqli_fetch_array($res)){ 
 $arreglitt = array();
 $arreglitt[0]=$row["cantidad"];

}
$cantpendi =$arreglitt[0];




$sql_statement = "SELECT * FROM operador WHERE id= '".$ID."'";
 $res = mysqli_query($mysqli, $sql_statement);

 while ($row = mysqli_fetch_array($res)){ 
  $arreglits = array();
  $arreglits[0]=$row["hora_final"];


}
 $timeFH = $arreglits[0];

$date = new DateTime($timeST);
 $ST = $date->format('i');

  $segundos =$cantpendi * $ST;

 $minute = $segundos / 60;

   $minutos = round($minute); 

   $hours = $minutos / 60;
//--hora extandar
   $hora = round($hours); 

 //--hora extandar

   $tiempoespera = ($cant  * $ST) / 3600;

     $eficencia =  $tiempoespera / $fist * 10;
  echo  $eficencias = round($eficencia); 

  if($eficencias != null){
    $efi=new eficencia();
    $efi->run($eficencias,$ID);
  }

}

}
$mysqli->close();
?>

<?php
class eficencia{
  public function run($eficencias,$ID){
    ;
    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $sql_statement = "UPDATE IGNORE operador SET eficencia='".$eficencias."' WHERE id= '".$ID."'";
    $result = mysqli_query($mysqli, $sql_statement);

    $mysqli->close();
}
}
  ?>


<?php
class efica{
  public function run($porcentaje,$ID){
    
    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$sql_statement = "UPDATE IGNORE operador SET eficacia='".$porcentaje."' WHERE id= '".$ID."'";
  $result = mysqli_query($mysqli, $sql_statement);

  $mysqli->close();
}
}

  ?>