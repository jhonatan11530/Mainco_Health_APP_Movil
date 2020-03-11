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
        $sql_statement = "SELECT * FROM operador";
      $resultado = mysqli_query($mysqli, $sql_statement);

      $verificador = array();

    while ($row = mysqli_fetch_array($resultado)){ 

        $verificador[0]=$row["llaves"];

      }
      foreach ($verificador as $key) {
      
         $llave = $key;
 
      }

 
 $sql_statement = "UPDATE  IGNORE operador SET cantidad='".$cantidad."',no_conforme='".$fallas."',cantidad_fallas='".$noconforme."'
 ,tarea='".$tarea."',final='".$final."',hora_final='".$finalh."' 
 WHERE id= '".$ID."' AND llaves = '".$llave."'";
  $result = mysqli_query($mysqli, $sql_statement);

  $workers=new ejecutar();
  $workers->run($ID,$op,$tarea,$llave);

class ejecutar{
    public function run($ID,$op,$tarea,$llave){
    
      $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
        $sql_statement = "SELECT * FROM operador WHERE id= '".$ID."' AND  llaves = '".$llave."'";
      $resultado = mysqli_query($mysqli, $sql_statement);

      $arreglito = array();

    while ($row = mysqli_fetch_array($resultado)){ 
  
      
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


      $sql_statementS = "SELECT cantidad FROM produccion WHERE numero_id= '".$op."'";
      $resultados = mysqli_query($mysqli, $sql_statementS);
      
    
    while ($row = mysqli_fetch_array($resultados)){ 
        $arreglit = array();
        $arreglit[0]=$row["cantidad"];
    
      }

    $cantAC=$arreglit[0];
    
    $cantno=$arreglito[8];
    


 // LA EFICACIA SE DEBE VERIFICAR
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
    
     $porcentaje = round($totales);
// FIN DE EFICACIA

if($porcentaje != null){
  $workers=new efica();
  $workers->run($porcentaje,$ID,$llave);
}



//-------TERMINANO

 $sql_statementt = "SELECT * FROM tarea WHERE tarea='".$tarea."' AND numero_op='".$op."'";
 $res = mysqli_query($mysqli, $sql_statementt);

 while ($row = mysqli_fetch_array($res)){ 
  $arreglit = array();
  $arreglit[0]=$row["extandar"];

}

   $timeST=$arreglit[0];


  $sql_statementst = "SELECT cantidad FROM produccion WHERE numero_id= '".$op."'";
$ress = mysqli_query($mysqli, $sql_statementst);

while ($row = mysqli_fetch_array($ress)){ 
 $arreglitt = array();
 $arreglitt[0]=$row["cantidad"];

}
$cantpendi =$arreglitt[0];

//EFICENCIA SE DEBE VERIFICAR
  $tiempoespera = ( $timeST * $cant) / 3600;

    $eficencia =  $tiempoespera / $fist * 100;

    $eficencias = round($eficencia); 
// FIN DE EFICENCIA

  if($eficencias != null){
    $efi=new eficencia();
    $efi->run($eficencias,$ID,$llave);
  }

}

}

class eficencia{
  public function run($eficencias,$ID,$llave){

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $sql_statement = "UPDATE  IGNORE operador SET eficencia='".$eficencias."' WHERE id= '".$ID."' AND llaves = '".$llave."'";
    $result = mysqli_query($mysqli, $sql_statement);

    
}
}
class efica{
  public function run($porcentaje,$ID,$llave){
   
    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$sql_statement = "UPDATE  IGNORE operador SET eficacia='".$porcentaje."' WHERE id= '".$ID."' AND llaves = '".$llave."'";
  $result = mysqli_query($mysqli, $sql_statement);

 
}
}
?>