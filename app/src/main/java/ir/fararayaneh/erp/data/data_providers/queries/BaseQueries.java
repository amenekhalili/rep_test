package ir.fararayaneh.erp.data.data_providers.queries;

import javax.inject.Inject;

import io.reactivex.Single;
import io.realm.Realm;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.data_providers.IDataProvider;
import ir.fararayaneh.erp.data.models.IModels;

public abstract class BaseQueries implements IDataProvider<Single<IModels>> {
    @Inject
    public Realm realm;

    @Override
    public Single<IModels> data(IModels iModels) {
        setupComponent();
        return null;
    }

    private void setupComponent(){
        App.getAppcomponent().injectBaseQueries(this);
    }

}
