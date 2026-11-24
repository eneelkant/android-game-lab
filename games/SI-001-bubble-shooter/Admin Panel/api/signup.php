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
    $refer = $_POST['refer'];
    $user_id = $_POST['user_id'];
    $refer_code = $_POST['refer_code'];

    //
    $admin_url = $_POST['admin_url'];
    $imageName = $admin_url. "api/images/ic_avatar_default_1.png";

    if (!empty($method) && $method == "regUser") {
        
        $sql = "SELECT * FROM users WHERE email='$email'";
        $result = mysqli_query($conn, $sql);
        if ($result->num_rows > 0) {
            echo "email already registered";
        } else {
            $sql = "SELECT * FROM users WHERE phone='$phone'";
            $result = mysqli_query($conn, $sql);
    
            if ($result->num_rows > 0) {

                echo "phone already registered";
                
            }else{
                $sqlDevice = "SELECT * FROM users WHERE device_id='$device_id'";
                $resultDevice = mysqli_query($conn, $sqlDevice);
                if ($resultDevice->num_rows > 0) {
                    echo "device already registered";
                    
                } else { 

                    if (empty( $refer_code )) {

                            //
                    $sqlGetSetting = "SELECT * FROM app_setting WHERE id='1'";
                    $resultreGetSetting = mysqli_query($conn, $sqlGetSetting);
                    if (mysqli_num_rows($resultreGetSetting) > 0) {
                        $settingRow = mysqli_fetch_assoc($resultreGetSetting);
                        $sign_up_bonus = $settingRow["signup_bonus"];
                        $spin_ava_ch = $settingRow["spin_ava_ch"];
                        //all list
                    }



                    $sql = "INSERT INTO users(name, email, password, username, phone, user_country, device_id, refer, user_id, image , points, spin_count  )
                    VALUES ('$name', '$email', '$password', '$username', '$phone', '$user_country', '$device_id', '$refer', '$user_id', '$imageName' , '$sign_up_bonus' , '$spin_ava_ch')";
        
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

                    }else{

                        $sqlrefergetu = "SELECT * FROM users WHERE refer='$refer_code'";
                        // Execute the query
                        $resultreferuser = mysqli_query($conn, $sqlrefergetu);
                                            
                        if (mysqli_num_rows($resultreferuser) > 0) {
                        while ($row = mysqli_fetch_assoc($resultreferuser)) {
                           
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
    
    
                        $sqlrefergetu = "SELECT * FROM users WHERE refer='$refer_code'";
                        // Execute the query
                        $resultreferuser = mysqli_query($conn, $sqlrefergetu);
                                            
                        if (mysqli_num_rows($resultreferuser) > 0) {
                        while ($row = mysqli_fetch_assoc($resultreferuser)) {
                            $currentPoints = $row["points"];
                            $user_id_refer = $row["user_id"];

                            $sqlGetSetting = "SELECT * FROM app_setting WHERE id='1'";
                            $resultreGetSetting = mysqli_query($conn, $sqlGetSetting);
                            if (mysqli_num_rows($resultreGetSetting) > 0) {
                                $settingRow = mysqli_fetch_assoc($resultreGetSetting);
                                $refer_bonus = $settingRow["refer_bonus"];
                            }

                            
                                // Calculate the new point value
                                $newPoints = $currentPoints + $refer_bonus;
                                $updateSql = "UPDATE users SET points = $newPoints, total_refer = total_refer + 1 WHERE refer='$refer_code'";
                                $updateResult = mysqli_query($conn, $updateSql);
                                
                                if ($updateResult) {

                                    $type_earn = 'Refer Bonus';
                            
                                    $sql = "INSERT INTO user_tracking(user_id, points, type_earn )
                                    VALUES ('$user_id_refer', '$refer_bonus', '$type_earn')";

                                    $result = mysqli_query($conn, $sql);
                                    if ($result) {
                                        echo "Registration Success";
                                    }else {
                                        echo 'error';
                                    }



                                } else {
                                    // Failed to update points
                                    echo "Failed to update points.";
                                }


                           
                        }
                        } else {
                        echo "No results found.";
                        }
                       

                        
                    }


                        }


                        
                      }else{
                        echo 'invalid referral code';
                      }

                       
                    }




                }
            
            
            }


        }

    }

} else {
    echo "invalid Request";
}
?>
