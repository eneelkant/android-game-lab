<?php

    include 'connect.php' ;
    
	$phone='0';
    $query = "SELECT * FROM u_app WHERE fff = '$phone'";
    $result = mysqli_query($conn, $query);
    
    if (mysqli_num_rows($result) > 0) {
         
         header('Location: config/vf.php');
         
     
      echo "  successfully.";
    } else {
      // User with the specified email does not exist
      // Add code here to handle error or notify user
     
    }

 

?>