package ir.fararayaneh.erp.adaptors.facilities_adaptor;

import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.custom_views.CustomCircleImageView;
import ir.fararayaneh.erp.utils.PicassoHandler;

public class FacilitiesRecHolder extends BaseRecHolder {

    private IClickRowFacilitiesRecHolderListener holderListener;
    @BindView(R.id.img_main_facilities_row)
    CustomCircleImageView img_main_facilities_row;
    @BindView(R.id.txt_main_facilities_row)
    AppCompatTextView txt_main_facilities_row;


    @OnClick({R.id.img_main_facilities_row, R.id.txt_main_facilities_row})
    public void clickFacilitiesRecHolder(View view) {
        if(holderListener!=null){
            holderListener.clickRow(getAdapterPosition());
        }
    }

    FacilitiesRecHolder(View itemView,IClickRowFacilitiesRecHolderListener holderListener) {
        super(itemView);
        this.holderListener=holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String title, String imageUrl) {
        txt_main_facilities_row.setText(title);
            PicassoHandler.setByPicasso(picasso, Commons.NULL,imageUrl,img_main_facilities_row, false, Commons.SPACE, true, false);
    }
}
