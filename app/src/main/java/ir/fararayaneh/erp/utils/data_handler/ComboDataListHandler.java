package ir.fararayaneh.erp.utils.data_handler;

import android.util.Log;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonUtilCode;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsFormCode;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.BaseCodingNameListFromBaseCodingListId;
import ir.fararayaneh.erp.data.data_providers.queries.GetComboIdCodeFromUtilCodeQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetComboIdNameFromBaseCoddingQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetComboIdNameFromKartableRecieverQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetComboIdNameFromUtilCodeQuery;
import ir.fararayaneh.erp.data.data_providers.queries.UtilCodeNameListFromUtilCodeListId;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * این کلاس آی دی و اسامی قابل نمایش
 * درون کمبو باکس های یک فرم را دریافت نموده و آنرا بر میگرداند
 * چه برای کویری
 * GetComboIdNameFromBaseCoddingQuery
 * چه برای کویری
 * GetComboIdNameFromUtilCodeQuery
 * چه برای کویری
 * * GetComboIdNameFromKartableRecieverQuery
 */
public class ComboDataListHandler {

    public static Disposable getBaseCoddingData(String formCode, IComboListener iComboListener) {
        GetComboIdNameFromBaseCoddingQuery getComboIdNameFromBaseCoddingQuery = (GetComboIdNameFromBaseCoddingQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_COMBO_ID_NAME_FROM_BASE_CODDING_QUERY);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setMyString(formCode);
        assert getComboIdNameFromBaseCoddingQuery != null;
        return getComboIdNameFromBaseCoddingQuery.data(globalModel).subscribe(iModels -> {
            if (iComboListener != null) {
                iComboListener.onData(globalModel.getListStringArrayList(), globalModel.getListStringArrayList2());
            }
            Log.i("arash", "accept:string Base id** " + globalModel.getListStringArrayList2().toString());
            Log.i("arash", "accept:string Base name** " + globalModel.getListStringArrayList().toString());
        }, throwable -> {
            if (iComboListener != null) {
                iComboListener.onError();
                ThrowableLoggerHelper.logMyThrowable("ComboDataListHandler//getBaseCoddingData***" + throwable.getMessage());
            }
        });
    }

    public static Disposable getUtilCoddingData(String formCode, IComboListener iComboListener) {
        GetComboIdNameFromUtilCodeQuery getComboIdNameFromUtilCodeQuery = (GetComboIdNameFromUtilCodeQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_COMBO_ID_NAME_FROM_UTIL_CODE_QUERY);
        GlobalModel globalModel = new GlobalModel();
        globalModel.setStringArrayList(getProperCodeUtilCode(formCode));

        assert getComboIdNameFromUtilCodeQuery != null;
        return getComboIdNameFromUtilCodeQuery.data(globalModel).subscribe(iModels -> {
            if (iComboListener != null) {
                iComboListener.onData(globalModel.getListStringArrayList(), globalModel.getListStringArrayList2());
            }
            Log.i("arash", "accept:string Util id" + globalModel.getListStringArrayList2().toString());
            Log.i("arash", "accept:string Util name" + globalModel.getListStringArrayList().toString());
        }, throwable -> {
            if (iComboListener != null) {
                iComboListener.onError();
            }
            ThrowableLoggerHelper.logMyThrowable("ComboDataListHandler//getUtilCoddingData***" + throwable.getMessage());
        });

    }

    public static Disposable getOneUtilCoddingData(String utilCode, IComboListener iComboListener) {
        GetComboIdCodeFromUtilCodeQuery getComboIdCodeFromUtilCodeQuery = (GetComboIdCodeFromUtilCodeQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_COMBO_ID_CODE_FROM_UTIL_CODE_QUERY);
        GlobalModel globalModel = new GlobalModel();
        ArrayList<String> stringArrayList = new ArrayList<>(1);
        stringArrayList.add(utilCode);
        globalModel.setStringArrayList(stringArrayList);

        assert getComboIdCodeFromUtilCodeQuery != null;
        return getComboIdCodeFromUtilCodeQuery.data(globalModel).subscribe(iModels -> {
            if (iComboListener != null) {
                iComboListener.onData(globalModel.getListStringArrayList(), globalModel.getListStringArrayList2());
            }
            Log.i("arash", "accept:string Util id" + globalModel.getListStringArrayList2().toString());
            Log.i("arash", "accept:string Util code" + globalModel.getListStringArrayList().toString());
        }, throwable -> {
            if (iComboListener != null) {
                iComboListener.onError();
            }
            ThrowableLoggerHelper.logMyThrowable("getOneUtilCoddingData//getUtilCoddingData***" + throwable.getMessage());
        });

    }

    /**
     * برای ایجاد لیست مناسب جهت استفاده در متد
     * getUtilCoddingData
     */
    private static ArrayList<String> getProperCodeUtilCode(String formCode) {
        // TODO: 2/2/2019 تکمیل متد برای بقیه فرم کد ها که از روش آرایه میخواهند دیتا بگیرند
        ArrayList<String> codeList = new ArrayList<>();
        switch (formCode) {
            case CommonsFormCode.TIME_SHEET_FORM_CODE:
            case CommonsFormCode.SHEET_PLAN_FORM_CODE:
            case CommonsFormCode.DEVICE_TIME_SHEET_FORM_CODE:
                codeList.add(CommonUtilCode.DAS);
                codeList.add(CommonUtilCode.WTT);
                //codeList.add(CommonUtilCode.VCS);//باید حذف شود چون یوزر نمیتواند آنرا انتخاب نماید
                break;
            case CommonsFormCode.TASK_FORM_CODE:
                codeList.add(CommonUtilCode.PRY);
                codeList.add(CommonUtilCode.TAT);
                //codeList.add(CommonUtilCode.VCS);//باید حذف شود چون یوزر نمیتواند آنرا انتخاب نماید
                break;
            case CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
            case CommonsFormCode.PERMANENT_RECEIPT_GOODS_FORM_CODE:
            case CommonsFormCode.WAREHOUSE_TRANSFERENCE_FORM_CODE:
            case CommonsFormCode.RECEIPT_GOODS_FORM_CODE:
            case CommonsFormCode.BUY_SERVICES_INVOICE_FORM_CODE:
            case CommonsFormCode.BUYER_REQUEST_FORM_CODE:
            case CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
            case CommonsFormCode.PRODUCTION_FORM_CODE:
                codeList.add(CommonUtilCode.CUR);
                codeList.add(CommonUtilCode.ACS);
                codeList.add(CommonUtilCode.SEM);
                //codeList.add(CommonUtilCode.VCS);//باید حذف شود چون یوزر نمیتواند آنرا انتخاب نماید
                break;
            case CommonsFormCode.FUEL_DETAIL_FORM_CODE:
                codeList.add(CommonUtilCode.DCF);
                break;
        }
        return codeList;
    }


    public static Disposable getKartableReceiverData(IComboListener iComboListener) {

        GetComboIdNameFromKartableRecieverQuery getComboIdNameFromKartableRecieverQuery = (GetComboIdNameFromKartableRecieverQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_COMBO_ID_NAME_FROM_KARTABLE_RECIEVER_QUERY);
        assert getComboIdNameFromKartableRecieverQuery != null;
        return getComboIdNameFromKartableRecieverQuery.data(App.getNullRXModel()).subscribe(iModels -> {
            GlobalModel globalModel = (GlobalModel) iModels;
            if (iComboListener != null) {
                iComboListener.onData(globalModel.getListStringArrayList(), globalModel.getListStringArrayList2());

                Log.i("arash", "accept:string Kartable name" + globalModel.getListStringArrayList().toString());
                Log.i("arash", "accept:string Kartable id" + globalModel.getListStringArrayList2().toString());
            }
        }, throwable -> {
            if (iComboListener != null) {
                iComboListener.onError();
            }
            ThrowableLoggerHelper.logMyThrowable("ComboDataListHandler/getKartableReceiverData***" + throwable.getMessage());
        });


    }

    public static Disposable getNameListFromIdListBaseCoding(ArrayList<String> idList, IComboIdNameListener iComboIdNameListener) {

        GlobalModel globalModel = new GlobalModel();
        globalModel.setStringArrayList(idList);
        BaseCodingNameListFromBaseCodingListId baseCodingListId = (BaseCodingNameListFromBaseCodingListId)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.BASE_CODING_NAME_LIST_FROM_BASE_CODING_LIST_ID);
        assert baseCodingListId != null;
        return baseCodingListId.data(globalModel)
                .subscribe(iModels -> {
                    if (iComboIdNameListener != null) {
                        iComboIdNameListener.onData(globalModel.getStringArrayList2());
                    }
                }, throwable -> {
                    if (iComboIdNameListener != null) {
                        iComboIdNameListener.onError();
                    }
                    ThrowableLoggerHelper.logMyThrowable("ComboDataListHandler/getNameListFromIdListBaseCoding***" + throwable.getMessage());
                });
    }

    public static Disposable getNameListFromIdListUtilCode(ArrayList<String> idList, IComboIdNameListener iComboIdNameListener) {

        GlobalModel globalModel = new GlobalModel();
        globalModel.setStringArrayList(idList);
        UtilCodeNameListFromUtilCodeListId utilCodeListId = (UtilCodeNameListFromUtilCodeListId)
                FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.UTIL_CODE_NAME_LIST_FROM_UTIL_CODE_LIST_ID);
        assert utilCodeListId != null;
        return utilCodeListId.data(globalModel)
                .subscribe(iModels -> {
                    if (iComboIdNameListener != null) {
                        iComboIdNameListener.onData(globalModel.getStringArrayList2());
                    }
                }, throwable -> {
                    if (iComboIdNameListener != null) {
                        iComboIdNameListener.onError();
                    }
                    ThrowableLoggerHelper.logMyThrowable("getNameListFromIdListUtilCode/getNameListFromIdListBaseCoding***" + throwable.getMessage());
                });
    }

}
