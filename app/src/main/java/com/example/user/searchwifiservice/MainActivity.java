package com.example.user.searchwifiservice;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.searchwifiservice.adapters.SpinnerAdapter;
import com.example.user.searchwifiservice.models.Info;
import com.example.user.searchwifiservice.models.Row;
import com.example.user.searchwifiservice.models.WIfiInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.user.searchwifiservice.R.id.map;
import static com.example.user.searchwifiservice.TypefaceManager.mKopubDotumLightTypeface;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    private ImageView mSearchImageview;
    private EditText mSearchEdittext;
    private WIfiInfo mData;

    private double mLatitude;
    private double mLongitude;
    private GoogleApiClient mGoogleApiClient;
    // 현재 위치
    private Location mLastLocation;

    private Realm mRealm;
    private Button mUpdateButton;
    private Spinner mGuSpinner;


    private WifiInfoApi wifiInfoApi1;
    private WifiInfoApi wifiInfoApi2;
    private WifiInfoApi wifiInfoApi3;
    private WifiInfoApi wifiInfoApi;

    private ArrayList<String> guName;
    private SpinnerAdapter mSpinnerAdapter;
    private WIfiInfo mGuData;


    private GoogleMap mMap;
    private LatLng myPosition;

    private Handler mHandler;
    private int totalDataCount;
    private WifiInfoApi wifiInfoApi4;
    private Toolbar mMainToolbar;
    private TextView mTitleTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 구글 맵 가져오기
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        // 렘 초기화
        mRealm = Realm.getDefaultInstance();

        mGuSpinner = (Spinner) findViewById(R.id.gu_spinner);
        mUpdateButton = (Button) findViewById(R.id.update_button);


        guName = new ArrayList<>();
        settingArraylistGuName();
        mSpinnerAdapter = new SpinnerAdapter(guName);
        mGuSpinner.setAdapter(mSpinnerAdapter);

        // 툴바연결

        // 툴바 연결
        mMainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mMainToolbar);
        getSupportActionBar().setTitle("");

        // TODO 툴바 제목, 부제목 연결 및 글씨체 설정
        mTitleTextview = (TextView) mMainToolbar.findViewById(R.id.title_textview);

        // TODO 오류 발생
        mTitleTextview.setTypeface(mKopubDotumLightTypeface);
        mTitleTextview.setText("서울시 무료 와이파이");


        // WIfiInfo 데이터 파싱하여 넣기
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WifiInfoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        wifiInfoApi = retrofit.create(WifiInfoApi.class);
        wifiInfoApi1 = retrofit.create(WifiInfoApi.class);
        wifiInfoApi2 = retrofit.create(WifiInfoApi.class);
        wifiInfoApi3 = retrofit.create(WifiInfoApi.class);

        wifiInfoApi4 = retrofit.create(WifiInfoApi.class);

        mGuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                startService(guName.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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


    }

    private void addAllwifiLocation() {

        // TODO 비동기 progress바
        Toast.makeText(this, "정보를 찾고 있습니다", Toast.LENGTH_SHORT).show();

        for (int i = 1; i < totalDataCount + 1; i++) {
            Info info = mRealm.where(Info.class).equalTo("id", i).findFirst();

            double x = Double.parseDouble(info.getINSTL_X());
            double y = Double.parseDouble(info.getINSTL_Y());
            String place = info.getPLACE_NAME();
            String div = info.getINSTL_DIV();

            String title = String.format("%s (설치기관:%s)", place, div);

            // 마커에 추가
            makeMarker(y, x, title);
        }

        moveMyPosition();

        Toast.makeText(this, "완료하였습니다", Toast.LENGTH_SHORT).show();
    }


    private void settingArraylistGuName() {

        guName.add("종로구");
        guName.add("중구");
        guName.add("용산구");
        guName.add("성동구");
        guName.add("광진구");
        guName.add("동대문구");
        guName.add("중랑구");
        guName.add("성북구");
        guName.add("강북구");
        guName.add("도봉구");
        guName.add("노원구");
        guName.add("은평구");
        guName.add("서대문구");
        guName.add("마포구");
        guName.add("양천구");
        guName.add("강서구");
        guName.add("구로구");
        guName.add("금천구");
        guName.add("영등포구");
        guName.add("동작구");
        guName.add("관악구");
        guName.add("서초구");
        guName.add("강남구");
        guName.add("송파구");
        guName.add("강동구");
//        guName.add("서울시 전체");
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
        moveMyPosition();

    }

    public void moveMyPosition() {

        myPosition = new LatLng(mLatitude, mLongitude);
        mMap.addMarker(new MarkerOptions().position(myPosition).title("내 위치")
                // 마커 색상 커스텀
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        // 필요 없어
//        mMap.moveCamera(CameraUpdateFactory
//                .newLatLng(myPosition));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 17));

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void startService(String gu) {
        wifiInfoApi.getWifiInfo("1", "1000", gu).enqueue(new Callback<WIfiInfo>() {
            @Override
            public void onResponse(Call<WIfiInfo> call, Response<WIfiInfo> response) {
                mGuData = response.body();

                if (mGuData != null) {
                    addWifiPositionMarker(mGuData);

                } else {
                    Toast.makeText(MainActivity.this, "데이터가 null 파싱 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WIfiInfo> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage() + " ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void makeMarker(double latitude, double longtitude, String title) {
        LatLng position = new LatLng(latitude, longtitude);

        mMap.addMarker(new MarkerOptions().position(position).title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


    }

    public void addWifiPositionMarker(WIfiInfo wIfiInfo) {

        ArrayList<Row> rowArrayList = wIfiInfo.getPublicWiFiPlaceInfo().getRow();
        int size = rowArrayList.size();


        for (int i = 0; i < size; i++) {
            Row dataI = wIfiInfo.getPublicWiFiPlaceInfo().getRow().get(i);

            double x = Double.parseDouble(dataI.getINSTL_X());
            double y = Double.parseDouble(dataI.getINSTL_Y());
            String place = dataI.getPLACE_NAME();
            String div = dataI.getINSTL_DIV();


            String title = String.format("%s (설치기관:%s)", place, div);
//
//                    // 마커에 추가
            makeMarker(y, x, title);

        }


        // 0번째 index에 들어있는 장소로 이동
        LatLng firstPosition = new LatLng(Double.parseDouble(rowArrayList.get(0).getINSTL_Y())
                , Double.parseDouble(rowArrayList.get(0).getINSTL_X()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPosition, 17));

    }


    public void updateTotalInfoToRealm() {
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
                totalDataCount = Integer.parseInt(mData.getPublicWiFiPlaceInfo().getList_total_count());

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_my_position:
                // GPS 상태 확인
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    // TODO 다이얼로그 띄우기로 수정?
                    Toast.makeText(this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                } else {
                    moveMyPosition();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
