package ir.fararayaneh.erp.activities.barcode_scanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.utils.data_handler.CheckPermissionHandler;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static androidx.appcompat.app.AppCompatDelegate.setCompatVectorFromResourcesEnabled;


public class BarCodeActivity extends AppCompatActivity {

    static {
        setCompatVectorFromResourcesEnabled(true);
    }


    private ZXingScannerView mScannerView;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);

    }

    @Override
    protected void onStart() {
        super.onStart();
        App.addToAppPowerOnList();
        disposable
                .add(CheckPermissionHandler.getCameraPermissions(this)
                        .subscribe(tedPermissionResult -> {
                            if (!tedPermissionResult.isGranted()) {
                                cancelResultIntent();
                            } else {
                                mScannerView = (ZXingScannerView) findViewById(R.id.barcodeScanner);
                                setupBarCode();
                            }
                        }, throwable -> {
                            ThrowableLoggerHelper.logMyThrowable("barcodeActivity/getAttachPermissions***" + throwable.getMessage());
                            ToastMessage.show(this, getString(R.string.some_problem_error));
                            cancelResultIntent();
                        }));

    }

    private void setupBarCode() {
        mScannerView.setResultHandler(rawResult -> {
            Log.i("arash", "handleResult: BarCodeActivity:" + rawResult.toString());
            sendBackResult(rawResult.toString());
        });
        mScannerView.setAspectTolerance(0.5f);
        mScannerView.setAutoFocus(true);
        mScannerView.setFlash(true);
        mScannerView.startCamera();
    }

    @Override
    protected void onStop() {
        if (mScannerView != null) {
            mScannerView.stopCamera();
            mScannerView.setResultHandler(null);
        }
        if (disposable != null) {
            disposable.clear();
        }
        App.removeFromAppPowerOnList();
        super.onStop();

    }

    protected void cancelResultIntent() {
        ActivityIntents.cancelResultIntent(this);
        finish();
    }

    private void sendBackResult(String result) {
        ActivityIntents.resultOkFromBarCodeAct(this, result);
    }
}
