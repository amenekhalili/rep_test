package ir.fararayaneh.erp.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.fararayaneh.erp.utils.show_information_message.message_strategy.DialogeIMessage;

@Module
//@Singleton
public class DialogeMessageModule {

    @Provides
    public DialogeIMessage creatDialogeMessage() {
        return new DialogeIMessage();
    }
}
