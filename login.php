<?php

require "int.php";

//$username="ajay";
//$password="1234";
//$username=$_POST["$username"];
//$password=$_POST["$password"];
$username=mysqli_real_escape_string($con,$_GET['username']);
$password=mysqli_real_escape_string($con,$_GET['password']);
$result = mysqli_query($con,"select * from users where username='$username' and password='$password'");
if (mysqli_num_rows($result)) {
	//login success
	$row=mysqli_fetch_array($result);
	//getting the user name or name from the table
	$name=$row[username];
	$response=array();
	$code="login_true";
	$message=$name;
	array_push($response, array("code"=>$code,"message" => $message));
	//echo json_encode($message);
	echo json_encode(array("server_response" => $response));


	
}

else{

	//login failed

	$response=array();
	$code="login_false";

	$message="Login Failed.. Try Again";
	//echo $message;
	array_push($response, array("code"=>$code,"message" => $message));
	//echo json_encode($message);
	echo json_encode(array("server_response" => $response));
	
}

?>