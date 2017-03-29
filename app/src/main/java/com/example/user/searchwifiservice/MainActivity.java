package com.example.user.searchwifiservice;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.searchwifiservice.adapters.ListviewAdapter;
import com.example.user.searchwifiservice.models.Info;
import com.example.user.searchwifiservice.models.Row;
import com.example.user.searchwifiservice.models.WIfiInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private ImageView mSearchImageview;
    private EditText mSearchEdittext;
    private ListView mListview;
    private ListviewAdapter adapter;
    private WIfiInfo mData;

    private double mLatitude;
    private double mLongitude;
    private GoogleApiClient mGoogleApiClient;
    // 현재 위치
    private Location mLastLocation;

    private Realm mRealm;
    private Button mUpdateButton;


    private WifiInfoApi wifiInfoApi1;
    private WifiInfoApi wifiInfoApi2;
    private WifiInfoApi wifiInfoApi3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 렘 초기화
        mRealm = Realm.getDefaultInstance();


        mListview = (ListView) findViewById(R.id.list_view);
        mUpdateButton = (Button) findViewById(R.id.update_button);

        // WIfiInfo 데이터 파싱하여 넣기
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WifiInfoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        wifiInfoApi1 = retrofit.create(WifiInfoApi.class);
        wifiInfoApi2 = retrofit.create(WifiInfoApi.class);
        wifiInfoApi3 = retrofit.create(WifiInfoApi.class);


        // TODO 오래걸린다 -> 비동기로?
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        mRealm.where(Info.class).findAll().deleteAllFromRealm();
                    }
                });


                // 1 ~ 1000
                wifiInfoApi1.getWifiInfo("1", "1000", "").enqueue(new Callback<WIfiInfo>() {
                    @Override
                    public void onResponse(Call<WIfiInfo> call, Response<WIfiInfo> response) {
                        mData = response.body();

                        if (mData != null) {
//                            adapter = new ListviewAdapter(mData.getPublicWiFiPlaceInfo().getRow());
//                            mListview.setAdapter(adapter);

                            for (int i = 0; i < 1000; i++) {
                                Row iData = mData.getPublicWiFiPlaceInfo().getRow().get(i);

                                final String PLACE_NAME = iData.getPLACE_NAME();
                                final String CATEGORY = iData.getCATEGORY();
                                final String GU_NM = iData.getGU_NM();
                                final String INSTL_DIV = iData.getINSTL_DIV();
                                final String INSTL_X = iData.getINSTL_X();
                                final String INSTL_Y = iData.getINSTL_Y();


                                mRealm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {

                                        Info info = mRealm.createObject(Info.class);

                                        info.setCATEGORY(CATEGORY);
                                        info.setGU_NM(GU_NM);
                                        info.setINSTL_DIV(INSTL_DIV);
                                        info.setINSTL_X(INSTL_X);
                                        info.setINSTL_Y(INSTL_Y);
                                        info.setPLACE_NAME(PLACE_NAME);

                                        // TODO id값 부여
                                        Number currentIdNum = mRealm.where(Info.class).max("id");
                                        int nextId;
                                        if (currentIdNum == null) {
                                            nextId = 1;
                                        } else {
                                            nextId = currentIdNum.intValue() + 1;
                                        }
                                        info.setId(nextId);
                                        mRealm.insertOrUpdate(info); // using insert API


                                    }
                                });

                            }

                            Toast.makeText(MainActivity.this, "저장된 데이터 갯수 : " + mRealm.where(Info.class).count(), Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(MainActivity.this, "들어온 데이터값이 없다 파싱 실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WIfiInfo> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                // 1001 ~ 2000
                wifiInfoApi2.getWifiInfo("1001", "2000", "").enqueue(new Callback<WIfiInfo>() {
                    @Override
                    public void onResponse(Call<WIfiInfo> call, Response<WIfiInfo> response) {
                        mData = response.body();

                        if (mData != null) {
//                            adapter = new ListviewAdapter(mData.getPublicWiFiPlaceInfo().getRow());
//                            mListview.setAdapter(adapter);

                            for (int i = 0; i < 1000; i++) {
                                Row iData = mData.getPublicWiFiPlaceInfo().getRow().get(i);

                                final String PLACE_NAME = iData.getPLACE_NAME();
                                final String CATEGORY = iData.getCATEGORY();
                                final String GU_NM = iData.getGU_NM();
                                final String INSTL_DIV = iData.getINSTL_DIV();
                                final String INSTL_X = iData.getINSTL_X();
                                final String INSTL_Y = iData.getINSTL_Y();


                                mRealm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {

                                        Info info = mRealm.createObject(Info.class);

                                        info.setCATEGORY(CATEGORY);
                                        info.setGU_NM(GU_NM);
                                        info.setINSTL_DIV(INSTL_DIV);
                                        info.setINSTL_X(INSTL_X);
                                        info.setINSTL_Y(INSTL_Y);
                                        info.setPLACE_NAME(PLACE_NAME);

                                        // TODO id값 부여
                                        Number currentIdNum = mRealm.where(Info.class).max("id");
                                        int nextId;
                                        if (currentIdNum == null) {
                                            nextId = 1;
                                        } else {
                                            nextId = currentIdNum.intValue() + 1;
                                        }
                                        info.setId(nextId);
                                        mRealm.insertOrUpdate(info); // using insert API


                                    }
                                });

                            }

                            Toast.makeText(MainActivity.this, "저장된 데이터 갯수 : " + mRealm.where(Info.class).count(), Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(MainActivity.this, "들어온 데이터값이 없다 파싱 실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WIfiInfo> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                // 2001~2994
                wifiInfoApi3.getWifiInfo("2001", "3000", "").enqueue(new Callback<WIfiInfo>() {
                    @Override
                    public void onResponse(Call<WIfiInfo> call, Response<WIfiInfo> response) {
                        mData = response.body();
                        int maxPage = Integer.parseInt(mData.getPublicWiFiPlaceInfo().getList_total_count());

                        if (mData != null) {
//                            adapter = new ListviewAdapter(mData.getPublicWiFiPlaceInfo().getRow());
//                            mListview.setAdapter(adapter);

                            for (int i = 0; i < maxPage - 2000; i++) {
                                Row iData = mData.getPublicWiFiPlaceInfo().getRow().get(i);

                                final String PLACE_NAME = iData.getPLACE_NAME();
                                final String CATEGORY = iData.getCATEGORY();
                                final String GU_NM = iData.getGU_NM();
                                final String INSTL_DIV = iData.getINSTL_DIV();
                                final String INSTL_X = iData.getINSTL_X();
                                final String INSTL_Y = iData.getINSTL_Y();


                                mRealm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {

                                        Info info = mRealm.createObject(Info.class);

                                        info.setCATEGORY(CATEGORY);
                                        info.setGU_NM(GU_NM);
                                        info.setINSTL_DIV(INSTL_DIV);
                                        info.setINSTL_X(INSTL_X);
                                        info.setINSTL_Y(INSTL_Y);
                                        info.setPLACE_NAME(PLACE_NAME);

                                        // TODO id값 부여
                                        Number currentIdNum = mRealm.where(Info.class).max("id");
                                        int nextId;
                                        if (currentIdNum == null) {
                                            nextId = 1;
                                        } else {
                                            nextId = currentIdNum.intValue() + 1;
                                        }
                                        info.setId(nextId);
                                        mRealm.insertOrUpdate(info); // using insert API


                                    }
                                });

                            }

                            Toast.makeText(MainActivity.this, "저장된 데이터 갯수 : " + mRealm.where(Info.class).count(), Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(MainActivity.this, "들어온 데이터값이 없다 파싱 실패", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<WIfiInfo> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        // GoogleAPIClient의 인스턴스 생성
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // GPS 상태 확인
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // TODO 다이얼로그 띄우기로 수정?
            Toast.makeText(this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }


    }


    @Override
    protected void onDestroy() {
        // 렘 닫기
        super.onDestroy();
        mRealm.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // 현재 위치
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);


        if (mLastLocation != null) {
            mLatitude = mLastLocation.getLatitude();
            mLongitude = mLastLocation.getLongitude();
        }
//        moveMyPosition();
        Toast.makeText(this, mLatitude + " " + mLongitude, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
