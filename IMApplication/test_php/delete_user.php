<?php
/**
 * Created by PhpStorm.
 * User: nazlimedghalchi
 * Date: 2015-11-30
 * Time: 12:32 PM
 */
/*
 * Following code will delete a accounts from table
 * A accounts is identified by product id (userid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['userid'])) {
    $userid = $_POST['userid'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched userid
    $result = mysql_query("DELETE FROM accounts WHERE userid = $userid");

    // check if row deleted or not
    if (mysql_affected_rows() > 0) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Account successfully deleted";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No accounts found";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>