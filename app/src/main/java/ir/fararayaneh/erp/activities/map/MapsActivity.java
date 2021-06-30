package ir.fararayaneh.erp.activities.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.libraries.places.compat.AutocompleteFilter;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.Places;
import com.google.android.libraries.places.compat.ui.PlaceAutocompleteFragment;
import com.google.android.libraries.places.compat.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.makesense.labs.curvefit.CurveOptions;
import com.makesense.labs.curvefit.impl.CurveManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.utils.ContextHelper;
import ir.fararayaneh.erp.utils.data_handler.CheckPermissionHandler;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.intent_handler.IntentReceiverHandler;
import ir.fararayaneh.erp.utils.location_handler.GPSHandler;
import ir.fararayaneh.erp.utils.location_handler.MyMarker;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;

import static androidx.appcompat.app.AppCompatDelegate.setCompatVectorFromResourcesEnabled;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * برای مسیر یابی از پنل سیدار مپ یا ... استفاده شود
 * *************************************************
 * permissions should be get before this activity
 * <p>
 * GPS should turn on here
 * <p>
 * use cocoahero and map_util frameWorks for work with geoJson
 * ------------------------------------------------------------------------------
 * به ازای هر ریکوست تایپ جدید که به اینجا می آید ، همه
 * todo
 * باید تکمیل شود
 * --------------------------------------------------------------------------------------------
 * do not create camera idle for destination ,more location, surface (that use cluster)
 * --------------------------------------------------------------------------------------------
 * اگر قرار است که اطلاعات یک مکان یا مسیر یا ناحیه از نقشه گرفته شود به صورت طول و عرض جغرافیایی خواهد بود
 * که درون
 * listLatForResult
 * و
 * listLongForResult
 * ریخته میشود
 * <p>
 * اگر قرار است که مکان یا مکان ها یا مسیر یا ناحیه نمایش داده شود به صورت یک فایل استرینگ که قالب
 * GeoJson
 * دارد دریافت شده و به مپ داده میشود
 */

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final float NORMAL_ZOOM = 15;

    static {
        setCompatVectorFromResourcesEnabled(true);
    }

    private static final long INTERVAL = 60000, FASTEST_INTERVAL = 30000; //in millySeconds
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private IntentModel intentModel;
    private ArrayList<String> listLatForResult = new ArrayList<>(), listLongForResult = new ArrayList<>();
    private IIOSDialogeListener iiosDialogeListener;
    private boolean goMyLocationAgain = true;
    private ResultCallback<LocationSettingsResult> locationSettingsResultResultCallback;
    private ClusterManager<MyMarker> mClusterManager;
    private PlaceSelectionListener placeSelectionListener;
    private PlaceAutocompleteFragment autocompleteFragment;
    private CurveManager curveManager;
    private CompositeDisposable compositeDisposable;


    //region binding
    @BindView(R.id.progress_map_act)
    ConstraintLayout progress_map_act;
    @BindView(R.id.img_marker_map_act)
    AppCompatImageView img_marker_map_act;
    @BindView(R.id.float_btn_clear_map_act)
    FloatingActionButton float_btn_clear_map_act;
    @BindView(R.id.float_btn_confirm_map_act)
    FloatingActionButton float_btn_confirm_map_act;
    @BindView(R.id.float_btn_traffic_map_act)
    FloatingActionButton float_btn_traffic_map_act;
    @BindView(R.id.float_btn_type_map_act)
    FloatingActionButton float_btn_type_map_act;
    @BindView(R.id.float_btn_no_traffic_map_act)
    FloatingActionButton float_btn_no_traffic_map_act;
    @BindView(R.id.float_btn_no_type_map_act)
    FloatingActionButton float_btn_no_type_map_act;
    private boolean canZoom;
    //endregion

    //region onClick
    @OnClick({R.id.float_btn_clear_map_act, R.id.float_btn_confirm_map_act, R.id.float_btn_traffic_map_act, R.id.float_btn_type_map_act, R.id.float_btn_no_traffic_map_act, R.id.float_btn_no_type_map_act})
    public void clickMapAct(View view) {
        switch (view.getId()) {
            case R.id.float_btn_confirm_map_act:
                ShowFinalAlert();
                break;
            case R.id.float_btn_clear_map_act:
                workClickClearMap();
                break;
            case R.id.float_btn_no_traffic_map_act:
                workClickTraffic(true);
                break;
            case R.id.float_btn_traffic_map_act:
                workClickTraffic(false);
                break;
            case R.id.float_btn_type_map_act:
                workClickType(false);
                break;
            case R.id.float_btn_no_type_map_act:
                workClickType(true);
                break;
        }
    }
    //endregion

    //-----*****----------------------------*****------------------------------------------>>>>>>
    //-----*****----------------------------*****------------------------------------------>>>>>>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUpTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        if (!handleIntent()) {
            goMainAndFinish();
        }
    }

    private void setUpTheme() {
        setTheme(SharedPreferenceProvider.getAppTheme(this));
        AppCompatDelegate.setDefaultNightMode(SharedPreferenceProvider.getNightMode(this));
    }

    private boolean handleIntent() {
        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                intentModel = IntentReceiverHandler.getIntentData(getIntent().getExtras());
                displayProperUI();
                showHelpMessage();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //todo set for other request codes
    private void displayProperUI() {
        switch (intentModel.getSomeString()) {
            case CommonRequestCodes.MAP_GET_ONE_LOCATION + "":
                displayProperUIOneLocations();
                break;
            case CommonRequestCodes.MAP_GET_MORE_LOCATION + "":
                displayProperUIMoreLocations();
                break;
            case CommonRequestCodes.MAP_GET_DESTINATION + "":
                displayProperUIDestination();
                break;
            case CommonRequestCodes.MAP_GET_SURFACE + "":
                displayProperUISurface();
                break;
            case CommonRequestCodes.MAP_SHOW_GEOJSON + "":
                displayProperUIShow();
                break;
        }
    }

    private void displayProperUIOneLocations() {
        showHideImgMarker(false);
    }

    private void displayProperUIMoreLocations() {
        showHideImgMarker(false);
    }

    private void displayProperUIDestination() {
        showHideImgMarker(false);
    }

    private void displayProperUISurface() {
        showHideImgMarker(false);
    }

    private void displayProperUIShow() {
        showHideImgMarker(false);
    }

    //-----*****----------------------------*****------------------------------------------>>>>>>
    //-----*****----------------------------*****------------------------------------------>>>>>>

    /**
     * ترتیب اجرای متدها نباید تغییر نماید
     */
    @Override
    protected void onStart() {
        Log.i("arash", "onStart: ");
        super.onStart();
        syncMap();
        setUpAutoCompleteFrag();
        setUpGoogleApi();
        connectAPI();
        createLocationRequest();
        addDisposable(getLocationPermission());
        enableGPS();
        App.addToAppPowerOnList();
    }

    private void syncMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_map_act);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            ThrowableLoggerHelper.logMyThrowable("MapsActivity***mapFragment is null");
            goMainAndFinish();
        }
    }

    //todo read COUNTRY_ABBREVIATION from database
    private void setUpAutoCompleteFrag() {
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_map_act);
        autocompleteFragment.setHint(getString(R.string.search));
        autocompleteFragment.setFilter(new AutocompleteFilter.Builder()
                .setCountry(Commons.COUNTRY_ABBREVIATION).build());

        placeSelectionListener = new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                workPlaceSelect(place);
            }

            @Override
            public void onError(Status status) {
                ThrowableLoggerHelper.logMyThrowable("MapsAct/**setUpAutoCompleteFrag: " + status.getStatusMessage());
                ToastMessage.show(getBaseContext(), getResources().getString(R.string.some_problem_error));
            }
        };
        autocompleteFragment.setOnPlaceSelectedListener(placeSelectionListener);
    }

    //todo set for other request codes
    private void workPlaceSelect(Place place) {
        if (checkIntentModel() && place != null) {
            switch (intentModel.getSomeString()) {
                case CommonRequestCodes.MAP_GET_ONE_LOCATION + "":
                    onPlacedSelectedOneLocations(mMap, place);
                    break;
                case CommonRequestCodes.MAP_GET_MORE_LOCATION + "":
                    onPlacedSelectedMoreLocations(place);
                    break;
                case CommonRequestCodes.MAP_GET_DESTINATION + "":
                    onPlacedSelectedDestination(place);
                    break;
                case CommonRequestCodes.MAP_GET_SURFACE + "":
                    onPlacedSelectedSurface(place);
                    break;
                case CommonRequestCodes.MAP_SHOW_GEOJSON + "":
                    onPlacedSelectedShow(place);
                    break;
            }
        }
    }

    private void onPlacedSelectedOneLocations(GoogleMap mMap, Place place) {
        clearMap(mMap);
        zoomMapCamera(place.getLatLng().latitude, place.getLatLng().longitude, NORMAL_ZOOM);
        //no need to add marker ...in idle state , marker were added
    }

    private void onPlacedSelectedMoreLocations(Place place) {
        zoomMapCamera(place.getLatLng().latitude, place.getLatLng().longitude, NORMAL_ZOOM);
    }

    private void onPlacedSelectedDestination(Place place) {
        zoomMapCamera(place.getLatLng().latitude, place.getLatLng().longitude, NORMAL_ZOOM);
    }

    private void onPlacedSelectedSurface(Place place) {
        zoomMapCamera(place.getLatLng().latitude, place.getLatLng().longitude, NORMAL_ZOOM);
    }

    private void onPlacedSelectedShow(Place place) {
        zoomMapCamera(place.getLatLng().latitude, place.getLatLng().longitude, NORMAL_ZOOM);
    }

    private void setUpGoogleApi() {

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addApi(LocationServices.API)
                    //.addApi(Places.GEO_DATA_API)//TODO migration
                    //.addApi(Places.PLACE_DETECTION_API)//TODO migration
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }

    private void connectAPI() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    private void createLocationRequest() {
        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest()
                    .setInterval(INTERVAL)
                    .setFastestInterval(FASTEST_INTERVAL)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
    }

    private void enableGPS() {
        locationSettingsResultResultCallback = this::workForGPSOnResult;
        GPSHandler.enableGPSAutomatically(mGoogleApiClient, mLocationRequest, locationSettingsResultResultCallback);
    }

    private void workForGPSOnResult(LocationSettingsResult locationSettingsResult) {

        switch (locationSettingsResult.getStatus().getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                ThrowableLoggerHelper.logMyThrowable("GPS is On ");
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                ThrowableLoggerHelper.logMyThrowable("GPS is On the request ");
                try {
                    locationSettingsResult.getStatus().startResolutionForResult(MapsActivity.this, CommonRequestCodes.TURN_ON_GPS);
                } catch (IntentSender.SendIntentException e) {
                    ThrowableLoggerHelper.logMyThrowable("mapsActivity/ enableGPS***" + e.getMessage());
                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                ThrowableLoggerHelper.logMyThrowable("user not permission to GPS ");
                break;
        }
    }

    //-----*****----------------------------*****------------------------------------------>>>>>>
    //-----*****----------------------------*****------------------------------------------>>>>>>
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("arash", "onMapReady: ......");
        setupMap(googleMap);
        manageFindLocationBtn();
        showHideProgress(false);
        workForSetCameraIdleOrAlternative();
//************************************************************************************************************
        mMap.setOnCameraMoveListener(this::workForCameraMove);
//***********************************************************************************************************/
        mMap.setOnMarkerClickListener(marker -> {
            workForMarkerClick(marker);
            return false;
        });
//*************************************************************************************************************/
        mMap.setOnMapLongClickListener(this::workForMapLongClick);
//*************************************************************************************************************/
        mMap.setOnInfoWindowClickListener(this::workForMarkerInfoClick);
//*************************************************************************************************************/
    }

    private void setupMap(GoogleMap googleMap) {
        mMap = googleMap;
        workClickTraffic(false);
        workClickType(false);

    }

    private void manageFindLocationBtn() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           if(mMap!=null){
               mMap.setMyLocationEnabled(false);
           }
        } else {
            if(mMap!=null){
                mMap.setMyLocationEnabled(true);
                changeFindLocationButton();
            }
        }
    }

    public void changeFindLocationButton() {
        if (mapFragment != null) {
            View mapView = mapFragment.getView();
            if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
                View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                        locationButton.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                layoutParams.setMargins(0, 0, 0, 10);

            }
        }
    }

    // TODO: 1/22/2019
    private void workForSetCameraIdleOrAlternative() {
        if (mMap != null && checkIntentModel()) {
            switch (intentModel.getSomeString()) {
                case CommonRequestCodes.MAP_GET_ONE_LOCATION + "":
                    mMap.setOnCameraIdleListener(this::workForCameraIdle);
                    break;
                case CommonRequestCodes.MAP_SHOW_GEOJSON + "":
                    showGeoJson(mMap, intentModel.getSomeString2());
                    break;
                case CommonRequestCodes.MAP_GET_DESTINATION + "":
                case CommonRequestCodes.MAP_GET_SURFACE + "":
                case CommonRequestCodes.MAP_GET_MORE_LOCATION + "":
                    setUpCluster();
                    break;
            }
        }
    }

    //todo
    private void workForCameraIdle() {
        if (checkIntentModel()) {
            switch (intentModel.getSomeString()) {
                case CommonRequestCodes.MAP_GET_ONE_LOCATION + "":
                    cameraIdleOneLocations();
                    break;
            }
        }
    }

    private void cameraIdleOneLocations() {
        if (mMap != null) {
            showHideImgMarker(false);
            clearMap(mMap);
            addMarkerToMap(mMap.getCameraPosition().target);
        }
    }

    private void showGeoJson(GoogleMap map, String geoJson) {
        if (map != null) {
            try {
                GeoJsonLayer geoJsonLayer = new GeoJsonLayer(map, new JSONObject(geoJson));
                geoJsonLayer.addLayerToMap();
                zoomToGeoJson(geoJsonLayer);

            } catch (JSONException e) {
                ThrowableLoggerHelper.logMyThrowable("MApsActivity***showGeoJson/" + e.getMessage());
                ToastMessage.show(this, getString(R.string.some_problem_error));
            }
        }
    }

    private void setUpCluster() {
        if (mMap != null) {
            if (mClusterManager == null) {
                mClusterManager = new ClusterManager<>(this, mMap);
            }
            mMap.setOnCameraIdleListener(mClusterManager);
            mMap.setOnMarkerClickListener(mClusterManager);
        }

    }

    //todo set for other request codes
    private void workForCameraMove() {
        if (checkIntentModel()) {
            switch (intentModel.getSomeString()) {
                case CommonRequestCodes.MAP_GET_ONE_LOCATION + "":
                    cameraMoveOneLocations();
                    break;
                case CommonRequestCodes.MAP_GET_MORE_LOCATION + "":
                    cameraMoveMoreLocations();
                    break;
                case CommonRequestCodes.MAP_GET_DESTINATION + "":
                    cameraMoveDestination();
                    break;
                case CommonRequestCodes.MAP_GET_SURFACE + "":
                    cameraMoveSurface();
                    break;
                case CommonRequestCodes.MAP_SHOW_GEOJSON + "":
                    cameraMoveShow();
                    break;
            }
        }
    }

    private void cameraMoveOneLocations() {
        clearMap(mMap);
        showHideImgMarker(true);
    }

    private void cameraMoveMoreLocations() {
        //doNothings
    }

    private void cameraMoveDestination() {
        //doNothings
    }

    private void cameraMoveSurface() {
        //doNothings
    }

    private void cameraMoveShow() {
        //doNothings
    }

    //todo set for other request codes
    private void workForMarkerClick(Marker marker) {

        switch (intentModel.getSomeString()) {
            case CommonRequestCodes.MAP_GET_ONE_LOCATION + "":
                markerClickOneLocations(marker);
                break;
            case CommonRequestCodes.MAP_GET_MORE_LOCATION + "":
                markerClickMoreLocations(marker);
                break;
            case CommonRequestCodes.MAP_GET_DESTINATION + "":
                markerClickDestination(marker);
                break;
            case CommonRequestCodes.MAP_GET_SURFACE + "":
                markerClickSurface(marker);
                break;
            case CommonRequestCodes.MAP_SHOW_GEOJSON + "":
                markerClickShow(marker);
                break;
        }
    }

    private void markerClickOneLocations(Marker marker) {
        if (marker.getPosition() != null) {
            addToFinalList(String.valueOf(marker.getPosition().latitude), String.valueOf(marker.getPosition().longitude));
            ShowFinalAlert();
        } else {
            ToastMessage.show(getBaseContext(), getResources().getString(R.string.some_problem_error));
        }
    }

    private void markerClickShow(Marker marker) {
        //doNothings
    }

    private void markerClickMoreLocations(Marker marker) {
        //do nothings
    }

    private void markerClickSurface(Marker marker) {
        //do nothings
    }

    private void markerClickDestination(Marker marker) {
        //do nothings
    }

    //todo set for other request codes
    private void workForMapLongClick(LatLng latLng) {
        if (latLng != null && checkIntentModel()) {
            switch (intentModel.getSomeString()) {
                case CommonRequestCodes.MAP_GET_ONE_LOCATION + "":
                    mapLongClickOneLocations(latLng);
                    break;
                case CommonRequestCodes.MAP_GET_MORE_LOCATION + "":
                    mapLongClickMoreLocations(latLng);
                    break;
                case CommonRequestCodes.MAP_GET_DESTINATION + "":
                    mapLongClickDestination(latLng);
                    break;
                case CommonRequestCodes.MAP_GET_SURFACE + "":
                    mapLongClickSurface(latLng);
                    break;
                case CommonRequestCodes.MAP_SHOW_GEOJSON + "":
                    mapLongClickShow(latLng);
                    break;
            }
        }

    }

    private void mapLongClickOneLocations(LatLng latLng) {
        //doNothings
    }

    private void mapLongClickMoreLocations(LatLng latLng) {
        addMarkerToMap(latLng);
        addToFinalList(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));
        showHideImgConfirm(true);
        showHideImgClear(true);
    }

    private void mapLongClickDestination(LatLng latLng) {
        addToFinalList(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));
        drawCurve();
        showHideImgConfirm(true);
        showHideImgClear(true);
    }

    private void mapLongClickSurface(LatLng latLng) {
        addToFinalList(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));
        drawCurve();
        showHideImgConfirm(true);
        showHideImgClear(true);
    }

    private void mapLongClickShow(LatLng latLng) {
        //doNothings
    }

    //todo
    private void workForMarkerInfoClick(Marker marker) {
        if (checkIntentModel()) {
            switch (intentModel.getSomeString()) {
                case CommonRequestCodes.MAP_GET_DESTINATION + "":
                case CommonRequestCodes.MAP_GET_SURFACE + "":
                    deleteOneMarker(marker);
                    break;

            }
        }
    }

    //-----*****----------------------------*****------------------------------------------>>>>>>
    //-----*****----------------------------*****------------------------------------------>>>>>>
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    //todo remove deprecation
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mGoogleApiClient != null && mLocationRequest != null)
                LocationServices.FusedLocationApi
                        .requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            updateLocation(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }

    //todo set for other request codes
    private void updateLocation(final LatLng location) {
        if (location != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(checkIntentModel()){
                        switch (intentModel.getSomeString()) {
                            case CommonRequestCodes.MAP_GET_ONE_LOCATION + "":
                            case CommonRequestCodes.MAP_GET_MORE_LOCATION + "":
                            case CommonRequestCodes.MAP_GET_DESTINATION + "":
                            case CommonRequestCodes.MAP_GET_SURFACE + "":
                            case CommonRequestCodes.MAP_SHOW_GEOJSON + "":
                                MapsActivity.this.updateLocationOneLocations(location);
                                break;
                        }
                    }
                }
            });
        }

    }

    private void updateLocationOneLocations(LatLng location) {
        if (location != null && goMyLocationAgain && mMap!=null) {
            goMyLocationAgain = false;
            zoomMapCamera(location.latitude,location.longitude,NORMAL_ZOOM);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //nothing
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //nothing
    }
    //-----*****----------------------------*****------------------------------------------>>>>>>
    //-----*****----------------------------*****------------------------------------------>>>>>>


    /**
     * for those listener that register in onStart or onMapReady
     */
    @Override
    protected void onStop() {
        stopLocationUpdates();
        removalWorkOnStop();
        App.removeFromAppPowerOnList();
        super.onStop();
    }

    //todo remove deprecation
    private void stopLocationUpdates() {
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }
    }

    /**
     * ترتیب متد ها نباید تغییر نماید
     */
    private void removalWorkOnStop() {

        if (mGoogleApiClient != null) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;
        }

        if (mapFragment != null) {
            mapFragment = null;
            //mapFragment.getMapAsync(null);
        }

        autocompleteFragment = null;
        placeSelectionListener = null;

        mLocationRequest = null;
        locationSettingsResultResultCallback = null;
        mClusterManager = null;
        //todo add other new listener that create in on map ready
        if (mMap != null) {
            mMap.setOnCameraMoveListener(null);
            mMap.setOnCameraIdleListener(null);
            mMap.setOnMarkerClickListener(null);
            mMap.setOnMapLongClickListener(null);
            mMap.setOnInfoWindowClickListener(null);
        }
        mMap = null;

    }

    //-----*****----------------------------*****------------------------------------------>>>>>>
    //-----*****----------------------------*****------------------------------------------>>>>>>
    @Override
    protected void onDestroy() {
        removalWorkOnDestroy();
        super.onDestroy();
    }

    /**
     * for those work that not ic_start in onCreate
     */
    private void removalWorkOnDestroy() {
        removalWorkOnStop();
        if (curveManager != null) {
            curveManager.unregister();
        }
        curveManager = null;
        iiosDialogeListener = null;
        mClusterManager = null;
        clearDisposable();
    }

    public void clearDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
    //-----*****----------------------------*****------------------------------------------>>>>>>
    //-----*****----------------------------*****------------------------------------------>>>>>>
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ContextHelper.customContext(newBase));
    }
    //-----*****----------------------------*****------------------------------------------>>>>>>
    //-----*****----------------------------*****------------------------------------------>>>>>>
    @Override
    public void onBackPressed() {
        ActivityIntents.cancelResultIntent(this);
        finish();
    }
    //-----*****----------------------------*****------------------------------------------>>>>>>
    //-----*****----------------------------*****------------------------------------------>>>>>>
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonRequestCodes.TURN_ON_GPS) {
            if (resultCode == Activity.RESULT_OK) {
                ThrowableLoggerHelper.logMyThrowable("GPS turned on successFully");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                ThrowableLoggerHelper.logMyThrowable("GPS turned on were canceled");
            }
        }

    }
    //-----*****----------------------------*****------------------------------------------>>>>>>
    //-----*****----------------------------*****------------------------------------------>>>>>>
    private void goMainAndFinish() {
        ActivityIntents.goMainActivity(this);
        finish();
    }

    // TODO: 1/20/2019 check
    private void showHelpMessage() {
        //no need when show Geojson
        switch (intentModel.getSomeString()) {
            case CommonRequestCodes.MAP_GET_ONE_LOCATION + "":
                ToastMessage.showIosDialog(this, getString(R.string.dear_user), getString(R.string.help_one_location_map),true);
                break;
            case CommonRequestCodes.MAP_GET_MORE_LOCATION + "":
            case CommonRequestCodes.MAP_GET_DESTINATION + "":
            case CommonRequestCodes.MAP_GET_SURFACE + "":
                ToastMessage.showIosDialog(this, getString(R.string.dear_user), getString(R.string.help_more_location_map),true);
                break;
        }
    }

    private void showHideProgress(boolean showHide) {
        progress_map_act.setVisibility(showHide ? VISIBLE : GONE);
    }

    private void showHideImgMarker(boolean showHide) {
        img_marker_map_act.setVisibility(showHide ? VISIBLE : GONE);
    }

    private void showHideImgClear(boolean showHide) {
        if (showHide) {
            float_btn_clear_map_act.show();
        } else {
            float_btn_clear_map_act.hide();
        }
    }

    private void showHideImgConfirm(boolean showHide) {
        if (showHide) {
            float_btn_confirm_map_act.show();
        } else {
            float_btn_confirm_map_act.hide();
        }
    }

    private void ShowFinalAlert() {
        if (haveEnoughLocation()) {
            showConfirmMessage();
        } else {
            showNotEnoughLocationMessage();
        }
    }

    //todo check
    private boolean haveEnoughLocation() {
        if (checkIntentModel()) {
            switch (intentModel.getSomeString()) {
                case CommonRequestCodes.MAP_GET_MORE_LOCATION + "":
                case CommonRequestCodes.MAP_GET_DESTINATION + "":
                    return listLatForResult.size() > 1;
                case CommonRequestCodes.MAP_GET_SURFACE + "":
                    return listLatForResult.size() > 2;
            }
        }
        return true;
    }

    private void showNotEnoughLocationMessage() {
        ToastMessage.showIosDialog(this, getString(R.string.dear_user), getString(R.string.not_enugh_location_error),true);
    }

    private boolean checkIntentModel() {
        return intentModel != null;
    }

    private void showConfirmMessage() {
        iiosDialogeListener = new IIOSDialogeListener() {
            @Override
            public void confirm() {
                sendBackResult();
            }

            @Override
            public void cancle() {
                workClickNegativeFinalAlert();
            }
        };

        ToastMessage.showIosDialogTypeConfirmCancle(this,
                getString(R.string.caution),
                getString(R.string.sure_selection),
                getString(R.string.ok),
                getString(R.string.refine),
                false,iiosDialogeListener
                );
    }

    private void addToFinalList(String latitude, String longitude) {
        if (listLatForResult != null) {
            listLatForResult.add(latitude);
            listLongForResult.add(longitude);
        }
    }

    private void sendBackResult() {
        Log.i("arash", "sendBackResult: listLatForResult" + listLatForResult.toString());
        Log.i("arash", "sendBackResult: listLongForResult" + listLongForResult.toString());
        ActivityIntents.resultOkFromMapAct(this, listLatForResult, listLongForResult);
    }

    // TODO: 1/20/2019 check
    private void workClickNegativeFinalAlert() {
        //do nothings

       /* clearMap(mMap);
        clearResultLists();
        showHideImgClear(false);
        showHideImgConfirm(false);*/
    }

    // TODO: 1/20/2019 check
    private void workClickClearMap() {
        clearMap(mMap);
        clearResultLists();
        showHideImgClear(false);
        showHideImgConfirm(false);
    }

    private void clearMap(GoogleMap mMap) {
        if (mMap != null) {
            mMap.clear();
            if (mClusterManager != null) {
                mClusterManager.clearItems();
            }
        }
    }

    private void zoomCameraForCluster(LatLng latLng) {
        float v;
        if(mMap!=null){
            if (mMap.getCameraPosition().zoom == mMap.getMaxZoomLevel()) {
                v = mMap.getCameraPosition().zoom - 1;
                Log.i("arash", "zoomCameraForCluster: 11111111111");
            } else if (mMap.getCameraPosition().zoom == mMap.getMinZoomLevel()) {
                v = mMap.getCameraPosition().zoom + 1;
                Log.i("arash", "zoomCameraForCluster: 222222222222222222");

            } else if (canZoom) {
                canZoom = false;
                v = mMap.getCameraPosition().zoom + 1;
                Log.i("arash", "zoomCameraForCluster: 33333333333333333333");
            } else {
                canZoom = true;
                v = mMap.getCameraPosition().zoom - 1;
                Log.i("arash", "zoomCameraForCluster: 44444444444444444");
            }
            zoomMapCamera(latLng.latitude, latLng.longitude, v);
        }
    }

    private void zoomMapCamera(double latitude, double longitude, float zoom) {
        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoom));
        }

    }

    private void zoomToGeoJson(GeoJsonLayer geoJsonLayer) {
        if (geoJsonLayer.getBoundingBox() != null && mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(geoJsonLayer.getBoundingBox().getCenter(), NORMAL_ZOOM));
        }
    }

    // TODO: 1/20/2019 check
    private void addMarkerToMap(LatLng latLng) {
        if (checkIntentModel() && mMap != null) {
            switch (intentModel.getSomeString()) {
                case CommonRequestCodes.MAP_GET_ONE_LOCATION + "":
                    mMap.addMarker(new MarkerOptions().position(latLng).draggable(false).icon(BitmapDescriptorFactory.defaultMarker()));
                    break;
                default:
                    if (mClusterManager != null) {
                        MyMarker marker = new MyMarker(latLng.latitude, latLng.longitude, getResources().getString(R.string.delete), "");
                        mClusterManager.addItem(marker);
                    }

            }

        }

    }

    private void clearResultLists() {
        if (listLatForResult != null && listLongForResult != null) {
            listLatForResult.clear();
            listLongForResult.clear();
        }
    }

    private void workClickTraffic(boolean showTraffic) {
        if (mMap != null) {
            setMapTraffic(showTraffic);
            if (showTraffic) {
                float_btn_no_traffic_map_act.hide();
                float_btn_traffic_map_act.show();
            } else {
                float_btn_no_traffic_map_act.show();
                float_btn_traffic_map_act.hide();
            }
        }
    }

    private void setMapTraffic(boolean showHide) {
        mMap.setTrafficEnabled(showHide);
    }

    private void workClickType(boolean showTypeMap) {
        if (mMap != null) {
            setMapType(showTypeMap ? GoogleMap.MAP_TYPE_SATELLITE : GoogleMap.MAP_TYPE_NORMAL);

            if (showTypeMap) {
                float_btn_no_type_map_act.hide();
                float_btn_type_map_act.show();
            } else {
                float_btn_no_type_map_act.show();
                float_btn_type_map_act.hide();
            }
        }
    }

    private void setMapType(int mapType) {
        mMap.setMapType(mapType);
    }

    private LatLng getLatLangFromString(String lat, String lang) {
        return new LatLng(Double.parseDouble(lat), Double.parseDouble(lang));
    }

    private void drawCurve() {
        clearMap(mMap);
        if (listLatForResult.size() != 0 && checkIntentModel()) {
            if (curveManager == null) {
                curveManager = new CurveManager(mMap);
            }
            CurveOptions curveOptions = new CurveOptions();
            for (int i = 0; i < listLatForResult.size(); i++) {
                LatLng latLng = getLatLangFromString(listLatForResult.get(i), listLongForResult.get(i));
                addMarkerToMap(latLng);
                curveOptions.add(latLng);
            }

            if (intentModel.getSomeString().equals(CommonRequestCodes.MAP_GET_SURFACE + "") && listLatForResult.size() != 2) {
                curveOptions.add(getLatLangFromString(listLatForResult.get(0), listLongForResult.get(0)));
                setupPolygon();
            }
            if (intentModel.getSomeString().equals(CommonRequestCodes.MAP_GET_DESTINATION + "") && listLatForResult.size() == 1) {
                curveOptions.add(getLatLangFromString(listLatForResult.get(0), listLongForResult.get(0)));
            }
            curveOptions.color(Color.DKGRAY);
            curveOptions.setAlpha(0f);
            curveOptions.width(5);
            List<PatternItem> pattern = Arrays.asList(new Dash(10), new Gap(10));
            curveOptions.pattern(pattern);
            curveOptions.geodesic(false);
            curveManager.drawCurveAsync(curveOptions);
            zoomCameraForCluster(getLatLangFromString(listLatForResult.get(listLatForResult.size()-1), listLongForResult.get(listLongForResult.size()-1)));

            }
    }

    private void setupPolygon() {
        if (mMap != null && listLatForResult.size() != 0) {
            PolygonOptions polygonOptions = new PolygonOptions();
            for (int i = 0; i < listLatForResult.size(); i++) {
                polygonOptions.add(getLatLangFromString(listLatForResult.get(i), listLongForResult.get(i)));
            }
            polygonOptions.fillColor(Color.GRAY);
            mMap.addPolygon(polygonOptions);
        }
    }

    private void deleteOneMarker(Marker marker) {
        listLatForResult.remove(marker.getPosition().latitude + "");
        listLongForResult.remove(marker.getPosition().longitude + "");
        drawCurve();
    }

    protected Disposable getLocationPermission() {
        return CheckPermissionHandler.getLocationPermissions(this)
                .subscribe(tedPermissionResult -> {
                            manageFindLocationBtn();
                        }

                        , throwable -> {
                    ThrowableLoggerHelper.logMyThrowable("mapActivity/getLocationPermission***" + throwable.getMessage());
                    ToastMessage.show(this,getString(R.string.some_problem_error));
                });
    }

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable=new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }



}
