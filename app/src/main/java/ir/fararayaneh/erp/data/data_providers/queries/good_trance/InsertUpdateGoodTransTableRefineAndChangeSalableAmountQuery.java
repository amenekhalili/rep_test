package ir.fararayaneh.erp.data.data_providers.queries.good_trance;

import android.util.Log;

import com.annimon.stream.Stream;

import java.util.ArrayList;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.middle.GoodTransDetailsModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.GoodTranceTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.SalableGoodsTable;
import ir.fararayaneh.erp.utils.CalculationHelper;
import ir.fararayaneh.erp.utils.data_handler.GoodTransDetailJsonHelper;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;


/**
 * call when user click on delete global btn
 */
public class InsertUpdateGoodTransTableRefineAndChangeSalableAmountQuery extends BaseQueries {
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        GoodTranceTable goodTranceTable = (GoodTranceTable) iModels;

        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {

                    //add deleted amount to temp amount
                    ArrayList<GoodTransDetailsModel> goodTransDetailsModelArrayList = GoodTransDetailJsonHelper.getGoodTransDetailArray(goodTranceTable.getGoodTransDetail());
                    for (int i = 0; i < goodTransDetailsModelArrayList.size(); i++) {
                        RealmResults<SalableGoodsTable> results =
                                realm.where(SalableGoodsTable.class)
                                        .equalTo(CommonColumnName.ID, goodTransDetailsModelArrayList.get(i).getIdRecallDetail())
                                        .findAll();
                        if (results.size() != 0) {
                            results.get(0).setTempAvailableAmount2(
                                    CalculationHelper.plusAnyAndRoundNonMoneyValue(results.get(0)
                                                    .getTempAvailableAmount2(),
                                            goodTransDetailsModelArrayList.get(i).getAmount2(),
                                            App.getAppContext())
                            );
                        }

                    }


                    //همه فیلد های تمپ داخل فیلد های اصلی ریخته شود
                    RealmResults<SalableGoodsTable> results = realm.where(SalableGoodsTable.class).findAll();
                    Stream.of(results).forEach(salableGoodsTable -> salableGoodsTable.setAvailableAmount2(salableGoodsTable.getTempAvailableAmount2()));

                    realm.insertOrUpdate(goodTranceTable);
                    Log.i("arash", "add data to goodTrans table: "+realm.where(GoodTranceTable.class).findAll().size());

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
                    ThrowableLoggerHelper.logMyThrowable("InsertUpdateGoodTransTableRefineAndChangeSalableAmountQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}