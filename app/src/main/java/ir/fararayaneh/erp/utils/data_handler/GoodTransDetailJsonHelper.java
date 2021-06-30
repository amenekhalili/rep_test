package ir.fararayaneh.erp.utils.data_handler;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.data.models.middle.GoodTransDetailsModel;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class GoodTransDetailJsonHelper {

    public static ArrayList<GoodTransDetailsModel> getGoodTransDetailArray(String goodTranceDetailJson) {

        ArrayList<GoodTransDetailsModel> goodTransDetailsModelArrayList = new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(goodTranceDetailJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                GoodTransDetailsModel goodTransDetailsModel = App.getmGson().fromJson(jsonArray.get(i).toString(), GoodTransDetailsModel.class);
                goodTransDetailsModelArrayList.add(goodTransDetailsModel);
            }
        } catch (JSONException e) {
            ThrowableLoggerHelper.logMyThrowable("GoodTransDetailJsonHelper+getGoodTransDetailArray "+e.getMessage());
        }
        return goodTransDetailsModelArrayList;
    }

    public static String goodTransDetailsJson(ArrayList<GoodTransDetailsModel> goodTransDetailsModelArrayList) {
        return App.getmGson().toJson(goodTransDetailsModelArrayList);
    }

}
