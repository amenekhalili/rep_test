package ir.fararayaneh.erp.adaptors.reports_adaptor;

import ir.fararayaneh.erp.IBase.common_base.IListeners;
import ir.fararayaneh.erp.data.models.IModels;


/**
 * witchClick : form CommonClickRowReport.class
 */
public interface IClickReportsRecAdaptor extends IListeners {

    void clickConfigs(IModels iModels, int witchClick,boolean clickOnNormalViewType);

    void notifyNewRow(int position);

    void notifyEditedRow(int position);
}

