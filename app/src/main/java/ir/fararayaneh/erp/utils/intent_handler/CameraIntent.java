package ir.fararayaneh.erp.utils.intent_handler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

import ir.fararayaneh.erp.commons.CommonRequestCodes;

public class CameraIntent {

    public static void openCamera(Activity activity, Uri uri){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, CommonRequestCodes.CAMERA);
    }
}
