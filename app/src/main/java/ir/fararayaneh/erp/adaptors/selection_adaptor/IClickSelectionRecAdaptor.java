package ir.fararayaneh.erp.adaptors.selection_adaptor;

import java.util.ArrayList;

import ir.fararayaneh.erp.data.models.IModels;

public interface IClickSelectionRecAdaptor {

    void clickAttachment(IModels rowOfTable);

    void notifyNewRow(int position);

    void notifyEditedRow(int position);

    void showDialog(ArrayList<String> stringArrayList);//for show complete data
}
