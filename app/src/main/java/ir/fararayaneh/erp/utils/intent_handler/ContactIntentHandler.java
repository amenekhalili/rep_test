package ir.fararayaneh.erp.utils.intent_handler;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import ir.fararayaneh.erp.commons.CommonRequestCodes;

public class ContactIntentHandler {

    public static void intent(Activity activity){

          Intent intent=new Intent(Intent.ACTION_PICK);
          intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
          intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
          activity.startActivityForResult(intent, CommonRequestCodes.CONTACT);
    }

}
