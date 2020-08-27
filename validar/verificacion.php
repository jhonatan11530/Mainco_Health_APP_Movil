<?php
set_time_limit(0);
require_once("ConexionSQL.php");
//error_reporting(0);

/*while (true) {
  usleep(1000000);

}*/
INICIO();


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
      $sql_consulta = "SELECT  cod_producto,descripcion FROM  proyecto.produccion ";
      $result = sqlsrv_query($SQL, $sql_consulta);
      $id = array();
      while($sql = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
        $id[] = $sql["cod_producto"];
      }
      $SQLSERVER = count($id);
      if ($SQLSERVER < $ORACLE) {
        echo "ESTO SE EJECUTA ".$SQLSERVER." ".$ORACLE;
        INICIO2($item);
      // PRODUCCION($item,$descrip);
      }
      if ($SQLSERVER == $ORACLE) {
        echo "ESTO NO SE EJECUTA";
      }
      
      /* CONSULTAR Y INSERTAR TAREA */
  /*-------------------------------------------*/

  
    
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
      $stid = oci_parse($conn, "SELECT DISTINCT ID_ITEM,DESCRIPCION_OPER,TIEMPO_OPERACION FROM MNRUTAS_PRODUCCION WHERE ID_ITEM ='".$items."' ORDER BY ID_ITEM ASC");
      oci_execute($stid);
      $item = array();
      $descrip = array();
      $timeoper = array();
      while ($row = oci_fetch_array($stid, OCI_ASSOC)) {
        $item[] = $row["ID_ITEM"];
        $descrip[] = $row["DESCRIPCION_OPER"];
        $timeoper[] = number_format((float)$row["TIEMPO_OPERACION"], 8, ',', '');
        echo $row["ID_ITEM"]." ".$row["DESCRIPCION_OPER"];
        echo "<br>";
      }

      $SQL = sqlsrv_connect(Server() , connectionInfo());
      $sql_consulta = "SELECT numero_op FROM  proyecto.tarea ";
      $result = sqlsrv_query($SQL, $sql_consulta);
      $id = array();
      while($sql = sqlsrv_fetch_array($result, SQLSRV_FETCH_ASSOC)) {
        $id[] = $sql["numero_op"];

      }
      $SQLSERVER = count($id);
     
      if ($SQLSERVER < $ORACLE) {
        echo "ESTO SE EJECUTA";
        TAREA($item,$descrip,$timeoper); 
      }
      if ($SQLSERVER == $ORACLE) {
        echo "ESTO NO SE EJECUTA";
      }
      

    }
  }
    
   



function PRODUCCION($item,$descrip){

        for ($i=0; $i < count($item); $i++) { 
          $iten = $item[$i];
          $descripcion = $descrip[$i];
          
        $SQL = sqlsrv_connect(Server() , connectionInfo());
        $sql_consulta = "INSERT INTO proyecto.produccion 
        VALUES('".$iten."',null,'".$iten."','".$descripcion."',0,null)";
        $result = sqlsrv_query($SQL, $sql_consulta);
        }
}

function TAREA($item,$descrip,$timeoper){
  
          for ($i=0; $i < count($item); $i++) { 
           echo $iten = $item[$i];
           echo $descripcion = $descrip[$i];
           echo $time = $timeoper[$i];
           echo "<br>";
        $SQL = sqlsrv_connect(Server() , connectionInfo());
        $sql_consulta = "INSERT INTO proyecto.tarea(numero_op,tarea,cantidadpentiente,extandar) 
        VALUES('".$iten."','".$descripcion."',0,'".$time."')";
        $result = sqlsrv_query($SQL, $sql_consulta);
          }
            
}
?>