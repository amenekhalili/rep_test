package ir.fararayaneh.erp.activities.weighing;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.Disposable;
import io.realm.RealmModel;
import ir.fararayaneh.erp.IBase.common_base.form_base.BasePresenterCollectionDataForm;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonComboClickType;
import ir.fararayaneh.erp.commons.CommonComboFormType;
import ir.fararayaneh.erp.commons.CommonTimeZone;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsFormCode;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.weighing.GetAllDataFromOneColumnWeighingQuery;
import ir.fararayaneh.erp.data.data_providers.queries.weighing.GetOneRowByIdWeighingQuery;
import ir.fararayaneh.erp.data.data_providers.queries.weighing.SetValueWeighingQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.middle.ComboFormModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WeighingTable;
import ir.fararayaneh.erp.utils.EditTextCheckHelper;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;

/**
 * formCode WEIGHTING_FORM_CODE = 60 :
 * در جدول
 * formRef
 * این فرم دارای هیچ کانفیگی نیست
 * و همه دیتا هارا از جدول
 * WeighingTable
 * خوانده میشود
 * <p>
 * این پرزنتر حاوی دو اسپینر
 * پلاک ماشین
 * و شماره حواله*
 * است
 */
public class WeighingPresenter extends BasePresenterCollectionDataForm<WeighingView> {

    //---------------------------------------------------------
    private String mainNumber = Commons.NULL_INTEGER;
    private ArrayList<String> idList;//>> list of id of data that be showed in spinner
    //---------------------------------------------------------

    public WeighingPresenter(WeakReference<WeighingView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }

    /**
     * only for set values that no need to database because get from db call after this method
     */
    @Override
    protected void setDefaultValue() {
        //do nothings
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
        //todo
    }

    @Override
    protected void ClearAllPresenterListener() {
        super.ClearAllPresenterListener();
    }

    //---------------------------------------------------------------------------------------------
    @Override
    protected void setupCustomLayoutManagerFactory() {
        if (checkNull()) {
            ArrayList<Integer> listOfPosition = new ArrayList<>();
            switch (myFormCode) {
                case CommonsFormCode.WEIGHTING_FORM_CODE:
                    listOfPosition.add(3);
                    listOfPosition.add(4);
                    listOfPosition.add(5);
                    listOfPosition.add(6);
                    listOfPosition.add(7);
                    listOfPosition.add(8);
                    listOfPosition.add(9);
                    listOfPosition.add(10);
                    getView().setupCustomLayoutManager(2, 1, listOfPosition);
                    break;
            }
        }
    }

    @Override
    protected void witchWorkForShowForm() {
        switch (myFormCode) {
            case CommonsFormCode.WEIGHTING_FORM_CODE:
                if (!primitiveGUID.equals(Commons.NULL)) {
                    //because guid is string of id :
                    id = primitiveGUID;
                    getOneRowById(primitiveGUID);
                } else {
                    showFormWeighing();
                }
                break;
        }
    }


    @Override
    protected void clickRowRecycleFactoryMethod(int comboFormClickType, String someValue) {
        switch (myFormCode) {
            case CommonsFormCode.WEIGHTING_FORM_CODE:
                clickRowWeighingForm(comboFormClickType, someValue);
                break;
        }
    }

    /**
     * dar inja bar khalaf baghie form ha data spinner ,
     * bayad az database queri shavad
     */
    @Override
    protected void setDataFromSearchAbleSpinner(RealmModel realmModel, String item, int position, int commonComboClickType) {
        if (checkNull()) {
            //here we do not set data to spinner, set them after (getOneRowById()) method
            //comboFormRecAdaptor.updateRowValue(commonComboClickType, new ComboFormModel(Commons.NULL, item, Commons.NULL, false));
            switch (commonComboClickType) {
                case CommonComboClickType.PLAQUE_CLICK_TYPE:
                case CommonComboClickType.GOOD_TRANS_NUMBER_CLICK_TYPE:
                    addDisposable(getOneRowById(idList.get(position)));
                    break;
            }
        }
    }


    //we do not come here, we have not confirm click
    @Override
    protected void checkData() {

    }

    @Override
    protected void workNextClick() {
        switch (myFormCode) {
            case CommonsFormCode.WEIGHTING_FORM_CODE:
                nextClickWeighing();
                break;
        }
    }

    //we do not come here, we have not delete click
    @Override
    protected void workDeleteClick() {

    }

    //---------------------------------------------------------------------------
    private void showFormWeighing() {
        if (checkNull()) {
            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToWeighingRec(), setProperClickRowTypeToWeighingRec(), setProperDataToWeighingRec());
            getView().showHideProgress(false);
            getView().showHideConslayWeighing(true);
        }
    }

    //for when we need set data
    private void showFormWeighing(WeighingTable weighingTable) {
        if (checkNull()) {
            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToWeighingRec(), setProperClickRowTypeToWeighingRec(), setProperDataToWeighingRec(weighingTable));
            getView().showHideProgress(false);
            getView().showHideConslayWeighing(true);
        }
    }

    private List<Integer> setProperRowTypeToWeighingRec() {
        ArrayList<Integer> comboFormType = new ArrayList<>();
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_15);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_14);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);
        return comboFormType;
    }

    private ArrayList<Integer> setProperClickRowTypeToWeighingRec() {
        ArrayList<Integer> comboFormClickType = new ArrayList<>();
        comboFormClickType.add(CommonComboClickType.PLAQUE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.GOOD_TRANS_NUMBER_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.MAIN_NUMBER_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_2);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_3);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_4);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_5);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_6);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_7);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE_8);
        return comboFormClickType;
    }

    private List<ComboFormModel> setProperDataToWeighingRec() {

        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel("", "", "", false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_trans_number), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.main_number), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel("", context.getString(R.string.good_trans_date), "", false));
        comboFormModels.add(new ComboFormModel("", context.getString(R.string.driver_name), "", false));
        comboFormModels.add(new ComboFormModel("", context.getString(R.string.empty_weight), "", false));
        comboFormModels.add(new ComboFormModel("", context.getString(R.string.buyer_name), "", false));
        comboFormModels.add(new ComboFormModel("", context.getString(R.string.good_trans_weight), "", false));
        comboFormModels.add(new ComboFormModel("", context.getString(R.string.sum_weight), "", false));
        comboFormModels.add(new ComboFormModel("", context.getString(R.string.balance_weight), "", false));
        comboFormModels.add(new ComboFormModel("", context.getString(R.string.empty_date), "", false));

        return comboFormModels;

    }

    private List<ComboFormModel> setProperDataToWeighingRec(WeighingTable weighingTable) {
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();

        comboFormModels.add(new ComboFormModel("", weighingTable.getCarPlaqueNumber(), "", false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.good_trans_number), weighingTable.getGoodTranseNumber(), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.main_number), weighingTable.getGoodTranseAmount(), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel("", EditTextCheckHelper.concatHandler(context.getString(R.string.good_trans_date), CustomTimeHelper.getPresentableDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(weighingTable.getGoodTranseDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), getView().getActivity())), "", false));
        comboFormModels.add(new ComboFormModel("", EditTextCheckHelper.concatHandler(context.getString(R.string.driver_name), weighingTable.getDriverName()), "", false));
        comboFormModels.add(new ComboFormModel("", EditTextCheckHelper.concatHandler(context.getString(R.string.empty_weight), weighingTable.getEmptyWeight()), "", false));
        comboFormModels.add(new ComboFormModel("", EditTextCheckHelper.concatHandler(context.getString(R.string.buyer_name), weighingTable.getBuyerName()), "", false));
        comboFormModels.add(new ComboFormModel("", EditTextCheckHelper.concatHandler(context.getString(R.string.good_trans_weight), weighingTable.getGoodTranseWeight()), "", false));
        comboFormModels.add(new ComboFormModel("", EditTextCheckHelper.concatHandler(context.getString(R.string.sum_weight), weighingTable.getSumWeight()), "", false));
        comboFormModels.add(new ComboFormModel("", EditTextCheckHelper.concatHandler(context.getString(R.string.balance_weight), weighingTable.getBalance()), "", false));
        comboFormModels.add(new ComboFormModel("", EditTextCheckHelper.concatHandler(context.getString(R.string.empty_date), CustomTimeHelper.getPresentableDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(weighingTable.getEmptyDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE), getView().getActivity())), "", false));

        return comboFormModels;
    }

    private void clickRowWeighingForm(int comboFormClickType, String someValue) {

        switch (comboFormClickType) {
            case CommonComboClickType.PLAQUE_CLICK_TYPE:
                addDisposable(getAllOneColumnWeighing(CommonColumnName.CARPLAQUENUMBER));
                break;
            case CommonComboClickType.GOOD_TRANS_NUMBER_CLICK_TYPE:
                addDisposable(getAllOneColumnWeighing(CommonColumnName.GOODTRANSENUMBER));
                break;
            case CommonComboClickType.MAIN_NUMBER_CLICK_TYPE:
                //no need to update,  dataList were updated in holder
                mainNumber = someValue;
               /* if (EditTextCheckHelper.isOnlyDigit(someValue)) {
                    mainNumber = Integer.valueOf(someValue);
                    Log.i("arash", "clickRowWeighingForm: 0000000000");
                } else {
                    mainNumber = Integer.valueOf(Commons.NULL_INTEGER) ;
                    Log.i("arash", "clickRowWeighingForm: 11111111111");

                }*/
                break;
        }
    }

    private Disposable getAllOneColumnWeighing(String columnName) {
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(columnName);
        GetAllDataFromOneColumnWeighingQuery getAllDataFromOneColumnWeighingQuery = (GetAllDataFromOneColumnWeighingQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GetAllDataFromOneColumnWeighingQuery);
        assert getAllDataFromOneColumnWeighingQuery != null;
        return getAllDataFromOneColumnWeighingQuery.data(globalModel).subscribe(iModels -> setProperDataToArrayListsForSearchSpinners(columnName, globalModel.getStringArrayList(), globalModel.getStringArrayList2()), throwable -> {
            if (checkNull()) {
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_55);
            }
        });
    }

    private void setProperDataToArrayListsForSearchSpinners(String columnName, ArrayList<String> stringArrayList, ArrayList<String> intArrayList) {
        idList = intArrayList;
        switch (columnName) {
            case CommonColumnName.CARPLAQUENUMBER:
                showSearchAbleSpinner(stringArrayList, CommonComboClickType.PLAQUE_CLICK_TYPE);
                break;
            case CommonColumnName.GOODTRANSENUMBER:
                showSearchAbleSpinner(stringArrayList, CommonComboClickType.GOOD_TRANS_NUMBER_CLICK_TYPE);
                break;
        }
    }

    private Disposable getOneRowById(String id) {

        if (checkNull()) {
            getView().showHideProgress(true);
        }

        GetOneRowByIdWeighingQuery getOneRowByIdWeighingQuery = (GetOneRowByIdWeighingQuery)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_ONE_ROW_BY_ID_WEIGHING_QUERY);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(id);
        assert getOneRowByIdWeighingQuery != null;
        return getOneRowByIdWeighingQuery.data(globalModel).subscribe(iModels -> {
            if (checkNull()) {
                getView().showHideProgress(false);
            }
            WeighingTable weighingTable = (WeighingTable) iModels;
            if (weighingTable.getId().equals(Commons.NULL_INTEGER)) {
                setAllValues(null);
            } else {
                setAllValues(weighingTable);
            }
        }, throwable -> {
            if (checkNull()) {
                getView().showHideProgress(false);
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_56);
                setAllValues(null);
            }
        });

    }

    private void setAllValues(WeighingTable weighingTable) {
        if (weighingTable == null) {
            id = Commons.NULL_INTEGER;;
            mainNumber = Commons.NULL_INTEGER;
            showFormWeighing();
        } else {
            id = weighingTable.getId();
            mainNumber = weighingTable.getGoodTranseAmount();
            showFormWeighing(weighingTable);
        }
    }

    private void nextClickWeighing() {
        if (id .equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showMessageNothingsSelected();
            }
        } else if (mainNumber.equals(Commons.SPACE)) {
            getView().showMessageMainValueNotSelected();
        } else {
            addDisposable(setValueWeighing());
        }
    }

    private Disposable setValueWeighing() {

        SetValueWeighingQuery setValueWeighingQuery = (SetValueWeighingQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SET_VALUE_WEIGHING_QUERY);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(mainNumber);
        globalModel.setMyString2(id);
        assert setValueWeighingQuery != null;
        return setValueWeighingQuery.data(globalModel).subscribe(iModels -> {
            WeighingTable weighingTable = (WeighingTable) iModels;
            if (!weighingTable.getId().equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showMessageSaveDataSuccess();
                }
                SendPacket.sendEncryptionMessage(context, SocketJsonMaker.weighingSocket(SharedPreferenceProvider.getOrganization(context), SharedPreferenceProvider.getUserId(context), weighingTable), false);
            } else {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_57);
                }
            }
            setAllValues(null);
        }, throwable -> {
            if (checkNull()) {
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_58);
                setAllValues(null);
            }
        });
    }

}
