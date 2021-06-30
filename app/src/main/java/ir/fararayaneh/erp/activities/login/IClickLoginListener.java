package ir.fararayaneh.erp.activities.login;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface IClickLoginListener extends IListeners {
    void loginClick(String userName,String passWord,String organization);
    void SettingClick();
}
