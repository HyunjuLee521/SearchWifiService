package com.hj.user.searchwifiservice;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hj.user.searchwifiservice.adapters.SpinnerAdapter;
import com.hj.user.searchwifiservice.models.Row;
import com.hj.user.searchwifiservice.models.WIfiInfo;

import java.util.ArrayList;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.hj.user.searchwifiservice.R.id.map;
import static com.hj.user.searchwifiservice.TypefaceManager.mKopubDotumLightTypeface;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, LocationListener {


    // LocationListener
    private LocationRequest mLocationRequest;

    private ImageView mSearchImageview;
    private EditText mSearchEdittext;
    private WIfiInfo mData;

    private GoogleApiClient mGoogleApiClient;

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

    private WifiInfoApi wifiInfoApi4;
    private Toolbar mMainToolbar;
    private TextView mTitleTextview;
    private Marker myPositionMarker;

    private Boolean mMyPositionIsClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        moveMyPosition(); 에 접근하는 것 처음인지 아닌지


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
        mTitleTextview.setText("서울시 무료 와이파이 지도");


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


        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("mMyPositionIsClicked", mMyPositionIsClicked);
        super.onSaveInstanceState(outState);
//        Toast.makeText(this, "onSaveInstanceState 에서 값 " + mMyPositionIsClicked, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // 복원 (null 체크 불필요)
        mMyPositionIsClicked = savedInstanceState.getBoolean("mMyPositionIsClicked");
        Toast.makeText(this, "onRestoreInstanceState 에서 값 " + mMyPositionIsClicked, Toast.LENGTH_SHORT).show();

        Log.d("MainActivity", "onRestoreInstanceState: ");

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
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("MainActivity", "onConnected: ");


        

    }

    // TODO 갱신 안됨
    @Override
    public void onLocationChanged(Location location) {
        Log.d("MainActivity", "onLocationChanged: " + location.toString());

        if (mMyPositionIsClicked) {
            Toast.makeText(this, "onConnected 에서 값 " + mMyPositionIsClicked, Toast.LENGTH_SHORT).show();
            moveMyPosition();
        }
    }


    public void moveMyPosition() {
        mMyPositionIsClicked = true;

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        Toast.makeText(this, "moveMyPosition 에서 값 " + mMyPositionIsClicked, Toast.LENGTH_SHORT).show();

        // GPS 상태 확인
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // TODO 다이얼로그 띄우기로 수정?
            Toast.makeText(this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }


        if (myPositionMarker != null) {
            myPositionMarker.remove();
        }

        // TODO 연결될 때의 위치가 아닌 실시간 현재 위치 가져오기
        // 현재 위치로 마커 추가하기
        myPositionMarker = mMap.addMarker(new MarkerOptions().position(newLatLng).title("내 위치")
                // 마커 색상 커스텀
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));


//         필요 없어
//        mMap.moveCamera(CameraUpdateFactory
//                .newLatLng(myPositionMarker));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 17));

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
        mMyPositionIsClicked = false;
        Toast.makeText(this, "startService 에서 값 " + mMyPositionIsClicked, Toast.LENGTH_SHORT).show();

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
                moveMyPosition();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        moveMyPosition();
    }
}
