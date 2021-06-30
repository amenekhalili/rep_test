package ir.fararayaneh.erp.utils.data_handler;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.GetFormCodeQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetFormIdQuery;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class FormIdFromFormCodeHandler {

    public static Disposable getFormId(String formCode, IFormIdListener iformIdListener) {

        GetFormIdQuery getFormIdQuery =(GetFormIdQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_FORM_ID_QUERY);
        GlobalModel globalModel=new GlobalModel();
        globalModel.setMyString(formCode);
        assert getFormIdQuery != null;

        return getFormIdQuery.data(globalModel).subscribe(iModels -> {
            if(iformIdListener!=null){
                iformIdListener.onGetData(globalModel.getMyString2());
            }
        }, throwable -> {
            if(iformIdListener!=null){
                iformIdListener.onError();
            }
            ThrowableLoggerHelper.logMyThrowable("FormIdFromFormCodeHandler/getFormId "+throwable.getMessage());
        });

    }

    public static Disposable getFormCode(String formId, IFormCodeListener iFormCodeListener) {

        GetFormCodeQuery getFormCodeQuery=(GetFormCodeQuery)FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.GET_FORM_CODE_QUERY);
        GlobalModel globalModel=new GlobalModel();
        globalModel.setMyString(formId);
        assert getFormCodeQuery != null;
        return getFormCodeQuery.data(globalModel).subscribe(iModels -> {
            if(iFormCodeListener!=null){
                iFormCodeListener.onGetData(globalModel.getMyString());
            }
        }, throwable -> {
            if(iFormCodeListener!=null){
                iFormCodeListener.onError();
            }
            ThrowableLoggerHelper.logMyThrowable("FormIdFromFormCodeHandler/getFormCode "+throwable.getMessage());
        });
    }

}
