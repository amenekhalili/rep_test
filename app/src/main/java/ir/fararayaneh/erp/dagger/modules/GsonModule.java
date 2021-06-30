package ir.fararayaneh.erp.dagger.modules;


import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
//@Singleton
public class GsonModule {

    @Provides
    public Gson creatGson() {
        return new Gson();
    }
}
