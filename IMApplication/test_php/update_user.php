<?php
/**
 * Created by PhpStorm.
 * User: nazlimedghalchi
 * Date: 2015-11-30
 * Time: 12:29 PM
 */
/*
 * Following code will update a product information
 * A product is identified by product id (userid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['userid']) && isset($_POST['pass'])) {

    $userid = $_POST['userid'];
    $pass = $_POST['pass'];
    $price = $_POST['price'];
    $description = $_POST['description'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched userid
    $result = mysql_query("UPDATE accounts SET pass = '$pass',  WHERE userid = $userid");

    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Account successfully updated.";

        // echoing JSON response
        echo json_encode($response);
    } else {

    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>