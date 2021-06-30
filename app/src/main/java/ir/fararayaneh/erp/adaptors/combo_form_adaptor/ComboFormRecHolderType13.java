package ir.fararayaneh.erp.adaptors.combo_form_adaptor;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;

public class ComboFormRecHolderType13 extends BaseRecHolder {

    private IClickRowComboFormRecHolderListener holderListener;
    @BindView(R.id.img_row_form_type_13)
    AppCompatImageView img_row_form_type_13;

    @OnClick({R.id.img_row_form_type_13})
    public void clickTimeSheetPlanRecHolder13(View view) {
        if(holderListener!=null){
            holderListener.clickRow(getAdapterPosition(),"");
        }
    }

    ComboFormRecHolderType13(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener=holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

}
