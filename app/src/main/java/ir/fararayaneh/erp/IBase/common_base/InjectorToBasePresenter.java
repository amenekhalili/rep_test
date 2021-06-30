package ir.fararayaneh.erp.IBase.common_base;

import com.google.gson.Gson;

import javax.inject.Inject;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.dagger.modules.GsonModule;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.utils.CompositeUnRegisterListener;
import ir.fararayaneh.erp.utils.show_information_message.message_strategy.ChooseMsgStrategy;
import ir.fararayaneh.erp.utils.show_information_message.message_strategy.DialogeIMessage;
import ir.fararayaneh.erp.utils.show_information_message.message_strategy.SnakMessage;
import ir.fararayaneh.erp.utils.waiting.WaitingHandler;


/**
 * for inject to basePresenter and baseFragPresenter
 */
public class InjectorToBasePresenter {
    @Inject
    public CompositeUnRegisterListener unRegisterListener;
    @Inject
    public SnakMessage snakMsg;
    @Inject
    public UserMessageModel userMessageModel;
    @Inject
    public DialogeIMessage dialogeMessage;
    @Inject
    public ChooseMsgStrategy msgStrategy;
    @Inject
    public Gson gson;
    @Inject
    public WaitingHandler waitingHandler;

    protected InjectorToBasePresenter() {
        App.getAppcomponent2().injectBasePresenter(this);
    }
}
