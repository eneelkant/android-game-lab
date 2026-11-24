<?php
include('../connect.php');


if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $username = $_POST['username'];
    $password = $_POST['password'];
    
    $query = "SELECT * FROM users WHERE email = '$username' AND password = '$password'";
    
    $result = mysqli_query($conn, $query);
    
    if (mysqli_num_rows($result) > 0) {
        $response['success'] = true;
        $response['message'] = "Login successful";
    } else {
        $response['success'] = false;
        $response['message'] = "Invalid username or password.";
    }
    
    echo json_encode($response);
}


?>
