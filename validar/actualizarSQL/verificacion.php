<?php
set_time_limit(0);
require_once("../ConexionSQL.php");
//error_reporting(0);
//INICIO();
//MOTIVOS();

function INICIO(){

        /* CONSULTAR Y INSERTAR PRODUCCION */
    /*-------------------------------------------*/
        $conn = oci_connect(ServerOracle(),ServerOracle(),ServerNameOracle());
        $stid = oci_parse($conn, "SELECT DISTINCT ID_ITEM,DESCRIPCION FROM ITEMS WHERE ID_PROCEDENCIA ='M' ORDER BY ID_ITEM ASC ");
        oci_execute($stid);
        $item = array();
        $descrip = array();
        while ($row = oci_fetch_array($stid, OCI_ASSOC)) {
          $item[] = $row["ID_ITEM"];
          $descrip[] = $row["DESCRIPCION"];
          echo $row["ID_ITEM"]." ".$row["DESCRIPCION"];
          echo "<br>";
        }
        $ORACLE = count($item);

      $SQL = sqlsrv_connect(Server() , connectionInfo());
      $sql_consulta = "SELECT DISTINCT cod_producto,descripcion FROM  proyecto.produccion ";
      $result = sqlsrv_query($SQL, $sql_consulta);
      $id = array();
      while($sql = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
        $id[] = $sql["cod_producto"];
      }
      $SQLSERVER = count($id);
      PRODUCCION($item,$descrip);

      /* CONSULTAR Y INSERTAR TAREA */
  /*-------------------------------------------*/

  //INICIO2($item);

}

function PRODUCCION($item,$descrip){

        for ($i=0; $i < count($item); $i++) { 
          $iten = $item[$i];
          $descripcion = $descrip[$i];
          
        $SQL = sqlsrv_connect(Server() , connectionInfo());
        $sql_consulta = "INSERT INTO proyecto.produccion(id,numero_op,cod_producto,descripcion,cantidad,autorizado)
        VALUES('".$iten."',null,'".$iten."','".$descripcion."',0,0)";
        $result = sqlsrv_query($SQL, $sql_consulta);
        }
}

function INICIO2($item){

  
  $descri = array();
  for ($i=0; $i < count($item); $i++) { 
    $items = $item[$i]; 
   $a = new nuevo();
   $a->new($items);
    }
}

  class nuevo{
    function new($items){
      
      $conn = oci_connect(ServerOracle(),ServerOracle(),ServerNameOracle());
      $stid = oci_parse($conn, "SELECT ID_ITEM,DESCRIPCION_OPER,CANTIDAD_BASE,TIEMPO_OPERACION FROM MNRUTAS_PRODUCCION WHERE ID_ITEM ='".$items."' ORDER BY ID_ITEM ASC");
      oci_execute($stid);
      $item = array();
      $descrip = array();
      $cant = array();
      $timeoper = array();
      while ($row = oci_fetch_array($stid, OCI_ASSOC)) {
        $item[] = $row["ID_ITEM"];
        $descrip[] = $row["DESCRIPCION_OPER"];
        $cant[] = $row["CANTIDAD_BASE"];
        $timeoper[] = number_format((float)$row["TIEMPO_OPERACION"], 8, '.', '');
        
        echo $row["ID_ITEM"]." ".$row["DESCRIPCION_OPER"]." ".number_format((float)$row["TIEMPO_OPERACION"], 8, '.', '');
         echo "<br>";
      }
      $ORACLE = count($item);

      $SQL = sqlsrv_connect(Server() , connectionInfo());
      $sql_consulta = "SELECT numero_op FROM proyecto.tarea ";
      $result = sqlsrv_query($SQL, $sql_consulta);
      $id = array();
      while($sql = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
        $id[] = $sql["numero_op"];

      }
       $SQLSERVER = count($id);
      

        TAREA($item,$descrip,$timeoper,$cant); 

      

    }
  }

function TAREA($item,$descrip,$timeoper,$cant){
  
          for ($i=0; $i < count($item); $i++) { 
           echo $iten = $item[$i];
           echo $descripcion = $descrip[$i];
           echo $time = $timeoper[$i];
           echo $base = $cant[$i];
           echo "<br>";
        $SQL = sqlsrv_connect(Server() , connectionInfo());
        $sql_consulta = "INSERT INTO proyecto.tarea(numero_op,tarea,cantidadpentiente,cantidadbase,extandar) 
        VALUES('".$iten."','".$descripcion."',0,'".$base."','".$time."')";
        $result = sqlsrv_query($SQL, $sql_consulta);
          }
            
}

function MOTIVOS()

{
    $conn = oci_connect(ServerOracle(),ServerOracle(),ServerNameOracle());
      $stid = oci_parse($conn, "SELECT DISTINCT ID_MOTIVO,DESC_MOTIVO FROM MNTIEMPOS_DE_PARO ORDER BY ID_MOTIVO ASC");
      oci_execute($stid);
      $item = array();
      $descrip = array();
      while ($row = oci_fetch_array($stid, OCI_ASSOC)) {
        $item[] = $row["ID_MOTIVO"];
        $descrip[] = $row["DESC_MOTIVO"];
       echo $row["ID_MOTIVO"]." ".$row["DESC_MOTIVO"];
       echo "<br>";
      }
       MOTIVO_PARO($item,$descrip);
}
  function MOTIVO_PARO($item,$descrip){
  
    for ($i=0; $i < count($item); $i++) { 
       $iten = $item[$i];
       $descripcion = $descrip[$i];
  $SQL = sqlsrv_connect(Server() , connectionInfo());
  $sql_consulta = "INSERT INTO proyecto.motivo(id,paro) VALUES('".$iten."','".$descripcion."')";
  $result = sqlsrv_query($SQL, $sql_consulta);
    }
      
}

?>