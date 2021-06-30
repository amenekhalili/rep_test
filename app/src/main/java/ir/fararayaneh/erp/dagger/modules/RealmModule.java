package ir.fararayaneh.erp.dagger.modules;



import android.util.Log;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * because realm management singleton situations ,
 * this module do not should be singleton
 */
@Module
public class RealmModule {

    @Provides
    public Realm creatRealm() {
        Log.i("arash", "creatRealm: getttt realm");
        return Realm.getDefaultInstance();
    }
}
