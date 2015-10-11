<?php
 
/*
 * Following code will create a new user
 * All user details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['username']) && isset($_POST['password']) && isset($_POST['country']) && isset($_POST['city']) && isset($_POST['firstname']) && isset($_POST['lastname'])) {
 
    $username = $_POST['username'];
    $password = $_POST['password'];
    $country = $_POST['country'];
	$city = $_POST['city'];
	$firstname = $_POST['firstname'];
	$lastname = $_POST['lastname'];
	$osmuser = $_POST['osmuser'];
	$osmpass = $_POST['osmpass'];
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO user(username, password, osmuser, osmpass, firstname, lastname, country, city) VALUES('$username', '$password', '$osmuser', '$osmpass', '$firstname', '$lastname', '$country', '$city')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "New user created!";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
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