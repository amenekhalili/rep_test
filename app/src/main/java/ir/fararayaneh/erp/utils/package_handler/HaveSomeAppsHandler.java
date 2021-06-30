package ir.fararayaneh.erp.utils.package_handler;

import android.content.Context;
import android.content.pm.PackageManager;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;


public class HaveSomeAppsHandler {

    private static boolean havePackage;

    public static boolean havePackage(Context context, ArrayList<String> packageNameList){

        PackageManager pm=context.getPackageManager();

        Stream.of(packageNameList).forEach(packageName -> {
            try {
                pm.getPackageInfo(packageName,PackageManager.GET_META_DATA);
                havePackage=true;
            } catch (PackageManager.NameNotFoundException e) {
                ThrowableLoggerHelper.logMyThrowable("HaveSomeAppsHandler***havePackage"+e.getMessage());
                havePackage=false;
            }
        });

        return havePackage;
    }
}
