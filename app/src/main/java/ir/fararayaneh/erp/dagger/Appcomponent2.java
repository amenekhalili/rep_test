package ir.fararayaneh.erp.dagger;



import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;
import ir.fararayaneh.erp.IBase.common_base.InjectorToBasePresenter;
import ir.fararayaneh.erp.dagger.modules.ChooseMsgStrategyModule;
import ir.fararayaneh.erp.dagger.modules.CompositeUnRegisterListenerModule;
import ir.fararayaneh.erp.dagger.modules.DialogeMessageModule;
import ir.fararayaneh.erp.dagger.modules.GsonModule;
import ir.fararayaneh.erp.dagger.modules.SnakMessageModule;
import ir.fararayaneh.erp.dagger.modules.UserMessageModelModule;
import ir.fararayaneh.erp.dagger.modules.WaitingHandlerModule;

/**
 * for all injection to BasePresenter
 */

@Component(modules = {ChooseMsgStrategyModule.class, UserMessageModelModule.class,SnakMessageModule.class, DialogeMessageModule.class,CompositeUnRegisterListenerModule.class,GsonModule.class,WaitingHandlerModule.class})
@Singleton
public interface Appcomponent2 {
    void injectBasePresenter(InjectorToBasePresenter injector);
}
