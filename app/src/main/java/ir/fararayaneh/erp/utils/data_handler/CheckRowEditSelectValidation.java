package ir.fararayaneh.erp.utils.data_handler;

import java.util.ArrayList;

import ir.fararayaneh.erp.commons.CommonActivityState;
import ir.fararayaneh.erp.commons.CommonB5HCStatusCode;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.CommonViewTypeReport;

public class CheckRowEditSelectValidation {


    public static boolean checkEditValidation(ArrayList<String> b5hcStatusIdList,ArrayList<String> b5hcStatusNameList,String b5HCStatusId, String syncState, String activityState) {

        String b5HCStatusCode = CommonB5HCStatusCode.FINALIZED;
        if (b5hcStatusIdList.contains(b5HCStatusId)) {
            b5HCStatusCode = b5hcStatusNameList.get(b5hcStatusIdList.indexOf(b5HCStatusId));
        }
        return (b5HCStatusCode.equals(CommonB5HCStatusCode.DRAFT) || b5HCStatusCode.equals(CommonB5HCStatusCode.DURING)) && !syncState.equals(CommonSyncState.ACCESS_DENIED )&& !activityState.equals(CommonActivityState.DELETE);
    }

    //if  view is accessdenied or deleted, can not be selected
    public static boolean isRowSelectAble(int viewType) {
        return !(viewType == CommonViewTypeReport.ACCESS_DENIED_VIEW_TYPE
                ||
                viewType == CommonViewTypeReport.DELETED_VIEW_TYPE);
    }

}
