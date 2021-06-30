package ir.fararayaneh.erp.activities.task;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import ir.fararayaneh.erp.data.data_providers.queries.task_queris.InsertUpdateTaskTableQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.middle.ComboFormModel;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TaskTable;
import ir.fararayaneh.erp.utils.data_handler.AttachJsonCreator;
import ir.fararayaneh.erp.utils.data_handler.GetOneRowByGUIDHandler;
import ir.fararayaneh.erp.utils.data_handler.IGetRowByGUIDQueryListener;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.socket_helper.SendPacket;
import ir.fararayaneh.erp.utils.socket_helper.SocketJsonMaker;
import ir.fararayaneh.erp.utils.time_helper.CustomDatePicker;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;
import ir.fararayaneh.erp.utils.time_helper.ISelectDateListener;

import static ir.fararayaneh.erp.commons.CommonsFormCode.TASK_FORM_CODE;

/**
 * formCode TASK_FORM_CODE=28 :
 * در جدول
 * formRef
 * این فرم کد دارای کانفیگ شماره  3 است
 * <p>
 * که کانفیگ شماره 2 آی دی یوزری که در حال ایجاد تسک است را نمایش میدهد
 * و کانفیگ شماره 3 نشان دهنده مقدار آی دی
 * taskRef
 * میباشد و از سوی دیگر مقدار کانفیگ شماره 1 از جدول
 * kartableReceiver
 * مقدار دهی میشود
 */

public class TaskPresenter extends BasePresenterCollectionDataForm<TaskView> {


    private ISelectDateListener iSelectDateListener;
    //-------------------------------------------------------------------------------
    private String subject, comments;
    private Date taskDate, deadLine;
    private String B5IdRefId, B5IdRefId2, B5IdRefId3 , T5SCTypeId, B5HCPriorityId , B5HCStatusId ;
    //-------------------------------------------------------------------------------

    public TaskPresenter(WeakReference<TaskView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }


    /**
     * only for set values that no need to database because get from db call after this method
     */
    @Override
    protected void setDefaultValue() {
        //we sure we have non null user id
        B5IdRefId2 = SharedPreferenceProvider.getUserId(context);
        subject = Commons.NULL;
        comments = Commons.NULL;
        taskDate = CustomTimeHelper.getCurrentDate();
        B5IdRefId = Commons.NULL_INTEGER;
        B5IdRefId3 = Commons.NULL_INTEGER;
        T5SCTypeId = Commons.NULL_INTEGER;
        B5HCPriorityId = Commons.NULL_INTEGER;
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
        if(attachNameList!=null){
        comboFormRecAdaptor.updateRowValue(CommonComboClickType.ATTACH_CLICK_TYPE, new ComboFormModel(Commons.NULL, String.valueOf(attachNameList.size()), Commons.NULL, false), true);
        }
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

    //---------------------------------------------------------------------------------------------
    @Override
    protected void setupCustomLayoutManagerFactory() {

        if (checkNull()) {
            ArrayList<Integer> listOfPosition = new ArrayList<>();
            switch (myFormCode) {
                case CommonsFormCode.TASK_FORM_CODE:
                    //listOfPosition.add(0);
                    listOfPosition.add(1);
                    listOfPosition.add(2);
                    getView().setupCustomLayoutManager(2, 1, listOfPosition);
                    break;
            }
        }

    }

    @Override
    protected void witchWorkForShowForm() {
        switch (myFormCode) {
            case TASK_FORM_CODE:
                showFormTask();
                break;
        }
    }

    @Override
    protected void clickRowRecycleFactoryMethod(int comboFormClickType, String someValue) {
        switch (myFormCode) {
            case CommonsFormCode.TASK_FORM_CODE:
                clickRowTaskForm(comboFormClickType, someValue);
                break;
        }
    }


    @Override
    protected void setDataFromSearchAbleSpinner(RealmModel realmModel, String item, int position, int commonComboClickType) {
        if (checkNull()) {
            comboFormRecAdaptor.updateRowValue(commonComboClickType, new ComboFormModel(Commons.NULL, item, Commons.NULL, false), true);
            switch (commonComboClickType) {
                case CommonComboClickType.SELECT_OTHER_USER_CLICK_TYPE:
                    B5IdRefId = myListKartableIdList.get(0).get(position);
                    break;
                case CommonComboClickType.TASK_CLICK_TYPE:
                    B5IdRefId3 = myListBaseIdList.get(2).get(position);
                    break;
                case CommonComboClickType.PRIORITY_CLICK_TYPE:
                    B5HCPriorityId = myListUtilIdList.get(0).get(position);
                    break;
                case CommonComboClickType.TASK_TYPE_CLICK_TYPE:
                    T5SCTypeId = myListUtilIdList.get(1).get(position);
                    break;
                    /*
                    deprecated >> user can not set manually

                case CommonComboClickType.STATUS_CLICK_TYPE:
                    B5HCStatusId = myListUtilIdList.get(2).get(position);
                    break;
                     */
            }
        }

    }

    @Override
    protected void checkData() {
        switch (myFormCode) {
            case CommonsFormCode.TASK_FORM_CODE:
                checkDataTask();
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
        addDisposable(saveDataTask());
    }

//-------------------------------------------------------------------------------------------------
    //region Task formCode
    private void showFormTask() {
        if (checkNull()) {
            if (!primitiveGUID.equals(Commons.NULL)) {
                showAndSetPrimitiveData();
            } else {
                comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToTaskRec(), setProperClickRowTypeToTaskRec(), setProperDataToTaskRec());
                getView().showHideProgress(false);
                getView().showHideConslayTask(true);
            }
        }
    }

    private List<Integer> setProperRowTypeToTaskRec() {

        ArrayList<Integer> comboFormType = new ArrayList<>();
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_6);//--->>tarikh sabt
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_2);//--->>deadline
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_9);//--->>attachment
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//--->>receiver
        if (checkHaveConfig(3)) {
            comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//--->>task reference
        }
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//--->>priority (PRY)
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//--->>taskType (TAT)
        //comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_1);//--->>Status id (SVC)(deprecated >>user can not set manually)
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_7);//--->>subject
        comboFormType.add(CommonComboFormType.COMMON_COMBO_FORM_TYPE_4);//--->>description

        return comboFormType;
    }

    private List<ComboFormModel> setProperPrimitiveDataToTaskRec() {

        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();
        if (checkNull()) {
            comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableDate(taskDate, getView().getActivity()), Commons.NULL, false));
            comboFormModels.add(new ComboFormModel(context.getString(R.string.dead_line), CustomTimeHelper.getPresentableDate(deadLine, getView().getActivity()), Commons.NULL, false));

        comboFormModels.add(new ComboFormModel(Commons.NULL,String.valueOf(attachNameList!=null?attachNameList.size():context.getString(R.string.zero)), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.to), B5IdRefId.equals(Commons.NULL_INTEGER)?Commons.NULL:myListKartableNameList.get(0).get(myListKartableIdList.get(0).indexOf(B5IdRefId)==-1?Commons.ZERO_INT:myListKartableIdList.get(0).indexOf(B5IdRefId)), Commons.NULL, false));//agar be har dalil id==-1 ast , chizi neshan dade nemishavad
        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.reference),B5IdRefId3.equals(Commons.NULL_INTEGER)?Commons.NULL: myListBaseNameList.get(2).get(myListBaseIdList.get(2).indexOf(B5IdRefId3)==-1?Commons.ZERO_INT:myListBaseIdList.get(2).indexOf(B5IdRefId3)), Commons.NULL, false));//agar be har dalil id==-1 ast , chizi neshan dade nemishavad
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.priority),B5HCPriorityId.equals(Commons.NULL_INTEGER)?Commons.NULL: myListUtilNameList.get(0).get(myListUtilIdList.get(0).indexOf(B5HCPriorityId)==-1?Commons.ZERO_INT:myListUtilIdList.get(0).indexOf(B5HCPriorityId)), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.task_type),T5SCTypeId.equals(Commons.NULL_INTEGER)?Commons.NULL: myListUtilNameList.get(1).get(myListUtilIdList.get(1).indexOf(T5SCTypeId)==-1?Commons.ZERO_INT:myListUtilIdList.get(1).indexOf(T5SCTypeId)), Commons.NULL, false));
        //comboFormModels.add(new ComboFormModel(context.getString(R.string.status),B5HCStatusId.equals(Commons.NULL_INTEGER)?Commons.NULL: myListUtilNameList.get(2).get(myListUtilIdList.get(2).indexOf(B5HCStatusId)==-1?Commons.ZERO_INT:myListUtilIdList.get(2).indexOf(B5HCStatusId)), Commons.NULL, false)); (deprecated >>user can not set manually)
        comboFormModels.add(new ComboFormModel(context.getString(R.string.subject), subject, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), comments, Commons.NULL, false));}
        return comboFormModels;
    }

    private void showAndSetPrimitiveData() {
        iGetRowByGUIDQueryListener = new IGetRowByGUIDQueryListener() {
            @Override
            public void onGetRow(IModels iModels) {
                workForShowAndSetPrimitiveData((TaskTable) iModels);
            }

            @Override
            public void onError() {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_44);
                }
            }
        };
        addDisposable(GetOneRowByGUIDHandler.getRowTask(primitiveGUID, TaskTable.class,false, iGetRowByGUIDQueryListener));
    }

    private void workForShowAndSetPrimitiveData(TaskTable table) {

        /**
         * here have only one formId and set it before in base
         */
        id = table.getId();//should be set here
        guid = table.getGuid();//should be set here

        subject = table.getSubject();
        comments = table.getComments();
        geoLocation = table.getGeoLocation();
        syncState = CommonSyncState.BEFORE_SYNC;
        activityState = CommonActivityState.UPDATE;
        oldValue = table.getOldValue();
        taskDate = CustomTimeHelper.stringDateToMyLocalDateConvertor(table.getTaskDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE);
        deadLine = CustomTimeHelper.stringDateToMyLocalDateConvertor(table.getDeadLine(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE);
        attachNameList = AttachJsonCreator.getAttachmentNameList(table.getAttach());
        attachColumnIdList = AttachJsonCreator.getAttachColumnIdList(table.getAttach());
        attachB5HCTypeIdList = AttachJsonCreator.getAttachB5HCTypeIdList(table.getAttach());
        B5IdRefId = table.getB5IdRefId();
        B5IdRefId2 = table.getB5IdRefId2();
        B5IdRefId3 = table.getB5IdRefId3();
        T5SCTypeId = table.getT5SCTypeId();
        B5HCPriorityId = table.getB5HCPriorityId();
        B5HCStatusId = table.getB5HCStatusId();
        if (checkNull()) {
            comboFormRecAdaptor.setViewRowTypeList(getView().getLayoutManager(), setProperRowTypeToTaskRec(), setProperClickRowTypeToTaskRec(), setProperPrimitiveDataToTaskRec());
            getView().showHideProgress(false);
            getView().showHideConslayTask(true);
        }
    }

    private ArrayList<Integer> setProperClickRowTypeToTaskRec() {

        ArrayList<Integer> comboFormClickType = new ArrayList<>();
        comboFormClickType.add(CommonComboClickType.NON_CLICK_ABLE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.END_DATE_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.ATTACH_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.SELECT_OTHER_USER_CLICK_TYPE);
        if (checkHaveConfig(3)) {
            comboFormClickType.add(CommonComboClickType.TASK_CLICK_TYPE);
        }
        comboFormClickType.add(CommonComboClickType.PRIORITY_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.TASK_TYPE_CLICK_TYPE);
        //comboFormClickType.add(CommonComboClickType.STATUS_CLICK_TYPE);(deprecated >>user can not set manually)
        comboFormClickType.add(CommonComboClickType.SUBJECT_CLICK_TYPE);
        comboFormClickType.add(CommonComboClickType.DESCRIPTION_CLICK_TYPE);

        return comboFormClickType;

    }

    private List<ComboFormModel> setProperDataToTaskRec() {

        ArrayList<ComboFormModel> comboFormModels = new ArrayList<>();
        comboFormModels.add(new ComboFormModel(Commons.NULL, CustomTimeHelper.getPresentableDate(taskDate, getView().getActivity()), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.dead_line), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(Commons.NULL, context.getString(R.string.zero), Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.to), Commons.NULL, Commons.NULL, false));
        if (checkHaveConfig(3)) {
            comboFormModels.add(new ComboFormModel(context.getString(R.string.reference), Commons.NULL, Commons.NULL, false));
        }
        comboFormModels.add(new ComboFormModel(context.getString(R.string.priority), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.task_type), Commons.NULL, Commons.NULL, false));
        //comboFormModels.add(new ComboFormModel(context.getString(R.string.status), Commons.NULL, Commons.NULL, false));(deprecated >>user can not set manually)
        comboFormModels.add(new ComboFormModel(context.getString(R.string.subject), Commons.NULL, Commons.NULL, false));
        comboFormModels.add(new ComboFormModel(context.getString(R.string.description), Commons.NULL, Commons.NULL, false));

        return comboFormModels;
    }

    private void clickRowTaskForm(int comboFormClickType, String someValue) {
        switch (comboFormClickType) {
            case CommonComboClickType.END_DATE_CLICK_TYPE:
                setTaskDeaLine();
                break;
            case CommonComboClickType.ATTACH_CLICK_TYPE:
                if (!primitiveGUID.equals(Commons.NULL)){
                    //if we are in edit mode we can only add attach
                    goAttachAlbumActivity(true,false,null);
                } else {
                    //if we are in new mode we can add and delete
                    goAttachAlbumActivity(true,true,null);
                }
                break;
            case CommonComboClickType.SELECT_OTHER_USER_CLICK_TYPE:
                showSearchAbleSpinner(myListKartableNameList.get(0), CommonComboClickType.SELECT_OTHER_USER_CLICK_TYPE);
                break;
            case CommonComboClickType.TASK_CLICK_TYPE:
                showSearchAbleSpinner(myListBaseNameList.get(2), CommonComboClickType.TASK_CLICK_TYPE);
                break;
            case CommonComboClickType.PRIORITY_CLICK_TYPE:
                showSearchAbleSpinner(myListUtilNameList.get(0), CommonComboClickType.PRIORITY_CLICK_TYPE);
                break;
            case CommonComboClickType.TASK_TYPE_CLICK_TYPE:
                showSearchAbleSpinner(myListUtilNameList.get(1), CommonComboClickType.TASK_TYPE_CLICK_TYPE);
                break;
            case CommonComboClickType.STATUS_CLICK_TYPE://(deprecated >>user can not set manually)
                //showSearchAbleSpinner(myListUtilNameList.get(2), CommonComboClickType.STATUS_CLICK_TYPE);
                break;
            case CommonComboClickType.SUBJECT_CLICK_TYPE:
                //no need to update,  dataList were updated in holder
                subject = someValue;
                break;
            case CommonComboClickType.DESCRIPTION_CLICK_TYPE:
                //no need to update,  dataList were updated in holder
                comments = someValue;
                break;
        }
    }

    private void setTaskDeaLine() {
        iSelectDateListener = new ISelectDateListener() {
            @Override
            public void onDateSelection(Date date) {

                if (CustomTimeHelper.isDateBetweenFinancialDate(context, date.getTime())) {
                    if (CustomTimeHelper.getDiffDate(taskDate, date) >= 0) {
                        if (CustomTimeHelper.getDiffDate(date, CustomTimeHelper.longToDateConvertor(SharedPreferenceProvider.getEndFinancialDate(context))) >= 0) {
                            if (checkNull()) {
                                comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.dead_line), CustomTimeHelper.getPresentableDate(date, getView().getActivity()), Commons.NULL, false), true);
                            }
                            deadLine = date;
                        } else {
                            if (checkNull()) {
                                comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.dead_line), Commons.NULL, context.getResources().getString(R.string.date_after_financial_year), false), true);
                                getView().showMessageDateBeforeNowError();
                            }
                            deadLine = null;
                        }
                    } else {
                        if (checkNull()) {
                            comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.dead_line), Commons.NULL, context.getResources().getString(R.string.date_before_now_error), false), true);
                            getView().showMessageDateBeforeNowError();
                        }
                        deadLine = null;
                    }

                } else {
                    if (checkNull()) {
                        comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.dead_line), Commons.NULL, context.getResources().getString(R.string.date_out_financial_date), false), true);
                        getView().showMessageDateOutFinancialDate();
                    }
                    deadLine = null;
                }

            }

            @Override
            public void onDateNotSelection() {
                //do nothings
            }
        };
        //unRegisterListener.add(iSelectDateListener);
        CustomDatePicker.showProperDateTimePicker(getView().getActivity(), iSelectDateListener);
    }

    private void checkDataTask() {

        //taskDate,B5HCPriorityId,B5IdRefId3 B5IdRefId2,not required




            /*if (taskDate == null) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.dead_line), Commons.NULL, context.getString(R.string.fill_proper_value), false));
                    return;
                }
            }*/
//--------------------------------------------------------------------------------
            /*if (myListBaseNameList.get(2).size() != 0) {
                if (B5IdRefId3 == 0) {
                    if (checkNull()) {
                        getView().showHideProgress(false);
                        comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_MAIN_USER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.from), Commons.NULL, context.getString(R.string.fill_proper_value), false));
                        return;
                    }
                }
            }*/
//--------------------------------------------------------------------------------
            /*  if (B5HCPriorityId == 0) {
                if (checkNull()) {
                    getView().showHideProgress(false);
                    comboFormRecAdaptor.updateRowValue(CommonComboClickType.PRIORITY_CLICK_TYPE, new ComboFormModel(context.getString(R.string.priority), Commons.NULL, context.getString(R.string.fill_proper_value), false));
                    return;
                }
            }*/

        if (deadLine == null) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.END_DATE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.dead_line), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                return;
            }
        }


        if (B5IdRefId.equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.SELECT_OTHER_USER_CLICK_TYPE, new ComboFormModel(context.getString(R.string.to), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                return;
            }
        }

        if (checkHaveConfig(3) && B5IdRefId3 .equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.TASK_CLICK_TYPE, new ComboFormModel(context.getString(R.string.reference), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                return;
            }
        }


        if (T5SCTypeId .equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.TASK_TYPE_CLICK_TYPE, new ComboFormModel(context.getString(R.string.task_type), Commons.NULL, context.getString(R.string.fill_proper_value), false), true);
                return;
            }
        }

        /*
        (deprecated >>user can not set manually)

        if (B5HCStatusId .equals(Commons.NULL_INTEGER)) {
            if (checkNull()) {
                getView().showHideProgress(false);
                comboFormRecAdaptor.updateRowValue(CommonComboClickType.STATUS_CLICK_TYPE, new ComboFormModel(context.getString(R.string.status), Commons.NULL, context.getString(R.string.fill_proper_value), false));
                return;
            }
        }*/

        addDisposable(saveDataTask());

    }

    private Disposable saveDataTask() {

        TaskTable taskTable = new TaskTable(id, guid, subject, comments, geoLocation, syncState, activityState, oldValue,
                CustomTimeHelper.longLocalDateToOtherLocalConverter(CommonTimeZone.SERVER_TIME_ZONE, taskDate.getTime(), CommonsFormat.DATE_SHARE_FORMAT_FROM_STRING),
                CustomTimeHelper.longLocalDateToOtherLocalConverter(CommonTimeZone.SERVER_TIME_ZONE, deadLine.getTime(), CommonsFormat.DATE_SHARE_FORMAT_FROM_STRING),
                AttachJsonCreator.buildJson(attachNameList, attachColumnIdList, attachB5HCTypeIdList),
                B5IdRefId, B5IdRefId2, B5IdRefId3, T5SCTypeId, B5HCPriorityId, B5HCStatusId);
        InsertUpdateTaskTableQuery insertUpdateTaskTableQuery = (InsertUpdateTaskTableQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_UPDATE_TASK_TABLE_QUERY);
        assert insertUpdateTaskTableQuery != null;
        return insertUpdateTaskTableQuery.data(taskTable).subscribe(iModels -> {
            if (checkNull()) {
                uploadAttachAndSendSocket(taskTable);
            }
        }, throwable -> {
            if (checkNull()) {
                getView().showHideProgress(false);
                getView().showMessageSomeProblems(CommonsLogErrorNumber.error_45);
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
     * <p>
     * agar file darim az roye soton attach motavajeh mishavim na az roye has file
     */
    private void uploadAttachAndSendSocket(TaskTable taskTable) {
        if (attachNameList.size() != 0) {
            addDisposable(startUploadAttachmentProcess());
        }
        SendPacket.sendEncryptionMessage(context, SocketJsonMaker.taskSocket(SharedPreferenceProvider.getOrganization(context), SharedPreferenceProvider.getUserId(context), taskTable), false);
        ActivityIntents.resultOkFromTaskAct(TaskPresenter.this.getView().getActivity());
    }

    //endregion
//-------------------------------------------------------------------------------------------------
}
