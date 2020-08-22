<?php
require_once("ConexionSQL.php");
error_reporting(0);
$op = $_GET["op"];

if(isset($op)){
$a = new comienzo();
$a->inicio($op);
}

class comienzo{
  function inicio($op){
      sleep(1);
      $mysql = sqlsrv_connect(Server() , connectionInfo());
      $sql_statements = "SELECT id,tarea,cantidad_fallas,inicial,nombre FROM proyecto.operador WHERE numero_op = '".$op."'  ";
      $llaves = sqlsrv_query($mysql, $sql_statements);
      while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {
        $ID = $row["id"];
        $tarea = $row["tarea"];
        $cantFALLAS = $row["cantidad_fallas"];
        $fecha = $row["inicial"];
        $nombre = $row["nombre"];
      }  

      $mysql = sqlsrv_connect(Server() , connectionInfo());
      // SUM(cantidad) AS cantidad
      $sql_statements = "SELECT SUM(cantidad) AS cantidad  FROM proyecto.operador WHERE  id='".$ID."' AND numero_op = '".$op."' ";
      $llaves = sqlsrv_query($mysql, $sql_statements);
      while($row = sqlsrv_fetch_array($llaves, SQLSRV_FETCH_ASSOC)) {
        $cant = $row["cantidad"];
      }  

      echo "CANTIDAD EN OP : ".$cant;
      echo "<br>";
      echo "<br>";

      $a = new produc();
      $a->produccion($cant,$op,$ID,$tarea,$cantFALLAS,$fecha,$nombre);

    }
}

class produc{
  function produccion($cant,$op,$ID,$tarea,$cantFALLAS,$fecha,$nombre){

      $mysqli = sqlsrv_connect(Server() , connectionInfo());
      $consulta = "SELECT cod_producto FROM proyecto.produccion WHERE numero_op = '".$op."'";
      $result = sqlsrv_query($mysqli, $consulta);  
  
      while($row = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
        
        $cod = $row["cod_producto"];

      }
      echo "CODIGO PRODUCTO :".$cod;
      echo "<br>";
      echo "<br>";


      $b = new tarea();
      $b->extand($cant,$cod,$ID,$tarea,$op,$cantFALLAS,$fecha,$nombre);
  }
} 
  
class tarea{
  function extand($cant,$cod,$ID,$tarea,$op,$cantFALLAS,$fecha,$nombre){

    $mysqli = sqlsrv_connect(Server() , connectionInfo());
    $search = "SELECT ROUND(extandar,5)*3600 AS extandar FROM proyecto.tarea WHERE numero_op = '".$cod."' AND tarea='".$tarea."' ";
    $res = sqlsrv_query($mysqli, $search);  
    
    while($listo = sqlsrv_fetch_array($res, SQLSRV_FETCH_ASSOC)) {
          
      $extandar = $listo["extandar"];
    
    }
    echo "TIEMPO ESTÁNDAR :".$extandar;
    echo "<br>";
    echo "<br>";


    $c = new HORA();
    $c->EFICACIA($cant,$extandar,$ID,$op,$cantFALLAS,$cod,$tarea,$fecha,$nombre);
  }
}

class HORA{
  function EFICACIA($cant,$extandar,$ID,$op,$cantFALLAS,$cod,$tarea,$fecha,$nombre){

    /*CONVERTIDO A HORA EFICACIA */
   $datosa = $extandar * $cant;
   $dato = gmdate('H:i:s', $datosa);
    echo "TIEMPO EN HORAS QUE SE DEBE DEMORAR EN HACER LA OP: ".$dato;
     
    $d = new entradasalida();
    $d->totaltime($ID,$datosa,$op,$cant,$cantFALLAS,$cod,$tarea,$fecha,$nombre);
  }
}

class entradasalida{
  function totaltime($ID,$datosa,$op,$cant,$cantFALLAS,$cod,$tarea,$fecha,$nombre){
    $mysqli = sqlsrv_connect(Server() , connectionInfo());
    // hora_inicial,hora_final
    // SUM(DATEDIFF(SECOND,hora_inicial,hora_final)) AS total
   $search = " SELECT SUM(DATEDIFF(SECOND,hora_inicial,hora_final)) AS total FROM proyecto.operador WHERE id = '".$ID."' AND numero_op = '".$op."'  ";
    $res = sqlsrv_query($mysqli, $search);  
    
    while($listo = sqlsrv_fetch_array($res, SQLSRV_FETCH_ASSOC)) {
          
      $inicia = $listo["total"];
      $inicia = gmdate("H:i:s", $inicia);
    }

    echo "<br>";  
    echo "<br>";
    echo "TIEMPO HORA INICIAL - FINAL = RESULTADO : ".$inicia;
    echo "<br>";  

    $e = new EFICIENCIA();
    $e->eficiencias($datosa,$ID,$final,$op,$cant,$cantFALLAS,$cod,$tarea,$fecha,$nombre,$inicia);
  }
}

class EFICIENCIA{
  function eficiencias($datosa,$ID,$final,$op,$cant,$cantFALLAS,$cod,$tarea,$fecha,$nombre,$inicia){

    /* YA ESTA LISTO */
    $mysql = sqlsrv_connect(Server() , connectionInfo());
    $sql_statements = "SELECT SUM(DATEDIFF(SECOND, '00:00:00', CONVERT(time, tiempo_descanso))) AS tiempo_descanso FROM proyecto.motivo_paro WHERE  numero_op= '".$op."'";
    $ensayo = sqlsrv_query($mysql, $sql_statements);
    while($row = sqlsrv_fetch_array($ensayo, SQLSRV_FETCH_ASSOC)) {
       $TIMEPAROSUM= $row["tiempo_descanso"] ;
       
    }  
     $promedioSU = gmdate('H:i:s', $TIMEPAROSUM);
    
    // RESTAR TIEMPO REAL PRODUCCIDA HORA INICIAL-FINAL // TIEMPO DE PARO
    list($horas, $minutos, $segundos) = explode(':', $inicia);
    $inicial = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;

    list($horas, $minutos, $segundos) = explode(':', $promedioSU);
    $promedioSUM = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;

   $rea=  $inicial - $promedioSUM;
   $real = gmdate("H:i:s", $rea);echo "<br>";

    // PASAR DE HORAS REALES LABORADAS A SEGUNDOS
    list($horas, $minutos, $segundos) = explode(':', $real);
    $datest = ($horas * 3600 ) + ($minutos * 60 ) + $segundos;
    
    $formula =  $datosa / $datest  * 100;
    $formulas = round($formula);

    $dates = gmdate('H:i:s', $datest);
    $dato = gmdate('H:i:s', $datosa);

      echo "EFICIENCIA : ".$formulas;
      
       $f = new EFICACIA();
       $f->eficacias($ID,$dato,$op,$cant,$cod,$tarea,$formulas,$dates,$fecha,$nombre,$inicia);

   

  }
}

class EFICACIA{
    function eficacias($ID,$dato,$op,$cant,$cod,$tarea,$formulas,$dates,$fecha,$nombre,$inicia){
        
          // TIEMPO EXTANDAR PROGRAMADA
        list($hours, $minutes, $segund) = explode(':', $inicia);
         $timeprogramado = ($hours * 3600 ) + ($minutes * 60 ) + $segund;
      // TIEMPO REAL LABORADO
      list($hours, $minutes, $segund) = explode(':', $dates);
       $timeproduccido = ($hours * 3600 ) + ($minutes * 60 ) + $segund;
  
       $eficacia = $timeproduccido / $timeprogramado  * 100;
        $eficacias = round($eficacia);
        echo "<br>";
        echo "<br>";
       echo "EFICACIA : ".$eficacias;
       echo "<br>";
        echo "<br>";
  
  
      $a = new CONSOLIDADO();
      $a->run($eficacias,$formulas,$dato,$dates,$fecha,$nombre,$op,$inicia);
      $mysqli->close();	
    }
}

  
  
class CONSOLIDADO{
    function run($eficacias,$formulas,$dato,$dates,$fecha,$nombre,$op,$inicia){
     echo "!------------------------------------------------!";echo "<br>";
         echo "FECHA : ".$fecha;echo "<br>";
         echo "NUMERO OP : ".$op;echo "<br>";
         echo "NOMBRE : ".$nombre;echo "<br>";echo "<br>";
         echo "TIEMPO HABIL  + TIEMPO DE PARO : ".$inicia; echo "<br>";
         echo "TIEMPO ESTIMADO : ".$dato;echo "<br>";
         echo "TIEMPO PRODUCCIDO - TIEMPO DE PARO : ".$dates;echo "<br>";echo "<br>";


         echo "% EFICIENCIA : ".$formulas;echo "<br>";
         echo "% PRODUCTIVIDAD : ".$eficacias;echo "<br>";
         
         $mysqli = sqlsrv_connect(Server() , connectionInfo());
         $res = "INSERT INTO proyecto.promedio (fecha,OP,Descripcion,tiempo_habil,timepo_estimado,tiempo_produccido,eficiencia,produccion)
         VALUES ('".$fecha."','".$op."','".$nombre."','".$inicia."','".$dato."','".$dates."','".$formulas."','".$eficacias."')";
         $resultado = sqlsrv_query($mysqli, $res);
    }
}
?>