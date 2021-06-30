package ir.fararayaneh.erp.adaptors.combo_form_adaptor;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;

public class ComboFormRecHolderType10 extends BaseRecHolder {

    private IClickRowComboFormRecHolderListener holderListener;
    @BindView(R.id.img_row_form_type_10)
    AppCompatImageView img_row_form_type_10;
    @BindView(R.id.img_filter_on_row_form_type_10)
    AppCompatImageView img_filter_on_row_form_type_10;
    @BindView(R.id.img_filter_off_row_form_type_10)
    AppCompatImageView img_filter_off_row_form_type_10;

    @OnClick({R.id.img_row_form_type_10,R.id.img_filter_off_row_form_type_10,R.id.img_filter_on_row_form_type_10})
    public void clickTimeSheetPlanRecHolder9(View view) {
        if(holderListener!=null){
            holderListener.clickRow(getAdapterPosition(),"");
        }
    }

    ComboFormRecHolderType10(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener=holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    public void setRowData(boolean on) {
        img_filter_on_row_form_type_10.setVisibility(on?View.VISIBLE:View.GONE);
        img_filter_off_row_form_type_10.setVisibility(on?View.GONE:View.VISIBLE);
    }
}