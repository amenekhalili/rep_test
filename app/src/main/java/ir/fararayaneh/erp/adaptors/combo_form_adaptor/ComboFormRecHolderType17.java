package ir.fararayaneh.erp.adaptors.combo_form_adaptor;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;

public class ComboFormRecHolderType17 extends BaseRecHolder {

    private IClickRowComboFormRecHolderListener holderListener;
    @BindView(R.id.img_row_form_type_17)
    AppCompatImageView img_row_form_type_11;

    @OnClick({R.id.img_row_form_type_17})
    public void clickComboFormRecHolderType17(View view) {
        if(holderListener!=null){
            holderListener.clickRow(getAdapterPosition(),"");
        }
    }

    ComboFormRecHolderType17(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener=holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

}
