package ir.fararayaneh.erp.utils.intent_handler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class BrowserIntentHandler {

    public static void handler(Context context, String url) {
        try{
        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
    }catch (Exception e) {
        Log.i("arash", "BrowserIntentHandler: "+e.getMessage());
        //todo : return error to activity
    }
    }
}
