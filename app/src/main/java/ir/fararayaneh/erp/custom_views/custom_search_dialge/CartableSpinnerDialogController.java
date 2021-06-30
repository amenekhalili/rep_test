package ir.fararayaneh.erp.custom_views.custom_search_dialge;

import android.app.Activity;
import io.realm.RealmModel;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDBDialogController;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDialogDBAdapter;
import ir.fararayaneh.erp.adaptors.spinner_dialog_adaptor.CartableSpinnerDialogeAdaptor;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CartableTable;

public class CartableSpinnerDialogController extends BaseSpinnerDBDialogController {

    public CartableSpinnerDialogController(Activity activity, String dialogTitle) {
        super(activity, dialogTitle);
    }

    public CartableSpinnerDialogController(Activity activity, String dialogTitle, String closeTitle) {
        super(activity, dialogTitle, closeTitle);
    }

    public CartableSpinnerDialogController(Activity activity, String dialogTitle, int style) {
        super(activity, dialogTitle, style);
    }

    public CartableSpinnerDialogController(Activity activity, String dialogTitle, int style, String closeTitle) {
        super(activity, dialogTitle, style, closeTitle);
    }

    @Override
    protected BaseSpinnerDialogDBAdapter getProperNewAdaptor() {
        return new CartableSpinnerDialogeAdaptor(data);
    }

    @Override
    protected RealmModel getProperRealmModelRow(int positionOfRealmData,int intPositionOfRow) {
        if(data!=null) {
            return getProperNewAdaptor().getCorrectItem(positionOfRealmData,intPositionOfRow);
        }
        return null;
    }

    @Override
    protected BaseSpinnerDialogDBAdapter setupCostumeSearchData(String searchValue) {
        if (searchValue.equals(Commons.SPACE)) {
            data = getSpinnerDialogQueriesInstance().spinnerCartableMainQuery(getRealmInstance());
            return getProperAdaptorWithData();
        } else {
            data = getSpinnerDialogQueriesInstance().spinnerCartableSearchQuery(getRealmInstance(),searchValue);
            return getProperAdaptorWithData();
        }
    }


}
