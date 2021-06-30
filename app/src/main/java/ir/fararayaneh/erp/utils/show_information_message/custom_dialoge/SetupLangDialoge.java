package ir.fararayaneh.erp.utils.show_information_message.custom_dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonDialogeClick;
import ir.fararayaneh.erp.commons.CommonDialogeType;
import ir.fararayaneh.erp.commons.Commons;

public class SetupLangDialoge extends Dialog {

    @BindView(R.id.btn_persian_alert_dialoge)
    AppCompatTextView btn_persian_alert_dialoge;
    @BindView(R.id.btn_english_alert_dialoge)
    AppCompatTextView btn_english_alert_dialoge;
    @BindView(R.id.btn_arabic_alert_dialoge)
    AppCompatTextView btn_arabic_alert_dialoge;



    private Activity activity;
    private MessageListener messageListener;


    public SetupLangDialoge(@NonNull Activity activity, MessageListener messageListener) {
        super(activity);
        this.activity = activity;
        this.messageListener = messageListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_setting_lang_layout);
        ButterKnife.bind(this);
        initAlert();
    }

    private void initAlert() {
        setAlertSize(activity);
        setCancelable(true);
    }



    @OnClick({R.id.btn_persian_alert_dialoge,R.id.btn_english_alert_dialoge,R.id.btn_arabic_alert_dialoge,})
    public void clickSetupLangDialoge(View view) {
        switch (view.getId()) {
            case R.id.btn_persian_alert_dialoge:
                messageListener.listener(CommonDialogeType.SETUP_LANGUAGE_DIALOG,CommonDialogeClick.CLICK_PERSIAN_LANG,null);
                break;
            case R.id.btn_english_alert_dialoge:
                messageListener.listener(CommonDialogeType.SETUP_LANGUAGE_DIALOG,CommonDialogeClick.CLICK_ENGLISH_LANG,null);
                break;
            case R.id.btn_arabic_alert_dialoge:
                messageListener.listener(CommonDialogeType.SETUP_LANGUAGE_DIALOG,CommonDialogeClick.CLICK_ARABIC_LANG,null);
                break;
        }
        dismiss();
    }




    private void setAlertSize(Activity a) {
        Objects.requireNonNull(getWindow()).setLayout(a.getWindowManager().getDefaultDisplay().getWidth() - Commons.MARGIN_ALERT_LEFT_RIGHT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}

