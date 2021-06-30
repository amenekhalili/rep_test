package ir.fararayaneh.erp.data.data_providers.queries.cartable_queris;

import android.util.Log;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CartableTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;

//todo eslahe hame queri haye class service va hazfe anha be samte queri haye asli

public class InsertUpdateCartableTableServiceQuery extends BaseQueries {


    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        CartableTable cartableTable = (CartableTable) iModels;

        return Single.create(emitter -> {
            realm.beginTransaction();
            realm.insertOrUpdate(cartableTable);
            realm.commitTransaction();
            //hata agar dar cartable report bashim ta user roye satr click nakarde ast, badge kam nemishavad
            SharedPreferenceProvider.setBadgeNumber(App.getAppContext(), 0, 1);

            Log.i("arash", "InsertUpdateCartableTableServiceQuery: " + realm.where(CartableTable.class).findAll().size());
            if (!emitter.isDisposed()) {
                emitter.onSuccess(App.getNullRXModel());
            }
            RealmCloseHelper.closeRealm(realm);
        });
    }
}