package ir.fararayaneh.erp.adaptors.good_detail_adaptor;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface IClickRowDetailGoodsRecHolderListener extends IListeners {

    void clickDeleteRow(int position);
    void clickEditRow(int position);
}
