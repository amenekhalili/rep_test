package ir.fararayaneh.erp.data.data_providers.queries.time_filter_query;

import com.annimon.stream.Stream;

import java.util.Locale;

import io.realm.Realm;
import ir.fararayaneh.erp.commons.CommonTimeZone;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TimeTable;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;

public class TimeTimeFilteredIdListQuery extends BaseGetTimeFilteredIdListQuery {
    @Override
    protected void calculateFilteredIdGuid(Realm realm) {
        Stream.of(realm.where(TimeTable.class).findAll())
                .forEach(timeTable -> {
                    if(CustomTimeHelper.getDiffDate(startDate,CustomTimeHelper.stringDateToMyLocalDateConvertor(timeTable.getStartDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE))>=0
                            &&
                            CustomTimeHelper.getDiffDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(timeTable.getStartDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE),endDate)>=0){
                        finalIdGuidList.add(timeTable.getGuid());
                    }
                    if(CustomTimeHelper.getDiffDate(startDate,CustomTimeHelper.stringDateToMyLocalDateConvertor(timeTable.getEndDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE))>=0
                            &&
                            CustomTimeHelper.getDiffDate(CustomTimeHelper.stringDateToMyLocalDateConvertor(timeTable.getEndDate(), CommonsFormat.DATE_SHARE_FORMAT_TO_STRING, Locale.ENGLISH, CommonTimeZone.SERVER_TIME_ZONE),endDate)>=0){
                        finalIdGuidList.add(timeTable.getGuid());
                    }
                });
    }

}
