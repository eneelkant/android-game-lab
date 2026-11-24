<?php
include('../connect.php');

$uaFull = strtolower($_SERVER['HTTP_USER_AGENT']);
if (strpos($uaFull, 'okhttp') !== false) {

    $method = $_POST['method'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $name = $_POST['name'];
    $username = $_POST['username'];
    $phone = $_POST['phone'];
    $user_country = $_POST['user_country'];
    $device_id = $_POST['device_id'];
    $user_id = $_POST['user_id'];
    $refer = $_POST['refer'];
    $image = $_POST['image'];
    
    $admin_url = $_POST['admin_url'];
    $imageName = $admin_url. "api/images/ic_avatar_default_1.png";

    if (!empty($method) && $method == "regUser") {
        
        $sql = "SELECT * FROM users WHERE email='$email'";
        $result = mysqli_query($conn, $sql);
        if ($result->num_rows > 0) {
            echo "email already registered";
        } else {

         
                //
                $sqlGetSetting = "SELECT * FROM app_setting WHERE id='1'";
                $resultreGetSetting = mysqli_query($conn, $sqlGetSetting);
                if (mysqli_num_rows($resultreGetSetting) > 0) {
                    $settingRow = mysqli_fetch_assoc($resultreGetSetting);
                    $sign_up_bonus = $settingRow["signup_bonus"];
                    $spin_ava_ch = $settingRow["spin_ava_ch"];
                
                    //all list
                }
       
                $sql = "INSERT INTO users(name, email, password, username, phone, user_country, device_id, refer, user_id, image , points, spin_count )
                VALUES ('$name', '$email', '$password', '$username', '$phone', '$user_country', '$device_id', '$refer', '$user_id', '$imageName' , '$sign_up_bonus', '$spin_ava_ch')";
    
    
                $result = mysqli_query($conn, $sql);
                if ($result) {
                    $type_earn = 'Sign up Bonus';
                    $sql = "INSERT INTO user_tracking(user_id, points, type_earn )
                    VALUES ('$user_id', '$sign_up_bonus', '$type_earn')";
                    $result = mysqli_query($conn, $sql);
                    if ($result) {
                    //   echo "Success";
                    }else {
                        // echo 'error';
                    }


                    echo "Registration Success";
                }

        }

    }

} else {
    echo "invalid Request";
}
?>
