package ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries;

import java.util.Objects;

import io.realm.RealmResults;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListDetailTable;

public class GetRowFuelDetailByGUIDQuery extends BaseRowByGuidQuery {

    private FuelListDetailTable fuelListDetailTable = new FuelListDetailTable();

    @Override
    protected IModels getProperModel() {
        return fuelListDetailTable;
    }

    @Override
    protected void getRowByGuid(RealmResults realmResults) {
        if (realmResults.size() != 0) {//==> if have not that row, we send a new table by default values
            FuelListDetailTable fuelListDetailTableProxy = (FuelListDetailTable) realmResults.get(0);
            fuelListDetailTable = new FuelListDetailTable(
                    Objects.requireNonNull(fuelListDetailTableProxy).getId(),
                    fuelListDetailTableProxy.getB5HCDistributionId(),
                    fuelListDetailTableProxy.getPlaceName(),
                    fuelListDetailTableProxy.getDeviceName(),
                    fuelListDetailTableProxy.getDistributionDate(),
                    fuelListDetailTableProxy.getAmount(),
                    fuelListDetailTableProxy.getDescription(),
                    fuelListDetailTableProxy.getMasterId(),
                    fuelListDetailTableProxy.getLocation(),
                    fuelListDetailTableProxy.getSyncState(),
                    fuelListDetailTableProxy.getActivityState(),
                    fuelListDetailTableProxy.getOldValue()
            );
        }
    }
}
