package ir.fararayaneh.erp.utils.package_handler;

import android.os.Build;

public class CheckAndroidOVersion {

    public static boolean SDKVersionAboveO(){
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }
}
