package ir.fararayaneh.erp.utils;

import android.util.Log;
import android.view.View;

import com.github.florent37.viewanimator.ViewAnimator;

import ir.fararayaneh.erp.commons.Commons;

public class AnimationHelper {

    public static void anim_type_1(View view){
        ViewAnimator.animate(view).newsPaper().duration(Commons.COMPANY_WAIT_TIME).start();
    }

    public static void anim_type_2(View view){
        ViewAnimator.animate(view).rubber().duration(Commons.SYNC_WAIT_TIME*20).start();
        Log.i("arash", "anim_type_2:");
    }
}
