package ir.fararayaneh.erp.adaptors.spinner_dialog_adaptor;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import io.realm.OrderedRealmCollection;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDialogDBAdapter;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.utils.EditTextCheckHelper;

public class GoodsSpinnerDialogeAdaptor extends BaseSpinnerDialogDBAdapter {

    public GoodsSpinnerDialogeAdaptor(@Nullable OrderedRealmCollection[] data) {
        super(data);
    }

    @Override
    protected void showProperDataInSpinnerRows(int position, AppCompatTextView spinnerRowText) {
        if(getCorrectItem(0,position)!=null){
            GoodsTable goodsTable=(GoodsTable)getCorrectItem(0,position);
            spinnerRowText.setText(EditTextCheckHelper.concatHandler(goodsTable.getName(),goodsTable.getSerial(),goodsTable.getCode()));
        }
    }

}
