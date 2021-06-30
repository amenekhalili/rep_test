package ir.fararayaneh.erp.activities.time;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
import ir.fararayaneh.erp.data.data_providers.queries.time_queris.InsertUpdateTimeTableQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.middle.ComboFormModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TimeTable;
import ir.fararayaneh.erp.utils.data_handler.GetOneRowByGUIDHandler;
import ir.fararayaneh.erp.utils.data_handler.IGetRowByGUIDQueryListener;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;
import ir.fararayaneh.erp.utils.time_helper.CustomDatePicker;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;
import ir.fararayaneh.erp.utils.time_helper.ISelectDateListener;

import static ir.fararayaneh.erp.commons.CommonsFormCode.DEVICE_TIME_SHEET_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.SHEET_PLAN_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.TIME_SHEET_FORM_CODE;

/**
 * این اکتیویتی از جنس for result است
 * و در صورتی که جواب آن اوکی بود لیستی که از ان به اینجا آمده ایم رفرش میشود
 * در واقع چیزی به مبدا برگردانده نمیشود و فقط تایید صحت عملیات برگردانده میشود
 * <p>-----------------------------------------------------
 * این اکتیویتی برای نمایش سه فرم کد به کار میرود
 * تایم شیت : که ساعت پایان آن نمیتواند بعد از الان باشد
 * شیت پلن : که ساعت پایان آن میتواند بعد از الان باشد و حالت برنامه ریزی دارد ولی بقیه جزییات آن شبیه تایم شیت است
 * دیوایس تام شیت : که فرمی برای استارت و استپ کردن یک تایم شیت پر شده و از پیش آماده است
 * <p>-----------------------------------------------------
 * شماره کمبوباکس های موجود موجود درون فرم همان ترتیب لیست های درون
 * listNameList و listIdList
 * را دارند و از صفر هم شروع میشوند
 * به این ترتیب وقتی لیست سوم مقدار خالی دارد کمبوباکس شماره 3
 * یعنی چهارمین کمبوباکس درون فرم نمایش داده نمیشود
 * و از سوی دیگر برای پیدا کردن ای دی و اسم قابل نمایش در کمبوباکس شماره صفر، از روی صفرمین لیست های موجود در
 * این دو لیست ستفاده میکنیم
 * <p>-----------------------------------------------------
 * برای هر فرم کد هر جا که در اینجا یا در آداپتوریا در بیس پرزنتر
 * todo
 * دارد را کافی است تکمیل نماییم
 * <p>-----------------------------------------------------
 * دقت شود که کانفیگ شماره صفر همیشه مقادیر صفرمین سطر از دو آرایه
 * listNameList و listIdList
 * را به خود اختصاص میدهد اما این نکته که این کانفیگ (کمبوباکس) در کدامین سطر ریسایکل نمایش داده شود متفاوت است
 * -------------------------------------------------------
 */
public class TimePresenter extends BasePresenterCollectionDataForm<TimeView> {

    private static final long MINIMUM_SHEET_PLAN_DURATION = 300000;
    private static final long MAXIMUM_SHEET_PLAN_DURATION = 72000000;
    private ISelectDateListener iSelectDateListener;

    private Date currentDateForStartOrEnd; //--for use in deviceTimeSheet
    //value for save in table--------------------------->>>
    private String B5IdRefId, B5IdRefId2, B5IdRefId3, B5HCDailyScheduleId, B5HCWageTitleId, B5HCStatusId;
    private Date startDate, endDate;
    private String description;
    private int isHappeningAtTheSameTime;
    //value for save in table--------------------------->>>

    TimePresenter(WeakReference<TimeView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }

    /**
     * only for set values that no need to database because get from db call after this method
     */
    @Override
    protected void setDefaultValue() {
        description = Commons.NULL;
        B5IdRefId = Commons.NULL_INTEGER;
        B5IdRefId2 = Commons.NULL_INTEGER;
        B5IdRefId3 = Commons.NULL_INTEGER;
        B5HCDailyScheduleId = Commons.NULL_INTEGER;
        B5HCWageTitleId = Commons.NULL_INTEGER;
        B5HCStatusId = SharedPreferenceProvider.getStatusIdDefualt(context);
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
        iSelectDateListener = null;
    }

    //------------------------------------------------------------------------------------->>
    //region Common work for all form code

    //todo تکمیل باقی مانده فرم کد ها
    @Override
    protected void setupCustomLayoutManagerFactory() {
        if (checkNull()) {
            ArrayList<Integer> listOfPosition = new ArrayList<>();
            switch (myFormCode) {
                case CommonsFormCode.TIME_SHEET_FORM_CODE:
                case CommonsFormCode.SHEET_PLAN_FORM_CODE:
                    listOfPosition.add(0);
                    listOfPosition.add(1);
                    getView().setupCustomLayoutManager(2, 1, listOfPosition);
                    break;
                case CommonsFormCode.DEVICE_TIME_SHEET_FORM_CODE:
                    getView().setupCustomLayoutManager(2, 1, listOfPosition);
                    break;
            }
        }
    }

    //todo تکمیل باقی مانده فرم کد ها
    @Override
    protected void witchWorkForShowForm() {
        switch (myFormCode) {
            case TIME_SHEET_FORM_CODE:
            case SHEET_PLAN_FORM_CODE:
                showFormTimeSheetPlane();
                break;
            case DEVICE_TIME_SHEET_FORM_CODE:
                showFormDeviceTimeSheet();
                break;
        }
    }

    /**
     * کلیک تایم شیت با شیت پلن متفاوت است به دلیل تفاوت در نحوه رفتار تقویم
     */
    //todo تکمیل باقی مانده فرم کد ها
    @Override
    protected void clickRowRecycleFactoryMethod(int comboFormClickType, String someValue) {
        switch (myFormCode) {
            case CommonsFormCode.SHEET_PLAN_FORM_CODE:
                clickRowSheetPlanForm(comboFormClickType, someValue);
                break;
            case CommonsFormCode.TIME_SHEET_FORM_CODE:
                clickRowTimeSheetForm(comboFormClickType, someValue);
                break;
            case CommonsFormCode.DEVICE_TIME_SHEET_FORM_CODE:
                clickRowDeviceTimeSheetForm(comboFormClickType, someValue);
                break;
        }
    }


    //todo تکمیل باقی مانده کلیک های کمبوباکس
    @Override
    protected void setDataFromSearchAbleSpinner(RealmModel realmModel, String item, int position, int commonComboClickType) {
        if (checkNull()) {
            comboFormRecAdaptor.updateRowValue(commonComboClickType, new ComboFormModel(Commons.NULL, item, Commons.NULL, false), true);
            switch (commonComboClickType) {
                case CommonComboClickType.TASK_CLICK_TYPE:
                    B5IdRefId = myListBaseIdList.get(0).get(position);
                    break;
                case CommonComboClickType.SERVICE_CLICK_TYPE:
                    B5IdRefId2 = myListBaseIdList.get(1).get(position);
                    break;
                case CommonComboClickType.PROJECT_COMPONENT_CLICK_TYPE:
                    B5IdRefId3 = myListBaseIdList.get(2).get(position);
                    break;
                case CommonComboClickType.DAILY_SCHEDULE_CLICK_TYPE:
                    B5HCDailyScheduleId = myListUtilIdList.get(0).get(position);
                    break;
                case CommonComboClickType.WAGE_TITLE_CLICK_TYPE:
                    B5HCWageTitleId = myListUtilIdList.get(1).get(position);
                    break;
                /*
                deprecated

                case CommonComboClickType.STATUS_CLICK_TYPE:
                    B5HCStatusId = myListUtilIdList.get(2).get(position);
                    break;*/
            }
        }
    }


    //todo برای بقیه فرم کدها
    @Override
    protected void checkData() {

        switch (myFormCode) {
            case CommonsFormCode.SHEET_PLAN_FORM_CODE:
            case CommonsFormCode.TIME_SHEET_FORM_CODE:
                checkDataTimeSheetPlane();
                break;
            case CommonsFormCode.DEVICE_TIME_SHEET_FORM_CODE:
                checkDataDeviceTimeSheet();
                break;

        }
    }

    @Override
    protected void workNextClick() {
        //we have not next click here
    }

    /**
     * if primitiveGUID = null, this method never call
     */
    @Override
    protected void workDeleteClick() {
        activityState = CommonActivityState.DELETE;
        addDisposable(saveDataTimeSheetPlaneDevice());
    }

    private Disposable saveDataTimeSheetPlaneDevice() {
        //in device time sheet isHappeningAtTheSameTime=0 by default
        //one ofe the start or end date is null, but no problem
        TimeTable timeTable = new TimeTable(id, guid, myFormId, B5IdRefId, B5IdRefId2, B5IdRefId3, B5HCDailyScheduleId, B5HCWageTitleId, isHappeningAtTheSameTime,
                CustomTimeHelper.longLocalDateToOtherLocalConverter(CommonTimeZone.SERVER_TIME_ZONE, CustomTimeHelper.dateToLongConvertor(startDate), CommonsFormat.DATE_SHARE_FORMAT_FROM_STRING),
                CustomTimeHelper.longLocalDateToOtherLocalConverter(CommonTimeZone.SERVER_TIME_ZONE, CustomTimeHelper.dateToLongConvertor(endDate), CommonsFormat.DATE_SHARE_FORMAT_FROM_STRING),
                description, geoLocation, syncState, activityState, oldValue, B5HCStatusId);
        InsertUpdateTimeTableQuery insertUpdateTimeTableQuery = (InsertUpdateTimeTableQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_TIME_TABLE_QUERY);
        assert insertUpdateTimeTableQuery != null;
        return insertUpdateTimeTableQuery.data(timeTable).subscribe(iModels -> {
            if (checkNull()) {
                uploadAttachAndSendSocket(timeTable);
            }
        }, throwable -> {
            if (checkNull()) {
                getView().showHideProgress(false);
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_46);
            }
        });
    }

    /**
     * inja lazem nist ke state sync be onSync tabdil shavad
     * chon agar emkane socket bashad , zamane daryafte pasokh
     * az server kam ast va server satate sync sahih ra ersal mikonad
     * (va hameye filed haye digar ham maghadire sahih migirad)
     * dar nahayat agar pasokh be har dalili be daste user naresad va user dobare
     * ersal namayad
     * db haman pasokh insert kardan ra barmigardanad, harchand ke ghablan ham anra insert karde bashad
     */
    private void uploadAttachAndSendSocket(TimeTable timeTable) {
        SendPacket.sendEncryptionMessage(context, SocketJsonMaker.timeSocket(SharedPreferenceProvider.getOrganization(context), SharedPreferenceProvider.getUserId(context), timeTable), false);
        ActivityIntents.resultOkFromTimeAct(TimePresenter.this.getView().getActivity());

    }

    private void showAndSetPrimitiveData() {
        iGetRowByGUIDQueryListener = new IGetRowByGUIDQueryListener() {
            @Override
            public void onGetRow(IModels iModels) {
                workForShowAndSetPrimitiveData((TimeTable) iModels);
            }

            @Override
            public void onError() {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_47);
                }
            }
        };
        addDisposable(GetOneRowByGUIDHandler.getRowTime(primitiveGUID, TimeTable.class,false, iGetRowByGUIDQueryListener));
    }

    private void workForShowAndSetPrimitiveData(TimeTable timeTable) {
        id = timeTable.getId();//should be set here
        guid = timeTable.getGuid();//should be set here
        myFormId = timeTable.getB5formRefId();//we come here with correct form code but ...
        B5IdRefId = timeTable.getB5IdRefId();
        B5IdRefId2 = timeTable.getB5IdRefId2();
        B5IdRefId3 = timeTable.getB5IdRefId3();
        B5HCDailyScheduleId = timeTable.getB5HCDailyScheduleId();
        B5HCWageTitleId = timeTable.getB5HCWageTitleId();
        isHappeningAtTheSameTime = timeTable.getIsHappeningAtTheSameTime();
        startDate = CustomTimeHelper.stringDateToMyLocalDateConvertor(timeTable.getStartDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE);
        endDate = CustomTimeHelper.stringDateToMyLocalDateConvertor(timeTable.getEndDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE);
        description = timeTable.getDescription();
        geoLocation = timeTable.getGeoLocation();
        syncState = CommonSyncState.BEFORE_SYNC;//we are in edit mode
        activityState = CommonActivityState.UPDATE;//we are in edit mode
        oldValue = timeTable.getOldValue();
        B5HCStatusId = timeTable.getB5HCStatusId();
        if (checkNull()) {
            // todo: 5/21/2019 add other form code
            switch (myFormCode) {
                case TIME_SHEET_FORM_CODE:
                case SHEET_PLAN_FORM_CODE:
                    comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToTimeSheetPlaneRec(), setProperClickRowTypeToTimeSheetPlaneRec(), setProperPrimitiveDataToTimeSheetPlaneRec());
                    break;
                case DEVICE_TIME_SHEET_FORM_CODE:
                    comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToDeviceTimeSheetRec(), setProperClickRowTypeToDeviceTimeSheetRec(), setProperPrimitiveDataToDeviceTimeSheetRec());
                    break;
            }
            getView().showHideProgress(false);
            getView().showHideConslaySheetPlanTime(true);
        }
    }


    //endregion
    //------------------------------------------------------------------------------------->>
    //region work for common TimeSheet and sheet plane  form code

    private void showFormTimeSheetPlane() {
        if (checkNull()) {
            if (!primitiveGUID.equals(Commons.NULL)) {
                showAndSetPrimitiveData();
            } else {
                comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToTimeSheetPlaneRec(), setProperClickRowTypeToTimeSheetPlaneRec(), setProperDataToTimeSheetPlaneRec());
                getView().showHideProgress(false);
                getView().showHideConslaySheetPlanTime(true);
            }
        }
    }

    /**
     * سه متد
     * setProperRowTypeToTimeSheetPlaneRec
     * setProperDataToTimeSheetPlaneRec
     * setProperClickRowTypeToTimeSheetPlaneRec
     * نقش اساسی در ایجاد ریسایکل دارند
     */
    private ArrayList<Integer> setProperRowTypeToTimeSheetPlaneRec() {
        ArrayList<Integer> comboFormType = new ArrayList<>();
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_2);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_2);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_3);
        if (myFormCode.equals(SHEET_PLAN_FORM_CODE)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_5);
        }
        if (checkHaveConfig(1)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        }
        if (checkHaveConfig(2)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        }
        if (checkHaveConfig(3)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        }
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        //comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_4);

        return comboFormType;
    }

    /**
     * سه متد
     * setProperRowTypeToTimeSheetPlaneRec
     * setProperDataToTimeSheetPlaneRec
     * setProperClickRowTypeToTimeSheetPlaneRec
     * نقش اساسی در ایجاد ریسایکل دارند
     */
    private ArrayList<Integer> setProperClickRowTypeToTimeSheetPlaneRec() {

        ArrayList<Integer> comboFormClickType = new ArrayList<>();
        comboFormClickType.add(CommonComboClickType.START_DATE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.END_DATE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE);
        if (myFormCode.equals(SHEET_PLAN_FORM_CODE)) {
            comboFormClickType.add(CommonComboClickType.SWITCH_CLICK_TYPE);
        }
        if (checkHaveConfig(1)) {
            comboFormClickType.add(CommonComboClickType.TASK_CLICK_TYPE);
        }
        if (checkHaveConfig(2)) {
            comboFormClickType.add(CommonComboClickType.SERVICE_CLICK_TYPE);
        }
        if (checkHaveConfig(3)) {
            comboFormClickType.add(CommonComboClickType.PROJECT_COMPONENT_CLICK_TYPE);
        }
        comboFormClickType.add(CommonComboClickType.DAILY_SCHEDULE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.WAGE_TITLE_CLICK_TYPE);
/*
deprecated
        comboFormClickType.add(CommonComboClickType.STATUS_CLICK_TYPE);
*/
        comboFormClickType.add(CommonComboClickType.DESCRIPTION_CLICK_TYPE);
        return comboFormClickType;
    }


    private ArrayList<ComboFormModel> setProperPrimitiveDataToTimeSheetPlaneRec() {

        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();
        if (checkNull()) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.start_date), CustomTimeHelper.getPresentableDate(startDate, getView().getActivity()), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.end_date), CustomTimeHelper.getPresentableDate(endDate, getView().getActivity()), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableWorkDuration(startDate, endDate, getView().getActivity()), Commons.NULL, false));
        }
        if (myFormCode.equals(SHEET_PLAN_FORM_CODE)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.asynchronous), context.getResources().getString(R.string.synchronous), Commons.NULL, isHappeningAtTheSameTime == Commons.SWITCH_IS_ON));
        }
        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.task), B5IdRefId.equals(Commons.NULL_INTEGER) ? Commons.NULL : myListBaseNameList.get(0).get(myListBaseIdList.get(0).indexOf(B5IdRefId)==-1?Commons.ZERO_INT:myListBaseIdList.get(0).indexOf(B5IdRefId)), Commons.NULL, false));
        }
        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.service_type), B5IdRefId2.equals(Commons.NULL_INTEGER) ? Commons.NULL : myListBaseNameList.get(1).get(myListBaseIdList.get(1).indexOf(B5IdRefId2)==-1?Commons.ZERO_INT:myListBaseIdList.get(1).indexOf(B5IdRefId2)), Commons.NULL, false));
        }
        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.project_components), B5IdRefId3.equals(Commons.NULL_INTEGER) ? Commons.NULL : myListBaseNameList.get(2).get(myListBaseIdList.get(2).indexOf(B5IdRefId3)==-1?Commons.ZERO_INT:myListBaseIdList.get(2).indexOf(B5IdRefId3)), Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.daily_schedule), B5HCDailyScheduleId.equals(Commons.NULL_INTEGER) ? Commons.NULL : myListUtilNameList.get(0).get(myListUtilIdList.get(0).indexOf(B5HCDailyScheduleId)==-1?Commons.ZERO_INT:myListUtilIdList.get(0).indexOf(B5HCDailyScheduleId)), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.wage_title), B5HCWageTitleId.equals(Commons.NULL_INTEGER) ? Commons.NULL : myListUtilNameList.get(1).get(myListUtilIdList.get(1).indexOf(B5HCWageTitleId)==-1?Commons.ZERO_INT:myListUtilIdList.get(1).indexOf(B5HCWageTitleId)), Commons.NULL, false));
        /*
        deprecated :
        comboFormModels.add(new ComboFormModel(context.getString(R.string.status), B5HCStatusId.equals(Commons.NULL_INTEGER) ? Commons.NULL : myListUtilNameList.get(2).get(myListUtilIdList.get(2).indexOf(B5HCStatusId)==-1?Commons.ZERO_INT:myListUtilIdList.get(2).indexOf(B5HCStatusId)), Commons.NULL, false));
         */
        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), description, Commons.NULL, false));

        return comboFormModels;
    }

    /**
     * سه متد
     * setProperRowTypeToTimeSheetPlaneRec
     * setProperDataToTimeSheetPlaneRec
     * setProperClickRowTypeToTimeSheetPlaneRec
     * نقش اساسی در ایجاد ریسایکل دارند
     */
    private ArrayList<ComboFormModel> setProperDataToTimeSheetPlaneRec() {

        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();
        comboFormModels.add(new ComboFormModel(context.getString(R.string.start_date), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.end_date), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.work_duration), Commons.NULL, Commons.NULL, false));
        if (myFormCode.equals(SHEET_PLAN_FORM_CODE)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.asynchronous), context.getResources().getString(R.string.synchronous), Commons.NULL, false));
        }
        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.task), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.service_type), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.project_components), Commons.NULL, Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.daily_schedule), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.wage_title), Commons.NULL, Commons.NULL, false));
        //comboFormModels.add(new ComboFormModel(context.getString(R.string.status), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), Commons.NULL, Commons.NULL, false));

        return comboFormModels;
    }


    /**
     * این متد باید در آخر محاسبه زمان شروع و پایان صدا زده شود
     * چون اگر به هر دلیل زمان شروع و پایان به خطا بخورد نال میشود،
     * اگر هر کدام نال بود یعنی باید مقدار این فیلد پاک شود
     */
    private void setProperWorkDuration(Date startDate, Date endDate) {
        if (checkNull()) {
            if (startDate != null && endDate != null) {
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE, new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableWorkDuration(startDate, endDate, getView().getActivity()), Commons.NULL, false), true);
            } else {
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.work_duration), Commons.NULL, Commons.NULL, false), true);
            }
        }
    }


    private void checkDataTimeSheetPlane() {

        if (checkHaveConfig(1)) {
            if (B5IdRefId.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.TASK_CLICK_TYPE, new ComboFormModel(context.getString(R.string.task), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    return;
                }
            }
        }

        if (checkHaveConfig(2)) {
            if (B5IdRefId2.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.SERVICE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.service_type), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    return;
                }
            }
        }

        if (checkHaveConfig(3)) {
            if (B5IdRefId3.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.PROJECT_COMPONENT_CLICK_TYPE, new ComboFormModel(context.getString(R.string.project_components), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    return;
                }
            }
        }

        if (checkHaveUtilConfig(1)) {
            if (B5HCDailyScheduleId.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.DAILY_SCHEDULE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.daily_schedule), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    return;
                }
            }
        }


        if (checkHaveUtilConfig(2)) {
            if (B5HCWageTitleId.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.WAGE_TITLE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.wage_title), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    return;
                }
            }
        }

        if (startDate == null) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.START_DATE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.start_date), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                return;
            }
        }

        if (endDate == null) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.end_date), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                return;
            }
        }

        /*
        deprecated

        if (B5HCStatusId.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.STATUS_CLICK_TYPE, new ComboFormModel(context.getString(R.string.status), Commons.NULL, context.getString(R.string.fill_proper_value), false));
                return;
            }
        }*/

        addDisposable(saveDataTimeSheetPlaneDevice());

    }

    //endregion
    //------------------------------------------------------------------------------------->>
    //region work for only time sheet form code

    private void clickRowTimeSheetForm(int comboFormClickType, String someValue) {

        switch (comboFormClickType) {
            case CommonComboClickType.START_DATE_CLICK_TYPE:
                setStartDateTimeSheet();
                break;
            case CommonComboClickType.END_DATE_CLICK_TYPE:
                setEndDateTimeSheet();
                break;
            case CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE:
                //do nothing work duration not selectable
                break;
            case CommonComboClickType.TASK_CLICK_TYPE: // --اگر مقداری برای نمایش وجود نداشته باشد این کلیک از آداپتور صدا زده نمیشود
                showSearchAbleSpinner(myListBaseNameList.get(0), CommonComboClickType.TASK_CLICK_TYPE);
                break;
            case CommonComboClickType.SERVICE_CLICK_TYPE:
                showSearchAbleSpinner(myListBaseNameList.get(1), CommonComboClickType.SERVICE_CLICK_TYPE);
                break;
            case CommonComboClickType.PROJECT_COMPONENT_CLICK_TYPE:
                showSearchAbleSpinner(myListBaseNameList.get(2), CommonComboClickType.PROJECT_COMPONENT_CLICK_TYPE);
                break;
            case CommonComboClickType.DAILY_SCHEDULE_CLICK_TYPE:
                showSearchAbleSpinner(myListUtilNameList.get(0), CommonComboClickType.DAILY_SCHEDULE_CLICK_TYPE);
                break;
            case CommonComboClickType.WAGE_TITLE_CLICK_TYPE:
                showSearchAbleSpinner(myListUtilNameList.get(1), CommonComboClickType.WAGE_TITLE_CLICK_TYPE);
                break;
            /*
            deprecated
            case CommonComboClickType.STATUS_CLICK_TYPE:
                showSearchAbleSpinner(myListUtilNameList.get(2), CommonComboClickType.STATUS_CLICK_TYPE);
                break;*/
            case CommonComboClickType.DESCRIPTION_CLICK_TYPE:
                description = someValue;
                //no need to update,  dataList were updated in holder
                break;
        }

    }

    private void setStartDateTimeSheet() {

        iSelectDateListener = new ISelectDateListener() {
            @Override
            public void onDateSelection(Date date) {

                if (CustomTimeHelper.isDateBetweenFinancialDate(context, date.getTime())) {
                    if (CustomTimeHelper.getDiffDate(date, CustomTimeHelper.getCurrentDate()) >= 0) {
                        if (endDate != null) {
                            if (CustomTimeHelper.getDiffDate(date, endDate) > MINIMUM_SHEET_PLAN_DURATION && CustomTimeHelper.getDiffDate(date, endDate) < MAXIMUM_SHEET_PLAN_DURATION) {
                                if (checkNull()) {
                                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.START_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableDate(date, getView().getActivity()), Commons.NULL, false), true);
                                }
                                startDate = date;
                            } else {
                                if (checkNull()) {
                                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.START_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.proper_sheet_plane_duration_error), false), true);
                                    getView().showMessageNotProperSheetPlaneTimeError();
                                }
                                startDate = null;
                            }
                        } else {
                            if (checkNull()) {
                                comboFormRecAdaptor.updateRowValue(CommonComboClickType.START_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableDate(date, getView().getActivity()), Commons.NULL, false), true);
                            }
                            startDate = date;
                        }
                    } else {
                        if (checkNull()) {
                            comboFormRecAdaptor.updateRowValue(CommonComboClickType.START_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.date_after_now_error), false), true);
                            getView().showMessageDateAfterNowError();
                        }
                        startDate = null;
                    }

                } else {
                    if (checkNull()) {
                        comboFormRecAdaptor.updateRowValue(CommonComboClickType.START_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.date_out_financial_date), false), true);
                        getView().showMessageDateOutFinancialDate();
                    }
                    startDate = null;
                }

                setProperWorkDuration(startDate, endDate);
            }

            @Override
            public void onDateNotSelection() {
                //do nothings
            }
        };
        //.add(iSelectDateListener);
        CustomDatePicker.showProperDateTimePicker(getView().getActivity(), iSelectDateListener);
    }

    private void setEndDateTimeSheet() {
        iSelectDateListener = new ISelectDateListener() {
            @Override
            public void onDateSelection(Date date) {

                if (CustomTimeHelper.isDateBetweenFinancialDate(context, date.getTime())) {
                    if (CustomTimeHelper.getDiffDate(date, CustomTimeHelper.getCurrentDate()) >= 0) {
                        if (startDate != null) {
                            if (CustomTimeHelper.getDiffDate(startDate, date) > MINIMUM_SHEET_PLAN_DURATION && CustomTimeHelper.getDiffDate(startDate, date)<MAXIMUM_SHEET_PLAN_DURATION) {
                                if (checkNull()) {
                                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableDate(date, getView().getActivity()), Commons.NULL, false), true);
                                }
                                endDate = date;
                            } else {
                                if (checkNull()) {
                                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.proper_sheet_plane_duration_error), false), true);
                                    getView().showMessageNotProperSheetPlaneTimeError();
                                }
                                endDate = null;
                            }
                        } else {
                            if (checkNull()) {
                                comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableDate(date, getView().getActivity()), Commons.NULL, false), true);
                            }
                            endDate = date;
                        }

                    } else {
                        if (checkNull()) {
                            comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.date_after_now_error), false), true);
                            getView().showMessageDateAfterNowError();
                        }
                        endDate = null;
                    }
                } else {
                    if (checkNull()) {
                        comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.date_out_financial_date), false), true);
                        getView().showMessageDateOutFinancialDate();
                    }
                    endDate = null;
                }
                setProperWorkDuration(startDate, endDate);
            }

            @Override
            public void onDateNotSelection() {
                //do nothings
            }
        };
        //unRegisterListener.add(iSelectDateListener);
        CustomDatePicker.showProperDateTimePicker(getView().getActivity(), iSelectDateListener);
    }

    //endregion
    //------------------------------------------------------------------------------------->>
    //region work for only sheet plane form code
    private void clickRowSheetPlanForm(int comboFormClickType, String someValue) {

        switch (comboFormClickType) {
            case CommonComboClickType.START_DATE_CLICK_TYPE:
                setStartDateSheetPlan();
                break;
            case CommonComboClickType.END_DATE_CLICK_TYPE:
                setEndDateSheetPlane();
                break;
            case CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE:
                //do nothing work duration not selectable
                break;
            case CommonComboClickType.TASK_CLICK_TYPE: // --اگر مقداری برای نمایش وجود نداشته باشد این کلیک از آداپتور صدا زده نمیشود
                showSearchAbleSpinner(myListBaseNameList.get(0), CommonComboClickType.TASK_CLICK_TYPE);
                break;
            case CommonComboClickType.SERVICE_CLICK_TYPE:
                showSearchAbleSpinner(myListBaseNameList.get(1), CommonComboClickType.SERVICE_CLICK_TYPE);
                break;
            case CommonComboClickType.PROJECT_COMPONENT_CLICK_TYPE:
                showSearchAbleSpinner(myListBaseNameList.get(2), CommonComboClickType.PROJECT_COMPONENT_CLICK_TYPE);
                break;
            case CommonComboClickType.DAILY_SCHEDULE_CLICK_TYPE:
                showSearchAbleSpinner(myListUtilNameList.get(0), CommonComboClickType.DAILY_SCHEDULE_CLICK_TYPE);
                break;
            case CommonComboClickType.WAGE_TITLE_CLICK_TYPE:
                showSearchAbleSpinner(myListUtilNameList.get(1), CommonComboClickType.WAGE_TITLE_CLICK_TYPE);
                break;
            /*
            deprecated
            case CommonComboClickType.STATUS_CLICK_TYPE:
                showSearchAbleSpinner(myListUtilNameList.get(2), CommonComboClickType.STATUS_CLICK_TYPE);
                break;*/
            case CommonComboClickType.DESCRIPTION_CLICK_TYPE:
                description = someValue;
                //no need to update,  dataList were updated in holder
                break;
            case CommonComboClickType.SWITCH_CLICK_TYPE:
                isHappeningAtTheSameTime = Integer.valueOf(someValue);
                //no need to update,  dataList were updated in holder
                break;
        }

    }

    private void setStartDateSheetPlan() {

        iSelectDateListener = new ISelectDateListener() {
            @Override
            public void onDateSelection(Date date) {

                if (CustomTimeHelper.isDateBetweenFinancialDate(context, date.getTime())) {
                    if (CustomTimeHelper.getDiffDate(date, CustomTimeHelper.getCurrentDate()) <= 0) {
                        if (endDate != null) {
                            if (CustomTimeHelper.getDiffDate(date, endDate) > MINIMUM_SHEET_PLAN_DURATION && CustomTimeHelper.getDiffDate(date, endDate) < MAXIMUM_SHEET_PLAN_DURATION) {
                                if (checkNull()) {
                                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.START_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableDate(date, getView().getActivity()), Commons.NULL, false), true);
                                }
                                startDate = date;
                            } else {
                                if (checkNull()) {
                                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.START_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.proper_sheet_plane_duration_error), false), true);
                                    getView().showMessageNotProperSheetPlaneTimeError();
                                }
                                startDate = null;
                            }
                        } else {
                            if (checkNull()) {
                                comboFormRecAdaptor.updateRowValue(CommonComboClickType.START_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableDate(date, getView().getActivity()), Commons.NULL, false), true);
                            }
                            startDate = date;
                        }
                    } else {
                        if (checkNull()) {
                            comboFormRecAdaptor.updateRowValue(CommonComboClickType.START_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.date_before_now_error), false), true);
                            getView().showMessageDateBeforeNowError();
                        }
                        startDate = null;
                    }

                } else {
                    if (checkNull()) {
                        comboFormRecAdaptor.updateRowValue(CommonComboClickType.START_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.date_out_financial_date), false), true);
                        getView().showMessageDateOutFinancialDate();
                    }
                    startDate = null;
                }
                setProperWorkDuration(startDate, endDate);
            }

            @Override
            public void onDateNotSelection() {
                //do nothings
            }
        };
        //unRegisterListener.add(iSelectDateListener);
        CustomDatePicker.showProperDateTimePicker(getView().getActivity(), iSelectDateListener);
    }

    private void setEndDateSheetPlane() {

        iSelectDateListener = new ISelectDateListener() {
            @Override
            public void onDateSelection(Date date) {


                if (CustomTimeHelper.isDateBetweenFinancialDate(context, date.getTime())) {

                    if (CustomTimeHelper.getDiffDate(date, CustomTimeHelper.getCurrentDate()) <= 0) {
                        if (startDate != null) {
                            if (CustomTimeHelper.getDiffDate(startDate, date) > MINIMUM_SHEET_PLAN_DURATION && CustomTimeHelper.getDiffDate(startDate, date) < MAXIMUM_SHEET_PLAN_DURATION) {
                                if (checkNull()) {
                                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableDate(date, getView().getActivity()), Commons.NULL, false), true);
                                }
                                endDate = date;
                            } else {
                                if (checkNull()) {
                                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.proper_sheet_plane_duration_error), false), true);
                                    getView().showMessageNotProperSheetPlaneTimeError();
                                }
                                endDate = null;
                            }
                        } else {
                            if (checkNull()) {
                                comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableDate(date, getView().getActivity()), Commons.NULL, false), true);
                            }
                            endDate = date;
                        }
                    } else {
                        if (checkNull()) {
                            comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.date_before_now_error), false), true);
                            getView().showMessageDateBeforeNowError();
                        }
                        endDate = null;
                    }
                } else {
                    if (checkNull()) {
                        comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(Commons.NULL, Commons.NULL, context.getResources().getString(R.string.date_out_financial_date), false), true);
                        getView().showMessageDateOutFinancialDate();
                    }
                    endDate = null;
                }


                setProperWorkDuration(startDate, endDate);
            }

            @Override
            public void onDateNotSelection() {
                //do nothings
            }
        };
        //unRegisterListener.add(iSelectDateListener);
        CustomDatePicker.showProperDateTimePicker(getView().getActivity(), iSelectDateListener);
    }

    //endregion
    //------------------------------------------------------------------------------------->>
    //region work for only device time sheet  form code
    private void clickRowDeviceTimeSheetForm(int comboFormClickType, String someValue) {
        switch (comboFormClickType) {
            case CommonComboClickType.SWITCH_CLICK_TYPE:
                updateStartEndDate(Integer.valueOf(someValue));
                //no need to update,  dataList were updated in holder
                break;
            case CommonComboClickType.TASK_CLICK_TYPE: // --اگر مقداری برای نمایش وجود نداشته باشد این کلیک از آداپتور صدا زده نمیشود
                showSearchAbleSpinner(myListBaseNameList.get(0), CommonComboClickType.TASK_CLICK_TYPE);
                break;
            case CommonComboClickType.SERVICE_CLICK_TYPE:
                showSearchAbleSpinner(myListBaseNameList.get(1), CommonComboClickType.SERVICE_CLICK_TYPE);
                break;
            case CommonComboClickType.PROJECT_COMPONENT_CLICK_TYPE:
                showSearchAbleSpinner(myListBaseNameList.get(2), CommonComboClickType.PROJECT_COMPONENT_CLICK_TYPE);
                break;
            case CommonComboClickType.DAILY_SCHEDULE_CLICK_TYPE:
                showSearchAbleSpinner(myListUtilNameList.get(0), CommonComboClickType.DAILY_SCHEDULE_CLICK_TYPE);
                break;
            case CommonComboClickType.WAGE_TITLE_CLICK_TYPE:
                showSearchAbleSpinner(myListUtilNameList.get(1), CommonComboClickType.WAGE_TITLE_CLICK_TYPE);
                break;
            /*
            deprecated:
            case CommonComboClickType.STATUS_CLICK_TYPE:
                showSearchAbleSpinner(myListUtilNameList.get(2), CommonComboClickType.STATUS_CLICK_TYPE);
                break;*/
            case CommonComboClickType.DESCRIPTION_CLICK_TYPE:
                description = someValue;
                //no need to update,  dataList were updated in holder
                break;

        }
    }

    private void updateStartEndDate(Integer switchState) {
        //by default currentDateForStartOrEnd  is set to start date
        switch (switchState) {
            case Commons.SWITCH_IS_ON:
                endDate = currentDateForStartOrEnd;
                startDate = null;
                break;
            case Commons.SWITCH_IS_OFF:
                startDate = currentDateForStartOrEnd;
                endDate = null;
                break;
        }

    }

    private void showFormDeviceTimeSheet() {
        if (checkNull()) {
            if (!primitiveGUID.equals(Commons.NULL)) {
                showAndSetPrimitiveData();
            } else {
                comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToDeviceTimeSheetRec(), setProperClickRowTypeToDeviceTimeSheetRec(), setProperDataToDeviceTimeSheetRec());
                getView().showHideProgress(false);
                getView().showHideConslaySheetPlanTime(true);
            }
        }
    }


    /**
     * سه متد
     * setProperRowTypeToTimeSheetPlaneRec
     * setProperDataToTimeSheetPlaneRec
     * setProperClickRowTypeToTimeSheetPlaneRec
     * نقش اساسی در ایجاد ریسایکل دارند
     */
    private ArrayList<Integer> setProperRowTypeToDeviceTimeSheetRec() {
        ArrayList<Integer> comboFormType = new ArrayList<>();
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_5);
        if (checkHaveConfig(1)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        }
        if (checkHaveConfig(2)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        }
        if (checkHaveConfig(3)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        }
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        //comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_4);
        return comboFormType;
    }

    private ArrayList<ComboFormModel> setProperPrimitiveDataToDeviceTimeSheetRec() {
        //here both start date and date , have values(in case of primitive save, if one of them were be null then save as '01-Jan-70 03:30:00'(minimum value of date) in database (see CustomTimeHelper.longLocalDateToOtherLocalConverter()  and CustomTimeHelper.dateToLongConvertor()))
        currentDateForStartOrEnd = CustomTimeHelper.getDiffDate(startDate, endDate) > 0 ? endDate : startDate;
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();
        comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableDate(currentDateForStartOrEnd, getView().getActivity()), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.start_date), context.getResources().getString(R.string.end_date), Commons.NULL, CustomTimeHelper.getDiffDate(startDate, endDate) > 0));
        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.task), myListBaseNameList.get(0).get(myListBaseIdList.get(0).indexOf(B5IdRefId)==-1?Commons.ZERO_INT:myListBaseIdList.get(0).indexOf(B5IdRefId)), Commons.NULL, false));
        }
        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.service_type), myListBaseNameList.get(1).get(myListBaseIdList.get(1).indexOf(B5IdRefId2)==-1?Commons.ZERO_INT:myListBaseIdList.get(1).indexOf(B5IdRefId2)), Commons.NULL, false));
        }
        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.project_components), myListBaseNameList.get(2).get(myListBaseIdList.get(2).indexOf(B5IdRefId3)==-1?Commons.ZERO_INT:myListBaseIdList.get(2).indexOf(B5IdRefId3)), Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.daily_schedule), myListUtilNameList.get(0).get(myListUtilIdList.get(0).indexOf(B5HCDailyScheduleId)==-1?Commons.ZERO_INT:myListUtilIdList.get(0).indexOf(B5HCDailyScheduleId)), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.wage_title), myListUtilNameList.get(1).get(myListUtilIdList.get(1).indexOf(B5HCWageTitleId)==-1?Commons.ZERO_INT:myListUtilIdList.get(1).indexOf(B5HCWageTitleId)), Commons.NULL, false));
/*
deprecated
        comboFormModels.add(new ComboFormModel(context.getString(R.string.status), myListUtilNameList.get(2).get(myListUtilIdList.get(2).indexOf(B5HCStatusId)==-1?Commons.ZERO_INT:myListUtilIdList.get(2).indexOf(B5HCStatusId)), Commons.NULL, false));
*/
        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), description, Commons.NULL, false));
        return comboFormModels;
    }

    /**
     * سه متد
     * setProperRowTypeToTimeSheetPlaneRec
     * setProperDataToTimeSheetPlaneRec
     * setProperClickRowTypeToTimeSheetPlaneRec
     * نقش اساسی در ایجاد ریسایکل دارند
     */
    private ArrayList<ComboFormModel> setProperDataToDeviceTimeSheetRec() {
        currentDateForStartOrEnd = CustomTimeHelper.getCurrentDate();
        startDate = currentDateForStartOrEnd;
        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();
        comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableDate(currentDateForStartOrEnd, getView().getActivity()), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.start_date), context.getResources().getString(R.string.end_date), Commons.NULL, false));
        if (checkHaveConfig(1)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.task), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(2)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.service_type), Commons.NULL, Commons.NULL, false));
        }
        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.project_components), Commons.NULL, Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.daily_schedule), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.wage_title), Commons.NULL, Commons.NULL, false));
        //comboFormModels.add(new ComboFormModel(context.getString(R.string.status), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), Commons.NULL, Commons.NULL, false));
        return comboFormModels;
    }

    /**
     * سه متد
     * setProperRowTypeToTimeSheetPlaneRec
     * setProperDataToTimeSheetPlaneRec
     * setProperClickRowTypeToTimeSheetPlaneRec
     * نقش اساسی در ایجاد ریسایکل دارند
     */
    private ArrayList<Integer> setProperClickRowTypeToDeviceTimeSheetRec() {

        ArrayList<Integer> comboFormClickType = new ArrayList<>();
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.SWITCH_CLICK_TYPE);
        if (checkHaveConfig(1)) {
            comboFormClickType.add(CommonComboClickType.TASK_CLICK_TYPE);
        }
        if (checkHaveConfig(2)) {
            comboFormClickType.add(CommonComboClickType.SERVICE_CLICK_TYPE);
        }
        if (checkHaveConfig(3)) {
            comboFormClickType.add(CommonComboClickType.PROJECT_COMPONENT_CLICK_TYPE);
        }
        comboFormClickType.add(CommonComboClickType.DAILY_SCHEDULE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.WAGE_TITLE_CLICK_TYPE);
/*
deprecated:
        comboFormClickType.add(CommonComboClickType.STATUS_CLICK_TYPE);
*/
        comboFormClickType.add(CommonComboClickType.DESCRIPTION_CLICK_TYPE);
        return comboFormClickType;
    }

    private void checkDataDeviceTimeSheet() {

        //no need to check start or end date , we have on of them from currentDateForStartOrEnd

        if (checkHaveConfig(1)) {
            if (B5IdRefId.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.TASK_CLICK_TYPE, new ComboFormModel(context.getString(R.string.task), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    return;
                }
            }
        }

        if (checkHaveConfig(2)) {
            if (B5IdRefId2.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.SERVICE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.service_type), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    return;
                }
            }
        }

        if (checkHaveConfig(3)) {
            if (B5IdRefId3.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.PROJECT_COMPONENT_CLICK_TYPE, new ComboFormModel(context.getString(R.string.project_components), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    return;
                }
            }
        }

        if (checkHaveUtilConfig(1)) {
            if (B5HCDailyScheduleId.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.DAILY_SCHEDULE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.daily_schedule), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    return;
                }
            }
        }


        if (checkHaveUtilConfig(2)) {
            if (B5HCWageTitleId.equals(Commons.NULL_INTEGER)) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.WAGE_TITLE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.wage_title), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                    return;
                }
            }
        }

        /*
        deprecated
        if (B5HCStatusId.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.STATUS_CLICK_TYPE, new ComboFormModel(context.getString(R.string.status), Commons.NULL, context.getString(R.string.fill_proper_value), false));
                return;
            }
        }*/


        addDisposable(saveDataTimeSheetPlaneDevice());
    }
    //endregion
    //------------------------------------------------------------------------------------->>
}
