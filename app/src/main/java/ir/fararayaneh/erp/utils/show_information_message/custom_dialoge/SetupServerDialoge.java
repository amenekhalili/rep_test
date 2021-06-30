package ir.fararayaneh.erp.utils.show_information_message.custom_dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import android.view.View;
import android.view.ViewGroup;

import com.shawnlin.numberpicker.NumberPicker;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonDialogeClick;
import ir.fararayaneh.erp.commons.CommonDialogeType;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.utils.IPHandler;

public class SetupServerDialoge extends Dialog {

    @BindView(R.id.num_pick_ip1_alert_dialoge)
    NumberPicker num_pick_ip1_alert_dialoge;
    @BindView(R.id.num_pick_ip2_alert_dialoge)
    NumberPicker num_pick_ip2_alert_dialoge;
    @BindView(R.id.num_pick_ip3_alert_dialoge)
    NumberPicker num_pick_ip3_alert_dialoge;
    @BindView(R.id.num_pick_ip4_alert_dialoge)
    NumberPicker num_pick_ip4_alert_dialoge;
    @BindView(R.id.btn_confirm_alert_dialoge)
    AppCompatTextView btn_confirm_alert_dialoge;


    private Activity activity;
    private MessageListener messageListener;


    public SetupServerDialoge(@NonNull Activity activity, MessageListener messageListener) {
        super(activity);
        this.activity = activity;
        this.messageListener = messageListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_setting_server_layout);
        ButterKnife.bind(this);
        initAlert();
    }

    private void initAlert() {
        setAlertSize(activity);
        setPrimitiveData();
        setCancelable(true);
    }

    private void setPrimitiveData() {
        if(SharedPreferenceProvider.getServerConfigs(activity).equals(Commons.NO_HAVE_CONFIGS_FOR_SERVER)){
           // setupSelector(213,108,240,238);//fara
            setupSelector(79,127,57,113);//ahvaz
        } else {
            String[] ipSplit=IPHandler.splitter(SharedPreferenceProvider.getServerConfigs(activity));
            setupSelector(Integer.valueOf(ipSplit[0].trim()),Integer.valueOf(ipSplit[1].trim()),Integer.valueOf(ipSplit[2].trim()),Integer.valueOf(ipSplit[3].trim()));
        }
    }

    private void setupSelector(int i1,int i2, int i3,int i4) {
        num_pick_ip1_alert_dialoge.setValue(i1);
        num_pick_ip2_alert_dialoge.setValue(i2);
        num_pick_ip3_alert_dialoge.setValue(i3);
        num_pick_ip4_alert_dialoge.setValue(i4);
    }

    @OnClick({R.id.btn_confirm_alert_dialoge})
    public void clickSetupServerDialoge(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_alert_dialoge:
                dismiss();
                messageListener.listener(CommonDialogeType.SETUP_SERVER_DIALOG, CommonDialogeClick.CLICK_CONFIRM, setUpConfigs());
                break;
        }
    }

    private GlobalModel setUpConfigs() {
        GlobalModel globalModel=new GlobalModel();
        globalModel.setMyIP(IPHandler.createForPing(String.valueOf(num_pick_ip1_alert_dialoge.getValue()),
                String.valueOf(num_pick_ip2_alert_dialoge.getValue()),
                String.valueOf(num_pick_ip3_alert_dialoge.getValue()),
                String.valueOf(num_pick_ip4_alert_dialoge.getValue())));
        return globalModel;
    }


    private void setAlertSize(Activity a) {
        Objects.requireNonNull(getWindow()).setLayout(a.getWindowManager().getDefaultDisplay().getWidth() - Commons.MARGIN_ALERT_LEFT_RIGHT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
