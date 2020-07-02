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
   $workers=new nuevo();
  $workers->run($nom);
  }
}
class Consolidado{
  public function CONSOLIDADO($nom){

    
$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$sql_statement = "SELECT DISTINCT id,numero_op,inicial FROM operador WHERE nombre = '".$nom."'";
$resultado = mysqli_query($mysqli, $sql_statement);
while($row = mysqli_fetch_array($resultado)) {
   $ID = $row["id"];
   $op = $row["numero_op"];
   $inicial = $row["inicial"];
} 
/*--------------------------------------------------------------*/

$mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statements = "SELECT cod_producto  FROM produccion WHERE `numero_op`='".$op."'";
  $llaves = mysqli_query($mysql, $sql_statements);
  while($row = mysqli_fetch_array($llaves)) {
    
     $cod_prodcuto =  $row["cod_producto"];
    
  } 
  $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statements = "SELECT ROUND(`extandar`*3600) AS `extandar` FROM `tarea` WHERE `numero_op`='".$cod_prodcuto."'";
  $llaves = mysqli_query($mysql, $sql_statements);
  $extand = array();
  while($row = mysqli_fetch_array($llaves)) {
    
    $extand[] = $row["extandar"];
    
  } 

      $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $sql_statements = "SELECT cantidad FROM operador WHERE id='".$ID."' AND numero_op = '".$op."'";
      $consultas = mysqli_query($mysql, $sql_statements);
      $cant = array();
      while($row = mysqli_fetch_array($consultas)) {
       
         $cant[] = $row["cantidad"];
        
      }

      $arrayResultados  = array();
      $suma = 0;

      for ($i = 0; $i < count($extand); $i++) {
        for ($i = 0; $i < count($cant); $i++) {
           $arrayResultados[$i] = $extand[$i] * $cant[$i];
        }
      }
      foreach ($arrayResultados as $numero) {
        $suma += $numero;
    }
    //TOTAL SUMADO TIME SEGUNDOS CONSOLIDADO
     $totalSegund = round($suma / 60); 
   
      $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $sql_statements = "SELECT TIMEDIFF(hora_final,hora_inicial) AS total FROM operador WHERE id='".$ID."' ";
      $consutatime = mysqli_query($mysql, $sql_statements);
      $timerestan = array();
      while($row = mysqli_fetch_array($consutatime)) {
       
         $timerestan[] = $row["total"];
        
      }
      $total = array();
      for ($i = 0; $i < count($timerestan); $i++) {
        list($horas, $minutos, $segundos) = explode(':', $timerestan[$i]);
        $total[] = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;
      }
       $tiempoPorduc = array_sum($total);
      $hours = floor($tiempoPorduc / 3600);
      $minutes = floor(($tiempoPorduc / 60) % 60);
      $seconds = $tiempoPorduc % 60;

      $tiempoPorduccido = $hours.":".$minutes.":".$seconds;

      $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $sql_statements = "SELECT tiempo_descanso FROM motivo_paro WHERE numero_op='".$op."' ";
      $consutatime = mysqli_query($mysql, $sql_statements);
      $numeroop = array();
      while($row = mysqli_fetch_array($consutatime)) {
       
         $numeroop[] = $row["tiempo_descanso"];
        
      }
      $NumeroOP = array();
      for ($i = 0; $i < count($numeroop); $i++) {
        list($horas, $minutos, $segundos) = explode(':', $numeroop[$i]);
        $NumeroOP[] = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;
      }
      $matriz2 = array();
      if ( count($total) == count($NumeroOP) )
    for ($i=0; $i < count($total); $i++){
        $matriz2[] = ($total[$i] - $NumeroOP[$i]);
      }
       $sumartotal =  array_sum($matriz2);
     $sumarsengund = $sumartotal / 60; 
    $real =  number_format((float)$sumarsengund, 1, '.', ''); 
     $rest = substr($real, 0,1);

    $horas = floor($sumartotal / 3600);
    $minutos = floor(($sumartotal - ($horas * 3600)) / 60);
    $segundos = $sumartotal - ($horas * 3600) - ($minutos * 60);
     $resultadominuto = $horas . ':' . $minutos . ":" . $segundos;

    // TOTAL PRODUCTIVIDAD 
    $productividad = round($sumartotal / $tiempoPorduc *100) ;
    
     //TOTAL EFICIENCIA CONSOLIDADO
      $eficiencias =  $totalSegund / $real * 100;
     $eficiencia = substr($eficiencias, 0,3);
    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $res = "INSERT INTO promedio (fecha,Descripcion,tiempo_habil,timepo_estimado,tiempo_produccido,eficiencia,produccion) VALUES ('".$inicial."','".$NOMBRE."','".$tiempoPorduccido."','".$totalSegund."','".$resultadominuto."','".$eficiencia."','".$productividad."')";
   $resultado = mysqli_query($mysqli, $res);
  }
}
?>