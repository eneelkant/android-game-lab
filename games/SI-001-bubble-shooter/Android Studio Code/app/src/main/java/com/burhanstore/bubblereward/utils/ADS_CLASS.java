package com.burhanstore.bubblereward.utils;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyReward;
import com.adcolony.sdk.AdColonyRewardListener;
import com.adcolony.sdk.AdColonyZone;
import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinSdk;
import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.BuildConfig;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.ironsource.mediationsdk.IronSource;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.VideoListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.vungle.warren.AdConfig;
import com.vungle.warren.InitCallback;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.error.VungleException;

import java.util.Collection;

public class ADS_CLASS extends AppCompatActivity {

    private static StartAppAd startAppAd = new StartAppAd(AppController.getContext());
    public static RewardedAd rewardedAd;
    public static final String TAG = "MyActivity";

    public static Dialog dialog;
    private static AppLovinInterstitialAdDialog appLovinInterstitialAd;
    public static InterstitialAd interstitialAd;
    private static final String PLACEMENT_INTERSTITIAL = "DefaultInterstitial";

    public static void AdmobVideoAdLoad(Activity activity) {

        //    ads_id_controller = new Ads_ID_Controller(activity);
        if (rewardedAd == null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(
                    activity,
                    App_Settings.getString(activity, App_Settings.ADMOB_VIDEO_ADS_ID),
                    adRequest,
                    new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error.
                            Log.d(TAG, loadAdError.getMessage());
                            rewardedAd = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            ADS_CLASS.rewardedAd = rewardedAd;
                            Log.d(TAG, "onAdLoaded");
                        }
                    });
        }
    }

    public static void ShowAdmobVideo(Activity activity) {

        if (rewardedAd == null) {
            Log.d("TAG", "The rewarded ad wasn't ready yet. Please try Again");
            AdmobVideoAdLoad(activity);
            return;
        }
        rewardedAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "onAdShowedFullScreenContent");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        rewardedAd = null;
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        AdmobVideoAdLoad(activity);


                    }
                });

        Activity activityContext = activity.getParent();
        rewardedAd.show(
                activityContext,
                new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        AdmobVideoAdLoad(activity);
                    }
                });
    }


    private static String placementID, Vungel_InterstitialPlacementID;

    public static void initVungle(final Activity activity) {

        Vungle.init(App_Settings.getString(activity, App_Settings.VUNGLE_KEY), activity.getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {

                Collection<String> placements = Vungle.getValidPlacements();
                String[] placementsArray = placements.toArray(new String[0]);

                placementID = placementsArray[0];

            }

            @Override
            public void onError(VungleException e) {
                //   showToastMessage("SDK Init Error : " + e.getLocalizedMessage());
            }

            @Override
            public void onAutoCacheAdAvailable(String pid) {
                //      showToastMessage("Auto Cache Ad Available For Placement : " + pid);
            }
        });
    }

    public static void loadAdVungle(final Activity activity) {

        Vungle.loadAd(placementID, new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {
                //  Toast.makeText(activity, "Congratulation Ads Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String id, VungleException e) {

            }
        });

    }

    public static void playAdVungle(final Activity activity) {

        Vungle.playAd(placementID, new AdConfig(), new PlayAdCallback() {
            @Override
            public void onAdStart(String placementReferenceID) {
                //       showToastMessage("Ad Start");
            }

            @Override
            public void onAdViewed(String placementReferenceID) {
                //     showToastMessage("Ad Viewed");
            }

            // Deprecated
            @Override
            public void onAdEnd(String id, boolean completed, boolean isCTAClicked) {
                // showToastMessage("Ad End : Completed : " + completed + " Clicked : " + isCTAClicked);

            }

            @Override
            public void onAdEnd(String placementReferenceID) {
                //      showToastMessage("Ad End");

            }

            @Override
            public void onAdClick(String placementReferenceID) {
                //     showToastMessage("Ad Clicked");
            }

            @Override
            public void onAdRewarded(String placementReferenceID) {
                //    showToastMessage("User Rewarded");
            }

            @Override
            public void onAdLeftApplication(String placementReferenceID) {
                //     showToastMessage("User Left Application");

            }

            @Override
            public void creativeId(String creativeId) {
                //   showToastMessage("Will play creative " + creativeId);
            }

            @Override
            public void onError(String id, VungleException e) {
                //     showToastMessage("Ad Play Error : " + e.getLocalizedMessage());
            }
        });
    }


    public static void initVungleInterstitial() {

        Vungle.init(App_Settings.getString(AppController.getContext(), App_Settings.VUNGLE_KEY), AppController.getContext().getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {

                Collection<String> placements = Vungle.getValidPlacements();
                String[] placementsArray = placements.toArray(new String[0]);

                Vungel_InterstitialPlacementID = placementsArray[0];
            }

            @Override
            public void onError(VungleException e) {

            }

            @Override
            public void onAutoCacheAdAvailable(String pid) {
                //   showToastMessage("Auto Cache Ad Available For Placement : " + pid);
            }
        });
    }

    public static void loadVungleAdInterstitial() {

        Vungel_InterstitialPlacementID = App_Settings.getString(AppController.getContext(), App_Settings.VUNGLE_INT_ADS_ID);
        Vungle.loadAd(Vungel_InterstitialPlacementID, new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {

            }

            @Override
            public void onError(String id, VungleException e) {

            }
        });
    }

    public static void playVungleAdInterstitial() {

        Vungel_InterstitialPlacementID = App_Settings.getString(AppController.getContext(), App_Settings.VUNGLE_INT_ADS_ID);
        Vungle.playAd(Vungel_InterstitialPlacementID, new AdConfig(), new PlayAdCallback() {
            @Override
            public void onAdStart(String placementReferenceID) {
                //  showToastMessage("Ad Start");
            }

            @Override
            public void onAdViewed(String placementReferenceID) {

            }

            // Deprecated
            @Override
            public void onAdEnd(String id, boolean completed, boolean isCTAClicked) {


            }

            @Override
            public void onAdEnd(String placementReferenceID) {

            }

            @Override
            public void onAdClick(String placementReferenceID) {

            }

            @Override
            public void onAdRewarded(String placementReferenceID) {

            }

            @Override
            public void onAdLeftApplication(String placementReferenceID) {

            }

            @Override
            public void creativeId(String creativeId) {

            }

            @Override
            public void onError(String id, VungleException e) {

            }
        });


    }


    private static AdColonyInterstitial rewardAdColony;
    private static AdColonyInterstitialListener rewardListener;
    private static AdColonyAdOptions rewardAdOptions;
    private static boolean isRewardLoaded;
    private static AdColonyInterstitial interstitialAdColony;
    private static boolean isInterstitialLoaded;
    private static AdColonyAdOptions interstitialAdOptions;
    private static AdColonyInterstitialListener interstitialListener;

    //Adcolony
    public static void initRewardedAdAdColony(final Activity activity) {

        AdColonyAppOptions appOptions = new AdColonyAppOptions().setKeepScreenOn(true);
        AdColony.configure((Activity) activity, appOptions, App_Settings.getString(activity, App_Settings.ADCOLONY_APP_ID), App_Settings.getString(activity, App_Settings.ADCOLONY_REWARD_ZONE_ID), App_Settings.getString(activity, App_Settings.ADCOLONY_INT_ZONE_ID));

        AdColony.setRewardListener(new AdColonyRewardListener() {
            @Override
            public void onReward(AdColonyReward reward) {
                if (reward.success()) {
                    Toast.makeText(activity, "Reward Earned", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Reward Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rewardListener = new AdColonyInterstitialListener() {
            @Override
            public void onRequestFilled(AdColonyInterstitial adReward) {
                rewardAdColony = adReward;
                isRewardLoaded = true;
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
            }

            @Override
            public void onOpened(AdColonyInterstitial ad) {
                super.onOpened(ad);
                //   dialogAdcolony.dismiss();
            }

            @Override
            public void onClosed(AdColonyInterstitial ad) {
                super.onClosed(ad);
                AdColony.requestInterstitial(App_Settings.getString(activity, App_Settings.ADCOLONY_REWARD_ZONE_ID), rewardListener, rewardAdOptions);

            }

            @Override
            public void onClicked(AdColonyInterstitial ad) {
                super.onClicked(ad);
            }

            @Override
            public void onLeftApplication(AdColonyInterstitial ad) {
                super.onLeftApplication(ad);
            }

            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                super.onExpiring(ad);
            }
        };
        rewardAdOptions = new AdColonyAdOptions()
                .enableConfirmationDialog(false)
                .enableResultsDialog(false);
        AdColony.requestInterstitial(App_Settings.getString(activity, App_Settings.ADCOLONY_REWARD_ZONE_ID), rewardListener, rewardAdOptions);


    }

    public static void AdcolonyDisplayInterstitialAd() {
        if (interstitialAdColony != null && isInterstitialLoaded) {
            interstitialAdColony.show();
            isInterstitialLoaded = false;
        } else {

        }
    }

    public static void AdcolonyInitInterstitialAd() {

        interstitialListener = new AdColonyInterstitialListener() {
            @Override
            public void onRequestFilled(AdColonyInterstitial adIn) {
                interstitialAdColony = adIn;
                isInterstitialLoaded = true;
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                super.onRequestNotFilled(zone);
            }

            @Override
            public void onOpened(AdColonyInterstitial ad) {
                super.onOpened(ad);
            }

            @Override
            public void onClosed(AdColonyInterstitial ad) {
                super.onClosed(ad);
                AdColony.requestInterstitial(App_Settings.getString(AppController.getContext(), App_Settings.ADCOLONY_INT_ZONE_ID), interstitialListener, interstitialAdOptions);
            }

            @Override
            public void onClicked(AdColonyInterstitial ad) {
                super.onClicked(ad);
            }

            @Override
            public void onLeftApplication(AdColonyInterstitial ad) {
                super.onLeftApplication(ad);
            }

            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                super.onExpiring(ad);
            }
        };
        interstitialAdOptions = new AdColonyAdOptions();
        AdColony.requestInterstitial(App_Settings.getString(AppController.getContext(), App_Settings.ADCOLONY_INT_ZONE_ID), interstitialListener, interstitialAdOptions);
    }


    public static void ShowAdcolonyAds(final Activity activity) {


        if (rewardAdColony != null && isRewardLoaded) {
            rewardAdColony.show();
        } else {
            Toast.makeText(activity, "Reward Ad Is Not Loaded Yet Please Try Again ", Toast.LENGTH_SHORT).show();
        }

    }


    //
    public static void StartappRewardedVideo(final Activity context) {

        final StartAppAd rewardedVideo = new StartAppAd(context);
        rewardedVideo.setVideoListener(new VideoListener() {
            @Override
            public void onVideoCompleted() {
                Toast.makeText(context, "Video Completed", Toast.LENGTH_SHORT).show();
                // Grant the reward to user
            }
        });

        rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
            @Override
            public void onReceiveAd(com.startapp.sdk.adsbase.Ad ad) {
                rewardedVideo.showAd();

            }

            @Override
            public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad ad) {

            }
        });
    }


    //

    public static void ApplovinShowAds(final Activity context) {


        appLovinInterstitialAd = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(context), context);
        appLovinInterstitialAd.show();
        appLovinInterstitialAd.setAdDisplayListener(new AppLovinAdDisplayListener() {
            @Override
            public void adDisplayed(AppLovinAd ad) {

            }

            @Override
            public void adHidden(AppLovinAd ad) {

            }
        });

    }


    public static void LoadStartAppInterstitial(Activity activity) {
        StartAppSDK.init(activity, App_Settings.getString(activity, App_Settings.STARTAPP_ID), true);
        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);
        StartAppAd.disableSplash();
        StartAppAd.enableConsent(activity, false);
        StartAppSDK.setTestAdsEnabled(false);

        startAppAd.loadAd();
    }



    //


    public static void loadAdmobAdINT(Activity activity) {

        String AD_UNIT_ID = App_Settings.getString(activity, App_Settings.INT_ADMOB_ADS_ID);
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                activity,
                AD_UNIT_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        ADS_CLASS.interstitialAd = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {

                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {


                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        interstitialAd = null;
                    }
                });
    }

    public static void ShowAdmobAdsINT(Activity activity) {
        if (interstitialAd != null) {
            interstitialAd.show(activity);
            loadAdmobAdINT(activity);
        } else {
            //        Toast.makeText(activity, "Ad did not load", Toast.LENGTH_SHORT).show();

        }
    }

    //
    public static void startAppInterstitialShow() {
        startAppAd.showAd();
    }


    //
    public static void IronSourceInterstitialShow() {
        IronSource.showInterstitial(PLACEMENT_INTERSTITIAL);
    }


    public static void IronSourceInterstitialInt(Activity activity) {

        IronSource.init(activity,App_Settings.getString(activity, App_Settings.IRONSOURCE_APP_KEY), IronSource.AD_UNIT.INTERSTITIAL);
        IronSource.loadInterstitial();
    }


}
