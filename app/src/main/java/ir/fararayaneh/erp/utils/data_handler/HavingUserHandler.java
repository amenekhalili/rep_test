package ir.fararayaneh.erp.utils.data_handler;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.HaveUserQuery;
import ir.fararayaneh.erp.data.models.GlobalModel;

public class HavingUserHandler {

    public static Disposable haveUser(IHaveUserListener iHaveUserListener){
        HaveUserQuery haveUserQuery=(HaveUserQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.HAVE_USER_QUERY);
        assert haveUserQuery != null;
        return haveUserQuery.data(App.getNullRXModel()).subscribe(iModels -> {
            GlobalModel globalModel=(GlobalModel)iModels;
            if(iHaveUserListener!=null){
                //we sour that we have user or get "null" word as userName and organization
                iHaveUserListener.haveUser(globalModel.isMyBoolean(),globalModel);
            }
        }, throwable -> {
            if(iHaveUserListener!=null){
                iHaveUserListener.error();
            }
        });
    }
}
