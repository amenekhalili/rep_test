package ir.fararayaneh.erp.activities.update_tables;

import android.content.Context;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import ir.fararayaneh.erp.IBase.common_base.BasePresenterWithRecycle;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.adaptors.update_table_adaptor.UpdateTableRecAdaptor;
import ir.fararayaneh.erp.commons.CommonNetRequest;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.offline.PrimitiveProgressListDataProvider;
import ir.fararayaneh.erp.data.data_providers.online.INetDataProviderListener;
import ir.fararayaneh.erp.data.data_providers.online.NetDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.InsertJsonQuery;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.data.models.online.response.RestResponseModel;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;

/**
 * این اکتیویتی برای دریافت همه جداول مورد نیاز برنامه میباشد
 * pageNumber start from 1 in server
 * listProgressSize : row number of recyclerView
 * currentRow : current row that getting data from net
 * دیتا در این اکتیویتی به جدول اضافه میشود...اینکه قبل از این عملیات دیتا باید پاک شود یا نه وظیفه اکتیویتی قبلی است
 * در صورتی که در هنگام دریافت هر کدام از صفحات دیتا در  هر کدام از ردیف های ریسایکلر، خطایی رخ دهد
 * به کاربر خطای همه ردیف های بعد از آن ردیف و آن ردیف داده میشود
 * هرچند ممکن است که برخی ردیف ها در دیتابیس به صورت کامل ذخیره شده باشد
 * وظیفه هندل کردن این خطا هم به عهده اکتیوتی مبدا است
 * مثلا در
 * login
 * تا زمانی که ریزالت اوکی از این اکتیویتی دریافت نکنیم از این مرحله عبور نمیکند و دوباره به همین اکتیویتی بر میگردد و قبل از ورود هم دیتا دریافت شده را هم پاک میکند
 */
public class UpdateTablesPresenter extends BasePresenterWithRecycle<UpdateTablesView> {


    private int pageNumber = 1, listProgressSize, currentRow = -1;
    private UpdateTableRecAdaptor updateTableRecAdaptor = new UpdateTableRecAdaptor(context);
    private IntentModel mIntentModel;
    private NetDataProvider netDataProvider;
    private boolean errorSituation;
    private INetDataProviderListener iNetDataProviderListener;
    private IIOSDialogeListener iiosDialogeListener;

    public UpdateTablesPresenter(WeakReference<UpdateTablesView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }

    @Override
    protected UserMessageModel makeProperShowDialogueData(int dialogueType, int CommonComboClickType, String someValue) {
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
    public void click() {

    }

    @Override
    public void onCreate(IntentModel intentModel) {
        mIntentModel = intentModel;
        listProgressSize = mIntentModel.getRequestModelList().size();
        super.onCreate(intentModel);
        Log.i("arash", "onCreate: update table:" + listProgressSize);
    }

    @Override
    protected void setRecyclAdaptor() {
        if (checkNull()) {
            getView().setRecycleAdaptor(updateTableRecAdaptor);
        }
    }

    @Override
    protected void workForLoadPage() {
        setPrimitiveData();
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
        if (checkNull()) {
            corruptDownload();
        }

    }

    //---------------------------------------------------------------------------------------->>
    private void corruptDownload() {
        if (!errorSituation) {
            iiosDialogeListener = new IIOSDialogeListener() {
                @Override
                public void confirm() {
                    cancelResultIntent();
                }

                @Override
                public void cancle() {

                }
            };
            getView().showCorruptDownloadLayout(iiosDialogeListener);
            //unRegisterListener.add(iFancyDialogeListener);

        } else {
            cancelResultIntent();
        }
    }


    @Override
    protected void ClearAllPresenterListener() {
        iNetDataProviderListener = null;
        iiosDialogeListener = null;
    }

    private void setPrimitiveData() {
        PrimitiveProgressListDataProvider progressListDataProvider = (PrimitiveProgressListDataProvider)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.PRIMITIVE_PROGRESSLIST_DATAPROVIDER);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setCount(listProgressSize);
        assert progressListDataProvider != null;
        updateTableRecAdaptor.setDataModelList(progressListDataProvider.data(globalModel));
        getDataForAllRows();
    }

    private void getDataForAllRows() {
        currentRow++;
        pageNumber = 1;
        if (currentRow < listProgressSize) {
            getDataFromNet();
        } else {
            resultFromUpdateTableAct();
        }
    }

    private void getDataFromNet() {

        if (netDataProvider == null) {
            netDataProvider = (NetDataProvider) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.NET_DATA_PROVIDER);
        }
        setProperNameToList(context.getResources().getString(R.string.downloading) + mIntentModel.getRequestModelList().get(currentRow).getKind().toLowerCase(), currentRow);
        //setProperPercent(0, currentRow);
        iNetDataProviderListener = new INetDataProviderListener() {
            @Override
            public void uploadProgressListener(int upLoad) {
                //do nothings
            }

            @Override
            public void downloadProgressListen(int downLoad) {
                //setProperPercent(downLoad, currentRow);
            }

            @Override
            public void responce(RestResponseModel response) {

                if (response.getItems().size() != 0) {
                    setProperNameToList(context.getResources().getString(R.string.saving) + mIntentModel.getRequestModelList().get(currentRow).getKind().toLowerCase(), currentRow);
                    addDisposable(addToTable(response));
                    int currentPercent = 100 * (pageNumber * Integer.valueOf(CommonNetRequest.DEFAULT_PAGE_ITEM)) / response.getItems().get(0).getTotalCount();
                    int myPercent = 100;
                    if (currentPercent < 100) {
                        myPercent = currentPercent;
                    }
                    setProperPercent(myPercent, currentRow);

                } else {
                    setProperPercent(100, currentRow);
                    setProperNameToList(context.getResources().getString(R.string.done), currentRow);
                    getDataForAllRows();
                }

            }

            @Override
            public void error(String error) {
                workForAnyError(error);
            }
        };
        //unRegisterListener.add(iNetDataProviderListener);
        netDataProvider.data(mIntentModel.getRequestModelList().get(currentRow), iNetDataProviderListener);

    }

    private Disposable addToTable(RestResponseModel response) {

        InsertJsonQuery insertJsonQuery = (InsertJsonQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.INSERT_JSON_QUERY);
        GlobalModel globalModel = new GlobalModel();
        ArrayList<Class> classArrayList = new ArrayList<>(1);
        classArrayList.add(mIntentModel.getClassArrayListForUpdate().get(currentRow));
        globalModel.setClassArrayList(classArrayList);
        globalModel.setRestResponseModel(response);

        assert insertJsonQuery != null;
        return insertJsonQuery.data(globalModel).subscribe(iModels -> {
            if (globalModel.getCount() < response.getItems().get(0).getTotalCount()) {
                pageNumber++;
                mIntentModel.getRequestModelList().get(currentRow).setPageNo(String.valueOf(pageNumber));
                getDataFromNet();
            } else {
                setProperNameToList(context.getResources().getString(R.string.done), currentRow);
                getDataForAllRows();
            }
        }, throwable -> workForAnyError(throwable.getMessage()));

    }

    private void workForAnyError(String error) {
        errorSituation = true;
        for (int i = currentRow; i < listProgressSize; i++) {
            setProperNameToList(context.getResources().getString(R.string.error) + "..." + mIntentModel.getRequestModelList().get(currentRow).getKind().toLowerCase(), i);
            setProperPercent(0, i);
        }
        if (checkNull()) {
            getView().showMessageSomeProblems(CommonsLogErrorNumber.error_48);
        }
        ThrowableLoggerHelper.logMyThrowable("UpdateTablePresenter/workForAnyError***" + error);
    }

    private void setProperPercent(int percent, int position) {
        if (updateTableRecAdaptor != null) {
            if (checkNull()) {
                updateTableRecAdaptor.updatePercentage(position, percent);
            }
        }
    }

    private void setProperNameToList(String name, int position) {
        if (updateTableRecAdaptor != null) {
            if (checkNull()) {
                updateTableRecAdaptor.updateName(position, name);
            }
        }
    }


}
