package ir.fararayaneh.erp.activities.sync_act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseView;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.utils.AnimationHelper;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;

public class SyncView extends BaseView {


    private ISyncActivity iSyncActivity;

    public void setiSyncActivity(ISyncActivity iSyncActivity) {
        this.iSyncActivity = iSyncActivity;
    }


    @BindView(R.id.imageView_sync_act)
    AppCompatImageView imageView_sync_act;

    public SyncView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SyncView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected SyncView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_sync, this, true);
        ButterKnife.bind(this);


    }

    @Override
    protected void setupToolbar() {

    }

    @Override
    public void showHideProgress(boolean showHide) {

    }

    @Override
    protected void workforOnBackPressed() {
        //no back
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
        if (resultCode == Activity.RESULT_CANCELED) {
            showMessageNothingsSelected();
            workForResultCancelFromAllActivity();
        } else {
            switch (requestCode) {
                case CommonRequestCodes.GET_DATA_INSERT_IN_TABLE:
                    workForResultOkUpdateActivity();
                    break;
            }
        }
    }

    @Override
    protected void clearAllViewClickListeners() {

    }


    //---------------------------------
    void setAnimation(){
        AnimationHelper.anim_type_2(imageView_sync_act);
    }

    private void workForResultOkUpdateActivity() {
        if(iSyncActivity!=null){
            iSyncActivity.resultOkFromUpdateActivity();
        }
    }

    private void workForResultCancelFromAllActivity() {
        if(iSyncActivity!=null){
            iSyncActivity.cancelResultFromOtherActivity();
        }
    }

    public void showConfirmMessage(IIOSDialogeListener iiosDialogeListener) {

        ToastMessage.showIosDialogTypeConfirmCancle(activity,
                getResources().getString(R.string.dear_user),
                getResources().getString(R.string.sync_state_message),
                getResources().getString(R.string.ok),
                getResources().getString(R.string.cancel),
                false,
                iiosDialogeListener
                );


    }
}
