package ir.fararayaneh.erp.utils.intent_handler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class CallIntentHandler {
    public static void handler(Context context, String phoneNumber) {
        try{
        context.startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse(phoneNumber)));
    }catch (Exception e) {
        Log.i("arash", "CallIntentHandler: "+e.getMessage());
        //todo : return error to activity
    }
    }
}
