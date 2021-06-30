package ir.fararayaneh.erp.utils;

import android.content.Context;
import android.content.res.TypedArray;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;

public class GetATTResources {
    public static int resId(Context context, int[] resId){
        TypedArray a = context.getTheme().obtainStyledAttributes(SharedPreferenceProvider.getAppTheme(context), resId);
        int attributeResourceId = a.getResourceId(0, 0);
        a.recycle();
        return attributeResourceId;
    }
}
