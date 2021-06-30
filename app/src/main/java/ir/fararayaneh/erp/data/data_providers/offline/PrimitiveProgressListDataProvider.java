package ir.fararayaneh.erp.data.data_providers.offline;

import java.util.ArrayList;
import java.util.List;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.data.data_providers.IDataProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.middle.ProgressPercentModel;

/**
 * primitive data for show in progress list in UpdateTableActivity
 */
public class PrimitiveProgressListDataProvider implements IDataProvider<List<ProgressPercentModel>> {

    @Override
    public List<ProgressPercentModel> data(IModels iModels) {

        GlobalModel globalModel = (GlobalModel) iModels;
        ArrayList<ProgressPercentModel> list=new ArrayList<>();
        for (int i = 0; i <globalModel.getCount() ; i++) {
            ProgressPercentModel progressPercentModel =new ProgressPercentModel();
            int START_PERCENTAGE = 0;
            progressPercentModel.setCurrentPercentage(START_PERCENTAGE);
            progressPercentModel.setName(App.getAppContext().getString(R.string.preparing));
            list.add(progressPercentModel);
        }
        return list;
    }
}