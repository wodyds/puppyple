package com.example;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.PUPPYPLE.R;
import com.example.location.ApiClient;
import com.example.location.ApiInterface;
import com.example.location.CategoryResult;
import com.example.location.Document;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment2 extends Fragment implements net.daum.mf.map.api.MapView.MapViewEventListener, net.daum.mf.map.api.MapView.POIItemEventListener, net.daum.mf.map.api.MapView.OpenAPIKeyAuthenticationResultListener, View.OnClickListener, net.daum.mf.map.api.MapView.CurrentLocationEventListener{
    Context mContext;
    Activity mActivity;
    MapView mapView;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
    //String BASE_URL = "https://dapi.kakao.com/";
    String API_KEY = "KakaoAK afb18fa91ad984918c09f531f8c18b9e";  // REST API 키

    MapPoint currentMapPoint;
    private double mCurrentLng; //Long = X, Lat = Yㅌ
    private double mCurrentLat;
    //private double mSearchLng = -1;
    //private double mSearchLat = -1;
    //private String mSearchName;
    boolean isTrackingMode = false;
    ArrayList<Document> hospital = new ArrayList<>();
    int matchAddress = 0;
    int matchPhone = 0;
    String message;
    String phonenum;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = getActivity();
        mContext = context;
        checkDangerousPermissions();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // mContext = getActivity();
        View root =  inflater.inflate(R.layout.location_map, container, false);
        mapView = new MapView(mContext);
        ViewGroup mapViewContainer = root.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        mapView.setPOIItemEventListener(this);

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {
            checkRunTimePermission();
        }
        // 동물병원 버튼 클릭 시 이벤트
        root.findViewById(R.id.map_Hospital).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //35.153566802595705, 126.88799104151401
                mapView.removeAllPOIItems();
                mapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                MapPoint.GeoCoordinate geoCoordinate = mapView.getMapCenterPoint().getMapPointGeoCoord();
                double latitude = geoCoordinate.latitude; // 위도
                double longitude = geoCoordinate.longitude; // 경도
                //Toast.makeText(mContext, "현재위치 x : "+longitude+", y : "+latitude, Toast.LENGTH_SHORT).show();
                requestSearchLocalHospital(longitude, latitude);


            }
        });
        // 애견미용실 찾기
        root.findViewById(R.id.map_Beauty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mapView.removeAllPOIItems();
                mapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                MapPoint.GeoCoordinate geoCoordinate = mapView.getMapCenterPoint().getMapPointGeoCoord();
                double latitude = geoCoordinate.latitude; // 위도
                double longitude = geoCoordinate.longitude; // 경도
                //Toast.makeText(mContext, "현재위치 x : "+longitude+", y : "+latitude, Toast.LENGTH_SHORT).show();
                requestSearchLocalBeauty(longitude, latitude);
            }
        });

        // 애견카페 찾기
        root.findViewById(R.id.map_cafe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.removeAllPOIItems();
                mapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                MapPoint.GeoCoordinate geoCoordinate = mapView.getMapCenterPoint().getMapPointGeoCoord();
                double latitude = geoCoordinate.latitude; // 위도
                double longitude = geoCoordinate.longitude; // 경도
                //Toast.makeText(mContext, "현재위치 x : "+longitude+", y : "+latitude, Toast.LENGTH_SHORT).show();
                requestSearchLocalCafe(longitude, latitude);
            }
        });


        return root;


    }
    // 동물병원찾기
    private void requestSearchLocalHospital(double x, double y){
        hospital.clear();
//        String query = "동물병원";
//        MapPoint.GeoCoordinate geoCoordinate = mapView.getMapCenterPoint().getMapPointGeoCoord();
//        double latitude = geoCoordinate.latitude; // 위도
//        double longitude = geoCoordinate.longitude; // 경도
//        int radius = 10000; // 중심 좌표로부터의 반경거리
//        int page = 1;
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<CategoryResult> call = apiInterface.getSearchLocation(API_KEY,"동물병원",x + "",y + "",10000);
        call.enqueue(new Callback<CategoryResult>() {
            @Override
            public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getDocuments() != null){
                        hospital.addAll(response.body().getDocuments());
                    }
                    call = apiInterface.getSearchLocation(API_KEY,"동물병원",x + "",y + "",10000);
                    call.enqueue(new Callback<CategoryResult>() {
                        @Override
                        public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {
                            if(response.isSuccessful()){
                                /*MapCircle circle = new MapCircle(
                                        MapPoint.mapPointWithGeoCoord(y,x),
                                        1000,
                                        Color.argb(128,255,0,0),
                                        Color.argb(128,0,255,0)
                                        );
                                circle.setTag(5678);
                                mapView.addCircle(circle);*/
                                // 마커생성
                                int tagNum = 10;
                                for(Document document : hospital){
                                    MapPOIItem marker = new MapPOIItem();
                                    marker.setItemName(document.getPlaceName());
                                    marker.setTag(tagNum++);
                                    double x = Double.parseDouble(document.getY());
                                    double y = Double.parseDouble(document.getX());
                                    //카카오맵은 참고로 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
                                    MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(x,y);
                                    marker.setMapPoint(mapPoint);
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CategoryResult> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CategoryResult> call, Throwable t) {

            }
        });
    }

    // 애견미용실 찾기
    private void requestSearchLocalBeauty(double x, double y){
        hospital.clear();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<CategoryResult> call = apiInterface.getSearchLocation(API_KEY,"애견미용",x + "",y + "",10000);
        call.enqueue(new Callback<CategoryResult>() {
            @Override
            public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getDocuments() != null){
                        hospital.addAll(response.body().getDocuments());
                    }
                    call = apiInterface.getSearchLocation(API_KEY,"애견미용",x + "",y + "",10000);
                    call.enqueue(new Callback<CategoryResult>() {
                        @Override
                        public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {
                            if(response.isSuccessful()){

                                // 마커생성
                                int tagNum = 10;
                                for(Document document : hospital){
                                    MapPOIItem marker = new MapPOIItem();
                                    marker.setItemName(document.getPlaceName());
                                    marker.setTag(tagNum++);
                                    double x = Double.parseDouble(document.getY());
                                    double y = Double.parseDouble(document.getX());
                                    //카카오맵은 참고로 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
                                    MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(x,y);
                                    marker.setMapPoint(mapPoint);
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CategoryResult> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CategoryResult> call, Throwable t) {

            }
        });
    }

    // 애견카페 찾기
    private void requestSearchLocalCafe(double x, double y){
        hospital.clear();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<CategoryResult> call = apiInterface.getSearchLocation(API_KEY,"애견카페",x + "",y + "",10000);
        call.enqueue(new Callback<CategoryResult>() {
            @Override
            public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getDocuments() != null){
                        hospital.addAll(response.body().getDocuments());
                    }
                    call = apiInterface.getSearchLocation(API_KEY,"애견카페",x + "",y + "",10000);
                    call.enqueue(new Callback<CategoryResult>() {
                        @Override
                        public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {
                            if(response.isSuccessful()){

                                // 마커생성
                                int tagNum = 10;
                                for(Document document : hospital){
                                    MapPOIItem marker = new MapPOIItem();
                                    marker.setItemName(document.getPlaceName());
                                    marker.setTag(tagNum++);
                                    double x = Double.parseDouble(document.getY());
                                    double y = Double.parseDouble(document.getX());
                                    //카카오맵은 참고로 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
                                    MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(x,y);
                                    marker.setMapPoint(mapPoint);
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                    mapView.addPOIItem(marker);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CategoryResult> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CategoryResult> call, Throwable t) {

            }
        });
    }

    // 위험권한
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(getActivity(), permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(getActivity(), "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[0])) {
                Toast.makeText(getActivity(), "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음

        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.
            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, REQUIRED_PERMISSIONS[0])) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(mContext, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions((Activity) mContext, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions((Activity) mContext, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }
    }

    // 위험권한 끝

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCurrentLocationUpdate(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint, float v) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        currentMapPoint = MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude);
        //이 좌표로 지도 중심 이동
        mapView.setMapCenterPoint(currentMapPoint, true);
        //전역변수로 현재 좌표 저장
        mCurrentLat = mapPointGeo.latitude;
        mCurrentLng = mapPointGeo.longitude;

        //트래킹 모드가 아닌 단순 현재위치 업데이트일 경우, 한번만 위치 업데이트하고 트래킹을 중단시키기 위한 로직
        if (!isTrackingMode) {
            mapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOff);
        }
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(net.daum.mf.map.api.MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(net.daum.mf.map.api.MapView mapView) {
        mapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }

    @Override
    public void onCurrentLocationUpdateCancelled(net.daum.mf.map.api.MapView mapView) {
        mapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }

    @Override
    public void onMapViewInitialized(net.daum.mf.map.api.MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(net.daum.mf.map.api.MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(net.daum.mf.map.api.MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onDaumMapOpenAPIKeyAuthenticationResult(net.daum.mf.map.api.MapView mapView, int i, String s) {

    }

    @Override
    public void onPOIItemSelected(net.daum.mf.map.api.MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(net.daum.mf.map.api.MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(net.daum.mf.map.api.MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

        double lat = mapPOIItem.getMapPoint().getMapPointGeoCoord().latitude;
        double lng = mapPOIItem.getMapPoint().getMapPointGeoCoord().longitude;


        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<CategoryResult> call = apiInterface.getSearchLocation(API_KEY,mapPOIItem.getItemName(), String.valueOf(lat), String.valueOf(lng), 10000);
        call.enqueue(new Callback<CategoryResult>() {
            @Override
            public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {
                if(response.isSuccessful()){

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    for(int i = 0; i < response.body().getDocuments().size(); i++ ){
                        String address = response.body().getDocuments().get(i).getAddressName();
                        if(address.startsWith("광주")){
                            matchAddress = i;
                            message = "주소 : "+response.body().getDocuments().get(matchAddress).getAddressName();
                        }else{
                            message = "중복된 값이 많아 찾을 수 없습니다.";
                        }

                    }
                    for(int i = 0; i < response.body().getDocuments().size(); i++){
                        String phone = response.body().getDocuments().get(i).getPhone();
                        if(phone.startsWith("062")){
                            matchPhone = i;
                            phonenum = "전화번호 : "+response.body().getDocuments().get(matchPhone).getPhone();
                        }else{
                            phonenum = "중복된 값이 많아 찾을 수 없습니다.";
                        }
                    }

                    builder.setTitle(mapPOIItem.getItemName());
                    if(message.equals(phonenum)){
                        phonenum = "";
                        builder.setMessage(message+"\n"+phonenum+"\n카카오맵 어플리케이션에서 확인하시겠습니까?");
                    }else if(phonenum.equals("중복된 값이 많아 찾을 수 없습니다.")){
                        builder.setMessage(message+"\n\n지번 전화를 찾을 수 없습니다."+"\n카카오맵 어플리케이션에서 확인하시겠습니까?");
                    }else{
                        builder.setMessage(message+"\n"+phonenum);
                        builder.setPositiveButton("전화걸기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String phn = response.body().getDocuments().get(matchPhone).getPhone();
                                phn.replace("-","");
                                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("tel:"+phn));
                                startActivity(intent);
                            }
                        });
                        builder.setNeutralButton("카카오맵 이동", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url = "kakaomap://look?p="+lat+","+lng;
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }


                    if(phonenum.equals("")||phonenum.equals("중복된 값이 많아 찾을 수 없습니다.")){
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // kakaomap://look?p=37.537229,127.005515
                                String url = "kakaomap://look?p="+lat+","+lng;
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }


                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<CategoryResult> call, Throwable t) {

            }
        });


    }

    @Override
    public void onDraggablePOIItemMoved(net.daum.mf.map.api.MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}
