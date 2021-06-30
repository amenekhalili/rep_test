package ir.fararayaneh.erp.IBase.common_base;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

public abstract class BaseViewWithRecycle extends BaseView {


    public BaseViewWithRecycle(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupRecyclers();
    }

    public BaseViewWithRecycle(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupRecyclers();
    }

    protected BaseViewWithRecycle(@NonNull BaseActivity activity) {
        super(activity);
        setupRecyclers();
    }


    protected abstract void setupRecyclers() ;

    //todo if we have many recyclers in one activity, we should create another base and in below methods, call all setRecycleAdaptor and other needed methods for those adaptors

    public abstract void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>...recycleAdaptor);

    public abstract void loadAtEndList(final IGoNextPage IGoNextPage);

}
