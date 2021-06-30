package ir.fararayaneh.erp.utils.intent_handler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class CallIntentHandler {
    public static void handler(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse(phoneNumber)));
    }
}
