package ir.fararayaneh.erp.data.data_providers.queries.goods;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * globalModel.getStringArrayList() is list of ids
 */
public class GetGoodsListByIdListQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);

        GlobalModel globalModel = (GlobalModel) iModels;
        ArrayList<GoodsTable> goodsTableArrayList = new ArrayList<>(globalModel.getStringArrayList().size());

        return Single.create(emitter -> realm.executeTransactionAsync(

                realm -> {
                    Stream.of(globalModel.getStringArrayList()).forEach(id -> {
                        GoodsTable goodsTable;
                        RealmResults<GoodsTable> goodsTableRealmResults = realm.where(GoodsTable.class).equalTo(CommonColumnName.ID, id).findAll();
                        if(goodsTableRealmResults.size()!=0){
                            goodsTable = new GoodsTable(Objects.requireNonNull(goodsTableRealmResults.get(0)).getId(),goodsTableRealmResults.get(0).getC5BrandId(),goodsTableRealmResults.get(0).getBrandName(),goodsTableRealmResults.get(0).getB5HCStatusId(),goodsTableRealmResults.get(0).getNationalityCode(),goodsTableRealmResults.get(0).getCode(),goodsTableRealmResults.get(0).getName(),goodsTableRealmResults.get(0).getLatinName(),goodsTableRealmResults.get(0).getDescription(),goodsTableRealmResults.get(0).getGoogleKeyWord(),goodsTableRealmResults.get(0).getTechInfo(),goodsTableRealmResults.get(0).getStatusName(),goodsTableRealmResults.get(0).getAttach(),goodsTableRealmResults.get(0).getGuid(),goodsTableRealmResults.get(0).getSerial(),goodsTableRealmResults.get(0).getGoodSUOMList(),goodsTableRealmResults.get(0).getGoodTypeList(),goodsTableRealmResults.get(0).getOldValue(),goodsTableRealmResults.get(0).getSyncState());
                        } else {
                            goodsTable = new GoodsTable();
                        }
                        goodsTableArrayList.add(goodsTable);
                    });
                    globalModel.setGoodsTableArrayList(goodsTableArrayList);
                }
                ,
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(globalModel);
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("GetGoodsListByIdListQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));

    }

}