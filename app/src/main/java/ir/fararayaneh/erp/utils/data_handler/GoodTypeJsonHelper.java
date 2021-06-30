package ir.fararayaneh.erp.utils.data_handler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.models.middle.GoodTransDetailsModel;
import ir.fararayaneh.erp.data.models.middle.GoodTypeModel;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class GoodTypeJsonHelper {

    public static ArrayList<GoodTypeModel> getGoodTypeArray(String goodTypeJson) {

        ArrayList<GoodTypeModel> goodTypeModelsList = new ArrayList<>();

        try {
            JSONArray jsonArray=new JSONArray(goodTypeJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                GoodTypeModel goodTypeModel = App.getmGson().fromJson(jsonArray.get(i).toString(), GoodTypeModel.class);
                goodTypeModelsList.add(goodTypeModel);
            }
        } catch (JSONException e) {
            ThrowableLoggerHelper.logMyThrowable("GoodTypeJsonHelper+getGoodTypeArray "+e.getMessage());
        }
        return goodTypeModelsList;
    }


    public static String goodTypeJson(ArrayList<GoodTypeModel> goodTypeModelArrayList) {
        return App.getmGson().toJson(goodTypeModelArrayList);
    }
}
