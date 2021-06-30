package ir.fararayaneh.erp.activities.chat_list;

import android.content.Intent;

import androidx.annotation.Nullable;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface IChatListActivityListener extends IListeners {

     void goSyncAct();

     void doSearch(@Nullable Intent data);
}
