package ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.search_spinner_dialog;

import androidx.annotation.Nullable;

import io.realm.RealmModel;
import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface IsearchDialogeListener extends IListeners {
    void onSelect(@Nullable RealmModel realmModel,@Nullable String item, int position);
}
