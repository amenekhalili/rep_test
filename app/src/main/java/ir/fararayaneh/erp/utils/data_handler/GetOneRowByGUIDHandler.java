package ir.fararayaneh.erp.utils.data_handler;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries.GetRowCartableByGUIDQuery;
import ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries.GetRowFuelDetailByGUIDQuery;
import ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries.GetRowGoodByGUIDQuery;
import ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries.GetRowGoodTranceByGUIDQuery;
import ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries.GetRowTaskByGUIDQuery;
import ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries.GetRowTimeByGUIDQuery;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class GetOneRowByGUIDHandler {


    public static Disposable getRowGoodTrance(String guid,Class clazz ,boolean haveFakeGUID, IGetRowByGUIDQueryListener iGetRowByGUIDQueryListener) {

        GetRowGoodTranceByGUIDQuery getRowGoodTranceByGUIDQuery =(GetRowGoodTranceByGUIDQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_ROW_GOOD_TRANCE_BY_GUID_QUERY);

        assert getRowGoodTranceByGUIDQuery != null;
        return getRowGoodTranceByGUIDQuery.data(getProperDataModel(guid,clazz,haveFakeGUID)).subscribe(iModels -> {
            if(iGetRowByGUIDQueryListener !=null){
                iGetRowByGUIDQueryListener.onGetRow(iModels);
            }

        }, throwable -> {
            if(iGetRowByGUIDQueryListener !=null){
                iGetRowByGUIDQueryListener.onError();
            }
            ThrowableLoggerHelper.logMyThrowable("GetOneRowByGUIDHandler/getRowGoodTrance "+throwable.getMessage());
        });

    }

    public static Disposable getRowTask(String guid,Class clazz,boolean haveFakeGUID, IGetRowByGUIDQueryListener iGetRowByGUIDQueryListener) {

        GetRowTaskByGUIDQuery getRowTaskByGUIDQuery =(GetRowTaskByGUIDQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_ROW_TASK_BY_GUID_QUERY);

        assert getRowTaskByGUIDQuery != null;
        return getRowTaskByGUIDQuery.data(getProperDataModel(guid,clazz,haveFakeGUID)).subscribe(iModels -> {
                if(iGetRowByGUIDQueryListener !=null){
                    iGetRowByGUIDQueryListener.onGetRow(iModels);
                }

        }, throwable -> {
            if(iGetRowByGUIDQueryListener !=null){
                iGetRowByGUIDQueryListener.onError();
            }
            ThrowableLoggerHelper.logMyThrowable("GetOneRowByGUIDHandler/getRowTask "+throwable.getMessage());
        });

    }



    public static Disposable getRowFuelDetail(String guid,Class clazz,boolean haveFakeGUID, IGetRowByGUIDQueryListener iGetRowByGUIDQueryListener) {

        GetRowFuelDetailByGUIDQuery fuelDetailByGUIDQuery =(GetRowFuelDetailByGUIDQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_ROW_FUEL_DETAIL_BY_GUID_QUERY);

        assert fuelDetailByGUIDQuery != null;
        return fuelDetailByGUIDQuery.data(getProperDataModel(guid,clazz,haveFakeGUID)).subscribe(iModels -> {
            if(iGetRowByGUIDQueryListener !=null){
                iGetRowByGUIDQueryListener.onGetRow(iModels);
            }

        }, throwable -> {
            if(iGetRowByGUIDQueryListener !=null){
                iGetRowByGUIDQueryListener.onError();
            }
            ThrowableLoggerHelper.logMyThrowable("GetOneRowByGUIDHandler/getRowFuelDetail "+throwable.getMessage());
        });

    }


    public static Disposable getRowTime(String guid,Class clazz,boolean haveFakeGUID, IGetRowByGUIDQueryListener iGetRowByGUIDQueryListener) {

        GetRowTimeByGUIDQuery getRowTimeByGUIDQuery =(GetRowTimeByGUIDQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_ROW_TIME_BY_GUID_QUERY);

        assert getRowTimeByGUIDQuery != null;
        return getRowTimeByGUIDQuery.data(getProperDataModel(guid,clazz,haveFakeGUID)).subscribe(iModels -> {
                if(iGetRowByGUIDQueryListener !=null){
                    iGetRowByGUIDQueryListener.onGetRow(iModels);
                }
        }, throwable -> {
            if(iGetRowByGUIDQueryListener !=null){
                iGetRowByGUIDQueryListener.onError();
            }
            ThrowableLoggerHelper.logMyThrowable("GetOneRowByGUIDHandler/getRowTime "+throwable.getMessage());
        });

    }

    public static Disposable getRowCartable(String guid,Class clazz,boolean haveFakeGUID, IGetRowByGUIDQueryListener iGetRowByGUIDQueryListener) {

        GetRowCartableByGUIDQuery getRowCartableByGUIDQuery =(GetRowCartableByGUIDQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_ROW_CARTABLE_BY_GUID_QUERY);

        assert getRowCartableByGUIDQuery != null;
        return getRowCartableByGUIDQuery.data(getProperDataModel(guid,clazz,haveFakeGUID)).subscribe(iModels -> {
            if(iGetRowByGUIDQueryListener !=null){
                iGetRowByGUIDQueryListener.onGetRow(iModels);
            }
        }, throwable -> {
            if(iGetRowByGUIDQueryListener !=null){
                iGetRowByGUIDQueryListener.onError();
            }
            ThrowableLoggerHelper.logMyThrowable("GetOneRowByGUIDHandler/getRowCartable "+throwable.getMessage());
        });

    }

    public static Disposable getRowGoods(String guid,Class clazz,boolean haveFakeGUID, IGetRowByGUIDQueryListener iGetRowByGUIDQueryListener) {

        GetRowGoodByGUIDQuery getRowGoodByGUIDQuery =(GetRowGoodByGUIDQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_ROW_GOOD_BY_GUID_QUERY);

        assert getRowGoodByGUIDQuery != null;
        return getRowGoodByGUIDQuery.data(getProperDataModel(guid,clazz,haveFakeGUID)).subscribe(iModels -> {
            if(iGetRowByGUIDQueryListener !=null){
                iGetRowByGUIDQueryListener.onGetRow(iModels);
            }
        }, throwable -> {
            if(iGetRowByGUIDQueryListener !=null){
                iGetRowByGUIDQueryListener.onError();
            }
            ThrowableLoggerHelper.logMyThrowable("GetOneRowByGUIDHandler/getRowGoodByGUIDQuery "+throwable.getMessage());
        });

    }

    /**
     * @param haveFakeGUID : for some table like GoodsTable is true
     */
    private static GlobalModel getProperDataModel(String guid, Class clazz,boolean haveFakeGUID) {
        GlobalModel globalModel=new GlobalModel();
        globalModel.setMyString(guid);
        globalModel.setMyBoolean(haveFakeGUID);
        ArrayList<Class> classes=new ArrayList<>();
        classes.add(clazz);
        globalModel.setClassArrayList(classes);
        return globalModel;
    }
}
