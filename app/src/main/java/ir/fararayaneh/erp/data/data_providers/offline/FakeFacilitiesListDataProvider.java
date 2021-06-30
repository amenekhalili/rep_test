package ir.fararayaneh.erp.data.data_providers.offline;

import java.util.ArrayList;
import java.util.List;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.IDataProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.middle.FacilitiesModel;

public class FakeFacilitiesListDataProvider implements IDataProvider<List<FacilitiesModel>> {
    @Override
    public List<FacilitiesModel> data(IModels iModels) {
        ArrayList<FacilitiesModel> arrayList=new ArrayList<>();

            String title=App.getAppContext().getResources().getString(R.string.loading);
            for (int i = 0; i <20 ; i++) {
                FacilitiesModel facilitiesModel=new FacilitiesModel(title,"0",Commons.NULL,true);
                arrayList.add(facilitiesModel);
            }

        return arrayList;
    }
}
