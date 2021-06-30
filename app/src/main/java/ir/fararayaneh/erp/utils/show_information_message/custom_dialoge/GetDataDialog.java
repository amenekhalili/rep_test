package ir.fararayaneh.erp.utils.show_information_message.custom_dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonDialogeClick;
import ir.fararayaneh.erp.commons.CommonDialogeType;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.utils.EditTextCheckHelper;

/**
 * این کلاس برای نمایش یک دیالوگ برای ورود اطلاعات دلخواه متنی از سوی یوزر است
 * دیتایی که به اینجا فرستاده میشود شامل
 * یکی از ثابت های
 * CommonComboClickType
 * (userMessageModel.getMessage())
 * و یک مقدار تکست برای نمایش توضیح به یوزر است
 * (userMessageModel.getTitle())
 * و متن تایپ شده قبلی از سوی یوزر
 * (userMessageModel.getSomeValue())
 * و آنچه از اینجا فرستاده میشود
 * متن تایپ شده توسط یوزر
 * و همان ثابت
 * CommonComboClickType
 * است
 */
public class  GetDataDialog extends Dialog {


    @BindView(R.id.txt_get_data)
    AppCompatTextView txt_get_data;
    @BindView(R.id.edt_get_data)
    TextInputEditText edt_get_data;


    private Activity activity;
    private MessageListener messageListener;
    private UserMessageModel userMessageModel;


    public GetDataDialog(@NonNull Activity activity, MessageListener messageListener, UserMessageModel userMessageModel) {
        super(activity);
        this.activity = activity;
        this.messageListener = messageListener;
        this.userMessageModel = userMessageModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_get_data_layout);
        ButterKnife.bind(this);
        initAlert();
    }

    private void initAlert() {
        setAlertSize(activity);
        prepareDialog();
        setCancelable(true);
    }

    private void prepareDialog() {
        edt_get_data.setHint(userMessageModel.getTitle());
        edt_get_data.setText(userMessageModel.getOtherData());
    }

    @OnClick({R.id.txt_get_data})
    public void clickGetDataDialog(View view) {

        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyInt(Integer.valueOf(userMessageModel.getMessage()));
        globalModel.setMyString(Commons.NULL);
        if (EditTextCheckHelper.checkWithoutRaiseError(edt_get_data)) {
            globalModel.setMyString(Objects.requireNonNull(edt_get_data.getText()).toString());
        }
        messageListener.listener(CommonDialogeType.GET_DATA_DIALOG, CommonDialogeClick.CLICK_CONFIRM, globalModel);
        dismiss();

    }


    private void setAlertSize(Activity a) {
        //Objects.requireNonNull(getWindow()).setLayout(a.getWindowManager().getDefaultDisplay().getWidth() - Commons.MARGIN_ALERT_LEFT_RIGHT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //Objects.requireNonNull(getWindow()).setLayout(a.getWindowManager().getDefaultDisplay().getWidth() - 0, ViewGroup.LayoutParams.WRAP_CONTENT);


        Window window = this.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
    }
}