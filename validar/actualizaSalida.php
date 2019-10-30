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
//  $sql_statement = "UPDATE IGNORE operador SET cantidad='".$cantidad."',no_conforme='".$fallas."',cantidad_fallas='".$noconforme."',tarea='".$tarea."',final='".$final."',hora_final='".$finalh."' WHERE id= '".$ID."'";
  $result = mysqli_query($mysqli, $sql_statement);


  $workers=new ejecutar();
  $workers->run($ID,$op);

 $mysqli->close();

?>
<?php
class ejecutar{
    public function run($ID,$op){
      $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $sql_statement = "SELECT * FROM operador WHERE id= '".$ID."'";
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
    $fis;
    
    $dates = new DateTime($arreglito[5]);
    echo $Hini = $dates->format('H:i');
    echo "<br/>";
      $datest = new DateTime($arreglito[7]);
    echo $Hfini = $datest->format('H:i');

    echo "<br/>";
     $dteDiff  = $dates->diff($datest);
     print $dteDiff->format("%H:%I");

     echo "<br/>";
     echo  $dates->modify('-30 second');
     $divicion = $cant - $cantno ;
    $totales = $divicion / $cantAC *100;
     $porcentaje = round($totales);


 $workers=new efica();
 $workers->run($porcentaje,$ID);


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

   $eficencias = $tiempoespera  / $fis * 100;
 //  

 $efi=new eficencia();
 $efi->run($eficencias,$ID);
}
}

?>

<?php
class eficencia{
  public function run($eficencias,$ID){
    sleep(2);
    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $sql_statement = "UPDATE IGNORE operador SET eficencia='".$eficencias."' WHERE id= '".$ID."'";
    $result = mysqli_query($mysqli, $sql_statement);
}
}
  ?>

<?php
class efica{
  public function run($porcentaje,$ID){
    sleep(2);
    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$sql_statement = "UPDATE IGNORE operador SET eficacia='".$porcentaje."' WHERE id= '".$ID."'";
  $result = mysqli_query($mysqli, $sql_statement);
}
}
  ?>