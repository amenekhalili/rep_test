package ir.fararayaneh.erp.utils.location_handler;

import android.location.Location;

import ir.fararayaneh.erp.commons.CommonsFormat;

public class LocationToString {

    public static String getString(Location location){
        return String.format(CommonsFormat.FORMAT_3,location.getLatitude(),location.getLongitude());
    }
}
