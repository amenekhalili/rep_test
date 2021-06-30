package ir.fararayaneh.erp.IBase.common_base;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImageView;

import javax.inject.Inject;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonB5HCTypeName;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsAttachmentType;
import ir.fararayaneh.erp.utils.SoftKeyHelper;
import ir.fararayaneh.erp.utils.show_information_message.StatusBarInformationKt;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;


public abstract class BaseView extends FrameLayout {

    @Inject
    public Picasso picasso;

    protected BaseActivity activity;

    public BaseActivity getActivity() {
        return activity;
    }

    protected IShowMessageDialogueListener iShowMessageDialogueListener;

    protected ISendAttachmentToPersenter iSendAttachmentToPersenter;

    protected ISendBarCodeToPersenter iSendBarCodeToPersenter;

    public void setiSendAttachmentToPersenter(ISendAttachmentToPersenter iSendAttachmentToPersenter) {
        this.iSendAttachmentToPersenter = iSendAttachmentToPersenter;
    }

    public void setiSendBarCodeToPersenter(ISendBarCodeToPersenter iSendBarCodeToPersenter) {
        this.iSendBarCodeToPersenter = iSendBarCodeToPersenter;
    }



    protected MenuSheetView.OnMenuItemClickListener menuAttachmentItemClickListener;
    protected MenuSheetView menuSheetView;
    protected CropImageView.OnCropImageCompleteListener cropImageCompleteListener;
    private IBottomSheetClickListener iBottomSheetClickListener;

    public void setiBottomSheetClickListener(IBottomSheetClickListener iBottomSheetClickListener) {
        this.iBottomSheetClickListener = iBottomSheetClickListener;
    }

    public void setiShowMessageDialogueListener(IShowMessageDialogueListener iShowMessageDialogueListener) {
        this.iShowMessageDialogueListener = iShowMessageDialogueListener;
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected BaseView(@NonNull BaseActivity activity) {
        super(activity);
        this.activity = activity;
        //methods that should be performed !
        inflateButterKnife();
        App.getAppcomponent3().injectBaseView(this);
        SoftKeyHelper.hideSoftKeyboard(activity);
    }

    protected abstract void inflateButterKnife();

    protected abstract void setupToolbar();

    public abstract void showHideProgress(boolean showHide);

    public void showMsg(final String msg) {
        if (iShowMessageDialogueListener != null) {
            iShowMessageDialogueListener.sendShowMsgToPresenter(msg);
        }
    }

    public void showDialogue(int dialogueType, int commonComboClickType, String someValue) {
        if (iShowMessageDialogueListener != null) {
            iShowMessageDialogueListener.sendShowDialogToPresenter(dialogueType, commonComboClickType, someValue);
        }
    }

    protected abstract void workforOnBackPressed();

    protected abstract void workforOnCreateOptionsMenu(Menu menu);

    protected abstract boolean workforOnOptionsItemSelected(MenuItem item);

    protected abstract void workForPermissionResults(int requestCode, @NonNull int[] grantResults);

    public abstract void workForActivityResult(int requestCode, int resultCode, Intent data);

    public void finish() {
        activity.finish();
    }

    public void reCreate() {
        activity.recreate();
    }


//----------------------------------------------------------------------------------------------->>

    public void showMessageSomeProblems(String errorNumber) {
        showMsg(errorNumber+ Commons.SPACE +getResources().getString(R.string.some_problem_error));
    }

    public void  showMessagePleaseWaite(){
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.wait));
    }

    public void showMessageNodeServerDisconnecting(Context context) {
        //this message should be a Toast message
        ToastMessage.show(context,getResources().getString(R.string.node_server_is_disconnected));

    }

    public void showMessageUnSyncData(Context context) {
        ToastMessage.showIosDialog(context,getResources().getString(R.string.dear_user),getResources().getString(R.string.sync_data),true);
    }

    public void showMessageUnSyncUploadData(Context context) {
        ToastMessage.showIosDialog(context,getResources().getString(R.string.dear_user),getResources().getString(R.string.sync_upload_data),true);
    }

    public void showMessageNoPing() {
        //showMsg(getResources().getString(R.string.connection_problem));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.connection_problem));

    }

    public void showMessageNoDetailInGoodTrance() {
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.no_detail_in_good_trance));
    }



    public void showMessageWrongPass() {
        //showMsg(getResources().getString(R.string.wrong_pass));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.wrong_pass));
    }

    public void showMessageWrongInput() {
        //showMsg(getResources().getString(R.string.wrong_Input));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.wrong_Input));
    }

    public void showMessageNoPermission() {
        //showMsg(getResources().getString(R.string.not_have_access));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.not_have_access));
    }

    public void showMessageNoEditPermission() {
        //showMsg(getResources().getString(R.string.not_have_access));
        ToastMessage.showOrangeSnack(getActivity(),getResources().getString(R.string.not_have_access_edit));
    }

    public void showMessageFillProperValue() {
        //showMsg(getResources().getString(R.string.not_have_access));
        ToastMessage.showOrangeSnack(getActivity(),getResources().getString(R.string.fill_proper_value));
    }

    public void showMessageZeroValue() {
        //showMsg(getResources().getString(R.string.not_have_access));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.have_zero_values_error));
    }

    public void showMessageNoPermissionGranted() {
        //showMsg(getResources().getString(R.string.not_have_access));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.not_application_have_access));
    }

    public void showMessageDateAfterNowError() {
        //showMsg(getResources().getString(R.string.date_after_now_error));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.date_after_now_error));

    }

    public void showMessageDateOutFinancialDate() {
        //showMsg(getResources().getString(R.string.date_out_financial_date));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.date_out_financial_date));

    }

    public void showMessageStartDateAfterEndDate() {
        //showMsg(getResources().getString(R.string.start_date_after_end_date_error));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.start_date_after_end_date_error));
    }

    public void showMessageEndDateBeforeStartDate() {
        //showMsg(getResources().getString(R.string.end_date_before_start_date_error));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.end_date_before_start_date_error));

    }

    public void showMessageDateBeforeNowError() {
        //showMsg(getResources().getString(R.string.date_before_now_error));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.date_before_now_error));
    }

    public void showMessageGoodTrancIsChanged() {
        ToastMessage.show(getActivity(),getResources().getString(R.string.good_trance_changed));
    }

    public void showMessageNotProperSheetPlaneTimeError() {
        //showMsg(getResources().getString(R.string.proper_sheet_plane_duration_error));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.proper_sheet_plane_duration_error));
    }

    public void showMessageSetTwoTimeError() {
        //showMsg(getResources().getString(R.string.proper_sheet_plane_duration_error));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.set_two_time));
    }

    public void showMockAppMessageError() {
        //showMsg(getResources().getString(R.string.mock_app_error));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.mock_app_error));
    }

    public void showForbiddenAppMessageError() {
        //showMsg(getResources().getString(R.string.forbidden_app_error));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.forbidden_app_error));
    }
    public void showMessageLocationNotValid() {
        //showMsg(getResources().getString(R.string.forbidden_app_error));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.not_location_valid));
    }

    public void showMessageSaveDataSuccess() {
        //showMsg(getResources().getString(R.string.save_data_success));
        ToastMessage.showGreenSnack(getActivity(),getResources().getString(R.string.save_data_success));
    }

    public void showMessageNoMoreGoods() {
        //showMsg(getResources().getString(R.string.save_data_success));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.no_more_goods));
    }

    public void shoMessageSuccessBackUp() {
        ToastMessage.showGreenSnack(getActivity(),getResources().getString(R.string.backUp_data_success));
    }

    public void showMessageNothingsSelected() {
        showMsg(getResources().getString(R.string.nothing_selected));
    }

    public void showMessageNoAttachmentSelected() {
        showMsg(getResources().getString(R.string.no_attach_selected));
    }


    public void showMessageMainValueNotSelected() {
        //showMsg(getResources().getString(R.string.please_set_proper_main_value));
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.please_set_proper_main_value));
    }


    public void showMessageSubValueNotSelected() {
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.please_set_proper_sub_value));

    }
    public void showMessageCurrencyUnitPrice() {
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.please_set_proper_currncy_unit_price_value));
    }
    public void showMessageNegativeTotalUnitPrice() {
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.total_value_should_not_be_negative));
    }

    public void showMessageDiscountPercantage() {
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.please_set_proper_discount_percentage_value));
    }
    public void showMessageTaxPercantage() {
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.please_set_proper_tax_percentage_value));
    }

    public void showMessageOverFlowAmount() {
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.over_flow_amount));
    }

    public void showMessageCurrencyDisCount2() {
        ToastMessage.showRedSnack(getActivity(),getResources().getString(R.string.please_set_proper_currency_discount_2_value));
    }


    private void showMessageNoBarCodeWereSelected() {
        showMsg(getResources().getString(R.string.no_barcode_selected));
    }

    protected void showMessageSyncWereDoneSuccessfully() {
       // showMsg(getResources().getString(R.string.sync_data_were_done));
        ToastMessage.showGreenSnack(getActivity(),getResources().getString(R.string.sync_data_were_done));
    }

    public void showMessagePleaseSetServerSetting() {
        showMsg(getResources().getString(R.string.pleaseSetupServerConfig));
    }

    public void showMessageDownloading() {
        showMsg(getResources().getString(R.string.downloading));
    }

    public void showStatusBarMessage(){
        activity.runOnUiThread(() -> StatusBarInformationKt.showStatusBarNotif(activity,getResources().getString(R.string.offline_mode)));
    }

    public void hideStatusBarMessage(){
        activity.runOnUiThread(() -> StatusBarInformationKt.hideStatusBar(activity));
    }

    /**
     * for clear all listener that were created in view
     * this method call from onDestroy, not in onStop
     * because this clicks define in on create and not in on start
     */
    protected void clearAllClicksListener() {
        cropImageCompleteListener = null;
        menuAttachmentItemClickListener = null;
        menuSheetView = null;
        clearAllViewClickListeners();
    }

    protected abstract void clearAllViewClickListeners();

    /**
     * use for com.flipboard.bottomsheet.BottomSheetLayout
     * caution -->> root layout should be BottomSheetLayout
     */
    protected void showAttachmentBottomSheet(BottomSheetLayout bottomSheetLayout, boolean camera, boolean voice, boolean file, boolean gallery, boolean location, boolean contact
                                             //String b5IdRefGUID >>> deprecated
                                              ) {
        menuAttachmentItemClickListener = item -> {
            bottomSheetLayout.dismissSheet();
            clickAttachmentBottomSheet(item, camera, voice, file, gallery, location, contact
                   /// , b5IdRefGUID >>> deprecated
            );
            return true;
        };
        menuSheetView = new MenuSheetView(activity, MenuSheetView.MenuType.GRID, "", menuAttachmentItemClickListener);
        menuSheetView.inflateMenu(R.menu.attachment_menu);
        bottomSheetLayout.showWithSheetView(menuSheetView);
    }

    protected void clickAttachmentBottomSheet(MenuItem item, boolean camera, boolean voice, boolean file, boolean gallery, boolean location, boolean contact
            ///, String b5IdRefGUID >>> ///
    ) {
            //todo if we need to camera for logo or certificate or ....?
        switch (item.getItemId()) {
            case R.id.menu_attach_camera:
                workForCameraBottomSheetClick(camera
                        ///, b5IdRefGUID >>> deprecated
                        , CommonB5HCTypeName.IMAGE);
                break;
            case R.id.menu_attach_voice:
                workForVoiceBottomSheetClick(voice
                        ////, b5IdRefGUID >>> deprecated
                        , CommonB5HCTypeName.FILE);
                break;
            case R.id.menu_attach_file:
                workForFileBottomSheetClick(file
                        ///, b5IdRefGUID >>> deprecated
                        , CommonB5HCTypeName.FILE);
                break;
            case R.id.menu_attach_gallery:
                workForGalleryBottomSheetClick(gallery
                        ///, b5IdRefGUID >>> deprecated
                         ,CommonB5HCTypeName.IMAGE);
                break;
            case R.id.menu_attach_location:
                workForLocationBottomSheetClick(location
                        ///, b5IdRefGUID >>> deprecated
                        , CommonB5HCTypeName.FILE);
                break;
            case R.id.menu_attach_contact:
                workForContactBottomSheetClick(contact
                        ///, b5IdRefGUID >>> deprecated
                        , CommonB5HCTypeName.FILE);
                break;
        }
    }

    private void workForContactBottomSheetClick(boolean contact
            ///, String b5IdRefGUID >>> deprecated
            , String b5HcName) {
        if (contact && iBottomSheetClickListener != null) {
            iBottomSheetClickListener.witchClick(CommonsAttachmentType.CONTACT
                    ///, b5IdRefGUID >>> deprecated
                    , b5HcName);
        } else {
            showMessageNoPermission();
        }
    }

    private void workForLocationBottomSheetClick(boolean location
            ///, String b5IdRefGUID >>> deprecated
            , String b5HcName) {
        if (location && iBottomSheetClickListener != null) {
            iBottomSheetClickListener.witchClick(CommonsAttachmentType.LOCATION
                    ///, b5IdRefGUID >>> deprecated
                    , b5HcName);
        } else {
            showMessageNoPermission();
        }
    }

    private void workForGalleryBottomSheetClick(boolean gallery
            ///, String b5IdRefGUID >>> deprecated
            , String b5HcName) {
        if (gallery && iBottomSheetClickListener != null) {
            iBottomSheetClickListener.witchClick(CommonsAttachmentType.GALLERY
                    ///, b5IdRefGUID >>> deprecated
                    , b5HcName);
        } else {
            showMessageNoPermission();
        }
    }

    private void workForFileBottomSheetClick(boolean file
            ///, String b5IdRefGUID >>> deprecated
            , String b5HcName) {
        if (file && iBottomSheetClickListener != null) {
            iBottomSheetClickListener.witchClick(CommonsAttachmentType.FILE
                    ///, b5IdRefGUID >>> deprecated
                    , b5HcName);
        } else {
            showMessageNoPermission();
        }
    }

    private void workForVoiceBottomSheetClick(boolean voice
            ///, String b5IdRefGUID >>> deprecated
            , String b5HcName) {
        if (voice && iBottomSheetClickListener != null) {
            iBottomSheetClickListener.witchClick(CommonsAttachmentType.VOICE
                    ///, b5IdRefGUID >>> deprecated
                    , b5HcName);
        } else {
            showMessageNoPermission();
        }
    }

    private void workForCameraBottomSheetClick(boolean camera
            ///, String b5IdRefGUID >>> deprecated
            , String b5HcName) {
        if (camera && iBottomSheetClickListener != null) {
            iBottomSheetClickListener.witchClick(CommonsAttachmentType.CAMERA
                    ///, b5IdRefGUID >>> deprecated
                    , b5HcName);
        } else {
            showMessageNoPermission();
        }
    }

    /**
     * get attachment name list and ...
     * from attachProvider or attachAlbum
     */
    protected void sendAttachmentToPresenter(Intent data,boolean fromAttachProvider) {
        if(data!=null){
            if(data.getExtras()!=null && iSendAttachmentToPersenter!=null){
                if(fromAttachProvider){
                    iSendAttachmentToPersenter.getAttachmentFromViewAttachProvider(data);
                } else {
                    iSendAttachmentToPersenter.getAttachmentFromViewAttachAlbum(data);
                }
            } else {
                showMessageNoAttachmentSelected();
            }
        } else {
            showMessageNoAttachmentSelected();
        }
    }

    protected void sendBarcodeToPresenter(Intent data) {
        if(data!=null){
            if(data.getExtras()!=null && iSendBarCodeToPersenter!=null){
                iSendBarCodeToPersenter.getBarCodeFromView(data);
            } else {
                showMessageNoBarCodeWereSelected();
            }
        } else {
            showMessageNoBarCodeWereSelected();
        }
    }



}
