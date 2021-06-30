package ir.fararayaneh.erp.utils.data_handler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.middle.AttachmentJsonModel;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * this class create jsonArray string in
 * format : "[{"attachmentGUID":"1a2b3","columnNumber":"123","B5HCTypeId":"123"},...]"
 * for insert in attach column of some table like message
 */
public class AttachJsonCreator {


    /**
     * all 3 lists have same size
     */

    public static String buildJson(ArrayList<String> attachmentNameList, ArrayList<String> columnNumberList, ArrayList<String> B5HCTypeIdList) {
        ArrayList<AttachmentJsonModel> attachmentJsonList = new ArrayList<>();
        for (int i = 0; i < attachmentNameList.size(); i++) {
            attachmentJsonList.add(new AttachmentJsonModel(attachmentNameList.get(i), columnNumberList.get(i), B5HCTypeIdList.get(i)));
        }

        return App.getmGson().toJson(attachmentJsonList);
    }

    public static ArrayList<String> getAttachmentNameList(String attachJson) {

        ArrayList<String> attachmentNameList = new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(attachJson);
            for (int i = 0; i < jsonArray.length(); i++) {
               AttachmentJsonModel attachmentJsonModel = App.getmGson().fromJson(jsonArray.get(i).toString(),AttachmentJsonModel.class);
                attachmentNameList.add(attachmentJsonModel.getAttachmentGUIDSuffix());
            }
        } catch (JSONException e) {
            ThrowableLoggerHelper.logMyThrowable("AttachJsonCreator+getAttachmentNameList"+e.getMessage());
        }
        return attachmentNameList;
    }

    public static ArrayList<String> getAttachColumnIdList(String attachJson) {

        ArrayList<String> attachColumnIdList = new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(attachJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                AttachmentJsonModel attachmentJsonModel = App.getmGson().fromJson(jsonArray.get(i).toString(),AttachmentJsonModel.class);
                attachColumnIdList.add(attachmentJsonModel.getColumnNumber());
            }
        } catch (JSONException e) {
            ThrowableLoggerHelper.logMyThrowable("AttachJsonCreator+getAttachColumnIdList"+e.getMessage());
        }
        return attachColumnIdList;
    }

    public static ArrayList<String> getAttachB5HCTypeIdList(String attachJson) {

        ArrayList<String> attachB5HCTypeIdList = new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(attachJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                AttachmentJsonModel attachmentJsonModel = App.getmGson().fromJson(jsonArray.get(i).toString(),AttachmentJsonModel.class);
                attachB5HCTypeIdList.add(attachmentJsonModel.getB5HCTypeId());
            }
        } catch (JSONException e) {
            ThrowableLoggerHelper.logMyThrowable("AttachJsonCreator+getAttachB5HCTypeIdList"+e.getMessage());
        }
        return attachB5HCTypeIdList;
    }

    public static String getFirstAttachName(String attachmentJson) {
        ArrayList<String> list = AttachJsonCreator.getAttachmentNameList(attachmentJson);
        if (list.size() != 0) {
            return list.get(0);
        }
        return Commons.NULL;
    }


}
