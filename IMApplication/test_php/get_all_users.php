<?php
/**
 * Created by PhpStorm.
 * User: nazlimedghalchi
 * Date: 2015-11-30
 * Time: 12:25 PM
 */

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();

// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all products from products table
$result = mysql_query("SELECT *FROM accounts") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["accounts"] = array();

    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $accounts = array();
        $accounts["useid"] = $row["useid"];
        $accounts["pass"] = $row["pass"];

        // push single product into final response array
        array_push($response["accounts"], $accounts);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no accounts found
    $response["success"] = 0;
    $response["message"] = "No accounts found";

    // echo no users JSON
    echo json_encode($response);
}
?>