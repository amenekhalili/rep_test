package ir.fararayaneh.erp.utils.show_information_message.message_strategy;

import android.graphics.Color;
import androidx.annotation.NonNull;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.widget.TextView;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.IBase.common_base.BasePresenter;
import ir.fararayaneh.erp.IBase.common_base.BaseView;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.MessageListener;


public class SnakMessage implements IMessage<BaseView> {

    private Snackbar snackbar;


    @NonNull
    private Snackbar setupSnakBar(View view, UserMessageModel userMessageModel) {
        return Snackbar.make(view, userMessageModel.getMessage(), Snackbar.LENGTH_LONG);
    }

    private void setupSnackText() {
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
    }

    @Override
    public void showMessageToUser(BasePresenter presenter, UserMessageModel userMessageModel, MessageListener messageListener) {
        if(presenter.checkNull()){
            snackbar = setupSnakBar(presenter.getView(), userMessageModel);
            setupSnackText();
            snackbar.show();
        }
    }
}
