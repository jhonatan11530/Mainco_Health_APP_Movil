<?php
function Server(){
    $serverName = "srv2008,1433";
    return $serverName;
}
function connectionInfo(){
$connectionInfo = array("Database"=>"proyecto","UID"=>"proyecto","PWD"=>"12345","CharacterSet"=>"UTF-8");
return $connectionInfo;
}

function ServerOracle(){
    $serverName = "biable01";
    return $serverName;
}
function ServerNameOracle(){
    $serverName = "xe";
    return $serverName;
}

?>