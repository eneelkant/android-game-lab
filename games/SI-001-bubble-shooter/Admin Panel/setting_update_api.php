
<?php include 'main_app.php'; ?>


<?php
include('connect.php');

// Retrieve the form data
if ($_SERVER["REQUEST_METHOD"] === "POST") {

    $id = '1';


    $signup_bonus = $_POST['signup_bonus'] ?? '';
    $refer_bonus = $_POST['refer_bonus'] ?? '';
    $privacy_policy = $_POST['privacy_policy'] ?? '';
    $one_signal_app_id = $_POST['one_signal_app_id'] ?? '';
    $os_rest_api = $_POST['os_rest_api'] ?? '';
    $daily_check_rewards = $_POST['daily_check_rewards'] ?? '';
    $int_ads_count = $_POST['int_ads_count'] ?? '';
    $minas_reward_point = $_POST['minas_reward_point'] ?? '';
    $refer_page_text = $_POST['refer_page_text'] ?? '';
    $startapp_id = $_POST['startapp_id'] ?? '';
    $admob_video_ads_id = $_POST['admob_video_ads_id'] ?? '';
    $vungle_key = $_POST['vungle_key'] ?? '';
    $vungle_int_ads_id = $_POST['vungle_int_ads_id'] ?? '';
    $adcolony_app_id = $_POST['adcolony_app_id'] ?? '';
    $adcolony_reward_zone_id = $_POST['adcolony_reward_zone_id'] ?? '';
    $adcolony_int_zone_id = $_POST['adcolony_int_zone_id'] ?? '';
    $admob_int_ads_id = $_POST['admob_int_ads_id'] ?? '';
    $ironsource_app_key = $_POST['ironsource_app_key'] ?? '';
    $unity_game_id = $_POST['unity_game_id'] ?? '';
    $spin_int_ads = $_POST['spin_int_ads'] ?? '';
    $spin_video_ads = $_POST['spin_video_ads'] ?? '';
    $maintenance_popup = $_POST['maintenance_popup'] ?? '';
    $maintenance_popup_message = $_POST['maintenance_popup_message'] ?? '';
    $maintenance_popup_url = $_POST['maintenance_popup_url'] ?? '';
    $maintenance_popup_url_control = $_POST['maintenance_popup_url_control'] ?? '';
    $home_dialog_show = $_POST['home_dialog_show'] ?? '';
    $home_dialog_message = $_POST['home_dialog_message'] ?? '';
    $home_dialog_url = $_POST['home_dialog_url'] ?? '';
    $home_dialog_url_control = $_POST['home_dialog_url_control'] ?? '';
    $game_complete_to_show = $_POST['game_complete_to_show'] ?? '';
    $game_lvl_show_ads_int = $_POST['game_lvl_show_ads_int'] ?? '';
    $spin_ava_ch = $_POST['spin_ava_ch'] ?? '';
    $game_level_com_re = $_POST['game_level_com_re'] ?? '';
    $game_lvl_show_ads_video = $_POST['game_lvl_show_ads_video'] ?? '';
    $admob_banner_id = $_POST['admob_banner_id'] ?? '';
    $daily_checking_video_ads = $_POST['daily_checking_video_ads'] ?? '';
    
    $conn = new mysqli($servername, $username, $password, $dbname);
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    $sql = "UPDATE app_setting SET 
    
    signup_bonus = '$signup_bonus',
    refer_bonus = '$refer_bonus',
    privacy_policy = '$privacy_policy',
    one_signal_app_id = '$one_signal_app_id',
    os_rest_api = '$os_rest_api',
    daily_check_rewards = '$daily_check_rewards',
    int_ads_count = '$int_ads_count',
    minas_reward_point = '$minas_reward_point',
    refer_page_text = '$refer_page_text',
    startapp_id = '$startapp_id',
    admob_video_ads_id = '$admob_video_ads_id',
    vungle_key = '$vungle_key',
    vungle_int_ads_id = '$vungle_int_ads_id',
    adcolony_app_id = '$adcolony_app_id',
    adcolony_reward_zone_id = '$adcolony_reward_zone_id',
    adcolony_int_zone_id = '$adcolony_int_zone_id',
    admob_int_ads_id = '$admob_int_ads_id',
    ironsource_app_key = '$ironsource_app_key',
    unity_game_id = '$unity_game_id',
    spin_int_ads = '$spin_int_ads',
    spin_video_ads = '$spin_video_ads',
    maintenance_popup = '$maintenance_popup',
    maintenance_popup_message = '$maintenance_popup_message',
    maintenance_popup_url = '$maintenance_popup_url',
    maintenance_popup_url_control = '$maintenance_popup_url_control',
    home_dialog_show = '$home_dialog_show',
    home_dialog_message = '$home_dialog_message',
    home_dialog_url = '$home_dialog_url',
    home_dialog_url_control = '$home_dialog_url_control',
    game_complete_to_show = '$game_complete_to_show',
    game_lvl_show_ads_int = '$game_lvl_show_ads_int',
    spin_ava_ch = '$spin_ava_ch',
    game_level_com_re = '$game_level_com_re',
    game_lvl_show_ads_video = '$game_lvl_show_ads_video',
    admob_banner_id = '$admob_banner_id',
    daily_checking_video_ads = '$daily_checking_video_ads'
 
     WHERE id = '$id'";
    $stmt = $conn->prepare($sql);

    if ($stmt === false) {
        die("Error: " . $conn->error);
    }


  
    if ($stmt->execute()) {
        header('Location: app_settings.php');
    } else {
        header('Location: app_settings.php');
    }
}
?>

 