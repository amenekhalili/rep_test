package ir.fararayaneh.erp.custom_views.custom_search_dialge;

import android.app.Activity;

import io.realm.RealmModel;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDBDialogController;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDialogDBAdapter;
import ir.fararayaneh.erp.adaptors.spinner_dialog_adaptor.OneConfigSpinnerDialogeAdaptor;
import ir.fararayaneh.erp.commons.Commons;

/**
 * for get first config of every formCode in baseCoding
 */
public class OneConfigSpinnerDialogController extends BaseSpinnerDBDialogController {

    private String formCode;
    private int positionOfConfig;

    public OneConfigSpinnerDialogController(Activity activity, String dialogTitle, String formCode,int positionOfConfig) {
        super(activity, dialogTitle);
        this.formCode = formCode;
        this.positionOfConfig = positionOfConfig;
    }

    public OneConfigSpinnerDialogController(Activity activity, String dialogTitle, String closeTitle, String formCode,int positionOfConfig) {
        super(activity, dialogTitle, closeTitle);
        this.formCode = formCode;
        this.positionOfConfig = positionOfConfig;

    }

    public OneConfigSpinnerDialogController(Activity activity, String dialogTitle, int style, String formCode,int positionOfConfig) {
        super(activity, dialogTitle, style);
        this.formCode = formCode;
        this.positionOfConfig = positionOfConfig;


    }

    public OneConfigSpinnerDialogController(Activity activity, String dialogTitle, int style, String closeTitle, String formCode,int positionOfConfig) {
        super(activity, dialogTitle, style, closeTitle);
        this.formCode = formCode;
        this.positionOfConfig = positionOfConfig;

    }

    @Override
    protected BaseSpinnerDialogDBAdapter getProperNewAdaptor() {
        return new OneConfigSpinnerDialogeAdaptor(data);
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
            switch (positionOfConfig){
                case 1:
                    data = getSpinnerDialogQueriesInstance().spinnerGetFirstConfigQuery(getRealmInstance(),formCode);
                    break;
                case 2:
                    data = getSpinnerDialogQueriesInstance().spinnerGetSecondConfigQuery(getRealmInstance(),formCode);
                    break;
                case 3:
                    data = getSpinnerDialogQueriesInstance().spinnerGetThirdConfigQuery(getRealmInstance(),formCode);
                    break;
                case 4:
                    data = getSpinnerDialogQueriesInstance().spinnerGetForthConfigQuery(getRealmInstance(),formCode);
                    break;
                case 5:
                    data = getSpinnerDialogQueriesInstance().spinnerGetFifthConfigQuery(getRealmInstance(),formCode);
                    break;
                case 6:
                    data = getSpinnerDialogQueriesInstance().spinnerGetSixthConfigQuery(getRealmInstance(),formCode);
                    break;
                case 7:
                    data = getSpinnerDialogQueriesInstance().spinnerGetSeventhConfigQuery(getRealmInstance(),formCode);
                    break;
                case 8:
                    data = getSpinnerDialogQueriesInstance().spinnerGetEighthConfigQuery(getRealmInstance(),formCode);
                    break;
                case 9:
                    data = getSpinnerDialogQueriesInstance().spinnerGetNinthConfigQuery(getRealmInstance(),formCode);
                    break;
                case 10:
                    data = getSpinnerDialogQueriesInstance().spinnerGetTenthConfigQuery(getRealmInstance(),formCode);
                    break;
                case 11:
                    data = getSpinnerDialogQueriesInstance().spinnerGetEleventhConfigQuery(getRealmInstance(),formCode);
                    break;
                case 12:
                    data = getSpinnerDialogQueriesInstance().spinnerGetTwelfthConfigQuery(getRealmInstance(),formCode);
                    break;
                case 13:
                    data = getSpinnerDialogQueriesInstance().spinnerGetThirteenthConfigQuery(getRealmInstance(),formCode);
                    break;
                case 14:
                    data = getSpinnerDialogQueriesInstance().spinnerGetFourteenthConfigQuery(getRealmInstance(),formCode);
                    break;
                case 15:
                    data = getSpinnerDialogQueriesInstance().spinnerGetFifteenthConfigQuery(getRealmInstance(),formCode);
                    break;
            }

        } else {
            switch (positionOfConfig){
                case 1:
                    data = getSpinnerDialogQueriesInstance().spinnerGetFirstConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 2:
                    data = getSpinnerDialogQueriesInstance().spinnerGetSecondConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 3:
                    data = getSpinnerDialogQueriesInstance().spinnerGetThirdConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 4:
                    data = getSpinnerDialogQueriesInstance().spinnerGetForthConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 5:
                    data = getSpinnerDialogQueriesInstance().spinnerGetFifthConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 6:
                    data = getSpinnerDialogQueriesInstance().spinnerGetSixthConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 7:
                    data = getSpinnerDialogQueriesInstance().spinnerGetSeventhConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 8:
                    data = getSpinnerDialogQueriesInstance().spinnerGetEighthConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 9:
                    data = getSpinnerDialogQueriesInstance().spinnerGetNinthConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 10:
                    data = getSpinnerDialogQueriesInstance().spinnerGetTenthConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 11:
                    data = getSpinnerDialogQueriesInstance().spinnerGetEleventhConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 12:
                    data = getSpinnerDialogQueriesInstance().spinnerGetTwelfthConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 13:
                    data = getSpinnerDialogQueriesInstance().spinnerGetThirteenthConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 14:
                    data = getSpinnerDialogQueriesInstance().spinnerGetFourteenthConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
                case 15:
                    data = getSpinnerDialogQueriesInstance().spinnerGetFifteenthConfigSearchQuery(getRealmInstance(),formCode,searchValue);
                    break;
            }
        }
        return getProperAdaptorWithData();
    }


}

