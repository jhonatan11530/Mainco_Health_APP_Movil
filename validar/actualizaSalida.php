<?php
error_reporting(0);


$ID = $_GET["id"];
$op = $_GET["op"];
$tarea = $_GET["tarea"];
$cantidad = $_GET["cantidad"];
$no_conforme = $_GET["conforme"];
$motivo = $_GET["motivo"];
$final = $_GET["Ffinal"];
$hora_final = $_GET["Hfinal"];

if(isset($ID,$op,$tarea,$cantidad,$no_conforme,$motivo,$final,$hora_final)){
 

$mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
$sql_statement = "UPDATE operador SET final='".$final."',cantidad='".$cantidad."',hora_final='".$hora_final."',tarea='".$tarea."',cantidad_fallas='".$no_conforme."',no_conforme='".$motivo."' 
WHERE id= '".$ID."' AND numero_op = '".$op."'";
$result = mysqli_query($mysqli, $sql_statement);


$a = new ejemplo();
$a->ejecutar($ID,$op,$tarea);

}else{
  echo "aqui no paso nada";
}
class ejemplo{
  function ejecutar($ID,$op,$tarea){
      sleep(1);
      $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $consulta = "SELECT cantidad FROM operador WHERE numero_op = '".$op."' AND id='".$ID."' ";
      $result = mysqli_query($mysqli, $consulta);  

      while($row = mysqli_fetch_array($result)) {
        
        $cant = $row["cantidad"];

          
      }
        echo "CANTIDAD EN OP :".$cant;
        echo "<br>";
        echo "<br>";

      $a = new produc();
      $a->produccion($cant,$op,$ID,$tarea);

    }
}

class produc{
  function produccion($cant,$op,$ID,$tarea){

      $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $consulta = "SELECT cod_producto FROM produccion WHERE numero_op = '".$op."'";
      $result = mysqli_query($mysqli, $consulta);  
  
      while($row = mysqli_fetch_array($result)) {
        
        $cod = $row["cod_producto"];

      }
      echo "CODIGO PRODUCTO :".$cod;
      echo "<br>";
      echo "<br>";


      $b = new tarea();
      $b->extand($cant,$cod,$ID,$op,$tarea);
  }
} 
  
class tarea{
  function extand($cant,$cod,$ID,$op,$tarea){

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "SELECT extandar FROM tarea WHERE numero_op = '".$cod."' AND tarea= '".$tarea."' ";
    $res = mysqli_query($mysqli, $search);  
    
    while($listo = mysqli_fetch_array($res)) {
          
      $extandar = $listo["extandar"];
    
    }
    echo "TIEMPO ESTÁNDAR :".$extandar;
    echo "<br>";
    echo "<br>";


    $c = new HORA();
    $c->EFICACIA($cant,$extandar,$ID,$op);
  }
}

class HORA{
  function EFICACIA($cant,$extandar,$ID,$op){

    /*CONVERTIDO A HORA EFICACIA */
  $SegundosXhoras = 3600;
  $listo = round($extandar * 3600);
  $dato = $SegundosXhoras / $listo;
 

    echo "TIEMPO EN HORAS QUE SE DEBE DEMORAR EN HACER LA OP: ".$dato;
     
    $d = new entradasalida();
    $d->totaltime($ID,$dato,$op,$cant);
  }
}

class entradasalida{
  function totaltime($ID,$dato,$op,$cant){

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "SELECT * FROM operador WHERE id = '".$ID."'";
    $res = mysqli_query($mysqli, $search);  
    
    while($listo = mysqli_fetch_array($res)) {
          
      $inicial = $listo["hora_inicial"];
      $final = $listo["hora_final"];

    }

    echo "<br>";  
    echo "<br>";
    echo "TIEMPO ENTRADA : ".$inicial;
    echo "<br>";
    echo "<br>";
    echo "TIEMPO SALIDA : ".$final;


    
    $e = new EFICIENCIA();
    $e->eficiencias($inicial,$final,$dato,$ID,$op,$cant);
  }
}

class EFICIENCIA{
  function eficiencias($inicial,$final,$dato,$ID,$op,$cant){

    
    $formulas = $cant / $dato;
    
    $formula = round($formulas * 100);

    echo "<br>";
    echo "<br>";
    echo "EFICIENCIA : ".$cant;


    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "UPDATE operador SET eficencia='".$formula."' WHERE id= '".$ID."'";
    $res = mysqli_query($mysqli, $search);

   $f = new EFICACIA();
    $f->eficacias($ID,$dato,$inicial,$final,$op,$cant);
  }
}

class EFICACIA{
  function eficacias($ID,$dato,$inicial,$final,$op,$cant){

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "SELECT * FROM operador WHERE id = '".$ID."'";
    $res = mysqli_query($mysqli, $search);  
    
    while($listo = mysqli_fetch_array($res)) {
          
      $buenas = $listo["cantidad"];
      $malas = $listo["cantidad_fallas"];
    }
    $efic = $buenas - $malas;
    $eficacias = $efic / $buenas * 100;
    $eficacia = round($eficacias);
    echo "<br>";
    echo "<br>";
    echo "EFICACIA : ".$eficacia;


    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "UPDATE operador SET eficacia='".$eficacia."' WHERE id= '".$ID."'";
    $res = mysqli_query($mysqli, $search);

    $f = new TIEMPO_PROMEDIO();
    $f->promedio($ID,$dato,$inicial,$final,$op,$cant);
    
  }
}
class TIEMPO_PROMEDIO{
  function promedio($ID,$dato,$inicial,$final,$op,$cant){

     $newDate = ($inicial + $final) / 2;

     $promedio = date("h:m:s", strtotime($newDate));  


    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "INSERT INTO promedio (numero_op,cantidad_op,cod_operador,time_promedio) VALUES ('".$op."','".$cant."','".$ID."','".$promedio."')";
    mysqli_query($mysqli, $search);
    $mysqli->close();	
  }
}



?>
