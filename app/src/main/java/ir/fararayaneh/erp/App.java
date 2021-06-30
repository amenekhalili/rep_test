package ir.fararayaneh.erp;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.yariksoffice.lingver.Lingver;
/*import com.squareup.leakcanary.LeakCanary;*/

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Objects;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureBufferStrategy;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.socket.client.IO;
import io.socket.client.Socket;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.dagger.Appcomponent;
import ir.fararayaneh.erp.dagger.Appcomponent2;
import ir.fararayaneh.erp.dagger.Appcomponent3;
import ir.fararayaneh.erp.dagger.DaggerAppcomponent;
import ir.fararayaneh.erp.dagger.DaggerAppcomponent2;
import ir.fararayaneh.erp.dagger.DaggerAppcomponent3;
import ir.fararayaneh.erp.dagger.modules.RealmModule;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.NullRXModel;
import ir.fararayaneh.erp.data.models.middle.AttachmentDownloadModel;
import ir.fararayaneh.erp.data.net.APIService;
import ir.fararayaneh.erp.data.net.Api;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.database_handler.MyMigrations;
import ir.fararayaneh.erp.utils.work_manager_helper.CallWorkManager;

public class App extends MultiDexApplication {

    private static final String FONT_PATH = "font/IRANSansMobile.ttf";
    private static NullRXModel nullRXModel;
    public static APIService apiService;
    private static Context context;
    private static Gson mGson;
    private static Socket mSocket;
    private String fireBaseToken;
    /**
     * for check my app is in foreground or not (use in show notification)
     */
    private static ArrayList<Integer> listOfPowerOnActivity;

    public static boolean isAppPowerOn() {
        if (listOfPowerOnActivity == null) {
            return false;
        }
        return listOfPowerOnActivity.size() != 0;
    }

    public static void addToAppPowerOnList() {
        if (listOfPowerOnActivity == null) {
            listOfPowerOnActivity = new ArrayList<>();
        }
        listOfPowerOnActivity.add(1);
        Log.i("arash", "removeFromAppPowerOnList: " + listOfPowerOnActivity.size());
    }

    public static void removeFromAppPowerOnList() {
        if (listOfPowerOnActivity == null || listOfPowerOnActivity.size() == 0) {
            return;
        }
        listOfPowerOnActivity.remove(0);
        Log.i("arash", "removeFromAppPowerOnList: " + listOfPowerOnActivity.size());
    }
    //---------------------------------------------------------
    /**
     * string is guid of those file that need to be download and use as tag in android networking
     */
    private static Flowable<String> finishDownloadFlowAble;
    private static FlowableEmitter<String> finishDownloadEmitter;
    public static ListIterator<AttachmentDownloadModel> downloadListIterator = new ArrayList<AttachmentDownloadModel>().listIterator();
    public static boolean oneDownloadIsInProgress;
    private static ArrayList<String> cancelDownloadTagList;

    public static FlowableEmitter<String> getFinishDownloadEmitter() {
        return finishDownloadEmitter;
    }

    public static Flowable<String> getFinishDownloadFlowAble() {
        if (finishDownloadFlowAble == null) {
            finishDownloadFlowAble = FlowableOnBackpressureBufferStrategy.create(emitter -> finishDownloadEmitter = emitter, BackpressureStrategy.BUFFER);
        }
        return finishDownloadFlowAble;
    }

    public static ArrayList<String> getCancelDownloadTagList() {
        return cancelDownloadTagList == null ? new ArrayList<>() : cancelDownloadTagList;
    }

    public static void removeFromCancellDownloadTagList(String downloadTag) {
        if(cancelDownloadTagList!=null){
            cancelDownloadTagList.remove(downloadTag);
        }
    }

    public static void addToCancellDownloadTagList(String downloadTag) {
        getCancelDownloadTagList().add(downloadTag);
    }
    //---------------------------------------------------------

    public static Context getAppContext() {
        return context;
    }

    public static Gson getmGson() {
        if (mGson == null)
            mGson = new Gson();
        return mGson;
    }

    public static NullRXModel getNullRXModel() {
        if (nullRXModel == null) {
            nullRXModel = new NullRXModel();
        }
        return nullRXModel;
    }

    /**
     * deprecated because use of Android-networking-library
     */
    public static APIService getApiService() {
        if (apiService == null)
            apiService = Api.createService(APIService.class);
        return apiService;
    }

    public static Socket getmSocket() {
        return mSocket;
    }

    public static void setmSocket(Socket mSocket) {
        App.mSocket = mSocket;
    }

    public static Appcomponent appcomponent;
    public static Appcomponent2 appcomponent2;
    public static Appcomponent3 appcomponent3;

    public static Appcomponent getAppcomponent() {
        return appcomponent;
    }

    public static Appcomponent2 getAppcomponent2() {
        return appcomponent2;
    }

    public static Appcomponent3 getAppcomponent3() {
        return appcomponent3;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        configFont();
        setupContext();
        configLocal();
        setUpRealm();
        initFireBase();
        setupAppComponent();
        setupAndroidNetWorking();
        installLeakDetector();
        CallWorkManager.callingWorker();

    }

    private void configLocal() {
        Lingver.init(this, SharedPreferenceProvider.getLocalLanguage(context));
    }

    private void setupAndroidNetWorking() {
        Api.initAndroidNetwork(this);
    }

    // TODO: 1/27/2019  
    private void installLeakDetector() {
       /* if (BuildConfig.DEBUG && 1==2) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }*/
    }

    private void setupContext() {
        context = getApplicationContext();
    }

    private void setUpRealm() {

        //todo encrypt realm in android key store
        Realm.init(context);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .schemaVersion(Commons.REALM_VERSION)
                .migration(new MyMigrations())
                .build());

    }

    private void setupAppComponent() {
        appcomponent = DaggerAppcomponent.builder()
                .realmModule(new RealmModule()).build();
        appcomponent2 = DaggerAppcomponent2.builder().build();
        appcomponent3 = DaggerAppcomponent3.builder().build();
    }

    private void configFont() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath(FONT_PATH)
                                .setFontAttrId(R.attr.fontPath)
                                .build())).build());

    }

    private void initFireBase() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.i("arash", "onComplete: firebase not init ");
                        return;
                    }
                    fireBaseToken = Objects.requireNonNull(task.getResult()).getToken();
                    Log.i("arash", "onComplete:token " + fireBaseToken);
                    SharedPreferenceProvider.setDeviceId(getAppContext(), fireBaseToken);
                    checkUserId();
                });
    }

    /**
     * only if we have userId and fireBaseId, connect to socket and crashlytics
     */
    private void checkUserId() {
        if (!SharedPreferenceProvider.getUserId(context).equals(Commons.NULL_INTEGER)) {
            installCrashLytics();
            setupMySocket();
        }
    }

    private void installCrashLytics() {
        logUserDataForCrash();
    }

    private void logUserDataForCrash() {
        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
        crashlytics.setUserId(SharedPreferenceProvider.getUserId(context));
        crashlytics.setUserId(fireBaseToken);
    }

    /**
     * we call this method after online login or here
     * in all two case , we sure that we have deviceId and userId
     */
    public static void setupMySocket() {
        try {
            mSocket = IO.socket(String.format(CommonsFormat.FORMAT_4, SharedPreferenceProvider.getNodeIp(context), SharedPreferenceProvider.getNodePort(context), SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getDeviceId(context)));
            Log.i("arash", "App/setupMySocket: " + String.format(CommonsFormat.FORMAT_4, SharedPreferenceProvider.getNodeIp(context), SharedPreferenceProvider.getNodePort(context), SharedPreferenceProvider.getUserId(context), SharedPreferenceProvider.getDeviceId(context)));
            CallWorkManager.callingWorker();
        } catch (URISyntaxException e) {
            ThrowableLoggerHelper.logMyThrowable("**App/setupMySocket" + e.getMessage());
        }
    }


}
