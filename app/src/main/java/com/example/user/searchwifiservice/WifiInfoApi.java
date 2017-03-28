package com.example.user.searchwifiservice;

import com.example.user.searchwifiservice.models.WIfiInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by USER on 2017-03-27.
 */

public interface WifiInfoApi {

    // http://openAPI.seoul.go.kr:8088/774f70747462736736326e426e7947/json/PublicWiFiPlaceInfo/1/300/강남구

    String BASE_URL = "http://openAPI.seoul.go.kr:8088/774f70747462736736326e426e7947/json/PublicWiFiPlaceInfo/";

    @GET("1/{END_INDEX}/{GU_NM}")
    Call<WIfiInfo> getWifiInfo(@Path("END_INDEX") String END_INDEX,
                               @Path("GU_NM") String GU_NM);


}