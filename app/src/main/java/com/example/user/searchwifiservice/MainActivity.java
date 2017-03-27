package com.example.user.searchwifiservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.searchwifiservice.adapters.ListviewAdapter;
import com.example.user.searchwifiservice.models.PublicWiFiPlaceInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageView mSearchImageview;
    private EditText mSearchEdittext;
    private ListView mListview;
    private ListviewAdapter adapter;
    private PublicWiFiPlaceInfo mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchImageview = (ImageView) findViewById(R.id.search_imageview);
        mSearchEdittext = (EditText) findViewById(R.id.search_edittext);
        mListview = (ListView) findViewById(R.id.list_view);

        // 데이터 파싱하여 넣기



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WifiInfoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WifiInfoApi wifiInfoApi = retrofit.create(WifiInfoApi.class);
        wifiInfoApi.getWifiInfoList("5", "강남구").enqueue(new Callback<PublicWiFiPlaceInfo>() {
            @Override
            public void onResponse(Call<PublicWiFiPlaceInfo> call, Response<PublicWiFiPlaceInfo> response) {
                mData = response.body();
            }

            @Override
            public void onFailure(Call<PublicWiFiPlaceInfo> call, Throwable t) {

            }
        });


        if (mData == null) {
            Toast.makeText(this, "들어온 데이터값이 없다 파싱 실패", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, mData.getRow().toString() + "", Toast.LENGTH_SHORT).show();

        }


//        adapter = new ListviewAdapter(tempData);
//        mListview.setAdapter(adapter);


    }
}
