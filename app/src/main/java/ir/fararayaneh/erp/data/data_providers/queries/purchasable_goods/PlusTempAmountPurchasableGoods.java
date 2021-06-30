package ir.fararayaneh.erp.data.data_providers.queries.purchasable_goods;

import java.util.ArrayList;
import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;
import ir.fararayaneh.erp.utils.CalculationHelper;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * در صورت نیاز به کاهش مقادیر، مقدار منفی به اینجا ارسال گردد
 */
public class PlusTempAmountPurchasableGoods extends BaseQueries {


    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        GlobalModel globalModel = (GlobalModel) iModels;
        ArrayList<String> idArrayList = globalModel.getStringArrayList();
        ArrayList<String> tempArrayList = globalModel.getStringArrayList2();

        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    for (int i = 0; i < idArrayList.size(); i++) {
                        RealmResults<PurchasableGoodsTable> results =
                                realm.where(PurchasableGoodsTable.class)
                                        .equalTo(CommonColumnName.ID, idArrayList.get(i))
                                        .findAll();
                        if (results.size() != 0) {
                            results.get(0).setTempAmount2(
                                    CalculationHelper
                                            .plusAnyAndRoundNonMoneyValue(
                                                    results.get(0)
                                                    .getTempAmount2(),
                                                    tempArrayList.get(i),
                                                    App.getAppContext())
                            );
                        }

                    }
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
                    ThrowableLoggerHelper.logMyThrowable("PlusTempAmountPurchasableGoods***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }

}
