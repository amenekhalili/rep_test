package ir.fararayaneh.erp.utils.show_information_message.message_strategy;


import ir.fararayaneh.erp.IBase.common_base.BasePresenter;
import ir.fararayaneh.erp.IBase.common_base.BaseView;
import ir.fararayaneh.erp.commons.CommonDialogeType;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.GetDataDialog;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.MessageListener;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.SelectCalendarTypeDialoge;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.SelectColorDialog;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.SetupLangDialoge;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.SetupServerDialoge;

public class DialogeIMessage implements IMessage<BaseView> {

    private int dialogeType;

    public void setDialogeType(int dialogeType) {
        this.dialogeType = dialogeType;
    }


    @Override
    public void showMessageToUser(BasePresenter presenter, UserMessageModel userMessageModel, MessageListener messageListener) {
        if(presenter.checkNull()){
            switch (dialogeType) {
            case CommonDialogeType.SETUP_SERVER_DIALOG:
                new SetupServerDialoge(presenter.getView().getActivity(),messageListener).show();
                    break;
            case CommonDialogeType.SETUP_LANGUAGE_DIALOG:
                new SetupLangDialoge(presenter.getView().getActivity(),messageListener).show();
                    break;
            case CommonDialogeType.SETUP_COLOR_DIALOG:
                new SelectColorDialog(presenter.getView().getActivity(),messageListener).show();
                    break;
            case CommonDialogeType.SETUP_CALENDAR_DIALOG:
                    new SelectCalendarTypeDialoge(presenter.getView().getActivity(),messageListener).show();
                    break;
            case CommonDialogeType.GET_DATA_DIALOG:
                new GetDataDialog(presenter.getView().getActivity(),messageListener,userMessageModel).show();
                break;


            }
        }
    }
}
