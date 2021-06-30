package ir.fararayaneh.erp.adaptors.message_adaptor;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface IClickRowMessageRecHolderListener  extends IListeners {

    void clickAttachRow(int position);

    void clickAvatarRow(int position);

    void clickMessage(int position);

}
