package ir.fararayaneh.erp.utils.intent_handler;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;

public class ContactIntentHandler {

    public static void intent(BaseActivity activity){
try{
          Intent intent=new Intent(Intent.ACTION_PICK);
          intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
          intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
          activity.startActivityForResult(intent, CommonRequestCodes.CONTACT);

    }catch (Exception e) {
    if(activity.getView()!=null){
        activity.getView().showMessageSomeProblems(CommonsLogErrorNumber.error_136);
    }
    }
    }

}
