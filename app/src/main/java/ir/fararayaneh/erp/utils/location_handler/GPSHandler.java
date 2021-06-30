package ir.fararayaneh.erp.utils.location_handler;



import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;



public class GPSHandler{

    public static void enableGPSAutomatically(GoogleApiClient mGoogleApiClient, LocationRequest mLocationRequest, ResultCallback<LocationSettingsResult>  locationSettingsResultResultCallback) {

        if (mGoogleApiClient != null && mLocationRequest!=null) {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);
            builder.setAlwaysShow(true);
                        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(mGoogleApiClient, builder.build());
            if(locationSettingsResultResultCallback!=null){
                result.setResultCallback(locationSettingsResultResultCallback);
            }
        }
    }


}
