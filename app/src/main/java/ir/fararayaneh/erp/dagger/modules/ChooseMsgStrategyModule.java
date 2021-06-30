package ir.fararayaneh.erp.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.fararayaneh.erp.utils.show_information_message.message_strategy.ChooseMsgStrategy;


@Module
//@Singleton
public class ChooseMsgStrategyModule {
    @Provides
    public ChooseMsgStrategy creatChooseMsgStrategy() {
        return new ChooseMsgStrategy();
    }
}
