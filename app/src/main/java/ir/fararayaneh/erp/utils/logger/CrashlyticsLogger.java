package ir.fararayaneh.erp.utils.logger;

import com.google.firebase.crashlytics.FirebaseCrashlytics;


public class CrashlyticsLogger {
    public static void logger(String activityName, String userId){
        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
        crashlytics.log("my log: userId: "+userId + "activity: "+activityName);
    }
}
