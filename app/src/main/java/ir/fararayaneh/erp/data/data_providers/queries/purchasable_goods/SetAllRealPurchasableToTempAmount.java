package ir.fararayaneh.erp.data.data_providers.queries.purchasable_goods;

import com.annimon.stream.Stream;
import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class SetAllRealPurchasableToTempAmount extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    RealmResults<PurchasableGoodsTable> results = realm.where(PurchasableGoodsTable.class).findAll();
                    Stream.of(results).forEach(purchasableGoodsTable -> purchasableGoodsTable.setTempAmount2(purchasableGoodsTable.getAmount2()));
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
                    ThrowableLoggerHelper.logMyThrowable("SetAllRealPurchasableToTempAmount***data/"+error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }

}
