package com.hj.user.searchwifiservice.models;

/**
 * Created by USER on 2017-03-28.
 */

public class WIfiInfo {
    private PublicWiFiPlaceInfo PublicWiFiPlaceInfo;

    public com.hj.user.searchwifiservice.models.PublicWiFiPlaceInfo getPublicWiFiPlaceInfo() {
        return PublicWiFiPlaceInfo;
    }

    public void setPublicWiFiPlaceInfo(com.hj.user.searchwifiservice.models.PublicWiFiPlaceInfo publicWiFiPlaceInfo) {
        PublicWiFiPlaceInfo = publicWiFiPlaceInfo;
    }

    @Override
    public String toString() {
        return "WIfiInfo{" +
                "PublicWiFiPlaceInfo=" + PublicWiFiPlaceInfo +
                '}';
    }
}
