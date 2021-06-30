package ir.fararayaneh.erp.adaptors.selection_adaptor;

import java.util.ArrayList;

public interface IClickRowSelectionRecHolderListener {

    void clickCheckBox(String selectedId,boolean isChecked);

    void PlusMinusValue(String selectedId,String finalValue);

    void PlusMinusCurrency(String selectedId,String finalValue);

    void PlusMinusTaxValue(String selectedId, String finalValue);

    void PlusMinusDiscountValue(String selectedId,String finalValue);


    void seeAttach(int position);

    void showDialog(ArrayList<String> stringArrayList);//for show complete data

}
