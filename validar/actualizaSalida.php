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


  $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statements = "SELECT llaves FROM operador WHERE id= '".$ID."'";
  $llaves = mysqli_query($mysql, $sql_statements);
  while($row = mysqli_fetch_array($llaves)) {
    $llave = $row["llaves"];
  }  

  $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
  $sql_statement = "UPDATE operador SET final='".$final."',cantidad='".$cantidad."',hora_final='".$hora_final."',tarea='".$tarea."',cantidad_fallas='".$no_conforme."',no_conforme='".$motivo."' 
  WHERE id= '".$ID."' AND numero_op = '".$op."' AND llaves='".$llave."' ";
  $result = mysqli_query($mysqli, $sql_statement);

$a = new ejemplo();
$a->ejecutar($ID,$op,$tarea,$llave);

}
else{
  echo "aqui no paso nada";
}

class ejemplo{
  function ejecutar($ID,$op,$tarea,$llave){
      sleep(1);
      $mysql = mysqli_connect("127.0.0.1", "root", "", "proyecto");
      $sql_statements = "SELECT * FROM operador WHERE id='".$ID."' AND numero_op = '".$op."'";
      $llaves = mysqli_query($mysql, $sql_statements);
      while($row = mysqli_fetch_array($llaves)) {
        $cant = $row["cantidad"];
        $cantFALLAS = $row["cantidad_fallas"];
      }  

      echo "CANTIDAD EN OP : ".$cant;
      echo "<br>";
      echo "<br>";

      $a = new produc();
      $a->produccion($cant,$op,$ID,$tarea,$llave,$cantFALLAS);

    }
}

class produc{
  function produccion($cant,$op,$ID,$tarea,$llave,$cantFALLAS){

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
      $b->extand($cant,$cod,$ID,$tarea,$op,$llave,$cantFALLAS);
  }
} 
  
class tarea{
  function extand($cant,$cod,$ID,$tarea,$op,$llave,$cantFALLAS){

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "SELECT extandar FROM tarea WHERE numero_op = '".$cod."' AND tarea='".$tarea."' ";
    $res = mysqli_query($mysqli, $search);  
    
    while($listo = mysqli_fetch_array($res)) {
          
      $extandar = $listo["extandar"];
    
    }
    echo "TIEMPO ESTÁNDAR :".$extandar;
    echo "<br>";
    echo "<br>";


    $c = new HORA();
    $c->EFICACIA($cant,$extandar,$ID,$op,$llave,$cantFALLAS);
  }
}

class HORA{
  function EFICACIA($cant,$extandar,$ID,$op,$llave,$cantFALLAS){

    /*CONVERTIDO A HORA EFICACIA */
   $SegundosXhoras = 3600;
   $listo = round($extandar * 3600);
   $dato = $listo * $cant;

 

    echo "TIEMPO EN HORAS QUE SE DEBE DEMORAR EN HACER LA OP: ".$dato;
     
    $d = new entradasalida();
    $d->totaltime($ID,$dato,$op,$cant,$llave,$cantFALLAS);
  }
}

class entradasalida{
  function totaltime($ID,$dato,$op,$cant,$llave,$cantFALLAS){

    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "SELECT * FROM operador WHERE id = '".$ID."' AND llaves='".$llave."' ";
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
    $e->eficiencias($dato,$ID,$inicial,$final,$op,$cant,$llave,$cantFALLAS);
  }
}

class EFICIENCIA{
  function eficiencias($dato,$ID,$inicial,$final,$op,$cant,$llave,$cantFALLAS){

    $datetime1 = new DateTime($final);
    $datetime2 = new DateTime($inicial);
    $interval = $datetime1->diff($datetime2);
    $hora = $interval->format('%H:%M:%S');

    list($horas, $minutos, $segundos) = explode(':', $hora);
    $hora_en_segundos = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;
    $dates = $dato / $hora_en_segundos;
    $formula = round($dates * 100);
    
    echo "<br>";
    echo "<br>";
    echo "EFICIENCIA : ".$formula;


    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "UPDATE operador SET eficencia='".$formula."' WHERE id= '".$ID."' AND llaves='".$llave."'";
    $res = mysqli_query($mysqli, $search);

   $f = new EFICACIA();
    $f->eficacias($ID,$dato,$op,$cant,$llave);
  }
}

class EFICACIA{
  function eficacias($ID,$dato,$op,$cant,$llave){

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
    $search = "UPDATE operador SET eficacia='".$eficacia."' WHERE id= '".$ID."' AND llaves='".$llave."'";
    $res = mysqli_query($mysqli, $search);
    $mysqli->close();	
  }
}




?>
