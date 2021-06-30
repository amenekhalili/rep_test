package ir.fararayaneh.erp.utils.show_information_message.custom_dialoge;


import ir.fararayaneh.erp.IBase.common_base.IListeners;
import ir.fararayaneh.erp.data.models.IModels;

public interface MessageListener extends IListeners {
     void listener(int wichDialoge, int wichClick, IModels iModels);
}
