package ir.fararayaneh.erp.adaptors.good_detail_adaptor;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.custom_views.CustomToolTip;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.tooltip.ToolTipHelper;

public class GoodDetailsRecHolder extends BaseRecHolder {

    private IClickRowDetailGoodsRecHolderListener holderListener;
    @BindView(R.id.img_delete_row_good_detail)
    AppCompatImageView img_delete_row_good_detail;
    @BindView(R.id.txt_content_row_good_detail)
    AppCompatTextView txt_content_row_good_detail;
    @BindView(R.id.txt_title_row_good_detail)
    AppCompatTextView txt_title_row_good_detail;


    @OnClick({R.id.img_delete_row_good_detail,R.id.txt_content_row_good_detail,R.id.txt_title_row_good_detail})
    public void clickGoodDetailsRecHolder(View view) {
        if (holderListener != null) {
            switch (view.getId()) {
                case R.id.img_delete_row_good_detail:
                    holderListener.clickDeleteRow(getAdapterPosition());
                    break;
                case R.id.txt_content_row_good_detail:
                case R.id.txt_title_row_good_detail:
                    holderListener.clickEditRow(getAdapterPosition());
                    break;
            }
        }
    }

    GoodDetailsRecHolder(View itemView, IClickRowDetailGoodsRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String title,String content) {
        txt_title_row_good_detail.setText(title);
        txt_content_row_good_detail.setText(content);
    }
}