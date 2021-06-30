package ir.fararayaneh.erp.utils.intent_handler;

import android.app.Activity;


import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.utils.data_handler.FormIdFromFormCodeHandler;
import ir.fararayaneh.erp.utils.data_handler.IFormIdListener;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;

import static ir.fararayaneh.erp.commons.CommonsFormCode.ADD_EDIT_ONE_GOOD_TO_GOOD_TRANS;
import static ir.fararayaneh.erp.commons.CommonsFormCode.BUYER_REQUEST_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.BUYER_REQUEST_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.BUY_SERVICES_INVOICE_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.BUY_SERVICES_INVOICE_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.CARTABLE_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.DEVICE_TIME_SHEET_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.FUEL_DETAIL_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.FUEL_DETAIL_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.FUEL_MASTER_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.GOODS_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.PERMANENT_RECEIPT_GOODS_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.PRODUCTION_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.PRODUCTION_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.RECEIPT_GOODS_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.RECEIPT_GOODS_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.SELECT_GOODS_FOR_ORDER_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.SHEET_PLAN_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.TASK_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.TASK_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.TIME_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.TIME_SHEET_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.WAREHOUSE_HANDLING_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.WAREHOUSE_TRANSFERENCE_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.WEIGHTING_FORM_CODE;
import static ir.fararayaneh.erp.commons.CommonsFormCode.WEIGHTING_REPORT_FORM_CODE;
import static ir.fararayaneh.erp.utils.intent_handler.ActivityIntents.goFuelListMasterActivity;
import static ir.fararayaneh.erp.utils.intent_handler.ActivityIntents.goGoodTrancActivityForResult;
import static ir.fararayaneh.erp.utils.intent_handler.ActivityIntents.goGoodForGoodTransActivityForResult;
import static ir.fararayaneh.erp.utils.intent_handler.ActivityIntents.goReportActForResult;
import static ir.fararayaneh.erp.utils.intent_handler.ActivityIntents.goSelectionActForResult;
import static ir.fararayaneh.erp.utils.intent_handler.ActivityIntents.goTaskActivityForResult;
import static ir.fararayaneh.erp.utils.intent_handler.ActivityIntents.goTimeActivityForResult;
import static ir.fararayaneh.erp.utils.intent_handler.ActivityIntents.goWarehouseActivityForResult;
import static ir.fararayaneh.erp.utils.intent_handler.ActivityIntents.goWeighingActivityForResult;

/**
 * these methods should be called only from presenter layer
 * <p>
 * با توجه به این موضوع که هر اکتیوتی که مسوول نمایش فرم است
 * بین یک تا n فرم کد با فرم های تقریبا مشابه را باید هندل نماید
 * این عمل در این جا هندل میشود
 *
 * باید مدل اینتنت  مورد نیاز قبل از اینجا ساخته شده و دارای فرم کد بوده و به اینجا ارسال شود
 *
 * activity : start activity
 *
 * needValidationFormCode : do we should check we have that formCode or not
 * if needValidationFormCode=true ==>>iFormIdListener !=null
 */
public class ActivityIntentFactoryHandler {
    // TODO: 2/2/2019 اضافه کردن بقیه فرم کد ها
    // TODO: 2/2/2019 ,و نال کردن لیستنر IFormIdListener
    public static void goToProperActivity(Activity activity, String formCode,  IntentModel intentModel,boolean shouldFinishOriginActivity,boolean needValidationFormCode) {
        if(needValidationFormCode){
            FormIdFromFormCodeHandler.getFormId(formCode, new IFormIdListener() {
                @Override
                public void onGetData(String formId) {
                    if(!formId.equals(Commons.NULL_INTEGER)){
                        //do not finish act
                        goToProperActivity(activity,formCode,intentModel,shouldFinishOriginActivity,false);
                    } else {
                        //do not finish act
                        ToastMessage.show(activity,activity.getResources().getString(R.string.not_have_access));
                    }
                }
                @Override
                public void onError() {
                    //do not finish act
                    ToastMessage.show(activity,activity.getResources().getString(R.string.some_problem_error));
                }
            });
        } else {
            switch (formCode) {
                case TIME_SHEET_FORM_CODE:
                case SHEET_PLAN_FORM_CODE:
                case DEVICE_TIME_SHEET_FORM_CODE:
                    goTimeActivityForResult(activity,intentModel);
                    break;
                case TASK_FORM_CODE:
                    goTaskActivityForResult(activity,intentModel);
                    break;
                case WAREHOUSE_HANDLING_FORM_CODE:
                    goWarehouseActivityForResult(activity,intentModel);
                    break;
                case WEIGHTING_FORM_CODE:
                    goWeighingActivityForResult(activity,intentModel);
                    break;
                case CARTABLE_REPORT_FORM_CODE:
                case TASK_REPORT_FORM_CODE:
                case TIME_REPORT_FORM_CODE:
                case WEIGHTING_REPORT_FORM_CODE:
                case GOODS_REPORT_FORM_CODE:
                case REQUEST_GOODS_FROM_WAREHOUSE_REPORT_FORM_CODE:
                case PERMANENT_RECEIPT_GOODS_REPORT_FORM_CODE:
                case WAREHOUSE_TRANSFERENCE_REPORT_FORM_CODE:
                case RECEIPT_GOODS_REPORT_FORM_CODE:
                case BUY_SERVICES_INVOICE_REPORT_FORM_CODE:
                case BUYER_REQUEST_REPORT_FORM_CODE:
                case GOODS_DELIVERY_PROCEEDINGS_REPORT_FORM_CODE:
                case PRODUCTION_REPORT_FORM_CODE:
                case FUEL_MASTER_REPORT_FORM_CODE:
                case FUEL_DETAIL_REPORT_FORM_CODE:
                    goReportActForResult(activity,intentModel);
                    break;
                case SELECT_GOODS_FOR_ORDER_FORM_CODE:
                    goSelectionActForResult(activity,intentModel);
                    break;
                case REQUEST_GOODS_FROM_WAREHOUSE_FORM_CODE:
                case BUYER_REQUEST_FORM_CODE:
                case GOODS_DELIVERY_PROCEEDINGS_FORM_CODE:
                case PRODUCTION_FORM_CODE:
                case PERMANENT_RECEIPT_GOODS_FORM_CODE :
                case WAREHOUSE_TRANSFERENCE_FORM_CODE :
                case RECEIPT_GOODS_FORM_CODE :
                case BUY_SERVICES_INVOICE_FORM_CODE :
                    goGoodTrancActivityForResult(activity,intentModel);
                    break;
                case ADD_EDIT_ONE_GOOD_TO_GOOD_TRANS:
                    goGoodForGoodTransActivityForResult(activity,intentModel);
                    break;
                case FUEL_DETAIL_FORM_CODE:
                    goFuelListMasterActivity(activity,intentModel);
                    break;
            }
            finishActivity(activity,shouldFinishOriginActivity);
        }

    }



    private static void finishActivity(Activity activity,boolean shouldFinishOriginActivity) {
        if(shouldFinishOriginActivity){
            activity.finish();
        }
    }

}
