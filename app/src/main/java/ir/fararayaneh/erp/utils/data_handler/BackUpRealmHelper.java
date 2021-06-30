package ir.fararayaneh.erp.utils.data_handler;

import io.reactivex.disposables.Disposable;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.AllDataBaseBackupQuery;

public class BackUpRealmHelper {

    public static Disposable makeBackUp(IAllDataBaseBackupQuery iAllDataBaseBackupQuery){
        AllDataBaseBackupQuery allDataBaseBackupQuery=(AllDataBaseBackupQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.ALL_DATA_BASE_BACKUP_QUERY);

        assert allDataBaseBackupQuery != null;
        return allDataBaseBackupQuery.data(App.getNullRXModel()).subscribe(iModels -> {
            if(iAllDataBaseBackupQuery!=null){
                iAllDataBaseBackupQuery.success();
            }
        }, throwable -> {
            if(iAllDataBaseBackupQuery!=null){
                iAllDataBaseBackupQuery.error();
            }
        });
    }
}
