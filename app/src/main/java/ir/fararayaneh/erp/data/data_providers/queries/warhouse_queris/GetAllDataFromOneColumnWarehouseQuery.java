package ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris;

import com.annimon.stream.Stream;
import java.util.ArrayList;
import io.reactivex.Single;
import io.realm.RealmResults;
import io.realm.Sort;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.CommonWarehouseSearchType;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * با توجه به اسم ستونی که به اینجا میاید،
 * و داشتن یا نداشتن شرظ فیلتر که روی ستون
 * placeShelfRow
 * اعمال میشود،
 * (globalModel.getMyString())
 * همه ان ستون برمیگردد
 * اسم ستون
 * globalModel.getMyInt() (from CommonWarehouseSearchType)
 */
public class GetAllDataFromOneColumnWarehouseQuery extends BaseQueries {

    @Override
    public Single<IModels> data(IModels iModels) {

        super.data(iModels);

        GlobalModel globalModel =  (GlobalModel)iModels;
        String filterValue = globalModel.getMyString();

        ArrayList<String> nameValue =new ArrayList<>();
        ArrayList<String> idValue =new ArrayList<>();

        return Single.create(emitter -> realm.executeTransactionAsync(

                realm -> {
                    RealmResults<WarehouseHandlingTable> results;

                    //filtering-----------------------------------------
                    if(filterValue.equals(Commons.NULL_WAREHOUSE_FILTER_VALUE)){
                         results = realm.where(WarehouseHandlingTable.class)
                                 .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNCED)
                                 .or()
                                 .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                                 .sort(CommonColumnName.PLACE_SHELF_ROW, Sort.ASCENDING,CommonColumnName.GOOD_CODE, Sort.ASCENDING)

                                 .findAll();
                    } else {
                         results = realm.where(WarehouseHandlingTable.class).
                                  equalTo(CommonColumnName.PLACE_SHELF_ROW,filterValue).
                                  equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNCED)
                                 .or()
                                 .equalTo(CommonColumnName.PLACE_SHELF_ROW,filterValue)
                                 .equalTo(CommonColumnName.SYNCSTATE, CommonSyncState.SYNC_ERROR)
                                 .sort(CommonColumnName.PLACE_SHELF_ROW, Sort.ASCENDING,CommonColumnName.GOOD_CODE, Sort.ASCENDING)

                                 .findAll();
                    }
                    //filtering-----------------------------------------

                    //add result for multy search value(serial or composite)----------------------------------------
                    switch (globalModel.getMyInt()){
                        case CommonWarehouseSearchType.COMPOSITE_SEARCH_TYPE:
                            Stream.of(results).forEach(warehouseHandlingTable -> {
                                nameValue.add(warehouseHandlingTable.getSearchValue());
                                idValue.add(warehouseHandlingTable.getId());
                            });
                            break;
                        case CommonWarehouseSearchType.SERIAL_SEARCH_TYPE:
                            Stream.of(results).forEach(warehouseHandlingTable -> {
                                nameValue.add(String.valueOf(warehouseHandlingTable.getSerial()));
                                idValue.add(warehouseHandlingTable.getId());
                            });
                            break;
                        default://for prevent of empty list
                            Stream.of(results).forEach(warehouseHandlingTable -> {
                                nameValue.add(String.valueOf(warehouseHandlingTable.getSearchValue()));
                                idValue.add(warehouseHandlingTable.getId());
                            });
                    }
                    //add result----------------------------------------


                    globalModel.setStringArrayList(nameValue);
                    globalModel.setStringArrayList2(idValue);
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
                    ThrowableLoggerHelper.logMyThrowable("GetAllDataFromOneColumnWarehouseQuery***data/"+error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));

    }

}
