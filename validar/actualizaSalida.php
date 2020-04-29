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
$a->ejecutar($ID,$op);

}else{
  echo "aqui no paso nada";
}

class ejemplo{
  function ejecutar($ID,$op){
      sleep(1);
      $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $consulta = "SELECT cantidad FROM produccion WHERE numero_op = '".$op."'";
      $result = mysqli_query($mysqli, $consulta);  

      while($row = mysqli_fetch_array($result)) {
        
        $cant = $row["cantidad"];

          
      }
        echo "CANTIDAD EN OP :".$cant;
        echo "<br>";
        echo "<br>";

      $a = new produc();
      $a->produccion($cant,$op,$ID);

    }
}

class produc{
  function produccion($cant,$op,$ID){

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
      $b->extand($cant,$cod,$ID);
  }
} 
  
class tarea{
  function extand($cant,$cod,$ID){

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "SELECT extandar FROM tarea WHERE numero_op = '".$cod."'";
    $res = mysqli_query($mysqli, $search);  
    
    while($listo = mysqli_fetch_array($res)) {
          
      $extandar = $listo["extandar"];
    
    }
    echo "TIEMPO ESTÁNDAR :".$extandar;
    echo "<br>";
    echo "<br>";


    $c = new HORA();
    $c->EFICACIA($cant,$extandar,$ID);
  }
}

class HORA{
  function EFICACIA($cant,$extandar,$ID){

    /*CONVERTIDO A HORA EFICACIA */
    $datos = $extandar * $cant;    

    $timestamp = strtotime($datos);
    $hora =date('H:i:s',$timestamp);

    list($horas, $minutos, $segundos) = explode(':', $hora);
    $dato = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;

    echo "TIEMPO EN HORAS QUE SE DEBE DEMORAR EN HACER LA OP: ".$dato;
     
    $d = new entradasalida();
    $d->totaltime($ID,$dato);
  }
}

class entradasalida{
  function totaltime($ID,$dato){

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "SELECT * FROM operador WHERE id = '".$ID."'";
    $res = mysqli_query($mysqli, $search);  
    
    while($listo = mysqli_fetch_array($res)) {
          
      $inicial = $listo["hora_inicial"];
      $final = $listo["hora_final"];

    }
    
    $fecha1 = new DateTime($inicial);
    $fecha2 = new DateTime($final);
    $intervalo = $fecha1->diff($fecha2);
    $time =  $intervalo->format('%H:%I:%S');

    list($horas, $minutos, $segundos) = explode(':', $time);
    $total = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;

    echo "<br>";  
    echo "<br>";
    echo "TIEMPO ENTRADA : ".$inicial;
    echo "<br>";
    echo "<br>";
    echo "TIEMPO SALIDA : ".$final;
    echo "<br>";
    echo "<br>";
    echo "TOTAL HORA REAL AL TERMINAR OP : ".$total;


    $e = new EFICIENCIA();
    $e->eficiencias($total,$dato,$ID);
  }
}

class EFICIENCIA{
  function eficiencias($total,$dato,$ID){

    

    $eficiencia = $dato / $total;
    $redondo = $eficiencia  * 100;
    $formula = round($redondo);
    

    echo "<br>";
    echo "<br>";
    echo "EFICIENCIA : ".$formula;


    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "UPDATE operador SET eficencia='".$formula."' WHERE id= '".$ID."'";
    $res = mysqli_query($mysqli, $search);

   $f = new EFICACIA();
    $f->eficacias($ID);
  }
}

class EFICACIA{
  function eficacias($ID){

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "SELECT * FROM operador WHERE id = '".$ID."'";
    $res = mysqli_query($mysqli, $search);  
    
    while($listo = mysqli_fetch_array($res)) {
          
      $buenas = $listo["cantidad"];
      $malas = $listo["cantidad_fallas"];
    }
    $eficacia = $buenas - $malas;
    $total = $eficacia / $buenas * 100;
    $dato = round($total);

    echo "<br>";
    echo "<br>";
    echo "EFICACIA : ".$total;


    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "UPDATE operador SET eficacia='".$dato."' WHERE id= '".$ID."'";
    $res = mysqli_query($mysqli, $search);
    $mysqli->close();	
  }
}



?>
