package ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import ir.fararayaneh.erp.App;


public abstract class BaseRecHolder extends RecyclerView.ViewHolder {
    @Inject
    public Picasso picasso;

    public BaseRecHolder(View itemView) {
        super(itemView);
        inflateButterKnife(itemView);
        App.getAppcomponent3().injectBaseRecHolder(this);

    }

    protected abstract void inflateButterKnife(View itemView);


}
