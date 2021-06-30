package ir.fararayaneh.erp.data.data_providers.offline;

import java.util.ArrayList;
import java.util.List;

import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.data.data_providers.IDataProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.middle.ShowAppConfigsModel;

public class ConfigsListDataProvider implements IDataProvider<List<ShowAppConfigsModel>> {


    @Override
    public List<ShowAppConfigsModel> data(IModels iModels) {
        ArrayList<ShowAppConfigsModel> list = new ArrayList<>();
        list.add(new ShowAppConfigsModel(R.string.server_configs, R.string.server_configs_detail, R.drawable.ic_connection));
        list.add(new ShowAppConfigsModel(R.string.theme_configs, R.string.theme_configs_detail, R.drawable.ic_paint));
        list.add(new ShowAppConfigsModel(R.string.calendar_configs, R.string.calendar_configs_detail, R.drawable.ic_calendar));
        return list;
    }
}
