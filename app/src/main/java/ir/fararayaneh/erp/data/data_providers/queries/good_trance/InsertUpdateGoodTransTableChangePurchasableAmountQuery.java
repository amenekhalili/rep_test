package ir.fararayaneh.erp.data.data_providers.queries.good_trance;

import android.util.Log;

import com.annimon.stream.Stream;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.GoodTranceTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class InsertUpdateGoodTransTableChangePurchasableAmountQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        GoodTranceTable goodTranceTable = (GoodTranceTable) iModels;

        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {

                    //همه فیلد های تمپ داخل فیلد های اصلی ریخته شود
                    RealmResults<PurchasableGoodsTable> results = realm.where(PurchasableGoodsTable.class).findAll();
                    Stream.of(results).forEach(purchasableGoodsTable -> purchasableGoodsTable.setAmount2(purchasableGoodsTable.getTempAmount2()));

                    realm.insertOrUpdate(goodTranceTable);
                    Log.i("arash", "add data to goodTrans table: " + realm.where(GoodTranceTable.class).findAll().size());
                }
                ,
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(App.getNullRXModel());
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("InsertUpdateGoodTransTableChangePurchasableAmountQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
