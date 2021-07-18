package ir.fararayaneh.erp.activities.attach_provider;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.SingleEmitter;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseView;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.utils.file_helper.CreateFolderFileHandler;
import ir.fararayaneh.erp.utils.file_helper.FileToUriHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;



public class AttachProviderView extends BaseView {

    @BindView(R.id.img_rot_left_crop_layout)
    AppCompatImageView img_rot_left_crop_layout;
    @BindView(R.id.img_rot_right_crop_layout)
    AppCompatImageView img_rot_right_crop_layout;
    @BindView(R.id.img_confirm_crop_layout)
    AppCompatImageView img_confirm_crop_layout;
    @BindView(R.id.crop_image_view_crop_layout)
    CropImageView crop_image_view_crop_layout;
    @BindView(R.id.conslay_crop_file_manager_act)
    ConstraintLayout conslay_crop_file_manager_act;
    @BindView(R.id.conslay_progress_attach_provider_act)
    ConstraintLayout conslay_progress_attach_provider_act;
    @BindView(R.id.conslay_description_layout)
    ConstraintLayout conslay_description_layout;
    @BindView(R.id.edt_tag_description_layout)
    TextInputEditText edt_tag_description_layout;
    @BindView(R.id.edt_description_description_layout)
    TextInputEditText edt_description_description_layout;
    @BindView(R.id.btn_confirm_description_layout)
    AppCompatButton btn_confirm_description_layout;
    private IAttachProviderListener iAttachProviderListener;
    private SingleEmitter<Uri> emitter;

    public void setIAttachProviderListener(IAttachProviderListener iAttachProviderListener) {
        this.iAttachProviderListener = iAttachProviderListener;
    }



    @OnClick({R.id.img_rot_left_crop_layout,R.id.img_rot_right_crop_layout,R.id.img_confirm_crop_layout ,R.id.btn_confirm_description_layout})
    public void clickAttachProviderAct(View view) {
        switch (view.getId()) {
            case R.id.img_rot_left_crop_layout:
                click_img_rot_left_crop_layout();
                break;
            case R.id.img_rot_right_crop_layout:
                click_img_rot_right_crop_layout();
                break;
            case R.id.img_confirm_crop_layout:
                click_img_confirm_crop_layout();
                break;
            case R.id.btn_confirm_description_layout:
                click_img_confirm_description();
                break;

        }
    }



    public AttachProviderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AttachProviderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected AttachProviderView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_attach_provider, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupToolbar() {

    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_progress_attach_provider_act.setVisibility(showHide?VISIBLE:GONE);
    }

    @Override
    protected void workforOnBackPressed() {

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

    /**
     * in this activity, if resultCode = Activity.RESULT_CANCELED, go back to parent activity immediately
     */
    @Override
    public void workForActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_CANCELED) {
            iAttachProviderListener.noResultListener();
        } else {
                switch (requestCode) {
                    case CommonRequestCodes.CAMERA:
                        workForShowCameraImage();
                        break;
                    case CommonRequestCodes.GALLERY:
                        workForMultiUriResult(data,CommonRequestCodes.GALLERY);
                        break;
                    case CommonRequestCodes.FILE:
                        workForMultiUriResult(data,CommonRequestCodes.FILE);
                        break;
                    case CommonRequestCodes.VOICE:
                        workForMultiUriResult(data,CommonRequestCodes.VOICE);
                        break;
                    case CommonRequestCodes.CONTACT:
                        workForMultiUriResult(data,CommonRequestCodes.CONTACT);
                        break;
                }
            }
        }

    @Override
    protected void clearAllViewClickListeners() {

    }


    //------------------------------------------------------------->>
    public void showHideCropImageView(SingleEmitter<Uri> emitter, Uri uri, boolean showHide) {
        this.emitter=emitter;
        showHideProgress(!showHide);
        if (showHide) {
            conslay_crop_file_manager_act.setVisibility(VISIBLE);
            crop_image_view_crop_layout.setImageUriAsync(uri);
        } else {
            crop_image_view_crop_layout.clearImage();
            conslay_crop_file_manager_act.setVisibility(GONE);
        }
    }

    public void showHideDescriptionLayput( boolean showHide) {
        conslay_description_layout.setVisibility(showHide ? VISIBLE : GONE);
    }


    /**
     * cropped image are written on original image
     */
    private void click_img_confirm_crop_layout() {
        if (iAttachProviderListener != null && crop_image_view_crop_layout.getCroppedImage() != null) {
            showHideProgress(true);
            cropImageCompleteListener = (view, result) -> {
                if(iAttachProviderListener != null){
                    if(result.isSuccessful() && iAttachProviderListener != null){
                        iAttachProviderListener.goToCompress(emitter,result.getUri());
                        showHideCropImageView(emitter, null, false);
                    } else {
                        showMessageSomeProblems(CommonsLogErrorNumber.error_8);
                        goToNoResult();
                        ThrowableLoggerHelper.logMyThrowable("AttachProviderView/onCropImageComplete error");
                    }
                }
            };

            crop_image_view_crop_layout.setOnCropImageCompleteListener(cropImageCompleteListener);
            //because we call createFile , no need to create folder
            crop_image_view_crop_layout.saveCroppedImageAsync(FileToUriHelper.getUri(CreateFolderFileHandler.createFile(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME,SharedPreferenceProvider.getCurrentAttachmentName(activity)),activity));
        } else {
            goToNoResult();
        }
    }


    private void click_img_confirm_description() {
        if(iAttachProviderListener!=null){
            iAttachProviderListener.confirmListener(Objects.requireNonNull(edt_tag_description_layout.getText()).toString(),Objects.requireNonNull(edt_description_description_layout.getText()).toString());
        }
    }

    private void goToNoResult() {
        if(iAttachProviderListener!=null){
            iAttachProviderListener.noResultListener();
        }
    }


    private void click_img_rot_right_crop_layout() {
        crop_image_view_crop_layout.rotateImage(90);
    }

    private void click_img_rot_left_crop_layout() {
        crop_image_view_crop_layout.rotateImage(-90);
    }

    private void workForShowCameraImage() {
        if(iAttachProviderListener!=null){
            iAttachProviderListener.cameraListener();}
    }


    /**
     * in record voice , data=null and voice only write on my file
     */
    private void workForMultiUriResult(Intent data, int witchRequestFile) {
        ArrayList<Uri> uriArrayList = new ArrayList<>();
        if(witchRequestFile == CommonRequestCodes.VOICE && iAttachProviderListener!=null){
            iAttachProviderListener.voiceListener();
        } else if (data != null) {
            if (data.getClipData() != null && iAttachProviderListener!=null) {
                if(data.getClipData().getItemCount()!=0){
                    for (int i = 0; i <data.getClipData().getItemCount() ; i++) {
                        uriArrayList.add(data.getClipData().getItemAt(i).getUri());
                    }
                    iAttachProviderListener.URIListener(uriArrayList,witchRequestFile);
                } else {
                    showMsg(getResources().getString(R.string.some_problem_error));
                    goToNoResult();
                }
            } else if (data.getData()!=null && iAttachProviderListener!=null){
                uriArrayList.add(data.getData());
                iAttachProviderListener.URIListener(uriArrayList,witchRequestFile);
            } else {
                showMsg(getResources().getString(R.string.some_problem_error));
                goToNoResult();
            }
        } else {
            showMessageSomeProblems(CommonsLogErrorNumber.error_9);
            goToNoResult();
        }
    }
}
