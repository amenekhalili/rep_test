package ir.fararayaneh.erp.utils.data_handler;

import android.content.Context;
import java.util.Locale;
import java.util.Objects;
import io.reactivex.disposables.Disposable;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonTimeZone;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.online.INetDataProviderListener;
import ir.fararayaneh.erp.data.data_providers.online.NetDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.CheckPassWordQuery;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.online.response.Body;
import ir.fararayaneh.erp.data.models.online.response.RestResponseModel;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;

public class CheckPassWordHandler {

    public static Disposable offLineChecking(GlobalModel globalModel, ICheckPassListener iCheckPassListener) {


        CheckPassWordQuery checkPassWordQuery = (CheckPassWordQuery) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.CHECK_PASSWORD_QUERY);
        assert checkPassWordQuery != null;
        return checkPassWordQuery.data(globalModel).subscribe(iModels -> {
            if (iCheckPassListener != null) {
                iCheckPassListener.checkPassword(globalModel.isMyBoolean(), null);
            }
        }, throwable -> {
            if (iCheckPassListener != null) {
                iCheckPassListener.error();
            }
            ThrowableLoggerHelper.logMyThrowable("CheckPassWordHandler***offLineChecking/" + throwable.getMessage());
        });
    }

    /**
     * only if we have deviceId , we can go to online login
     */
    public static void onLineChecking(String userName, String passWord, String organization, INetDataProviderListener iNetDataProviderListener) {
        if (!SharedPreferenceProvider.getDeviceId(App.getAppContext()).toUpperCase().equals(Commons.NULL_CAP)) {
            NetDataProvider netDataProvider = (NetDataProvider) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.NET_DATA_PROVIDER);
            assert netDataProvider != null;
            netDataProvider.data(RestReqDataHandler.loginRequest(userName, passWord, organization), iNetDataProviderListener);
        } else {
            ToastMessage.show(App.getAppContext(), App.getAppContext().getResources().getString(R.string.no_deveiceid_error));
        }

    }


    public static void saveApplicationConfig(Context context,RestResponseModel response) {
        Body body = response.getItems().get(0).getBody().get(0);
        SharedPreferenceProvider.setLocalLanguage(context, response.getItems().get(0).getLang().toLowerCase());
        //LocaleHelper.setLocale(context, response.getItems().get(0).getLang().toLowerCase());
        SharedPreferenceProvider.setCompanyName(context, body.getCompanyName());
        SharedPreferenceProvider.setNodeIp(context, body.getNodeServerIP());
        SharedPreferenceProvider.setNodePort(context, body.getNodeServerPort());
        SharedPreferenceProvider.setNodeRestServerPort(context, body.getNodeRestServerPort());
        SharedPreferenceProvider.setOnlineServerIp(context, body.getOnlineServerIP());
        SharedPreferenceProvider.setBaseAttachIp(context, body.getAttachBaseURL());
        SharedPreferenceProvider.setUserId(context, response.getItems().get(0).getUserId());
        SharedPreferenceProvider.setOrganization(context, response.getItems().get(0).getOrganization());
        SharedPreferenceProvider.setForceLocation(context, body.getForceLocation());
        SharedPreferenceProvider.setDisplayableWarehouseList(context, body.getDisplayableWarehouseList());
        SharedPreferenceProvider.setMonetaryUnitId(context, body.getMonetaryUnitId());
        SharedPreferenceProvider.setMonetaryUnitName(context, body.getMonetaryUnitName());
        SharedPreferenceProvider.setSellEmporiumId(context, body.getSellEmporiumId());
        SharedPreferenceProvider.setSellEmporiumName(context, body.getSellEmporiumName());
        SharedPreferenceProvider.setCountryDivissionID(context, body.getCountryDivisionId());
        SharedPreferenceProvider.setStatusIdDefualt(context, body.getStatusIdDefault());
        SharedPreferenceProvider.setStartFinancialDate(context, Objects.requireNonNull(CustomTimeHelper.stringDateToMyLocalDateConvertor(body.getFinancialStartDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE)).getTime());
        SharedPreferenceProvider.setEndFinancialDate(context, Objects.requireNonNull(CustomTimeHelper.stringDateToMyLocalDateConvertor(body.getFinancialEndDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE)).getTime());
        SharedPreferenceProvider.setTaxPercent(context, body.getTaxPercent());
        SharedPreferenceProvider.setPrecisionAmountLength(context, body.getPrecisionAmountLength());
        SharedPreferenceProvider.setPrecisionPriceLength(context, body.getPrecisionPriceLength());
    }
}
