package ir.fararayaneh.erp.data.data_providers.queries;

import com.annimon.stream.Stream;
import java.util.ArrayList;
import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.GroupRelatedTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/*
ما دو سطح گروه برای کالا ها داریم
سطح ریشه یا سطح 1( rootId in GR) :
که انبارها به آن متصل میشوند(مثلا گروه تنقلات)

سطح 2 (groupId in GR) :
که کالا ها با آن در ارتباط هستند
(مثل گروه پفک یا چیپس که گروه زیر دست گروه تنقلات است)
چیپس مزمز به گروه
چیپس متصل است و نه گروه تنقلات
---------------------------------------------------------
ای دی انبار را در جدول GR میابیم
و به لیستی از groupId میرسیم
(چون انبارها به گروه های اصلی وابسته است)
سپس این لیست
حاوی groupId
است
که باید ردیف هایی را یافت که
rootId
آنها مساوی این
groupId
باشد
که در نهایت کالاهایی از این خروجی خارج شود که
id
آنها در
b5IdRefId
لیست فوق موجود است
 */

public class GetGoodIdFromWareHouseIdQuery extends BaseQueries {

    private GlobalModel globalModel;
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        globalModel= (GlobalModel)iModels;
        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {

                    //create for result
                    ArrayList<String> goodIdList = new ArrayList<>();
                    ArrayList<String> groupIdList = new ArrayList<>();

                    RealmResults<GroupRelatedTable> results = realm
                            .where(GroupRelatedTable.class)
                            .equalTo(CommonColumnName.B5IDREFID,globalModel.getMyString())
                            .findAll();

                    Stream.of(results).forEach(groupRelatedTable ->
                            groupIdList.add(groupRelatedTable.getGroupId()));

                    RealmResults<GroupRelatedTable> results2 = realm.where(GroupRelatedTable.class)
                            .in(CommonColumnName.ROOT_ID, (String[]) groupIdList.toArray(new String[0]))
                            .findAll();

                    Stream.of(results2).forEach(groupRelatedTable ->
                            goodIdList.add(groupRelatedTable.getB5IdRefId()));

                    /*
                     * آی دی هایی که در اینجا انتخاب میشوند ممکن است
                     * از نظر تعداد بیش از تعداد واقعی کالا ها باشند
                     * که این موضوع مشکلی ایجاد نمینماید
                     * چون ایدی های اینجا برای فیلتر روی کالا استفاده میشود و نه خود کالا
                     */

                    globalModel.setStringArrayList(goodIdList);
                }
                ,
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(globalModel);
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("GetGoodIdFromWareHouseIdQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}

