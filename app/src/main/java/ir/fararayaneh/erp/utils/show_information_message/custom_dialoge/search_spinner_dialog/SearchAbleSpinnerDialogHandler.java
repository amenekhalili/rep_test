package ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.search_spinner_dialog;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;


import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.IBase.common_base.spinner.BaseSpinnerDBDialogController;
import ir.fararayaneh.erp.custom_views.custom_search_dialge.AddressListSpinnerDialogController;
import ir.fararayaneh.erp.custom_views.custom_search_dialge.CartableSpinnerDialogController;
import ir.fararayaneh.erp.custom_views.custom_search_dialge.CustomerGroupSpinnerDialogController;
import ir.fararayaneh.erp.custom_views.custom_search_dialge.GoodsSpinnerDialogController;
import ir.fararayaneh.erp.custom_views.custom_search_dialge.OneConfigSpinnerDialogController;
import ir.fararayaneh.erp.custom_views.custom_search_dialge.OneUtilCodeConfigSpinnerDialogController;
import ir.fararayaneh.erp.custom_views.custom_search_dialge.SpinnerDialogWithArrayListController;


/**
 * caution caution caution : realmModel or item maybe null
 */
public class SearchAbleSpinnerDialogHandler {

    public static void show(Activity activity, ArrayList<String> showList, IsearchDialogeListener listener) {

        SpinnerDialogWithArrayListController spinnerDialogWithArrayListController = new SpinnerDialogWithArrayListController(activity, showList,  Commons.SPACE, R.style.DialogAnimations, Commons.SPACE);
        spinnerDialogWithArrayListController.setCancellable(true);
        spinnerDialogWithArrayListController.setShowKeyboard(true);
        spinnerDialogWithArrayListController.setUseContainsFilter(true);
        spinnerDialogWithArrayListController.bindOnSpinerListener((realmModel, item, position) -> {
            if (listener != null) {
                listener.onSelect(realmModel,item, position);
                Log.i("arash", "show: position"+position+"********"+item);
            }
        });
        spinnerDialogWithArrayListController.showSpinerDialog();
    }

    /**
     * show all rows of user cartable in dialog spinner
     */
    public static void showCartableDBDialog(Activity activity, IsearchDialogeListener listener) {

        BaseSpinnerDBDialogController baseSpinnerDBDialogController =
                new CartableSpinnerDialogController(activity,Commons.SPACE,R.style.DialogAnimations,Commons.SPACE);

        baseSpinnerDBDialogController.setCancellable(true);
        baseSpinnerDBDialogController.setShowKeyboard(true);
        baseSpinnerDBDialogController.bindOnSpinnerListener((realmModel, item, position) -> {
            if (listener != null) {
                listener.onSelect(realmModel,item, position);
            }
        });
        baseSpinnerDBDialogController.showSpinnerDialog();
    }

    /**
     * show all non accessDenied rows of goods in dialog spinner
     * FILTER BY WAREHOUSE NUMBER
     * فقط کالاهایی به یوزر نشان داده مشود که مربوط به شماره انبار مناسب باشد
     */
    public static void showGoodsNameDBDialog(Activity activity, ArrayList<String> filteredGoodidByWarehouseid, IsearchDialogeListener listener) {
        BaseSpinnerDBDialogController baseSpinnerDBDialogController =
                new GoodsSpinnerDialogController(activity,Commons.SPACE,R.style.DialogAnimations,Commons.SPACE,filteredGoodidByWarehouseid);

        baseSpinnerDBDialogController.setCancellable(true);
        baseSpinnerDBDialogController.setShowKeyboard(true);
        baseSpinnerDBDialogController.bindOnSpinnerListener((realmModel, item, position) -> {
            if (listener != null) {
                listener.onSelect(realmModel,item, position);
            }
        });
        baseSpinnerDBDialogController.showSpinnerDialog();
    }

    /**
     * show all non accessDenied rows of goods in dialog spinner
     */
    public static void showCustomerGroupNameDBDialog(Activity activity,String b5IdRefId, IsearchDialogeListener listener) {
        BaseSpinnerDBDialogController baseSpinnerDBDialogController =
                new CustomerGroupSpinnerDialogController(activity,Commons.SPACE,R.style.DialogAnimations,Commons.SPACE,b5IdRefId);

        baseSpinnerDBDialogController.setCancellable(true);
        baseSpinnerDBDialogController.setShowKeyboard(true);
        baseSpinnerDBDialogController.bindOnSpinnerListener((realmModel, item, position) -> {
            if (listener != null) {
                listener.onSelect(realmModel,item, position);
            }
        });
        baseSpinnerDBDialogController.showSpinnerDialog();
    }


    public static void showAddressNameDBDialog(Activity activity,String b5IdRefId, IsearchDialogeListener listener) {
        BaseSpinnerDBDialogController baseSpinnerDBDialogController =
                new AddressListSpinnerDialogController(activity,Commons.SPACE,R.style.DialogAnimations,Commons.SPACE,b5IdRefId);

        baseSpinnerDBDialogController.setCancellable(true);
        baseSpinnerDBDialogController.setShowKeyboard(true);
        baseSpinnerDBDialogController.bindOnSpinnerListener((realmModel, item, position) -> {
            if (listener != null) {
                listener.onSelect(realmModel,item, position);
            }
        });
        baseSpinnerDBDialogController.showSpinnerDialog();
    }

    /**
     * for show one of the config of form form ref in dialog spinner
     */
    public static void showOneConfigDBDialog(Activity activity,String formCode,int positionOfConfig ,IsearchDialogeListener listener) {

        BaseSpinnerDBDialogController baseSpinnerDBDialogController =
                new OneConfigSpinnerDialogController(activity,Commons.SPACE,R.style.DialogAnimations,Commons.SPACE,formCode,positionOfConfig);
        baseSpinnerDBDialogController.setCancellable(true);
        baseSpinnerDBDialogController.setShowKeyboard(true);
        baseSpinnerDBDialogController.bindOnSpinnerListener((realmModel, item, position) -> {
            if (listener != null) {
                listener.onSelect(realmModel, item, position);
            }
        });
        baseSpinnerDBDialogController.showSpinnerDialog();
    }


    /**
     * for show one of the Util code config in dialog spinner
     */
    public static void showOneUtilCodeConfigDBDialog(Activity activity,String utilAbbreviation,IsearchDialogeListener listener) {

        BaseSpinnerDBDialogController baseSpinnerDBDialogController =
                new OneUtilCodeConfigSpinnerDialogController(activity,Commons.SPACE,R.style.DialogAnimations,Commons.SPACE,utilAbbreviation);
        baseSpinnerDBDialogController.setCancellable(true);
        baseSpinnerDBDialogController.setShowKeyboard(true);
        baseSpinnerDBDialogController.bindOnSpinnerListener((realmModel, item, position) -> {
            if (listener != null) {
                listener.onSelect(realmModel,item, position);
            }
        });
        baseSpinnerDBDialogController.showSpinnerDialog();
    }



}
