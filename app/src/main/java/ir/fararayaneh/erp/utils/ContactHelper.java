package ir.fararayaneh.erp.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import ir.fararayaneh.erp.commons.Commons;

public class ContactHelper {

    public static String getContactPhoneNumber(Context context,Uri uri) {
        String number = Commons.NULL;
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            number = cursor.getString(numberIndex);
        }
        assert cursor != null;
        cursor.close();
        return number;
    }

    public static String getContactName(Context context, Uri uri) {
        String number = Commons.NULL;
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            number = cursor.getString(numberIndex);
        }
        assert cursor != null;
        cursor.close();
        return number;
    }
}
