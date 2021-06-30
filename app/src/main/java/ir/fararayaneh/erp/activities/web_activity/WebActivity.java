package ir.fararayaneh.erp.activities.web_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonWebApplicationNumber;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.utils.ContextHelper;
import ir.fararayaneh.erp.utils.data_handler.HavingUserHandler;
import ir.fararayaneh.erp.utils.data_handler.IHaveUserListener;
import ir.fararayaneh.erp.utils.intent_handler.IntentReceiverHandler;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;

import static androidx.appcompat.app.AppCompatDelegate.setCompatVectorFromResourcesEnabled;

public class WebActivity extends AppCompatActivity {

    static {
        setCompatVectorFromResourcesEnabled(true);
    }

    private static final String TEXT_ENCODING = "utf-8";
    private static final int FONT_SIZE = 14;
    private boolean isFirstLogin = true;
    private IHaveUserListener iHaveUserListener;
    private CompositeDisposable compositeDisposable;
    private String appId = CommonWebApplicationNumber.GRL_APP_NUMBER;
    private String pageId = CommonWebApplicationNumber.GRL_PAGE_NUMBER;
    private String formCode = Commons.SPACE;


    @BindView(R.id.web_view_web_activity)
    WebView webView;
    @BindView(R.id.web_progress)
    ConstraintLayout web_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUpTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_activity);
        ButterKnife.bind(this);
        appId = IntentReceiverHandler.getIntentData(Objects.requireNonNull(getIntent().getExtras())).getSomeString();
        pageId = IntentReceiverHandler.getIntentData(getIntent().getExtras()).getSomeString2();
        formCode = IntentReceiverHandler.getIntentData(getIntent().getExtras()).getSomeString3();

        addDisposable(getUserAccount());

    }

    private Disposable getUserAccount() {
        iHaveUserListener = new IHaveUserListener() {
            @Override
            public void haveUser(boolean user, GlobalModel globalModel) {
                setupWebView(globalModel.getUserName(),globalModel.getPassWord(),globalModel.getOrganization());
            }

            @Override
            public void error() {
                ToastMessage.show(getBaseContext(),getResources().getString(R.string.some_problem_error));
            }
        };
        return HavingUserHandler.haveUser(iHaveUserListener);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView(String userName, String password, String organization) {
        webView.requestFocusFromTouch();
        webView.canGoBack();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName(TEXT_ENCODING);
        webSettings.setDefaultFontSize(FONT_SIZE);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                onPageStartWorks(url);
                super.onPageStarted(view, url, favicon);
            }
            public void onPageFinished(final WebView view, String url) {
                onPageFinishedWork(url,userName,password,organization);
            }
        });

        loadLoginPage();
    }

    private void onPageStartWorks(String url) {
        Log.i("arash", "onPageStarted: "+url);
        showHideProgress(true);
    }

    private void onPageFinishedWork(String url,String userName, String password, String organization) {
        Log.i("arash", "onPageFinishedWork: "+url);
            if (url.startsWith(SharedPreferenceProvider.getOnlineServerIp(getBaseContext()))) {
                if (isFirstLogin) {
                    onlineLoginProcess(userName, password, organization);
                } else {
                    onBackPressed();
                }
            } else {
                showHideProgress(false);
            }
    }

    private void loadLoginPage() {
        webView.loadUrl(SharedPreferenceProvider.getOnlineServerIp(this));
    }

    private void onlineLoginProcess(String userName, String password, String organization) {
        Log.i("arash", "onlineLoginProcess: "+userName);
        isFirstLogin = false;
       webView.loadUrl("javascript:phoneLogin('" + userName + "','" + password + "','" + organization+"','"+appId+"','"+pageId+"','"+formCode+"')");
    }

    private void setUpTheme() {
        setTheme(SharedPreferenceProvider.getAppTheme(this));
        AppCompatDelegate.setDefaultNightMode(SharedPreferenceProvider.getNightMode(this));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ContextHelper.customContext(newBase));
    }

    @Override
    protected void onStart() {
        super.onStart();
        App.addToAppPowerOnList();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.setWebViewClient(null);
        }
        iHaveUserListener = null;
        App.removeFromAppPowerOnList();
        clearDisposable();
        super.onDestroy();
    }

    public void clearDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable=new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    private void showHideProgress(boolean showHide) {
        web_progress.setVisibility(showHide ? View.VISIBLE : View.GONE);
    }
}
