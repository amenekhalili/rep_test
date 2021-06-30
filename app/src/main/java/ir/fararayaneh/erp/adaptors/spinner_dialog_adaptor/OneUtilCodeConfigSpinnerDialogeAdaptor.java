package ir.fararayaneh.erp.adaptors.spinner_dialog_adaptor;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import io.realm.OrderedRealmCollection;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDialogDBAdapter;
import ir.fararayaneh.erp.data.models.tables.UtilCodeTable;

public class OneUtilCodeConfigSpinnerDialogeAdaptor extends BaseSpinnerDialogDBAdapter {

    public OneUtilCodeConfigSpinnerDialogeAdaptor(@Nullable OrderedRealmCollection[] data) {
        super(data);
    }

    @Override
    protected void showProperDataInSpinnerRows(int position, AppCompatTextView spinnerRowText) {
        if(getCorrectItem(0,position)!=null){
            UtilCodeTable utilCodeTable=(UtilCodeTable) getCorrectItem(0,position);
            spinnerRowText.setText(utilCodeTable.getName());
        }
    }


}
