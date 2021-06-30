package ir.fararayaneh.erp.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonNotification;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.attach_query.FindFirstUnSyncAttachmentRowQuery;
import ir.fararayaneh.erp.data.data_providers.queries.evacuate.SendAllEvacuatePacketQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.utils.NotificationHelper;
import ir.fararayaneh.erp.utils.PingHandler;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.package_handler.CheckAndroidOVersion;

/**
 * این سرویس برای ارسال همه پکت های موجود در جدول
 * Evacuate
 *  بدون توجه به اتصال نت یا دیگر موارد ارسال میگردد
 */
public class EvacuatePacketForeGroundService extends Service {

    private CompositeDisposable compositeDisposable;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setupForegroundNotify();
        return START_STICKY;
    }

    /**
     * FOREGROUND_NOTIFICATIONS_UPLOAD_ID != 0
     * notify priority should be greater than low
     */
    private void setupForegroundNotify() {
        /* after maximum 5 second, this code should be ran*/
        startForeground(CommonNotification.FOREGROUND_EVACUATION_PACKET_ID, NotificationHelper.setForeGroundEvacuatePacketNotification(this));
        if (compositeDisposable == null) {
            startProcess();
        }
    }

    private void startProcess() {
        createDisposable();
        SharedPreferenceProvider.setEvacuationProcess(this, true);
        if(PingHandler.checkPingNode()){
            addDisposable(sendEvacuateRows());
        } else {
            stopEvacuatePacketForGroundService();
        }

    }

    private Disposable sendEvacuateRows() {
        SendAllEvacuatePacketQuery sendAllEvacuatePacketQuery = (SendAllEvacuatePacketQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SEND_ALL_EVACUATE_PACKET_QUERY);
        assert sendAllEvacuatePacketQuery != null;
        return sendAllEvacuatePacketQuery.data(App.getNullRXModel()).subscribe(iModels -> {
            Log.i("arash", "sendEvacuateRows: work were be done");
            stopEvacuatePacketForGroundService();
        }, throwable -> {
            ThrowableLoggerHelper.logMyThrowable("sendEvacuateRows: work error" + throwable.getMessage());
            stopEvacuatePacketForGroundService();
        });
    }

    private void createDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    private void addDisposable(Disposable disposable) {
        if (compositeDisposable != null)
            compositeDisposable.add(disposable);
    }

    private void clearDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void onDestroy() {
        stopEvacuatePacketForGroundService();
        super.onDestroy();
    }

    public void stopEvacuatePacketForGroundService() {
        Log.i("arash", "stopEvacuatePacketForGroundService: ");
        SharedPreferenceProvider.setEvacuationProcess(this, false);
        clearDisposable();
        stopForeground(true);
        stopSelf();
    }

    public static void intentToEvacuatePacketForeGroundService(Context context) {
        Log.i("arash", "intentToEvacuatePacketForeGroundService: "+SharedPreferenceProvider.haveEvacuateProcess(context));
       // if(!SharedPreferenceProvider.haveEvacuateProcess(context)){
            if (CheckAndroidOVersion.SDKVersionAboveO()) {
                context.startForegroundService(new Intent(context, EvacuatePacketForeGroundService.class));
            } else {
                context.startService(new Intent(context, EvacuatePacketForeGroundService.class));
            }
       // }
    }

}

