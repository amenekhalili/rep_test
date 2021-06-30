package ir.fararayaneh.erp.utils.intent_handler;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * use in socket notifications
 */
// TODO: 1/21/2019

public class PendingIntentHelper {

    public static PendingIntent makePendingIntent(Context context,Class destination) {
        return PendingIntent.getActivity(context, 0, new Intent(context, destination), PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
