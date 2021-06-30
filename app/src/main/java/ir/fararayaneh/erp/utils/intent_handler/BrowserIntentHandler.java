package ir.fararayaneh.erp.utils.intent_handler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class BrowserIntentHandler {

    public static void handler(Context context, String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
    }
}
