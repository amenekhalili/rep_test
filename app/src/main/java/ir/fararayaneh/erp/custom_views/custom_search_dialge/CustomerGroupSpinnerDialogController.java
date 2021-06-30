package ir.fararayaneh.erp.custom_views.custom_search_dialge;

import android.app.Activity;

import io.realm.RealmModel;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDBDialogController;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDialogDBAdapter;
import ir.fararayaneh.erp.adaptors.spinner_dialog_adaptor.CustomerGroupSpinnerDialogeAdaptor;
import ir.fararayaneh.erp.commons.Commons;

public class CustomerGroupSpinnerDialogController extends BaseSpinnerDBDialogController {

    private String b5IdRefId;


    public CustomerGroupSpinnerDialogController(Activity activity, String dialogTitle,String b5IdRefId) {
        super(activity, dialogTitle);
        this.b5IdRefId = b5IdRefId;
    }

    public CustomerGroupSpinnerDialogController(Activity activity, String dialogTitle, String closeTitle,String b5IdRefId) {
        super(activity, dialogTitle, closeTitle);
        this.b5IdRefId = b5IdRefId;
    }

    public CustomerGroupSpinnerDialogController(Activity activity, String dialogTitle, int style,String b5IdRefId) {
        super(activity, dialogTitle, style);
        this.b5IdRefId = b5IdRefId;
    }

    public CustomerGroupSpinnerDialogController(Activity activity, String dialogTitle, int style, String closeTitle,String b5IdRefId) {
        super(activity, dialogTitle, style, closeTitle);
        this.b5IdRefId = b5IdRefId;
    }

    @Override
    protected BaseSpinnerDialogDBAdapter getProperNewAdaptor() {
        return new CustomerGroupSpinnerDialogeAdaptor(data);
    }

    @Override
    protected RealmModel getProperRealmModelRow(int positionOfRealmData, int intPositionOfRow) {
        if(data!=null) {
            return getProperNewAdaptor().getCorrectItem(positionOfRealmData,intPositionOfRow);
        }
        return null;
    }

    @Override
    protected BaseSpinnerDialogDBAdapter setupCostumeSearchData(String searchValue) {
        if (searchValue.equals(Commons.SPACE)) {
            data = getSpinnerDialogQueriesInstance().spinnerCustomerGroupMainQuery(getRealmInstance(),b5IdRefId);
            return getProperAdaptorWithData();
        } else {
            data = getSpinnerDialogQueriesInstance().spinnerCustomerGroupSearchQuery(getRealmInstance(),b5IdRefId,searchValue);
            return getProperAdaptorWithData();
        }
    }

}
