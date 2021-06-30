package ir.fararayaneh.erp.activities.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.widget.AppCompatButton;


import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseView;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonRequestCodes;

import ir.fararayaneh.erp.utils.EditTextCheckHelper;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;


public class LoginView extends BaseView {

    @BindView(R.id.conslay_prog_login_act)
    ConstraintLayout conslay_prog_login_act;
    @BindView(R.id.edt_username_login_act)
    TextInputEditText edt_username_login_act;
    @BindView(R.id.edt_password_login_act)
    TextInputEditText edt_password_login_act;
    @BindView(R.id.edt_organization_login_act)
    TextInputEditText edt_organization_login_act;
    @BindView(R.id.btn_login_login_act)
    AppCompatButton btn_login_login_act;
    @BindView(R.id.img_setting_login_act)
    AppCompatImageView img_setting_login_act;
    @BindView(R.id.txt_version_login)
    AppCompatTextView txt_version_login;



    private IClickLoginListener iClickLoginListener;
    private ISaveUserDetails iSaveUserDetails;

    @OnClick({R.id.btn_login_login_act,R.id.img_setting_login_act})
    public void clickLoginView(View view) {
        switch (view.getId()){
            case R.id.btn_login_login_act:
                clickLoginBTN();
                break;
            case R.id.img_setting_login_act:
                clickSettingBTN();
                break;
        }

    }




    public void showConfigIMG(boolean show) {
        img_setting_login_act.setVisibility(show?VISIBLE:GONE);
    }


    public void setiClickLoginListener(IClickLoginListener iClickLoginListener) {
        this.iClickLoginListener = iClickLoginListener;
    }

    public void setiSaveUserDetails(ISaveUserDetails iSaveUserDetails) {
        this.iSaveUserDetails = iSaveUserDetails;
    }

    public LoginView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public LoginView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected LoginView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_login, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupToolbar() {

    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_login_act.setVisibility(showHide?VISIBLE:GONE);
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

        if (resultCode == Activity.RESULT_CANCELED) {
            showMessageNothingsSelected();
        } else {
            switch (requestCode) {
                case CommonRequestCodes.Do_SYNC:
                    workForSaveUserDetails();
                    break;
                case CommonRequestCodes.Do_SYNC_EXPIRED_FINANCIAL_DATE:
                    workForExpireFinancialDate();
                    break;
            }
        }

    }



    @Override
    protected void clearAllViewClickListeners() {

    }

    //--------------------------------------------------------------------------------->>
    public void setUserNameOrganization(String userName, String organization) {
        edt_username_login_act.setText(userName);
        edt_organization_login_act.setText(organization);
    }

    private void clickSettingBTN() {

        if(iClickLoginListener!=null){
            iClickLoginListener.SettingClick();
        }
    }

    private void clickLoginBTN() {
        if(EditTextCheckHelper.checkWithRaiseError(edt_username_login_act,activity) &&
                EditTextCheckHelper.checkWithRaiseError(edt_password_login_act,activity) &&
                EditTextCheckHelper.checkWithRaiseError(edt_organization_login_act,activity) &&
                iClickLoginListener!=null){
            showHideProgress(true);
            iClickLoginListener.loginClick(
                    Objects.requireNonNull(edt_username_login_act.getText()).toString().trim(),
                    Objects.requireNonNull(edt_password_login_act.getText()).toString().trim(),
                    Objects.requireNonNull(edt_organization_login_act.getText()).toString().trim()
            );
        }
    }

    /**
     * here we sure that all tables saved in proper manner
     */
    private void workForSaveUserDetails() {
        if(iSaveUserDetails!=null){
            iSaveUserDetails.saveUserDetails();
        }
    }

    private void workForExpireFinancialDate() {
        if(iSaveUserDetails!=null){
            iSaveUserDetails.workForExpireFinancialDate();
        }
    }

    public void showInvalidFinancialLayout(IIOSDialogeListener iiosDialogeListener) {
        ToastMessage.showIosDialogType2(activity,getResources().getString(R.string.dear_user),getResources().getString(R.string.expire_financial_msg),getResources().getString(R.string.ok),true,iiosDialogeListener);
    }


    public void setVersionName(String versionName){
        txt_version_login.setText(versionName);
    }

}
