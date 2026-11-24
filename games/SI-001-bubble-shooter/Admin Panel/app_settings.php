<?php include 'main_app.php'; ?>
<?php 

include 'header.php' ;
include 'connect.php' ;
 
  
$id = 1; // Change this to the desired ID
$sql = "SELECT * FROM app_setting WHERE id = $id";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Output data of each row
    while ($row = $result->fetch_assoc()) {
    
        $signup_bonus = $row["signup_bonus"];
        $refer_bonus = $row["refer_bonus"];
        $privacy_policy = $row["privacy_policy"];
        $one_signal_app_id = $row['one_signal_app_id'];
        $os_rest_api = $row["os_rest_api"];
        $daily_check_rewards = $row["daily_check_rewards"];
        $int_ads_count = $row["int_ads_count"];
        $minas_reward_point = $row["minas_reward_point"];
        $refer_page_text = $row["refer_page_text"];
        $startapp_id = $row["startapp_id"];
        $admob_video_ads_id = $row["admob_video_ads_id"];
        $vungle_key = $row["vungle_key"];
        $vungle_int_ads_id = $row["vungle_int_ads_id"];
        $adcolony_app_id = $row["adcolony_app_id"];
        $adcolony_reward_zone_id = $row["adcolony_reward_zone_id"];
        $adcolony_int_zone_id = $row["adcolony_int_zone_id"];
        $admob_int_ads_id = $row["admob_int_ads_id"];
        $ironsource_app_key = $row["ironsource_app_key"];
        $unity_game_id = $row["unity_game_id"];
        $spin_int_ads = $row["spin_int_ads"];
        $spin_video_ads = $row["spin_video_ads"];
        $maintenance_popup = $row["maintenance_popup"];
        $maintenance_popup_message = $row["maintenance_popup_message"];
        $maintenance_popup_url = $row["maintenance_popup_url"];
        $maintenance_popup_url_control = $row["maintenance_popup_url_control"];
        $home_dialog_show = $row["home_dialog_show"];
        $home_dialog_message = $row["home_dialog_message"];
        $home_dialog_url = $row["home_dialog_url"];
        $home_dialog_url_control = $row["home_dialog_url_control"];
        $game_complete_to_show = $row["game_complete_to_show"];
        $game_lvl_show_ads_int = $row["game_lvl_show_ads_int"];
        $spin_ava_ch = $row["spin_ava_ch"];
        $game_level_com_re = $row["game_level_com_re"];
        $game_lvl_show_ads_video = $row["game_lvl_show_ads_video"];
        $admob_banner_id = $row["admob_banner_id"];
        $daily_checking_video_ads = $row["daily_checking_video_ads"];
        
    }
} else {
    echo "No results found.";
}
 
 
//ads id
 
 

?>








<div class="modal-body">


<form action="setting_update_api.php" method="POST" enctype="multipart/form-data">
   


   

    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">Signup Bonus</label>
            <input type="text" name="signup_bonus" id="nameBasic" class="form-control"  value="<?php  echo $signup_bonus ?>" placeholder="<?php  echo $signup_bonus ?>" />
        </div>
    </div>

    

    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">Refer Bonus</label>
            <input type="text" name="refer_bonus" id="nameBasic" class="form-control"  value="<?php  echo $refer_bonus ?>" placeholder="<?php  echo $refer_bonus ?>" />
        </div>
    </div>

    


    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label"> Privacy Policy </label>
            <input type="text" name="privacy_policy" id="nameBasic" class="form-control"  value="<?php  echo $privacy_policy ?>" placeholder="<?php  echo $privacy_policy ?>" />
        </div>
    </div>

    



    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label"> OneSignal App ID </label>
            <input type="text" name="one_signal_app_id" id="nameBasic" class="form-control"  value="<?php  echo $one_signal_app_id ?>" placeholder="<?php  echo $one_signal_app_id ?>" />
        </div>
    </div>

    


    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label"> OneSignal Rest API </label>
            <input type="text" name="os_rest_api" id="nameBasic" class="form-control"  value="<?php  echo $os_rest_api ?>" placeholder="<?php  echo $os_rest_api ?>" />
        </div>
    </div>

    


    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label"> Daily Check Rewards</label>
            <input type="text" name="daily_check_rewards" id="nameBasic" class="form-control"  value="<?php  echo $daily_check_rewards ?>" placeholder="<?php  echo $daily_check_rewards ?>" />
        </div>
    </div>

 

    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Interstitial Ads Count </label>
            <input type="text" name="int_ads_count" id="nameBasic" class="form-control"  value="<?php  echo $int_ads_count ?>" placeholder="<?php  echo $int_ads_count ?>" />
        </div>
    </div>

    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Mians Reards Point </label>
            <input type="text" name="minas_reward_point" id="nameBasic" class="form-control"  value="<?php  echo $minas_reward_point ?>" placeholder="<?php  echo $minas_reward_point ?>" />
        </div>
    </div>


       
    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label"> Spin Available Chance </label>
            <input type="text" name="spin_ava_ch" id="nameBasic" class="form-control"  value="<?php  echo $spin_ava_ch ?>" placeholder="<?php  echo $spin_ava_ch ?>" />
        </div>
    </div>

    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label"> Game Complete To Reward </label>
            <input type="text" name="game_level_com_re" id="nameBasic" class="form-control"  value="<?php  echo $game_level_com_re ?>" placeholder="<?php  echo $game_level_com_re ?>" />
        </div>
    </div>


    
    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Refer Page Text </label>
            <input type="text" name="refer_page_text" id="nameBasic" class="form-control"  value="<?php  echo $refer_page_text ?>" placeholder="<?php  echo $refer_page_text ?>" />
        </div>
    </div>

    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  StartApp ID </label>
            <input type="text" name="startapp_id" id="nameBasic" class="form-control"  value="<?php  echo $startapp_id ?>" placeholder="<?php  echo $startapp_id ?>" />
        </div>
    </div>



    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Admob Video Ads ID </label>
            <input type="text" name="admob_video_ads_id" id="nameBasic" class="form-control"  value="<?php  echo $admob_video_ads_id ?>" placeholder="<?php  echo $admob_video_ads_id ?>" />
        </div>
    </div>


    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Admob Interstitial Ads ID </label>
            <input type="text" name="admob_int_ads_id" id="nameBasic" class="form-control"  value="<?php  echo $admob_int_ads_id ?>" placeholder="<?php  echo $admob_int_ads_id ?>" />
        </div>
    </div>



    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Admob Banner Ads ID </label>
            <input type="text" name="admob_banner_id" id="nameBasic" class="form-control"  value="<?php  echo $admob_banner_id ?>" placeholder="<?php  echo $admob_banner_id ?>" />
        </div>
    </div>





 

    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Vungle Key</label>
            <input type="text" name="vungle_key" id="nameBasic" class="form-control"  value="<?php  echo $vungle_key ?>" placeholder="<?php  echo $vungle_key ?>" />
        </div>
    </div>

    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Vungle Interstitial Ads ID</label>
            <input type="text" name="vungle_int_ads_id" id="nameBasic" class="form-control"  value="<?php  echo $vungle_int_ads_id ?>" placeholder="<?php  echo $vungle_int_ads_id ?>" />
        </div>
    </div>

    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Adcolony App Id </label>
            <input type="text" name="adcolony_app_id" id="nameBasic" class="form-control"  value="<?php  echo $adcolony_app_id ?>" placeholder="<?php  echo $adcolony_app_id ?>" />
        </div>
    </div>

    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Adcolony Reward Zone Id </label>
            <input type="text" name="adcolony_reward_zone_id" id="nameBasic" class="form-control"  value="<?php  echo $adcolony_reward_zone_id ?>" placeholder="<?php  echo $adcolony_reward_zone_id ?>" />
        </div>
    </div>


    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Adcolony Interstitial Zone Id </label>
            <input type="text" name="adcolony_int_zone_id" id="nameBasic" class="form-control"  value="<?php  echo $adcolony_int_zone_id ?>" placeholder="<?php  echo $adcolony_int_zone_id ?>" />
        </div>
    </div>




    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  IronSource App Key</label>
            <input type="text" name="ironsource_app_key" id="nameBasic" class="form-control"  value="<?php  echo $ironsource_app_key ?>" placeholder="<?php  echo $ironsource_app_key ?>" />
        </div>
    </div>



    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Unity Game ID </label>
            <input type="text" name="unity_game_id" id="nameBasic" class="form-control"  value="<?php  echo $unity_game_id ?>" placeholder="<?php  echo $unity_game_id ?>" />
        </div>
    </div>


 
 
    
 
    <div class="row">
       <div class="col mb-3">
            <label for="nameBasic" class="form-label"> Maintenance Popup Dailog Show/Hide</label>
            <select name="maintenance_popup" class="form-control">
                <option value="<?= $maintenance_popup ?? '' ?>"><?= $maintenance_popup ?? '' ?></option>      
                <option value="true">true</option>
                <option value="false">false</option>
              
            </select>
        </div>
    </div>
 
    

    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Maintenance Popup Message </label>
            <input type="text" name="maintenance_popup_message" id="nameBasic" class="form-control"  value="<?php  echo $maintenance_popup_message ?>" placeholder="<?php  echo $maintenance_popup_message ?>" />
        </div>
    </div>

    


    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label"> Maintenance Popup Url </label>
            <input type="text" name="maintenance_popup_url" id="nameBasic" class="form-control"  value="<?php  echo $maintenance_popup_url ?>" placeholder="<?php  echo $maintenance_popup_url ?>" />
        </div>
    </div>
 
    <div class="row">
       <div class="col mb-3">
            <label for="nameBasic" class="form-label"> Maintenance Popup Url Control </label>
            <select name="maintenance_popup_url_control" class="form-control">
                <option value="<?= $maintenance_popup_url_control ?? '' ?>"><?= $maintenance_popup_url_control ?? '' ?></option>      
                <option value="true">true</option>
                <option value="false">false</option>
              
            </select>
        </div>
    </div>




    <div class="row">
       <div class="col mb-3">
            <label for="nameBasic" class="form-label">Home Dialog Show Hide/Show</label>
            <select name="home_dialog_show" class="form-control">
                <option value="<?= $home_dialog_show ?? '' ?>"><?= $home_dialog_show ?? '' ?></option>      
                <option value="true">true</option>
                <option value="false">false</option>
              
            </select>
        </div>
    </div>



    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Home Dialog Message </label>
            <input type="text" name="home_dialog_message" id="nameBasic" class="form-control"  value="<?php  echo $home_dialog_message ?>" placeholder="<?php  echo $home_dialog_message ?>" />
        </div>
    </div>

    <div class="row">
        <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Home Dialog url </label>
            <input type="text" name="home_dialog_url" id="nameBasic" class="form-control"  value="<?php  echo $home_dialog_url ?>" placeholder="<?php  echo $home_dialog_url ?>" />
        </div>
    </div>

    <div class="row">
       <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Home Dailgo Url Control Open/Hide </label>
            <select name="home_dialog_url_control" class="form-control">
                <option value="<?= $home_dialog_url_control ?? '' ?>"><?= $home_dialog_url_control ?? '' ?></option>      
                <option value="true">true</option>
                <option value="false">false</option>
              
            </select>
        </div>
    </div>


    <div class="row">
       <div class="col mb-3">
           <label for="nameBasic" class="form-label">  Game level Complte to Interstitial/Video Ads </label>
            <select name="game_complete_to_show" class="form-control">
                <option value="<?= $game_complete_to_show ?? '' ?>"><?= $game_complete_to_show ?? '' ?></option>      
                <option value="Interstitial">Interstitial</option>
                <option value="video_ads">Video Ads</option>
              
            </select>
        </div>
    </div>

    

    <div class="row">
       <div class="col mb-3">
            <label for="nameBasic" class="form-label">  Game level Complte to show Video Ads </label>
            <select name="game_lvl_show_ads_video" class="form-control">
                <option value="<?= $game_lvl_show_ads_video ?? '' ?>"><?= $game_lvl_show_ads_video ?? '' ?></option>      
                <option value="Admob">Admob</option>
                <option value="Vungle">Vungle</option>
                <option value="UnityAds">UnityAds</option>
                <option value="AdColony">AdColony</option>
                <option value="Startapp">Startapp</option>
                <option value="applovin">applovin</option>
            </select>
        </div>
    </div>


    <div class="row">
       <div class="col mb-3">
       <label for="nameBasic" class="form-label">  Game level Complte to Show Interstitial Ads </label>
            <select name="game_lvl_show_ads_int" class="form-control">
                <option value="<?= $game_lvl_show_ads_int ?? '' ?>"><?= $game_lvl_show_ads_int ?? '' ?></option>      
                <option value="Admob">Admob</option>
                <option value="Vungle">Vungle</option>
                <option value="AdColony">AdColony</option>
                <option value="Startapp">Startapp</option>
                <option value="IronSource">IronSource</option>
            </select>
        </div>
    </div>
 

    
    <div class="row">
       <div class="col mb-3">
            <label for="nameBasic" class="form-label"> Spin Interstitial Ads </label>
            <select name="spin_int_ads" class="form-control">
                <option value="<?= $spin_int_ads ?? '' ?>"><?= $spin_int_ads ?? '' ?></option>      
                <option value="Admob">Admob</option>
                <option value="Vungle">Vungle</option>
                <option value="AdColony">AdColony</option>
                <option value="Startapp">Startapp</option>
                <option value="IronSource">IronSource</option>
            </select>
        </div>
    </div>
 
    <div class="row">
       <div class="col mb-3">
            <label for="nameBasic" class="form-label"> Spin Video Ads </label>
            <select name="spin_video_ads" class="form-control">
                <option value="<?= $spin_video_ads ?? '' ?>"><?= $spin_video_ads ?? '' ?></option>      
                <option value="Admob">Admob</option>
                <option value="Vungle">Vungle</option>
                <option value="UnityAds">UnityAds</option>
                <option value="AdColony">AdColony</option>
                <option value="Startapp">Startapp</option>
                <option value="applovin">applovin</option>
            </select>
        </div>
    </div>


    <div class="row">
       <div class="col mb-3">
            <label for="nameBasic" class="form-label"> Daily checking Video Ads</label>
            <select name="daily_checking_video_ads" class="form-control">
                <option value="<?= $daily_checking_video_ads ?? '' ?>"><?= $daily_checking_video_ads ?? '' ?></option>      
                <option value="Admob">Admob</option>
                <option value="Vungle">Vungle</option>
                <option value="UnityAds">UnityAds</option>
                <option value="AdColony">AdColony</option>
                <option value="Startapp">Startapp</option>
                <option value="applovin">applovin</option>
            </select>
        </div>
    </div>

 


   <input style="display: none;" name="product_id" value="<?php echo $id ?>" id="nameBasic" class="form-control" placeholder="Enter Ads Details" />
    


   <div class="modal-footer">
       <input style="display: none;" name="method" value="submit_ads" id="nameBasic" class="form-control" placeholder="Enter Ads Details" />
       <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
       <button type="submit" value="Submit" class="btn btn-primary">Save changes</button>
   </div>
</form>



           </div>



<?php include 'footer.php' ; ?>