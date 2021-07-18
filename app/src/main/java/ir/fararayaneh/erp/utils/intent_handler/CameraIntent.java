package ir.fararayaneh.erp.utils.intent_handler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;

public class CameraIntent {

    public static void openCamera(BaseActivity activity, Uri uri){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        try{
            activity.startActivityForResult(intent, CommonRequestCodes.CAMERA);
        }catch (Exception e) {
            if(activity.getView()!=null){
                activity.getView().showMessageSomeProblems(CommonsLogErrorNumber.error_135);
            }
        }


    }
}
