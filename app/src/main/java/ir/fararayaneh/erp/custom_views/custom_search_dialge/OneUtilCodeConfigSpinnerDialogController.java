package ir.fararayaneh.erp.custom_views.custom_search_dialge;

import android.app.Activity;

import io.realm.RealmModel;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDBDialogController;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDialogDBAdapter;
import ir.fararayaneh.erp.adaptors.spinner_dialog_adaptor.OneUtilCodeConfigSpinnerDialogeAdaptor;
import ir.fararayaneh.erp.commons.Commons;

public class OneUtilCodeConfigSpinnerDialogController extends BaseSpinnerDBDialogController {

    private String utilCodeAbbreviation;

    public OneUtilCodeConfigSpinnerDialogController(Activity activity, String dialogTitle, String utilCodeAbbreviation) {
        super(activity, dialogTitle);
        this.utilCodeAbbreviation = utilCodeAbbreviation;
    }

    public OneUtilCodeConfigSpinnerDialogController(Activity activity, String dialogTitle, String closeTitle, String utilCodeAbbreviation) {
        super(activity, dialogTitle, closeTitle);
        this.utilCodeAbbreviation = utilCodeAbbreviation;

    }

    public OneUtilCodeConfigSpinnerDialogController(Activity activity, String dialogTitle, int style, String utilCodeAbbreviation) {
        super(activity, dialogTitle, style);
        this.utilCodeAbbreviation = utilCodeAbbreviation;


    }

    public OneUtilCodeConfigSpinnerDialogController(Activity activity, String dialogTitle, int style, String closeTitle, String utilCodeAbbreviation) {
        super(activity, dialogTitle, style, closeTitle);
        this.utilCodeAbbreviation = utilCodeAbbreviation;

    }

    @Override
    protected BaseSpinnerDialogDBAdapter getProperNewAdaptor() {
        return new OneUtilCodeConfigSpinnerDialogeAdaptor(data);
    }

    @Override
    protected RealmModel getProperRealmModelRow(int positionOfRealmData, int intPositionOfRow) {
        if (data != null) {
            return getProperNewAdaptor().getCorrectItem(positionOfRealmData, intPositionOfRow);
        }
        return null;
    }

    @Override
    protected BaseSpinnerDialogDBAdapter setupCostumeSearchData(String searchValue) {
        if (searchValue.equals(Commons.SPACE)) {
            data = getSpinnerDialogQueriesInstance().spinnerGetOneUtilCodeConfigQuery(getRealmInstance(), utilCodeAbbreviation);
        } else {
            data = getSpinnerDialogQueriesInstance().spinnerGetOneUtilCodeConfigSearchQuery(getRealmInstance(), utilCodeAbbreviation,searchValue);
        }
        return getProperAdaptorWithData();
    }



}

