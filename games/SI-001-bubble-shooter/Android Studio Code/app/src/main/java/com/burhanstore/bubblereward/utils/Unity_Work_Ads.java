package com.burhanstore.bubblereward.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.burhanstore.bubblereward.AppController;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

public class Unity_Work_Ads implements IUnityAdsInitializationListener {


    private Context context;
    private Boolean testMode = false;
    private String adUnitId = "Rewarded_Android";

    private IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
        @Override
        public void onUnityAdsAdLoaded(String placementId) {
            UnityAds.show((Activity) context, adUnitId, new UnityAdsShowOptions(), showListener);
        }

        @Override
        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {

        }

        // Other loadListener methods...

    };

    private IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
        @Override
        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
            AppController.DismissLoding();
        }

        @Override
        public void onUnityAdsShowStart(String placementId) {
            AppController.DismissLoding();
        }

        @Override
        public void onUnityAdsShowClick(String placementId) {

        }

        @Override
        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {


        }
        // showListener methods...
    };

    public Unity_Work_Ads(Context context) {
        this.context = context;
    }

    public void initializeUnityAds(String gameId) {
        UnityAds.initialize(context, gameId, testMode, this);
    }

    @Override
    public void onInitializationComplete() {

        //  loadRewardedAd();
    }

    @Override
    public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
        Log.e("UnityAdsExample", "Unity Ads initialization failed with error: [" + error + "] " + message);
    }

    public void loadRewardedAd() {
        UnityAds.load(adUnitId, loadListener);
    }
}


