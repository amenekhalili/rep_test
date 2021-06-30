package ir.fararayaneh.erp.utils.show_information_message.custom_dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
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

public class SelectColorDialog  extends Dialog {

    @BindView(R.id.btn_style_default)
    AppCompatButton btn_style_default;
    @BindView(R.id.btn_style_2)
    AppCompatButton btn_style_2;
    @BindView(R.id.btn_style_3)
    AppCompatButton btn_style_3;
    @BindView(R.id.btn_style_4)
    AppCompatButton btn_style_4;
    @BindView(R.id.btn_style_5)
    AppCompatButton btn_style_5;
    @BindView(R.id.btn_style_6)
    AppCompatButton btn_style_6;
    @BindView(R.id.btn_style_7)
    AppCompatButton btn_style_7;
    @BindView(R.id.btn_style_8)
    AppCompatButton btn_style_8;


    private Activity activity;
    private MessageListener messageListener;


    public SelectColorDialog(@NonNull Activity activity, MessageListener messageListener) {
        super(activity);
        this.activity = activity;
        this.messageListener = messageListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_setting_color_layout);
        ButterKnife.bind(this);
        initAlert();
    }

    private void initAlert() {
        setAlertSize(activity);
        setCancelable(true);
    }

    @OnClick({R.id.btn_style_default,R.id.btn_style_2,R.id.btn_style_3,R.id.btn_style_4,R.id.btn_style_5,R.id.btn_style_6,R.id.btn_style_7,R.id.btn_style_8})
    public void clickSelectColorDialog(View view) {
        switch (view.getId()) {
            case R.id.btn_style_default:
                messageListener.listener(CommonDialogeType.SETUP_COLOR_DIALOG,CommonDialogeClick.CLICK_COLOR_1,null);
                break;
            case R.id.btn_style_2:
                messageListener.listener(CommonDialogeType.SETUP_COLOR_DIALOG,CommonDialogeClick.CLICK_COLOR_2,null);
                break;
            case R.id.btn_style_3:
                messageListener.listener(CommonDialogeType.SETUP_COLOR_DIALOG,CommonDialogeClick.CLICK_COLOR_3,null);
                break;
            case R.id.btn_style_4:
                messageListener.listener(CommonDialogeType.SETUP_COLOR_DIALOG,CommonDialogeClick.CLICK_COLOR_4,null);
                break;
            case R.id.btn_style_5:
                messageListener.listener(CommonDialogeType.SETUP_COLOR_DIALOG,CommonDialogeClick.CLICK_COLOR_5,null);
                break;
            case R.id.btn_style_6:
                messageListener.listener(CommonDialogeType.SETUP_COLOR_DIALOG,CommonDialogeClick.CLICK_COLOR_6,null);
                break;
            case R.id.btn_style_7:
                messageListener.listener(CommonDialogeType.SETUP_COLOR_DIALOG,CommonDialogeClick.CLICK_COLOR_7,null);
                break;
            case R.id.btn_style_8:
                messageListener.listener(CommonDialogeType.SETUP_COLOR_DIALOG,CommonDialogeClick.CLICK_COLOR_8,null);
                break;
        }
        dismiss();
    }

    private void setAlertSize(Activity a) {
        Objects.requireNonNull(getWindow()).setLayout(a.getWindowManager().getDefaultDisplay().getWidth() - Commons.MARGIN_ALERT_LEFT_RIGHT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
