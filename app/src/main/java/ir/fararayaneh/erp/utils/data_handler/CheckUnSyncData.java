package ir.fararayaneh.erp.utils.data_handler;

// TODO: 1/29/2019 matn zir khande shavad

import android.util.Log;

import io.reactivex.disposables.Disposable;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.HaveUnSyncQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * استفاده از این متد در صفحه کانفیگ چندان ضروری نیست چون اگر یوزر دارای دیتای سینک نشده باشد، حتما دارای یوزر آی دی است و بنا بر این
 * حتما دارای یوزر ای دی است و بنا برای به صفحه کانفیگ نمیرود
 * کاربرد اصلی این متد، در زمان کلیک
 * روی دکمه لاگ اوت در هر کجای اپ است که اگر دیتای سینک نشده داریم نباید اجازه لاگ اوت داده شود
 * و اگر نداریم هم
 * ابتدا باید به سوکت یک پکت بفرستیم
 (device delete)
 که این دیوایس را ازلیست دیوایس های آن یوزر حذف کند و به دیتابیس هم بگوید
 اگر توانستیم این پکت را بفرستیم
 * سپس همه تیبل های یوزر را پاک کنیم
 * سپس به اسپلش اینتنت بزنیم
 *
 */
public class CheckUnSyncData {


    /**
     *  @param globalModel : from AllTBLListHelper class
     */

    public static Disposable haveUnSync(ICheckUnSyncTableListener iCheckUnSyncTableListener, GlobalModel globalModel){


        HaveUnSyncQuery haveUnSyncQuery =(HaveUnSyncQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.HAVE_UN_SYNC_QUERY);


        assert haveUnSyncQuery != null;
        return haveUnSyncQuery.data(globalModel)
                .subscribe(iModels -> {

                    if(globalModel.isMyBoolean()){
                        if(iCheckUnSyncTableListener!=null){
                            iCheckUnSyncTableListener.haveUnsyncData();
                        }
                    } else if(SharedPreferenceProvider.haveUploadProcess(App.getAppContext())){
                        if(iCheckUnSyncTableListener!=null){
                            iCheckUnSyncTableListener.uploadProcess();

                        }
                    } else {
                         if(iCheckUnSyncTableListener!=null){
                            iCheckUnSyncTableListener.haveNotUnsyncData();

                         }
                    }

        }, throwable -> {
            if(iCheckUnSyncTableListener!=null){
                iCheckUnSyncTableListener.error();

            }
        });
    }
}
