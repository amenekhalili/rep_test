package ir.fararayaneh.erp.utils.work_manager_helper;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import ir.fararayaneh.erp.App;

public class CallWorkManager {

    public static void callingWorker() {

        PeriodicWorkRequest periodicWorkRequest=new PeriodicWorkRequest
                .Builder(SocketReceiverWorker.class,5, TimeUnit.SECONDS)
                //.setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build();

        WorkManager.getInstance(App.getAppContext()).enqueue(periodicWorkRequest);


        Log.i("arash", "callingWorker: were be called");
    }
}
