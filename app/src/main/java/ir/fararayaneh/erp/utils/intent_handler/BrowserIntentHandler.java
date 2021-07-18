package ir.fararayaneh.erp.utils.intent_handler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;

public class BrowserIntentHandler {

    public static void handler(BaseActivity activity, String url) {
        try{
            activity.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
    }catch (Exception e) {
            if(activity.getView()!=null){
                activity.getView().showMessageSomeProblems(CommonsLogErrorNumber.error_139);
            }
    }
    }
}
