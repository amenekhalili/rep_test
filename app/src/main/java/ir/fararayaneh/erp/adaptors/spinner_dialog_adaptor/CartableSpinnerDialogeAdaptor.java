package ir.fararayaneh.erp.adaptors.spinner_dialog_adaptor;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import io.realm.OrderedRealmCollection;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDialogDBAdapter;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CartableTable;

public class CartableSpinnerDialogeAdaptor extends BaseSpinnerDialogDBAdapter {

    public CartableSpinnerDialogeAdaptor(@Nullable OrderedRealmCollection[] data) {
        super(data);
    }

    @Override
    protected void showProperDataInSpinnerRows(int position, AppCompatTextView spinnerRowText) {
        if(getCorrectItem(0,position)!=null){
            CartableTable cartableTable=(CartableTable)getCorrectItem(0,position);
            spinnerRowText.setText(cartableTable.getSenderName()+"**"+position);
        }
    }


}
