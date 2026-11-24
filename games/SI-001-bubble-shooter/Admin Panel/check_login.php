<?php
session_start();
include 'connect.php';
$conn = mysqli_connect($servername, $username, $password, $dbname);

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}



//start

$sql = "SELECT purchase_code FROM u_app";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        // Get the purchase code from user input
        $purchase_code = $row['purchase_code'];
 
    
    }
} else {
    echo "No results found.";
}
 

$ch = "Error: Could not add game details.";

// Set your Envato API key
$api_key = (getenv('ENVATO_API_KEY') ?: '');

// Set the API endpoint URL
$url = 'https://api.envato.com/v3/market/author/sale?code=' . $purchase_code;

// Set the request headers
$headers = array(
    'Authorization: Bearer ' . $api_key,
);

// Initialize a cURL session and set the options
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $url);
curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

// Execute the cURL session and get the response and HTTP status code
$response = curl_exec($ch);
$http_status = curl_getinfo($ch, CURLINFO_HTTP_CODE);

// Close the cURL session
curl_close($ch);

// Check the HTTP status code
if ($http_status == 200) {
    

    if(isset($_POST['email'])){
        $email = $_POST['email'];
        $password = $_POST['password'];
        $email = mysqli_real_escape_string($conn, $email);
        $hashedPassword = md5($password);
    
        $sql = "SELECT * FROM admin WHERE email='$email' AND password='$password'";
        $result = $conn->query($sql);
        if ($result) {
            if ($result->num_rows == 1) {
                $row=mysqli_fetch_array($result);
                $_SESSION['login']=true;
                $_SESSION['name']=$row['name'];
                $_SESSION['email']=$row['email'];
                header('location:index.php');
                exit;
            } else {
                $_SESSION['wrong']=true;
                header('location:login.php');
            }
        } else {
            $_SESSION['wrong']=true;
            header('location:login.php');
        }
    }



} else {
    
    header('Location: config/vf.php');
    
}


//end

$conn->close();
?>
