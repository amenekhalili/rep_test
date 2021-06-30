package ir.fararayaneh.erp.custom_views.custom_search_dialge;

import android.app.Activity;

import java.util.ArrayList;

import io.realm.RealmModel;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDBDialogController;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDialogDBAdapter;
import ir.fararayaneh.erp.adaptors.spinner_dialog_adaptor.GoodsSpinnerDialogeAdaptor;
import ir.fararayaneh.erp.commons.Commons;

public class GoodsSpinnerDialogController extends BaseSpinnerDBDialogController {

    private ArrayList<String> filteredGoodidByWarehouseid;
    public GoodsSpinnerDialogController(Activity activity, String dialogTitle, ArrayList<String> filteredGoodidByWarehouseid) {
        super(activity, dialogTitle);
        this.filteredGoodidByWarehouseid = filteredGoodidByWarehouseid;
    }

    public GoodsSpinnerDialogController(Activity activity, String dialogTitle, String closeTitle,ArrayList<String> filteredGoodidByWarehouseid) {
        super(activity, dialogTitle, closeTitle);
        this.filteredGoodidByWarehouseid = filteredGoodidByWarehouseid;

    }

    public GoodsSpinnerDialogController(Activity activity, String dialogTitle, int style,ArrayList<String> filteredGoodidByWarehouseid) {
        super(activity, dialogTitle, style);
        this.filteredGoodidByWarehouseid = filteredGoodidByWarehouseid;

    }

    public GoodsSpinnerDialogController(Activity activity, String dialogTitle, int style, String closeTitle,ArrayList<String> filteredGoodidByWarehouseid) {
        super(activity, dialogTitle, style, closeTitle);
        this.filteredGoodidByWarehouseid = filteredGoodidByWarehouseid;

    }

    @Override
    protected BaseSpinnerDialogDBAdapter getProperNewAdaptor() {
        return new GoodsSpinnerDialogeAdaptor(data);
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
            data = getSpinnerDialogQueriesInstance().spinnerGoodsMainQuery(getRealmInstance(),filteredGoodidByWarehouseid);
            return getProperAdaptorWithData();
        } else {
            data = getSpinnerDialogQueriesInstance().spinnerGoodsSearchQuery(getRealmInstance(), searchValue,filteredGoodidByWarehouseid);
            return getProperAdaptorWithData();
        }
    }

}
