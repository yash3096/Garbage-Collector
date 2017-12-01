<?php

require "int.php";

$name=$_POST["name"];
$username=$_POST["username"];
$password=$_POST["password"];;
$gender=$_POST["gender"];;
$email=$_POST["email"];
$contact=$_POST["contact"];

//checking whether d username already exists or not

$query="select * from users where username='$username'";


$result = mysqli_query($con,"select * from users where username='$username'");



if ($result->num_rows > 0) {
	
	//error due to same username

	$response = array();
	$code = "reg_false";
	$message = "username already exists..";

	array_push($response, array("code"=>$code,"message" => $message));
	//echo json_encode($message);
	echo json_encode(array("server_response" => $response));

}
else {
	$query="insert into users values('','$name','$username','$password','$gender','$email','$contact');"; 
	$result=mysqli_query($con,$query);
		if(!$result){

				//error due to server issue

				$response = array();
				$code = "reg_false";
				$message = "server error occured TRY AGAIN..!";

				array_push($response, array("code" => $code, "message" => $message));

				echo json_encode(array("server_response" => $response));

		}
		else
		{
				//registration successful

				$response = array();
				$code = "reg_true";
				$message = "Registation Successful... Thank You..!";

				array_push($response, array("code" => $code, "message" => $message));

				echo json_encode(array("server_response" => $response));
		}

	mysqli_close($con);
}


?>