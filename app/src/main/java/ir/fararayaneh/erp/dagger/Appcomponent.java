package ir.fararayaneh.erp.dagger;


import dagger.Component;
import ir.fararayaneh.erp.dagger.modules.RealmModule;
import ir.fararayaneh.erp.data.data_providers.queries.BaseQueries;


@Component(modules = RealmModule.class)

public interface Appcomponent {
    void injectBaseQueries(BaseQueries baseQueries);
}
