package com.hj.user.searchwifiservice;

import com.hj.user.searchwifiservice.models.WifiInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by USER on 2017-03-27.
 */

public interface WifiInfoApi {

    // http://openAPI.seoul.go.kr:8088/774f70747462736736326e426e7947/json/PublicWiFiPlaceInfo/1/300/강남구

    String BASE_URL = "http://openAPI.seoul.go.kr:8088/774f70747462736736326e426e7947/json/PublicWifiPlaceInfo/";

    @GET("{START_INDEX}/{END_INDEX}/{GU_NM}")
    Call<WifiInfo> getWifiInfo(@Path("START_INDEX") String START_INDEX,
                               @Path("END_INDEX") String END_INDEX,
                               @Path("GU_NM") String GU_NM);


}
