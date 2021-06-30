package ir.fararayaneh.erp.activities.splash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseView;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.utils.AnimationHelper;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;

public class SplashView extends BaseView {

    private ISplshListener iSplshListener;

    public void setiSplshListener(ISplshListener iSplshListener) {
        this.iSplshListener = iSplshListener;
    }

    @BindView(R.id.conslay_progress_splash_act)
    ConstraintLayout conslay_progress_splash_act;
    @BindView(R.id.fara_logo_splash)
    AppCompatImageView fara_logo_splash;


    public SplashView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SplashView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected SplashView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_splash, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupToolbar() {

    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_progress_splash_act.setVisibility(showHide?VISIBLE:GONE);
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
        return false;
    }

    @Override
    protected void workForPermissionResults(int requestCode, @NonNull int[] grantResults) {

    }

    @Override
    public void workForActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== Activity.RESULT_CANCELED){
            showMessageNothingsSelected();
        } else {
            switch (requestCode){
                case CommonRequestCodes.Do_SYNC:
                    workForAfterSync();
                    break;
            }
        }

    }



    @Override
    protected void clearAllViewClickListeners() {

    }

    //--------------------------------------------------------------------------->>
    @SuppressLint("ResourceType")
    public void showDisconnectionLayout(IIOSDialogeListener iiosDialogeListener) {

        ToastMessage.showIosDialogTypeConfirmCancle(activity,
                getResources().getString(R.string.connection_problem),
                getResources().getString(R.string.check_connection),
                getResources().getString(R.string.retry),
                getResources().getString(R.string.support_call),
                false,
                iiosDialogeListener
                );
    }

    @SuppressLint("ResourceType")
    public void showUpdateLayout(IIOSDialogeListener iiosDialogeListener) {
        ToastMessage.showIosDialogType2(activity,getResources().getString(R.string.new_version),getResources().getString(R.string.have_new_version),getResources().getString(R.string.download),true,iiosDialogeListener);
    }

    private void workForAfterSync() {
        if(iSplshListener!=null){
            iSplshListener.afterSyncProcess();
        }
    }

    public void animateCompanyIcon(){
        fara_logo_splash.setVisibility(VISIBLE);
        AnimationHelper.anim_type_1(fara_logo_splash);

    }
}
