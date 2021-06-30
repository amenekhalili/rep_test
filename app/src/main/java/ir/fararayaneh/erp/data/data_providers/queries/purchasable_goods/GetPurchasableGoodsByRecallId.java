package ir.fararayaneh.erp.data.data_providers.queries.purchasable_goods;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;


public class GetPurchasableGoodsByRecallId extends BaseQueries {

    private PurchasableGoodsTable purchasableGoodsTable;
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        GlobalModel globalModel =  (GlobalModel)iModels;
        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {
                    RealmResults<PurchasableGoodsTable> results = realm.where(PurchasableGoodsTable.class).
                            equalTo(CommonColumnName.ID,globalModel.getMyString()).findAll();
                    if(results.size()!=0){
                        purchasableGoodsTable = new PurchasableGoodsTable(results.get(0).getGuid(),results.get(0).getId(),results.get(0).getCode(),results.get(0).getGoodCode(),results.get(0).getTechInfo(),results.get(0).getGoodName(),results.get(0).getUnitName1(),results.get(0).getGoodColumnNumber(),results.get(0).getUnitId(),results.get(0).getUnitName2(),results.get(0).getNumerator(),results.get(0).getDenominator(),results.get(0).getAmount2(),results.get(0).getTempAmount2(),results.get(0).getCode6(),results.get(0).getName6(),results.get(0).getFormNumber(),results.get(0).getFormDate(),results.get(0).getDescription(),results.get(0).getLength(),results.get(0).getWidth(),results.get(0).getHeight(),results.get(0).getAlloy(),results.get(0).getExpireDate(),results.get(0).getB5IdRefId15(),results.get(0).getB5IdRefId8(),results.get(0).getB5IdRefId9(),results.get(0).getB5IdRefId10(),results.get(0).getStatusName(),results.get(0).getFormName(),results.get(0).getYearCode(),results.get(0).getGoodId(),results.get(0).getSyncState(),results.get(0).getActivityState(),results.get(0).getOldValue(),results.get(0).getAttach());
                    } else {
                        purchasableGoodsTable = new PurchasableGoodsTable();
                    }
                }
                ,
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(purchasableGoodsTable);
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("GetPurchasableGoodsByRecallId***data/"+error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }

}
