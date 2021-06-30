package ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries;

import java.util.Objects;

import io.realm.RealmResults;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.GoodTranceTable;

public class GetRowGoodTranceByGUIDQuery extends BaseRowByGuidQuery {

    private GoodTranceTable goodTranceTable = new GoodTranceTable();
    @Override
    protected IModels getProperModel() {
        return goodTranceTable;
    }

    @Override
    protected void getRowByGuid(RealmResults realmResults) {
        if(realmResults.size()!=0){//==> if have not that row, we send a new table by default values
            GoodTranceTable goodTranceTableProxy =(GoodTranceTable)realmResults.get(0);
            goodTranceTable = new GoodTranceTable(Objects.requireNonNull(goodTranceTableProxy).getId(),goodTranceTableProxy.getB5FormRefId(),goodTranceTableProxy.getB5IdRefId1(),goodTranceTableProxy.getB5IdRefId2(),goodTranceTableProxy.getB5IdRefId3(),goodTranceTableProxy.getB5IdRefId4(),goodTranceTableProxy.getB5IdRefId5(),goodTranceTableProxy.getB5IdRefId6(),goodTranceTableProxy.getB5IdRefId14(),goodTranceTableProxy.getB5HCCurrencyId(),goodTranceTableProxy.getB5HCSellMethodId(),goodTranceTableProxy.getB5HCAccountSideId(),goodTranceTableProxy.getB5IdRefIdRecall(),goodTranceTableProxy.getB5HCStatusId(),goodTranceTableProxy.getDeliveryName(),goodTranceTableProxy.getFormNumber(),goodTranceTableProxy.getFormDate(),goodTranceTableProxy.getExpireDate(),goodTranceTableProxy.getDescription(),goodTranceTableProxy.getGuid(),goodTranceTableProxy.getSyncState(),goodTranceTableProxy.getActivityState(),goodTranceTableProxy.getOldValue(),goodTranceTableProxy.getGoodTransDetail(),goodTranceTableProxy.getDuration(),goodTranceTableProxy.getCustomerGroupId(),goodTranceTableProxy.getAddressId());
        }
    }
}
