package ir.fararayaneh.erp.utils;

import android.content.Context;
import android.view.ViewGroup;

import jp.wasabeef.blurry.Blurry;

// TODO: 1/26/2019 set best value
public class BlurHandler {

    public static void add(Context context,ViewGroup rootView){
        Blurry.with(context)
                .radius(10)
                .sampling(1)
                //.color(Color.argb(66, 255, 255, 0))
                .async()
                //.animate(500)
                .onto(rootView);
    }

    public static void remove(ViewGroup rootView){
        Blurry.delete(rootView);
    }
}
