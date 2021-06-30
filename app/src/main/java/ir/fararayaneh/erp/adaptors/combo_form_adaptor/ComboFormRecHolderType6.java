package ir.fararayaneh.erp.adaptors.combo_form_adaptor;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;

public class ComboFormRecHolderType6 extends BaseRecHolder {

    private IClickRowComboFormRecHolderListener holderListener;
    @BindView(R.id.txt_value_row_form_type_6)
    AppCompatTextView txt_value_row_form_type_6;




    ComboFormRecHolderType6(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener=holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String value ) {
        txt_value_row_form_type_6.setText(value);
    }

}
