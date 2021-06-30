package ir.fararayaneh.erp.utils.location_handler;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;

import java.util.List;

import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class MockLocationHelper {

    public static boolean check(Context context) {
        int count = 0;
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")){
            Log.i("arash", "check mokc is enable: false");
        } else{
            count++;
        }

        //check if mock permission is in device :
        /*PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo applicationInfo : packages) {
            PackageInfo packageInfo = null;
            try {
                packageInfo = pm.getPackageInfo(applicationInfo.packageName,
                        PackageManager.GET_PERMISSIONS);
            } catch (PackageManager.NameNotFoundException e) {
                ThrowableLoggerHelper.logMyThrowable("MockLocationHelper/check"+e.getMessage());
            }
            assert packageInfo != null;
            String[] requestedPermissions = packageInfo.requestedPermissions;

            if (requestedPermissions != null) {
                for (String requestedPermission : requestedPermissions) {
                    if (requestedPermission
                            .equals("android.permission.ACCESS_MOCK_LOCATION")
                            && !applicationInfo.packageName.equals(context.getPackageName())) {
                        Log.i("arash", "check: "+applicationInfo.packageName);

                        if (Settings.Secure.getString(context.getContentResolver(),
                                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")){
                            Log.i("arash", "check: 0000000000000000000000");
                        } else{
                            count++;
                        }
                    }
                }
            }
        }*/

        return count > 0;
    }
}
