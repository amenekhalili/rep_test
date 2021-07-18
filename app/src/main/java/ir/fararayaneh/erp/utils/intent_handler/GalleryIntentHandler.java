package ir.fararayaneh.erp.utils.intent_handler;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;

import ir.fararayaneh.erp.commons.CommonRequestCodes;

public class GalleryIntentHandler {

    public static void intent(Activity activity){
        try{
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        activity.startActivityForResult(galleryIntent, CommonRequestCodes.GALLERY);
    }catch (Exception e) {
        Log.i("arash", "GalleryIntentHandler: "+e.getMessage());
        //todo : return error to activity
    }
    }
}
