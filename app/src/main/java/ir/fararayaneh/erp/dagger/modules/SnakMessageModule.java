package ir.fararayaneh.erp.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.fararayaneh.erp.utils.show_information_message.message_strategy.SnakMessage;

@Module
//@Singleton
public class SnakMessageModule {
    @Provides
    public SnakMessage creatSnakMessage() {
        return new SnakMessage();
    }
}
