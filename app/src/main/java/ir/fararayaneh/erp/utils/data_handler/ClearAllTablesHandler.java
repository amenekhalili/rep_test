package ir.fararayaneh.erp.utils.data_handler;


import io.reactivex.disposables.Disposable;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.ClearTableQuery;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * for clear all or some table
 * do not clear shared preference
 */
public class ClearAllTablesHandler {

    /**
     * @param listOfTablesForDelete : from AllTBLListHelper class
     */

    public static Disposable clear(IClearAllTablesListener iClearAllTablesListener,IModels listOfTablesForDelete){


        ClearTableQuery clearTableQuery=(ClearTableQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.CLEAR_TABLE_QUERY);
        assert clearTableQuery != null;
        return clearTableQuery.data(listOfTablesForDelete).subscribe(iModels -> {
                if(iClearAllTablesListener!=null){
                    iClearAllTablesListener.done();
                }
        }, throwable -> {
            if(iClearAllTablesListener!=null){
                iClearAllTablesListener.error();
            }
        });
    }


}
