package ir.fararayaneh.erp.data.data_providers.queries.salable_goods;

import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.SalableGoodsTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;


public class GetSalableByRecallId extends BaseQueries {

    private SalableGoodsTable salableGoodsTable;
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        GlobalModel globalModel =  (GlobalModel)iModels;
        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    RealmResults<SalableGoodsTable> results = realm.where(SalableGoodsTable.class).
                            equalTo(CommonColumnName.ID,globalModel.getMyString()).findAll();
                    if(results.size()!=0){
                        salableGoodsTable = new SalableGoodsTable(results.get(0).getGoodColumnNumber(),results.get(0).getGuid(),results.get(0).getId(),results.get(0).getCustomerGroupId(),results.get(0).getGoodId(),results.get(0).getCode(),results.get(0).getGoodCode(),results.get(0).getTechInfo(),results.get(0).getGoodName(),results.get(0).getUnitName(),results.get(0).getUnitId2(),results.get(0).getUnitName2(),results.get(0).getDescription(),results.get(0).getUnitPrice(),results.get(0).getLength(),results.get(0).getWidth(),results.get(0).getHeight(),results.get(0).getAlloy(),results.get(0).getGoodBrandId(),results.get(0).getDiscountPercentage(),results.get(0).getTaxPercentage(),results.get(0).getAvailableAmount2(),results.get(0).getTempAvailableAmount2(),results.get(0).getNumerator(),results.get(0).getDenominator(),results.get(0).getBaseAmountForGift(),results.get(0).getGoodsIdGift(),results.get(0).getGiftAmount(),results.get(0).getExpireDate(),results.get(0).getSyncState(),results.get(0).getActivityState(),results.get(0).getOldValue());
                    } else {
                        salableGoodsTable = new SalableGoodsTable();
                    }
                }
                ,
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(salableGoodsTable);
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("GetSalableByRecallId***data/"+error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }

}
