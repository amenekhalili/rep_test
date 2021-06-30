package ir.fararayaneh.erp.data.data_providers.queries.time_filter_query;

import com.annimon.stream.Stream;
import java.util.Locale;
import io.realm.Realm;
import ir.fararayaneh.erp.commons.CommonTimeZone;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;

public class PurchasableGoodsTimeFilteredIdListQuery extends BaseGetTimeFilteredIdListQuery {
    @Override
    protected void calculateFilteredIdGuid(Realm realm) {
        Stream.of(realm.where(PurchasableGoodsTable.class).findAll())
                .forEach(purchasableGoodsTable -> {
                    if(CustomTimeHelper.getDiffDate(startDate,CustomTimeHelper.stringDateToMyLocalDateConvertor(purchasableGoodsTable.getExpireDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE))>=0
                            &&
                            CustomTimeHelper.getDiffDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(purchasableGoodsTable.getExpireDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE),endDate)>=0){
                        finalIdGuidList.add(purchasableGoodsTable.getId());
                    }
                    if(CustomTimeHelper.getDiffDate(startDate,CustomTimeHelper.stringDateToMyLocalDateConvertor(purchasableGoodsTable.getFormDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE))>=0
                            &&
                            CustomTimeHelper.getDiffDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(purchasableGoodsTable.getFormDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE),endDate)>=0){
                        finalIdGuidList.add(purchasableGoodsTable.getId());
                    }
                });
    }

}