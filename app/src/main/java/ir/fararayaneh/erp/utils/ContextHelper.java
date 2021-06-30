package ir.fararayaneh.erp.utils;

import android.content.Context;

import com.yariksoffice.lingver.Lingver;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;

/**
 *
 https://proandroiddev.com/change-language-programmatically-at-runtime-on-android-5e6bc15c758
 */
public class ContextHelper {
    public static Context customContext(Context context) {
        //return ViewPumpContextWrapper.wrap(LocaleHelper.onAttach(context));

        Lingver.getInstance().setLocale(context, SharedPreferenceProvider.getLocalLanguage(context));
        return ViewPumpContextWrapper.wrap(context);
    }
}
