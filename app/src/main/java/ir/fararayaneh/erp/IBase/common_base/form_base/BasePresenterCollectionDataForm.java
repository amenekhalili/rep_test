package ir.fararayaneh.erp.IBase.common_base.form_base;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.realm.RealmModel;
import ir.fararayaneh.erp.IBase.common_base.BasePresenterWithRecycle;
import ir.fararayaneh.erp.IBase.common_base.IClickCollectionDataListener;
import ir.fararayaneh.erp.adaptors.combo_form_adaptor.ComboFormRecAdaptor;
import ir.fararayaneh.erp.adaptors.combo_form_adaptor.IClickComboFormRecAdaptor;
import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.form_ref.GetFormLocationQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.utils.UUIDHelper;
import ir.fararayaneh.erp.utils.data_handler.ComboDataListHandler;
import ir.fararayaneh.erp.utils.data_handler.FormIdFromFormCodeHandler;
import ir.fararayaneh.erp.utils.data_handler.IComboListener;
import ir.fararayaneh.erp.utils.data_handler.IGetRowByGUIDQueryListener;
import ir.fararayaneh.erp.utils.data_handler.IFormIdListener;
import ir.fararayaneh.erp.utils.location_handler.ILiveLocationListener;
import ir.fararayaneh.erp.utils.location_handler.LocationToString;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.search_spinner_dialog.IsearchDialogeListener;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.search_spinner_dialog.SearchAbleSpinnerDialogHandler;

/**
 * in this base, primitive data for load forms were prepared
 * after that, if we have someInt2 = primitiveGUID in intent model
 * (idForShowAsPrimitiveData of SetFormCodeToIntentModel.class  )
 * should load primitive data to views
 */
public abstract class BasePresenterCollectionDataForm<V extends BaseViewCollectionDataForm> extends BasePresenterWithRecycle<V> {

    public BasePresenterCollectionDataForm(WeakReference<V> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }


    protected IGetRowByGUIDQueryListener iGetRowByGUIDQueryListener;//will set in child and clear here
    protected IClickComboFormRecAdaptor iClickComboFormRecAdaptor;
    protected IClickCollectionDataListener iClickCollectionDataListener;
    private ILiveLocationListener iLiveLocationListener;
    private IFormIdListener iformIdListener;
    private IComboListener iComboListener;
    protected IsearchDialogeListener isearchDialogeListener;
    protected ComboFormRecAdaptor comboFormRecAdaptor = new ComboFormRecAdaptor(context);
    protected String myFormCode = Commons.NULL_INTEGER, myFormId = Commons.NULL_INTEGER, id = Commons.NULL_INTEGER;//-->>id is zero(-1) by default before get from dataBase
    protected String primitiveGUID = Commons.NULL;// this value is guid(for show form in edit mode but is save time we use guid)
    protected String geoLocation = Commons.NULL, syncState = CommonSyncState.BEFORE_SYNC, activityState = CommonActivityState.ADD, oldValue = Commons.NULL, guid = UUIDHelper.getProperUUID();
    protected ArrayList<ArrayList<String>> myListBaseNameList = new ArrayList<>(), myListUtilNameList = new ArrayList<>(), myListKartableNameList = new ArrayList<>(); //لیستی از تمام لیست های اسم قابل نمایش در کمبوباکس ها
    protected ArrayList<ArrayList<String>> myListBaseIdList = new ArrayList<>(), myListUtilIdList = new ArrayList<>(), myListKartableIdList = new ArrayList<>();  //لیستی از تمام لیست های آی دی های قابل نمایش در کمبوباکس ها
    protected int neededLocation = Commons.NO_NEED_FORCE_LOCATION;
    //-------------------------------------------------------------------------------

    @Override
    public void click() {

        iClickCollectionDataListener = new IClickCollectionDataListener() {
            @Override
            public void confirmClick() {
                workConfirmClick();
            }

            @Override
            public void cancelClick() {
                cancelResultIntent();
            }

            @Override
            public void nextClick() {
                workNextClick();
            }

            @Override
            public void deleteClick() {
                workDeleteClick();
            }

        };
        if (checkNull()) {
            getView().setCollectionDataListner(iClickCollectionDataListener);
        }
        //unRegisterListener.add(iClickCollectionDataListener);
        iClickComboFormRecAdaptor = BasePresenterCollectionDataForm.this::clickRowRecycleFactoryMethod;
        comboFormRecAdaptor.setClickRecycle(iClickComboFormRecAdaptor);
        //unRegisterListener.add(iClickComboFormRecAdaptor);
    }

    @Override
    protected void ClearAllPresenterListener() {
        iGetRowByGUIDQueryListener = null;
        iClickCollectionDataListener = null;
        iClickComboFormRecAdaptor = null;
        iLiveLocationListener = null;
        iformIdListener = null;
        iComboListener = null;
        isearchDialogeListener = null;
    }

    private void workConfirmClick() {
        if(checkNull()){
            getView().showHideProgress(true);
        }if (SharedPreferenceProvider.getForceLocation(context) == Commons.NEED_FORCE_LOCATION) {
            addDisposable(checkNeededLocationForm());
        } else {
            checkData();
        }
    }

    //check need location or not
    private Disposable checkNeededLocationForm(){
        GlobalModel globalModel=new GlobalModel();
        globalModel.setMyString(myFormCode);
        GetFormLocationQuery getFormLocationQuery=(GetFormLocationQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_FORM_LOCATION_QUERY);
        assert getFormLocationQuery != null;
        return getFormLocationQuery.data(globalModel)
                .subscribe(iModels -> {
                    neededLocation=globalModel.getMyInt();
                    if(neededLocation!=Commons.NO_NEED_FORCE_LOCATION){
                        listenToLiveLocation();
                    } else {
                        checkData();
                    }
                }, throwable -> {
                    //no need to change default value of neededLocation
                    checkData();
                    ThrowableLoggerHelper.logMyThrowable("BasePresenterCollectionDataForm/checkNeededLocationForm:  "+throwable.getMessage());
                });
    }

    private void listenToLiveLocation() {

        iLiveLocationListener = new ILiveLocationListener() {
            @Override
            public void getLocation(Location location) {
                if (location != null) {
                    geoLocation = LocationToString.getString(location);
                    Log.i("arash", "getLocation: "+geoLocation);
                }
                workForAfterLocation();
            }

            @Override
            public void getError(String error) {
                //if we get error, my work will be continued
                Log.i("arash", "getError: location"+error);
                workForAfterLocation();
            }

            @Override
            public void forbiddenApp() {
                if (checkNull()) {
                    getView().showForbiddenAppMessageError();
                    getView().showHideProgress(false);
                }
            }

            @Override
            public void mockLocation() {
                if (checkNull()) {
                    getView().showMockAppMessageError();
                    getView().showHideProgress(false);
                }
            }
        };
        getUserLiveLocation(null, iLiveLocationListener);
        //unRegisterListener.add(iLiveLocationListener);
    }

    private  void workForAfterLocation(){
        if(neededLocation==Commons.NEED_FORCE_LOCATION && geoLocation.equals(Commons.NULL)){
            if(checkNull()){
                getView().showHideProgress(false);
                getView().showMessageLocationNotValid();
            }
        } else {
            checkData();
        }
    }

    @Override
    public void onCreate(IntentModel intentModel) {
        myFormCode = intentModel.getSomeString2();//-->> we need myFormCode before super
        primitiveGUID = intentModel.getSomeString();//-->> we need guid for primitive data
        if (checkNull()) {
            getView().showHideBTNDelete(!primitiveGUID.equals(Commons.NULL));
        }
        super.onCreate(intentModel);
        startGetDataProcess();
    }

    private void startGetDataProcess() {
        setDefaultValue();//only for constant values
        getProperFormId();
    }

    /**
     * هر نوع ویو را در قالب همین آدپتور و با استفاده از انواع
     * CommonComboFormType
     * ایجاد میگردد
     */
    @Override
    protected void setRecyclAdaptor() {
        if (checkNull()) {
            setupCustomLayoutManagerFactory();
            getView().setRecycleAdaptor(comboFormRecAdaptor);
        }
    }

    @Override
    protected void workForLoadPage() {
        //به دلیل اینکه باید ابتدا از دیتا بیس کویری کنیم تا
        // تعداد کمبو ها و مقادیر آنها تعیین شود در این مرحله کاری نمیکنیم
    }

    @Override
    protected void setLazyLoad() {
        if (checkNull()) {
            getView().loadAtEndList(() -> {
                //no need to scrolling
            });
        }
    }


    @Override
    public void onPause() {

    }

    @Override
    public void onBackPress() {
        cancelResultIntent();
    }

    private void getProperFormId() {
        iformIdListener = new IFormIdListener() {
            @Override
            public void onGetData(String formId) {
                if(!formId.equals(Commons.NULL_INTEGER)){
                    myFormId = formId;
                    getProperBaseCoddingData();
                } else {
                    if(checkNull()){
                        getView().showMessageSomeProblems(CommonsLogErrorNumber.error_66);
                    }
                }

            }

            @Override
            public void onError() {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_10);
                }
            }
        };
        addDisposable(FormIdFromFormCodeHandler.getFormId(myFormCode, iformIdListener));
        // unRegisterListener.add(iformIdListener);
    }

    private void getProperBaseCoddingData() {
        iComboListener = new IComboListener() {
            @Override
            public void onData(ArrayList<ArrayList<String>> listNameList, ArrayList<ArrayList<String>> listIdList) {
                myListBaseNameList = listNameList;
                myListBaseIdList = listIdList;
                getProperUtilCoddingData();
            }

            @Override
            public void onError() {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_11);
                }
            }
        };
        addDisposable(ComboDataListHandler.getBaseCoddingData(myFormCode, iComboListener));
        //unRegisterListener.add(iComboListener);
    }

    //todo getProperCodeUtilCode تکمیل شود ( in ComboDataListHandler class)
    private void getProperUtilCoddingData() {
        iComboListener = new IComboListener() {
            @Override
            public void onData(ArrayList<ArrayList<String>> listNameList, ArrayList<ArrayList<String>> listIdList) {
                myListUtilNameList = listNameList;
                myListUtilIdList = listIdList;
                getProperKartableReceiverData();
            }

            @Override
            public void onError() {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_12);
                }
            }
        };
        addDisposable(ComboDataListHandler.getUtilCoddingData(myFormCode, iComboListener));
        //unRegisterListener.add(iComboListener);
    }

    private void getProperKartableReceiverData() {

        iComboListener = new IComboListener() {
            @Override
            public void onData(ArrayList<ArrayList<String>> listNameList, ArrayList<ArrayList<String>> listIdList) {
                myListKartableNameList = listNameList;
                myListKartableIdList = listIdList;
                witchWorkForShowForm();
            }

            @Override
            public void onError() {
                if (checkNull()) {
                    getView().showMessageSomeProblems(CommonsLogErrorNumber.error_13);
                }
            }
        };
        addDisposable(ComboDataListHandler.getKartableReceiverData(iComboListener));
        //unRegisterListener.add(iComboListener);
    }


    /**
     * این متد همه دیالوگ های قابل سرچ که دیتای آنها درون آرایه است را نشان میدهد
     * position is position of primitive data , not filtered data, and can use safely
     */
    protected void showSearchAbleSpinner(ArrayList<String> data, int commonComboClickType) {
        if (checkNull()) {
            isearchDialogeListener = (realmModel, item, position) -> BasePresenterCollectionDataForm.this.setDataFromSearchAbleSpinner(realmModel, item, position, commonComboClickType);

            //unRegisterListener.add(isearchDialogeListener);

            //for prevent from filter and reduce main arrayList
            // ArrayList<String> tempArrayList = new ArrayList<>(data);
            SearchAbleSpinnerDialogHandler.show(getView().getActivity(), data, isearchDialogeListener);
        }
    }

    /**
     * @param positionOfConfig one of the 1 ... 15 configs
     */
    protected void showSearchAbleSpinnerOfFormRefConfig(int positionOfConfig, int commonComboClickType) {
        if (checkNull()) {
            isearchDialogeListener = (realmModel, item, position) -> BasePresenterCollectionDataForm.this.setDataFromSearchAbleSpinner(realmModel, item, position, commonComboClickType);
            SearchAbleSpinnerDialogHandler.showOneConfigDBDialog(getView().getActivity(), myFormCode, positionOfConfig, isearchDialogeListener);
        }
    }

    /**
     * @param utilAbbreviation : from CommonUtilCode.class
     */
    protected void showSearchAbleSpinnerOfUtilCodeConfig(String utilAbbreviation, int commonComboClickType) {
        if (checkNull()) {
            isearchDialogeListener = (realmModel, item, position) -> BasePresenterCollectionDataForm.this.setDataFromSearchAbleSpinner(realmModel, item, position, commonComboClickType);
            SearchAbleSpinnerDialogHandler.showOneUtilCodeConfigDBDialog(getView().getActivity(), utilAbbreviation, isearchDialogeListener);
        }
    }


    /**
     * @param number is between 1-15
     */
    protected boolean checkHaveConfig(int number) {
        return myListBaseIdList.get(number-1).size() != 0;
    }

    /**
     * @param number is between 1-15
     */
    protected boolean checkHaveUtilConfig(int number) {
        return myListUtilIdList.get(number-1).size() != 0;
    }


    //--------------------------------------------------------------------------


    /**
     * in metod, maghadir defult ra por mikonad ke niaz be database nadarad
     */
    protected abstract void setDefaultValue();

    protected abstract void setupCustomLayoutManagerFactory();

    protected abstract void witchWorkForShowForm();

    protected abstract void clickRowRecycleFactoryMethod(int comboFormClickType, String someValue);

    protected abstract void setDataFromSearchAbleSpinner(@Nullable RealmModel realmModel, @Nullable String item, int position, int commonComboClickType);

    protected abstract void checkData();

    protected abstract void workNextClick();

    protected abstract void workDeleteClick();


}
