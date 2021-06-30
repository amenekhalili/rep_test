package ir.fararayaneh.erp.utils.database_handler;

import android.util.Log;

import io.realm.Realm;

public class RealmCloseHelper {
    public static void closeRealm(Realm realm) {
        if(!realm.isClosed()){
            Log.i("arash", "closeRealm: "+realm.toString());
            realm.close();
        }
    }
}
