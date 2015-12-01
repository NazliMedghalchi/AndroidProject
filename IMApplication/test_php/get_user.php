<?php
/**
 * Created by PhpStorm.
 * User: nazlimedghalchi
 * Date: 2015-11-30
 * Time: 12:20 PM
 */

/*
 * Following code will get single product details
 * A product is identified by user id (userid)
 */

// array for JSON response
$response = array();

// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// check for post data
if (isset($_GET["userid"])) {
    $userid = $_GET['userid'];

    // get a product from products table
    $result = mysql_query("SELECT *FROM accounts WHERE userid = $userid");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);

            $product = array();
            $product["userid"] = $result["userid"];
            $product["name"] = $result["name"];
            // success
            $response["success"] = 1;

            // user node
            $response["accounts"] = array();

            array_push($response["accounts"], $accounts);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No such user found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No user found";

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
