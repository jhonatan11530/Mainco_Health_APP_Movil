<?php
    $op = $_GET["op"];
    $cantidad = $_GET["cantidad"];
    $item = $_GET["item"];
    
    $mysqli = mysqli_connect("127.0.0.1", "root", "", "proyecto");
    $search = "UPDATE produccion SET cantidad='".$cantidad."' WHERE numero_op='".$op."' AND cod_producto= '".$item."'";
    $res = mysqli_query($mysqli, $search);

    ?>