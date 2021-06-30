package ir.fararayaneh.erp.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.fararayaneh.erp.utils.waiting.WaitingHandler;

//@Singleton
@Module
public class WaitingHandlerModule {

    @Provides
    public  WaitingHandler createWaitingHandler() {
        return new WaitingHandler();
    }
}
