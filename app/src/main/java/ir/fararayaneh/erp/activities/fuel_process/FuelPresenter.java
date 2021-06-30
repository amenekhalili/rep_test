package ir.fararayaneh.erp.activities.fuel_process;

import android.content.Context;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.realm.RealmModel;
import ir.fararayaneh.erp.IBase.common_base.form_base.BasePresenterCollectionDataForm;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonComboClickType;
import ir.fararayaneh.erp.commons.CommonComboFormType;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.CommonTimeZone;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsFormCode;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.fuel_list_detail.InsertUpdateFuelListTableQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.middle.ComboFormModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListDetailTable;
import ir.fararayaneh.erp.utils.CalculationHelper;
import ir.fararayaneh.erp.utils.data_handler.GetOneRowByGUIDHandler;
import ir.fararayaneh.erp.utils.data_handler.IGetRowByGUIDQueryListener;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;


/**
 *  we have not edit here, but primitive data were be loaded from table in coming to activity
 *  یوزر دوبار به اینجا نمی اید بنابراین
 *  مقادیر به دست امده از جداول در فرم نمایش داده نمیشود چون حتما پیش از این چیزی توسط کسی ست نشده است
 *
 */

public class FuelPresenter extends BasePresenterCollectionDataForm<FuelView> {

    public FuelPresenter(WeakReference<FuelView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }


    //----------------------------------------------------------------------------------------------
    private String B5HCDistributionId,amount,description,deviceName,masterId
    ,placeName;
    //----------------------------------------------------------------------------------------------

    @Override
    protected void setDefaultValue() {
        //here ne need to defualt value, all data were be load from table

    }

    @Override
    protected UserMessageModel makeProperShowDialogueData(int dialogueType, int commonComboClickType, String someValue) {
        return null;
    }

    @Override
    protected void workForListenToMSGOrDialoge(int whichDialog, int whichClick, IModels iModels) {

    }

    @Override
    protected void updateUIWhenReceivedAttachment() {

    }

    @Override
    protected void workForSocketListener(ISocketModel iSocketModel) {

    }

    @Override
    protected void ClearAllPresenterListener() {
        super.ClearAllPresenterListener();
    }

    @Override
    protected void setupCustomLayoutManagerFactory() {
        if (checkNull()) {
            ArrayList<Integer> listOfPosition = new ArrayList<>();
            switch (myFormCode) {
                case CommonsFormCode.FUEL_DETAIL_FORM_CODE:
                   // listOfPosition.add(0);
                    //listOfPosition.add(1);
                   // listOfPosition.add(2);
                    getView().setupCustomLayoutManager(2, 1, listOfPosition);
                    break;
            }
        }
    }

    @Override
    protected void witchWorkForShowForm() {
        switch (myFormCode) {
            case CommonsFormCode.FUEL_DETAIL_FORM_CODE:
                //we sure we should primitive data
                showAndSetPrimitiveData();
                break;
        }
    }


    @Override
    protected void clickRowRecycleFactoryMethod(int comboFormClickType, String someValue) {
        switch (myFormCode) {
            case CommonsFormCode.FUEL_DETAIL_FORM_CODE:
                clickRowDetailForm(comboFormClickType, someValue);
                break;
        }
    }



    @Override
    protected void setDataFromSearchAbleSpinner(@Nullable RealmModel realmModel, @Nullable String item, int position, int commonComboClickType) {
        comboFormRecAdaptor.updateRowValue(commonComboClickType, new ComboFormModel(Commons.NULL, item, Commons.NULL, false), true);
        switch (commonComboClickType) {
            case CommonComboClickType.SERVICE_CLICK_TYPE:
                B5HCDistributionId = myListUtilIdList.get(0).get(position);
                break;
                    }
    }

    @Override
    protected void checkData() {
        switch (myFormCode) {
            case CommonsFormCode.FUEL_DETAIL_FORM_CODE:
                checkDataFuel();
                break;
        }
    }

    @Override
    protected void workNextClick() {
            //no need here
    }

    @Override
    protected void workDeleteClick() {
            //no need here
    }

    //----------------------------------------------------------------------------------------------
    private void showFormFuel() {
        if (checkNull()) {
                //we have not edit mode, we have only edit mode
                comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToFuelRec(), setProperClickRowTypeToFuelRec(), setProperDataToFuelRec());
                getView().showHideProgress(false);
                getView().showHideConslayFuel(true);
        }
    }

    private List<Integer> setProperRowTypeToFuelRec() {
        ArrayList<Integer> comboFormType = new ArrayList<>();
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_21);


        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToFuelRec() {
        ArrayList<Integer> comboFormClickType = new ArrayList<>();
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_2);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
        comboFormClickType.add(CommonComboClickType.SERVICE_CLICK_TYPE);//select distribution type
        comboFormClickType.add(CommonComboClickType.MAIN_NUMBER_CLICK_TYPE);

        return comboFormClickType;
    }

    private List<ComboFormModel> setProperDataToFuelRec() {
        // we do not come here twice
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3,context.getString(R.string.device_name) , deviceName), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, String.format(CommonsFormat.FORMAT_3,context.getString(R.string.location) , placeName), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, description, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.distribut_type), Commons.NULL, Commons.NULL, false));//user should set that
        comboFormModels.add(new ComboFormModel(context.getString(R.string.delivered_fuel), Commons.NULL, Commons.NULL, false));//user should set that
        return comboFormModels;
    }

    private void showAndSetPrimitiveData() {
        iGetRowByGUIDQueryListener = new IGetRowByGUIDQueryListener() {
            @Override
            public void onGetRow(IModels iModels) {
                workForShowAndSetPrimitiveData((FuelListDetailTable) iModels);
            }
            @Override
            public void onError() {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_130);
                }
            }
        };
        addDisposable(GetOneRowByGUIDHandler.getRowFuelDetail(primitiveGUID, FuelListDetailTable.class,true, iGetRowByGUIDQueryListener));
    }

    private void workForShowAndSetPrimitiveData(FuelListDetailTable table) {
        //geoLocation from base were be gotten
        primitiveGUID = table.getId();
        B5HCDistributionId = table.getB5HCDistributionId();
        amount = table.getAmount();
        description=table.getDescription();
        deviceName =table.getDeviceName();
        placeName =table.getPlaceName();
        masterId =table.getMasterId();
        oldValue=table.getOldValue();
        showFormFuel();
    }

    private void clickRowDetailForm(int comboFormClickType, String someValue) {
        switch (comboFormClickType) {
            case CommonComboClickType.SERVICE_CLICK_TYPE:
                showSearchAbleSpinner(myListUtilNameList.get(0), CommonComboClickType.SERVICE_CLICK_TYPE);
                break;
            case CommonComboClickType.MAIN_NUMBER_CLICK_TYPE:
                //no need to update,  dataList were updated in holder
                amount = someValue;
                break;
        }
    }

    private void checkDataFuel() {

        if (!CalculationHelper.numberValidation(amount)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.MAIN_NUMBER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.delivered_fuel), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                getView().showMessageMainValueNotSelected();
                return;
            }
        }

        if (B5HCDistributionId.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SERVICE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.distribut_type), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                return;
            }
        }

        addDisposable(saveDataFuel());


    }

    private Disposable saveDataFuel() {
        FuelListDetailTable fuelListDetailTable = new FuelListDetailTable(primitiveGUID,B5HCDistributionId,placeName,deviceName,
                CustomTimeHelper.longLocalDateToOtherLocalConverter(CommonTimeZone.SERVER_TIME_ZONE, CustomTimeHelper.getCurrentDate().getTime(), CommonsFormat.DATE_SHARE_FORMAT_FROM_STRING)
                ,amount,description,masterId,geoLocation,CommonSyncState.BEFORE_SYNC,CommonActivityState.UPDATE,oldValue);
        InsertUpdateFuelListTableQuery insertUpdateFuelListTableQuery = (InsertUpdateFuelListTableQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_FUEL_LIST_TABLE_QUERY);
        assert insertUpdateFuelListTableQuery != null;
        return insertUpdateFuelListTableQuery.data(fuelListDetailTable).subscribe(iModels -> {
            if (checkNull()) {
                sendSocketAndGoBAck(fuelListDetailTable);
            }
        }, throwable -> {
            if (checkNull()) {
                getView().showHideProgress(false);
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_131);
            }
        });
    }

    private void sendSocketAndGoBAck(FuelListDetailTable fuelListDetailTable) {
        SendPacket.sendEncryptionMessage(context, SocketJsonMaker.fuelListSocket(SharedPreferenceProvider.getOrganization(context), SharedPreferenceProvider.getUserId(context), fuelListDetailTable),false);
        ActivityIntents.resultOkFromFuel(FuelPresenter.this.getView().getActivity());
    }
}
