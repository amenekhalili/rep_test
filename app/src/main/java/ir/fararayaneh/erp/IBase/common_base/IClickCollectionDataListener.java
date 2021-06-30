package ir.fararayaneh.erp.IBase.common_base;

/**
 * for listen to BaseViewCollectionData
 */
public interface IClickCollectionDataListener extends IListeners {
    void confirmClick();
    void cancelClick();
    void nextClick();
    void deleteClick();
}
