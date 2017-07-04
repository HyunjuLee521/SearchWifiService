package com.hj.user.searchwifiservice.models;

/**
 * Created by USER on 2017-03-28.
 */

public class WifiInfo {
    private PublicWifiPlaceInfo PublicWiFiPlaceInfo;

    public PublicWifiPlaceInfo getPublicWiFiPlaceInfo() {
        return PublicWiFiPlaceInfo;
    }

    public void setPublicWiFiPlaceInfo(PublicWifiPlaceInfo publicWiFiPlaceInfo) {
        PublicWiFiPlaceInfo = publicWiFiPlaceInfo;
    }

    @Override
    public String toString() {
        return "WifiInfo{" +
                "PublicWifiPlaceInfo=" + PublicWiFiPlaceInfo +
                '}';
    }
}
