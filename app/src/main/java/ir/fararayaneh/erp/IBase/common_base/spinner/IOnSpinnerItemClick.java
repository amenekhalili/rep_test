package ir.fararayaneh.erp.IBase.common_base.spinner;

import io.realm.RealmModel;

public interface IOnSpinnerItemClick {
     void onClick(RealmModel realmModel, String item, int position);
}
