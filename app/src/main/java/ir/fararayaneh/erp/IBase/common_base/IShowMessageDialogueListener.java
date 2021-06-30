package ir.fararayaneh.erp.IBase.common_base;

/**
 * for connect showMessage or showDialogue methods from view to presenter
 */
public interface IShowMessageDialogueListener extends IListeners {
    void sendShowMsgToPresenter(final String msg);
    void sendShowDialogToPresenter(final int dialogueType,int commonComboClickType,String someValue);
}
