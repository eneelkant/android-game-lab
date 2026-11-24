<?php
include('../connect.php');


$id = '1';

$sql = "SELECT * FROM app_setting WHERE id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    // Fetch the data from the result set
    $row = $result->fetch_assoc();

    $response = array(
        'signup_bonus' => $row['signup_bonus'],
        'refer_bonus' => $row['refer_bonus'],
        'privacy_policy' => $row['privacy_policy'],
        'one_signal_app_id' => $row['one_signal_app_id'],
        'daily_check_rewards' => $row['daily_check_rewards'],
        'int_ads_count' => $row['int_ads_count'],
        'minas_reward_point' => $row['minas_reward_point'],
        'block_rooted_devices' => $row['block_rooted_devices'],
        'block_vpn_access' => $row['block_vpn_access'],
        'block_app_clone' => $row['block_app_clone'],
        'refer_page_text' => $row['refer_page_text'],
        'startapp_id' => $row['startapp_id'],
        'admob_video_ads_id' => $row['admob_video_ads_id'],
        'vungle_key' => $row['vungle_key'],
        'vungle_int_ads_id' => $row['vungle_int_ads_id'],
        'adcolony_app_id' => $row['adcolony_app_id'],
        'adcolony_reward_zone_id' => $row['adcolony_reward_zone_id'],
        'adcolony_int_zone_id' => $row['adcolony_int_zone_id'],
        'admob_int_ads_id' => $row['admob_int_ads_id'],
        'ironsource_app_key' => $row['ironsource_app_key'],
        'unity_game_id' => $row['unity_game_id'],
        'spin_int_ads' => $row['spin_int_ads'],
        'spin_video_ads' => $row['spin_video_ads'],
        'maintenance_popup' => $row['maintenance_popup'],
        'maintenance_popup_message' => $row['maintenance_popup_message'],
        'maintenance_popup_url' => $row['maintenance_popup_url'],
        'maintenance_popup_url_control' => $row['maintenance_popup_url_control'],
        'home_dialog_show' => $row['home_dialog_show'],
        'home_dialog_message' => $row['home_dialog_message'],
        'home_dialog_url' => $row['home_dialog_url'],
        'home_dialog_url_control' => $row['home_dialog_url_control'],
        'game_complete_to_show' => $row['game_complete_to_show'],
        'game_lvl_show_ads_int' => $row['game_lvl_show_ads_int'],
        'spin_ava_ch' => $row['spin_ava_ch'],
        'game_level_com_re' => $row['game_level_com_re'],
        'admob_banner_id' => $row['admob_banner_id']

    );

    echo json_encode($response);
} else {
    // Return an empty JSON array
    echo json_encode(array());
}
 
?>
