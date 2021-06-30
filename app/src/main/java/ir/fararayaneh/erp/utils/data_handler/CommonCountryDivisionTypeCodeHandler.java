package ir.fararayaneh.erp.utils.data_handler;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;

public class CommonCountryDivisionTypeCodeHandler {

    public static String divisionName(int code){
        switch (code){
            case 1:
                return App.getAppContext().getResources().getString(R.string.continent);
            case 2:
                return App.getAppContext().getResources().getString(R.string.country);
            case 3:
                return App.getAppContext().getResources().getString(R.string.province);
            case 4:
                return App.getAppContext().getResources().getString(R.string.city);
            case 5:
                return App.getAppContext().getResources().getString(R.string.town);
            case 6:
                return App.getAppContext().getResources().getString(R.string.district);
            default:
                return App.getAppContext().getResources().getString(R.string.city);//by default send city


        }
    }
}
