<?php

error_reporting(E_ERROR | E_PARSE);
header('Content-type:application/json; charset=utf-8');

$response = array();

if (!isset($_POST['name']) || !isset($_POST['last_name'])) {
    $response["success"] = 0;
    $response["message"] = "การเพิ่มข้อมูลล้มเหลว: ส่งพารามิเตอร์มาไม่ครบ";
    echo json_encode($response);
    exit();
}

require_once 'db_config.php';
$db = new mysqli(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

if (mysqli_connect_errno()) {
    $response["success"] = 0;
    $response["message"] = "การเพิ่มข้อมูลล้มเหลว: ไม่สามารถเชื่อมต่อฐานข้อมูลได้: " . mysqli_connect_error();
    echo json_encode($response);
    exit();
}

$name = $_POST['name'];
$lastName = $_POST['last_name'];

$charset = "SET character_set_results=utf8";
$db->query($charset);
$charset = "SET character set utf8";
$db->query($charset);

$insertSql = "INSERT INTO people(`name`, `last_name`)"
        . " VALUES ('$name', '$lastName')";

if ($insertResult = $db->query($insertSql)) {
    $response["success"] = 1;
    $response["message"] = "เพิ่มข้อมูลสำเร็จ";
} else {
    $response["success"] = 0;
    $response["message"] = "การเพิ่มข้อมูลล้มเหลว: $insertSql";
}

$db->close();
echo json_encode($response);

?>
