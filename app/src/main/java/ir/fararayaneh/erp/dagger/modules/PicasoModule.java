package ir.fararayaneh.erp.dagger.modules;


import com.squareup.picasso.Picasso;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Due to picasso Documents :
 * LRU memory cache of 15% the available application RAM
 * Disk cache of 2% storage space up to 50MB but no less than 5MB.
 */
@Module
//@Singleton
public class PicasoModule {

    @Provides
    public Picasso creatPicaso() {
        Picasso picasso = Picasso.get();
        picasso.setIndicatorsEnabled(true);
        return picasso;
    }
}
