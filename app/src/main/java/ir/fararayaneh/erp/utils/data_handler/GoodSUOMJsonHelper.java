package ir.fararayaneh.erp.utils.data_handler;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.middle.GoodSUOMModel;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class GoodSUOMJsonHelper {

    public static ArrayList<GoodSUOMModel> getGoodSUOMArray(String goodSUOMJson) {

        ArrayList<GoodSUOMModel> goodSUOMArrayList = new ArrayList<>();
        if(!goodSUOMJson.equals(Commons.NULL_ARRAY) ){
            try {
                JSONArray jsonArray = new JSONArray(goodSUOMJson);
                for (int i = 0; i < jsonArray.length(); i++) {
                    GoodSUOMModel goodSUOMModel = App.getmGson().fromJson(jsonArray.get(i).toString(), GoodSUOMModel.class);
                    goodSUOMArrayList.add(goodSUOMModel);
                }
            } catch (JSONException e) {
                ThrowableLoggerHelper.logMyThrowable("GoodSUOMJsonHelper+goodSUOMArrayList "+e.getMessage());
            }
        }

        return goodSUOMArrayList;
    }

    public static String getGoodSuomUnitName(ArrayList<GoodSUOMModel> goodSUOMModelArrayList,String unitId){
        final String[] unitName = {Commons.NULL};
        Stream.of(goodSUOMModelArrayList).forEach(goodSUOMModel -> {
            if(unitId.equals(goodSUOMModel.getC5UnitId())){
                unitName[0] = goodSUOMModel.getUnitName();
            }
        });
        return unitName[0];
    }

    public static int getGoodSuomPosition(ArrayList<GoodSUOMModel> goodSUOMModelArrayList,String unitName){
        int position= 0;//by defualt we send 0 az row, because we sure we have minimum one row in goodSUOMJson
        for (int i = 0; i < goodSUOMModelArrayList.size(); i++) {
            if(unitName.equals(goodSUOMModelArrayList.get(i).getUnitName())){
                position =i;
            }
        }

        return position;
    }

    public static GoodSUOMModel getGoodSuomModel(ArrayList<GoodSUOMModel> goodSUOMModelArrayList,int position){
        return goodSUOMModelArrayList.get(position);//we sure we have minimum 1 row (main unit row)
    }

    public static GoodSUOMModel getGoodSuomModel(ArrayList<GoodSUOMModel> goodSUOMModelArrayList,String unitName){

        final GoodSUOMModel[] selectedGoodSUOMModel = new GoodSUOMModel[1];
        Stream.of(goodSUOMModelArrayList).forEach(goodSUOMModel -> {
            if(unitName.equals(goodSUOMModel.getUnitName())){
                selectedGoodSUOMModel[0] = goodSUOMModel;
            }
        });
        return selectedGoodSUOMModel[0];
    }

    public static ArrayList<String> getGoodSuomUnitNameList(ArrayList<GoodSUOMModel> goodSUOMModelArrayList){

        ArrayList<String> unitNameList = new ArrayList<>();
        Stream.of(goodSUOMModelArrayList).forEach(goodSUOMModel -> unitNameList.add(goodSUOMModel.getUnitName()));
        return unitNameList;
    }

    public static String goodSUOMJson(ArrayList<GoodSUOMModel> goodSUOMModelArrayList) {
        return App.getmGson().toJson(goodSUOMModelArrayList);
    }
}
