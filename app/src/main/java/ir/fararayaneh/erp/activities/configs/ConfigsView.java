package ir.fararayaneh.erp.activities.configs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.abdeveloper.library.MultiSelectModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmModel;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseViewWithRecycle;
import ir.fararayaneh.erp.IBase.common_base.IGoNextPage;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.custom_views.CustomToolTip;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomRecycleView;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.molty_select_dialoge.IMoltySelectDialogListener;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.molty_select_dialoge.MoltySelectDialogHelper;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.search_spinner_dialog.IsearchDialogeListener;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.search_spinner_dialog.SearchAbleSpinnerDialogHandler;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.tooltip.ToolTipHelper;

public class ConfigsView extends BaseViewWithRecycle {

    @BindView(R.id.global_toolbar)
    Toolbar global_toolbar;
    @BindView(R.id.img_global_toolbar)
    AppCompatImageView img_global_toolbar;
    @BindView(R.id.txt_global_toolbar)
    AppCompatTextView txt_global_toolbar;
    @BindView(R.id.custom_recycle_config_act)
    CustomRecycleView custom_recycle_config_act;
    @BindView(R.id.conslay_prog_configs_act)
    ConstraintLayout conslay_prog_configs_act;
    private IConfigsClickListener iConfigsClickListener;

    public void setiConfigsClickListener(IConfigsClickListener iConfigsClickListener) {
        this.iConfigsClickListener = iConfigsClickListener;
    }

    public ConfigsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ConfigsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected ConfigsView(@NonNull BaseActivity activity) {
        super(activity);
    }


    @Override
    protected void setupRecyclers() {
        custom_recycle_config_act.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        custom_recycle_config_act.setHasFixedSize(true);
    }

    @SafeVarargs
    @Override
    public final void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>... recycleAdaptor) {
        custom_recycle_config_act.setAdapter(recycleAdaptor[0]);
    }

    @Override
    public void loadAtEndList(IGoNextPage IGoNextPage) {
        custom_recycle_config_act.setListenEndScroll(() -> {
            //no need to done any work, data were loaded  before here in first time
        });
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_configs, this, true);
        ButterKnife.bind(this);
        setupToolbar();
    }
    @Override
    protected void setupToolbar() {
        activity.setSupportActionBar(global_toolbar);
        setupTextToolbar(getResources().getString(R.string.setting));
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void setupTextToolbar(String txt) {
        txt_global_toolbar.setText(txt);
    }



    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_configs_act.setVisibility(showHide?VISIBLE:GONE);
    }

    @Override
    protected void workforOnBackPressed() {
        finish();
    }

    @Override
    protected void workforOnCreateOptionsMenu(Menu menu) {
    }

    @Override
    protected boolean workforOnOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(iConfigsClickListener!=null){
                    iConfigsClickListener.clickBackToolbar();
                }
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void workForPermissionResults(int requestCode, @NonNull int[] grantResults) {

    }

    @Override
    public void workForActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clearAllViewClickListeners() {

    }

    //----------------------------------------------------------------------------------------
    @SuppressLint("ResourceType")
    public void showSaveIPAlert(IIOSDialogeListener iiosDialogeListener) {

        ToastMessage.showIosDialogTypeConfirmCancle(activity,
                getResources().getString(R.string.caution),
                getResources().getString(R.string.clear_data_caution),
                getResources().getString(R.string.ok),
                getResources().getString(R.string.cancel),
                true,iiosDialogeListener);


    }

}
