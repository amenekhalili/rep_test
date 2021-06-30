package ir.fararayaneh.erp.custom_views.custom_recycle_view;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.custom_views.IScrool;


abstract class CustomRecycleScrool extends RecyclerView.OnScrollListener implements IScrool {


    private boolean loading;

    void setLoading(boolean loading) {
        this.loading = loading;
    }

    private  RecyclerView.LayoutManager layoutManager;

    CustomRecycleScrool(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    /**
     *   no need to lazy scroll in staggered and grid recycler in this project =>>
     *   do not worry about
     *   if (layoutManager instanceof LinearLayoutManager)
     */
    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        int allSize = layoutManager.getItemCount();
        if (layoutManager instanceof LinearLayoutManager){

            int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            int visibleThreshold = Commons.VISIBLE_THRESHOLD_RECYCLER;
            if(!loading && allSize <= lastVisibleItem + visibleThreshold){
                loading=true;
                onMoreLoad();
            }
        }
        super.onScrolled(recyclerView, dx, dy);
    }

}

