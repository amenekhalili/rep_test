package ir.fararayaneh.erp.custom_views.custom_recycle_view;

import android.content.Context;
import androidx.annotation.Nullable;

import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;
import ir.fararayaneh.erp.custom_views.EndScrollListener;
import ir.fararayaneh.erp.custom_views.ILazyLoad;


public class CustomRecycleView extends RecyclerView implements ILazyLoad {

    private CustomRecycleScrool endListScroll;
    private EndScrollListener endScrollListener;

    public void setListenEndScroll(EndScrollListener endScrollListener) {
        this.endScrollListener = endScrollListener;
    }


    public  void setLoading(boolean loading){
        endListScroll.setLoading(loading);
    }


    public CustomRecycleView(Context context) {
        super(context);
    }

    public CustomRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        //  TODO clear LinearLayoutManager limitation
        /*if(!(layout instanceof LinearLayoutManager)){
            throw new RuntimeException();}*/
        setLazyLoad(layout);
        super.setLayoutManager(layout);
    }


    @Override
    public void setLazyLoad(LayoutManager layoutManager) {
        endListScroll=new CustomRecycleScrool(layoutManager) {
            @Override
            public void onMoreLoad() {
                if(endScrollListener!=null){
                    endScrollListener.loadMore();}
            }
        };
        addOnScrollListener(endListScroll);
    }
}
