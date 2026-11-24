<?php
include('../connect.php');

// Assuming you receive the email as a parameter named 'email'
$email = $_GET['email'];

// Prepare the SQL query using a prepared statement
$sql = "SELECT * FROM users WHERE email = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $email);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    // Fetch the data from the result set
    $row = $result->fetch_assoc();

    $response = array(
        'id' => $row['id'],
        'name' => $row['name'],
        'phone' => $row['phone'],
        'image' => $row['image'],
        'username' => $row['username'],
        'password' => $row['password'],
        'refer' => $row['refer'],
        'email' => $row['email'],
        'device_id' => $row['device_id'],
        'user_country' => $row['user_country'],
        'date_registered' => $row['date_registered'],
        'points' => $row['points'],
        'user_id' => $row['user_id'],
        'status' => $row['status'],
        'total_refer' => $row['total_refer'],
        'notice_badge' => $row['notice_badge'],
        'daily_date_store' => $row['daily_date_store'],
        'spin_count' => $row['spin_count'],
        'last_date_spin' => $row['last_date_spin']


    );

    echo json_encode($response);
} else {
    // Return an empty JSON array
    echo json_encode(array());
}
 
?>
