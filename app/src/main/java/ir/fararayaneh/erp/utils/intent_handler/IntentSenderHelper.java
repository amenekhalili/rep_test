package ir.fararayaneh.erp.utils.intent_handler;

import android.os.Bundle;

import ir.fararayaneh.erp.commons.CommonIntents;
import ir.fararayaneh.erp.data.models.middle.IntentModel;


public class IntentSenderHelper {

    public static Bundle setIntentData(IntentModel intentModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonIntents.INTENT_BUNDLE_KEY, intentModel);
        return bundle;
    }
}
