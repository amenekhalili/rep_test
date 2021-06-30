package ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries;

import io.realm.RealmResults;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CartableTable;

public class GetRowCartableByGUIDQuery extends BaseRowByGuidQuery {

    private CartableTable cartableTable = new CartableTable();
    @Override
    protected IModels getProperModel() {
        return cartableTable;
    }

    @Override
    protected void getRowByGuid(RealmResults realmResults) {
        if(realmResults.size()!=0){//==> if have not that row, we send a new table by default values
            CartableTable cartableTableProxy =(CartableTable)realmResults.get(0);
            cartableTable = new CartableTable(cartableTableProxy.getId(),cartableTableProxy.getInsertDate(),cartableTableProxy.getSubject(),cartableTableProxy.getB5HCPriorityName(),cartableTableProxy.getDefinitionName(),cartableTableProxy.getSenderName(),cartableTableProxy.getB5FormRefTitle(),cartableTableProxy.getSyncState(),cartableTableProxy.getAttach());
        }
    }
}
