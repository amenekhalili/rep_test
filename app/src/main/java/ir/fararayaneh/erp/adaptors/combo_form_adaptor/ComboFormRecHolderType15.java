package ir.fararayaneh.erp.adaptors.combo_form_adaptor;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.utils.EditTextCheckHelper;

public class ComboFormRecHolderType15 extends BaseRecHolder {

    private IClickRowComboFormRecHolderListener holderListener;



    @BindView(R.id.txt_value_1_row_form_type_15)
    AppCompatTextView txt_value_1_row_form_type_15;
    @BindView(R.id.txt_value_2_row_form_type_15)
    AppCompatTextView txt_value_2_row_form_type_15;
    @BindView(R.id.img_row_form_type_15)
    AppCompatImageView img_row_form_type_15;

    @OnClick({R.id.txt_value_1_row_form_type_15, R.id.txt_value_2_row_form_type_15,R.id.img_row_form_type_15})
    public void clickComboFormRecHolderType15(View view) {
        if(holderListener!=null){
            holderListener.clickRow(getAdapterPosition(),"");
        }
    }

    public ComboFormRecHolderType15(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener=holderListener;

    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String value) {
        if(value.length()==Commons.FUll_PLAQUE_LENGTH && value.contains("-")){
            Log.i("arash", "setRowData:*******//********* "+value.length());
        txt_value_1_row_form_type_15.setText(value.substring(0,8));
        txt_value_2_row_form_type_15.setText(value.substring(9,11));
        } else {
            txt_value_1_row_form_type_15.setText(value);
            txt_value_2_row_form_type_15.setText("");
        }
    }
}
