package com.burhanstore.bubblereward.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

public class NetworkUtils {

    public static boolean isSlowConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            Network network = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                network = connectivityManager.getActiveNetwork();
            }
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);

            if (capabilities != null) {
                // Check if the network speed is slower than a certain threshold (e.g., 1 Mbps)
                return capabilities.getLinkDownstreamBandwidthKbps() < 1000; // 1000 Kbps = 1 Mbps
            }
        }
        return false; // Unable to determine connection speed
    }
}
