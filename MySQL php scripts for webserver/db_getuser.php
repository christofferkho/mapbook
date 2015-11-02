<?php
 
/*
 * Following code will get user details
 * with corresponding locations saved
 */
 //USER table:
 //username
 //password
//firstname
//lastname
//country
//city
//osmuser
//osmpass
//idlocation
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET["username"] && $_GET["password"])) {
    $username = $_GET['username'];
	$password = $_GET['password'];
 
    // get a user from user table
    $resultuser = mysql_query("SELECT * FROM user WHERE username = $username AND password = $password");
 
    if (!empty($resultuser)) {
        // check for empty resultuser
        if (mysql_num_rows($resultuser) > 0) {
 
            $resultuser = mysql_fetch_array($resultuser);
 
            $user = array();
            $user["iduser"] = $resultuser["iduser"];
            $user["username"] = $resultuser["username"];
            $user["password"] = $resultuser["password"];
            $user["osmuser"] = $resultuser["osmuser"];
            $user["osmpass"] = $resultuser["osmpass"];
            $user["firstname"] = $resultuser["firstname"];
			$user["lastname"] = $resultuser["lastname"];
			$user["counter"] = $resultuser["counter"];
			$user["city"] = $resultuser["city"];
            // success
            $response["success"] = 1;
			
			//get locations according to this user's location id's
			$resultlocation = mysql_query("SELECT e.idlocation, e.idpath, e.locname, e.address, e.notes, e.image FROM location AS e JOIN user AS f ON e.iduser = f.iduser");
			//set array for user's locations
			$response["locations"] = array();
 
			while ($locationrow = mysql_fetch_array($resultlocation)) {
				// temp user array
				$location = array();
				$location["idlocation"] = $locationrow["idlocation"];
				$location["idpath"] = $locationrow["idpath"];
				$location["locname"] = $locationrow["locname"];
				$location["address"] = $locationrow["address"];
				$location["notes"] = $locationrow["notes"];
				$location["image"] = $locationrow["image"];
				$location["iduser"] = $locationrow["iduser"];
				
				$resultpath = mysql_query("SELECT e.idpath, e.latitude, e.longitude FROM path AS e JOIN location AS f ON e.idpath = f.idpath");
				//set array for location's coordinates, or the path
				$response["path"] = array();
	 
				while ($pathrow = mysql_fetch_array($resultpath)) {
					// temp user array
					$path = array();
					$path["idpath"] = $pathrow["idpath"];
					$path["latitude"] = $pathrow["latitude"];
					$path["longitude"] = $pathrow["longitude"];
					
					// push single product into final response array
					array_push($response["path"], $path);
				}
				// push single product into final response array
				array_push($response["locations"], $location);
			}
 
            // user node
            $response["user"] = array();
 
            array_push($response["user"], $user);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No user found!";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "User doesn't exist!";
 
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