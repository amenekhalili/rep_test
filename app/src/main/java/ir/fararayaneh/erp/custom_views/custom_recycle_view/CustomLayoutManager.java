package ir.fararayaneh.erp.custom_views.custom_recycle_view;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * برای اینکه در مرتبه اول لود ریسایکل ویو
 * تعداد آیتم بیشتری لود شود
 */
public class CustomLayoutManager extends LinearLayoutManager {
    private static final int DEFAULT_EXTRA_LAYOUT_SPACE = 50;
    private int extraLayoutSpace = -1;
    private Context context;

    public CustomLayoutManager(Context context) {
        super(context);
        this.context = context;
    }

    public CustomLayoutManager(Context context, int extraLayoutSpace) {
        super(context);
        this.context = context;
        this.extraLayoutSpace = extraLayoutSpace;
    }

    public CustomLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        this.context = context;
    }

    public void setExtraLayoutSpace(int extraLayoutSpace) {
        this.extraLayoutSpace = extraLayoutSpace;
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        /*if (extraLayoutSpace > 0) {
            return extraLayoutSpace;
        }*/
        Log.i("arash", "getExtraLayoutSpace: /////////////////////////");
        return DEFAULT_EXTRA_LAYOUT_SPACE;
    }
}
