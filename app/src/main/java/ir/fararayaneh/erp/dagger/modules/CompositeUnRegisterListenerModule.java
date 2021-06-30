package ir.fararayaneh.erp.dagger.modules;

import dagger.Module;
import dagger.Provides;
import ir.fararayaneh.erp.utils.CompositeUnRegisterListener;

/**
 * because each presenter should have own unRegisterListener,
 * this module do not should be singleton
 */
@Module
public class CompositeUnRegisterListenerModule {

        @Provides
        public CompositeUnRegisterListener createCompositeUnRegisterListener() {
            return new CompositeUnRegisterListener();
        }

}
