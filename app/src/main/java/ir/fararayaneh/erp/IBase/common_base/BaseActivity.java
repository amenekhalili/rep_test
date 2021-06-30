package ir.fararayaneh.erp.IBase.common_base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

//import com.squareup.leakcanary.LeakCanary;

import java.lang.ref.WeakReference;

import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.services.SocketService;
import ir.fararayaneh.erp.utils.ContextHelper;
import ir.fararayaneh.erp.utils.NotificationHelper;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.intent_handler.IntentReceiverHandler;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.molty_select_dialoge.MoltySelectDialogHelper;

import static androidx.appcompat.app.AppCompatDelegate.setCompatVectorFromResourcesEnabled;

public abstract class BaseActivity<P extends BasePresenter, V extends BaseView> extends AppCompatActivity {

    static {
        setCompatVectorFromResourcesEnabled(true);
    }

    protected P presenter;
    protected V view;
    protected WeakReference<V> weekView;

    public V getView() {
        return view;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    /**
     * onCreate methods should perform in this arrangement, do not change them !!
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setUpTheme();
        super.onCreate(savedInstanceState);
        initView();
        createWeakView();
        setContentView(view);
        initPresenter();
        setupOnCreate();
        setupLeakDetector();
        NotificationHelper.clearAllNotif(this);
        callSocketService();
    }


    private void setUpTheme() {
        setTheme(SharedPreferenceProvider.getAppTheme(this));
        AppCompatDelegate.setDefaultNightMode(SharedPreferenceProvider.getNightMode(this));
    }

    protected abstract void initView();

    protected void createWeakView() {
        weekView = new WeakReference<>(view);
    }

    protected abstract void initPresenter();

    private void setupOnCreate() {
        if (presenter != null) {
            if (!presenter.shouldHaveIntent) {
                presenter.onCreate(null);
            } else {
                if (getIntent() != null) {
                    if (getIntent().getExtras() != null) {
                        presenter.onCreate(IntentReceiverHandler.getIntentData(getIntent().getExtras()));
                    } else {
                        if (view != null) {
                            goToMainActivity();
                        }
                    }

                } else {
                    if (view != null) {
                        goToMainActivity();
                    }
                }
            }
        }
    }

    //todo
    private void setupLeakDetector() {
        /*if(BuildConfig.DEBUG && 1==2){
            LeakCanary.refWatcher(this)
                    .watchDelay(1, TimeUnit.SECONDS);
        }*/
    }

    private void goToMainActivity() {
        ActivityIntents.goMainActivity(this);
        if (view != null) {
            view.finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null)
            presenter.onResume();
    }

    @Override
    public void onPause() {
        if (presenter != null)
            presenter.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        if (presenter != null)
            presenter.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (presenter != null)
            presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (presenter != null)
            presenter.onBackPress();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ContextHelper.customContext(newBase));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (presenter != null) {
            presenter.workForPermissionResults(requestCode, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (presenter != null ) {
                presenter.workForActivityResult(requestCode, resultCode, data);
            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (view != null) {
            view.workforOnCreateOptionsMenu(menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * click on toolbar menu or toggle
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (view != null) {
            return view.workforOnOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (presenter != null) {
            presenter.onSaveInstantstate(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (presenter != null) {
            presenter.onRestoreInstantstate(savedInstanceState);
        }
    }

    protected void callSocketService(){
        SocketService.intentToSocketService(this);
    }

}
