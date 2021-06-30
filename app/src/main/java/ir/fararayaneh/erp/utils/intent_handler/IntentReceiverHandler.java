package ir.fararayaneh.erp.utils.intent_handler;

import android.os.Bundle;

import ir.fararayaneh.erp.commons.CommonIntents;
import ir.fararayaneh.erp.data.models.middle.IntentModel;


public class IntentReceiverHandler {

    public static IntentModel getIntentData(Bundle bundle) {
        return (IntentModel) bundle.getSerializable(CommonIntents.INTENT_BUNDLE_KEY);
    }
}
