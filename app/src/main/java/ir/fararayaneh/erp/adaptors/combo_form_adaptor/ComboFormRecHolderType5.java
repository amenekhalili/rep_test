package ir.fararayaneh.erp.adaptors.combo_form_adaptor;

import android.view.View;

import com.github.angads25.toggle.widget.LabeledSwitch;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.middle.ComboFormModel;

public class ComboFormRecHolderType5 extends BaseRecHolder {

    private IClickRowComboFormRecHolderListener holderListener;
    private ComboFormModel comboFormModel;

    @BindView(R.id.switch_row_form_type_5)
    LabeledSwitch switch_row_form_type_5;

    public void setComboFormModel(ComboFormModel comboFormModel) {
        this.comboFormModel = comboFormModel;
    }

    ComboFormRecHolderType5(View itemView, IClickRowComboFormRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
        setupSwitch();
    }

    private void setupSwitch() {

        //todo remove listener
        switch_row_form_type_5.setOnToggledListener((toggleableView, isOn) -> {
            if(holderListener!=null){
                if(isOn){
                    holderListener.clickRow(getAdapterPosition(),String.valueOf(Commons.SWITCH_IS_ON));
                    comboFormModel.setOn(true);
                } else {
                    holderListener.clickRow(getAdapterPosition(),String.valueOf(Commons.SWITCH_IS_OFF));
                    comboFormModel.setOn(false);
                }
            }
        });
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String title,String value,boolean onOrOff) {
        switch_row_form_type_5.setLabelOff(title);
        switch_row_form_type_5.setLabelOn(value);
        switch_row_form_type_5.setOn(onOrOff);
    }

}

