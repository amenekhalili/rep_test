package ir.fararayaneh.erp.services;



import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.utils.NotificationHelper;
import ir.fararayaneh.erp.utils.work_manager_helper.CallWorkManager;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onCreate() {
        Log.i("arash", "onCreate: MyFireBaseMessagingService");
        super.onCreate();
    }

    /**
     * remote message will be notified if app not be ran and we get message with notif data
     * in node server ==>
     * (let notification = {
     *   android: {priority: "high"},
     *   data: {"node" : "hello"},
     *   token : "",
     *   notification:{
     *     "title":"Please chech your Application",
     *   }
     * };)
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //because my service is long running, call that by work manager (below 10 seconds have time)
        Log.i("arash", "onStartCommand: MyFireBaseMessagingService:"+remoteMessage.getData());
        Log.i("arash", "onStartCommand: MyFireBaseMessagingService:"+remoteMessage.getNotification());
        CallWorkManager.callingWorker();
    }

    @Override
    public void onNewToken(@NonNull String fireBaseToken) {
        super.onNewToken(fireBaseToken);
        SharedPreferenceProvider.setDeviceId(App.getAppContext(), fireBaseToken);
    }

    /**
    no need if we do not extend from service , this method not show notify automatically
     */
    /*@Override
    public  int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("arash", "onStartCommand: MyFireBaseMessagingService");
        SocketService.intentToSocketService(App.getAppContext());
        return START_STICKY;
    }*/

    @Override
    public void onDestroy() {
        Log.i("arash", "onDestroy: MyFireBaseMessagingService");
        super.onDestroy();
    }


}