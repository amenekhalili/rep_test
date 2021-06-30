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
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;

public class SelectCalendarTypeDialoge extends Dialog {


    @BindView(R.id.btn_hijri_calendar)
    AppCompatTextView btn_hijri_calendar;
    @BindView(R.id.btn_gregorian_calendar)
    AppCompatTextView btn_gregorian_calendar;


    private Activity activity;
    private MessageListener messageListener;


    public SelectCalendarTypeDialoge(@NonNull Activity activity, MessageListener messageListener) {
        super(activity);
        this.activity = activity;
        this.messageListener = messageListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_setting_calendar_layout);
        ButterKnife.bind(this);
        initAlert();
    }

    private void initAlert() {
        setAlertSize(activity);
        setCancelable(true);
    }

    @OnClick({R.id.btn_hijri_calendar,R.id.btn_gregorian_calendar})
    public void clickSetupCalendarDialoge(View view) {
        switch (view.getId()) {
            case R.id.btn_hijri_calendar:
                SharedPreferenceProvider.setCalendarType(activity,Commons.HIJRI_CALENDAR);
                dismiss();
                break;
            case R.id.btn_gregorian_calendar:
                SharedPreferenceProvider.setCalendarType(activity,Commons.GREGORIAN_CALENDAR);
                dismiss();
                break;
        }
    }


    private void setAlertSize(Activity a) {
        Objects.requireNonNull(getWindow()).setLayout(a.getWindowManager().getDefaultDisplay().getWidth() - Commons.MARGIN_ALERT_LEFT_RIGHT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}