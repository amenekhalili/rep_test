package ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries;


import io.realm.RealmResults;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;

public class GetRowGoodByGUIDQuery extends BaseRowByGuidQuery {

    private GoodsTable goodsTable = new GoodsTable();

    @Override
    protected IModels getProperModel() {
        return goodsTable;
    }

    @Override
    protected void getRowByGuid(RealmResults realmResults) {
        if (realmResults.size() != 0) {//==> if have not that row, we send a new table by default values
            GoodsTable goodsTableProxy = (GoodsTable) realmResults.get(0);
            goodsTable = new GoodsTable(goodsTableProxy.getId(),goodsTableProxy.getC5BrandId(),goodsTableProxy.getBrandName(),goodsTableProxy.getB5HCStatusId(),goodsTableProxy.getNationalityCode(),goodsTableProxy.getCode(),goodsTableProxy.getName(),goodsTableProxy.getLatinName(),goodsTableProxy.getDescription(),goodsTableProxy.getGoogleKeyWord(),goodsTableProxy.getTechInfo(),goodsTableProxy.getStatusName(),goodsTableProxy.getAttach(),goodsTableProxy.getGuid(),goodsTableProxy.getSerial(),goodsTableProxy.getGoodSUOMList(),goodsTableProxy.getGoodTypeList(),goodsTableProxy.getOldValue(),goodsTableProxy.getSyncState());
        }
    }
}
