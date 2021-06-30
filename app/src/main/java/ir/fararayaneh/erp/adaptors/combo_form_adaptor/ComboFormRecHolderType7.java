package ir.fararayaneh.erp.adaptors.combo_form_adaptor;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.middle.ComboFormModel;

public class ComboFormRecHolderType7 extends BaseRecHolder {

    private ComboFormModel comboFormModel;

    public void setComboFormModel(ComboFormModel comboFormModel) {
        this.comboFormModel = comboFormModel;
    }
    private IClickRowComboFormRecHolderListener holderListener;
    @BindView(R.id.txt_title_row_form_type_7)
    AppCompatTextView txt_title_row_form_type_7;
    @BindView(R.id.edt_txt_value_row_form_type_7)
    AppCompatEditText edt_txt_value_row_form_type_7;
    private String myValue = "";
    private int myPosition;


    @OnTextChanged(R.id.edt_txt_value_row_form_type_7)
    protected void onTextChanged(CharSequence text) {
        Log.i("arash", "onTextChanged2: "+text);
        myValue = text.toString();
        comboFormModel.setValue(myValue);
        if (holderListener != null) {
            holderListener.clickRow(getAdapterPosition(), myValue);
        }
    }

    /*@OnFocusChange({R.id.edt_txt_value_row_form_type_4})
    void onFocusChange(View view, boolean hasFocus) {
        if(hasFocus){
            myPosition=getAdapterPosition();
        }
        else if (holderListener != null) {
            holderListener.clickRow(myPosition, myValue);
        }
        Log.i("arash", "onFocusChange: hasFocus:"+hasFocus+"***"+"myValue:"+myValue);
    }*/


    ComboFormRecHolderType7(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String title,String value) {
        txt_title_row_form_type_7.setText(title);
        if(!value.equals(Commons.NULL)){
            edt_txt_value_row_form_type_7.setText(value);
        }else {
            edt_txt_value_row_form_type_7.setText("");
        }
    }

}
