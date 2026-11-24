<?php
$servername = "localhost";
$username = (getenv('DB_USERNAME') ?: 'CHANGE_ME');
$password = (getenv('DB_PASSWORD') ?: 'CHANGE_ME');
$dbname = (getenv('DB_NAME') ?: 'CHANGE_ME');

// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
// echo "Connected successfully";


 


?>