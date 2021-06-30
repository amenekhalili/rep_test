package ir.fararayaneh.erp.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.fararayaneh.erp.data.models.UserMessageModel;

@Module
//@Singleton
public class UserMessageModelModule {
    @Provides
    public UserMessageModel createUserMessageModel(){
        return new UserMessageModel();
    }
}
