<?php

require "int.php";
$name=$_POST["name"];
$address=$_POST["address"];
$locality=$_POST["locality"];
$pincode=$_POST["pincode"];
$contact=$_POST["contact"];
$paper=$_POST["paper"];
$plastic=$_POST["plastic"];
$metal=$_POST["metal"];
$quantity=$_POST["quantity"];
$code=$_POST["code"];
$username=$_POST["username"];

/*$name="ajay";
$address="krishna leela";
$locality="bhayander";
$pincode="401105";
$contact="7666672285";
$paper="yes";
$plastic="no";
$metal="yes";
$quantity="4";
$code="4581";
$username="ajay12";
*/
$sql="insert into garbage values('$name','$address','$locality','$pincode','$contact','$paper','$plastic','$metal','$quantity','$code','$username','');";

if(mysqli_query($con,$sql))
{

	$code="add_posted";
	echo $code;

	


}
else
{
	$code="not_posted";
	echo $code;
}

?>
