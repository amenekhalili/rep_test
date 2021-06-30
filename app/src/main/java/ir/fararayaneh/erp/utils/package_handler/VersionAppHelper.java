package ir.fararayaneh.erp.utils.package_handler;

import android.content.Context;
import android.content.pm.PackageManager;

import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class VersionAppHelper {


    public static int getMyVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            ThrowableLoggerHelper.logMyThrowable("VersionAppHelper/**getMyVersionCode"+e.getLocalizedMessage());
            return 0;
        }
    }

    public static String getMyVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            ThrowableLoggerHelper.logMyThrowable("VersionAppHelper/**getMyVersionName"+e.getLocalizedMessage());
            return Commons.SPACE;
        }
    }

}
