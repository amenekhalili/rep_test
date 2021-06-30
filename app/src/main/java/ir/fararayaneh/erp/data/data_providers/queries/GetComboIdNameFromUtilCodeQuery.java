package ir.fararayaneh.erp.data.data_providers.queries;



import com.annimon.stream.Stream;
import java.util.ArrayList;
import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.UtilCodeTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

/**
 * اینجا ما لیستی از کد های موجود در ستون
 * code
 *  جدول
 * util code
 * را در قالب stringArrayList
 * از کلاس
 * GlobalModel
 * دریافت کرده
 * مثل
 * DAS , WTT
 * گرفته و به ازای هر کدام از آنها لیستی از آی دی و لیستی از اسامی را
 * ایجاد مینماید
 *
 */
public class GetComboIdNameFromUtilCodeQuery extends BaseQueries {


    private ArrayList<String> inputCodes;
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        GlobalModel globalModel = (GlobalModel) iModels;
        inputCodes=globalModel.getStringArrayList();

        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {

                    //create for result
                    ArrayList<ArrayList<String>> comboIds = new ArrayList<>();
                    ArrayList<ArrayList<String>> comboNames = new ArrayList<>();

                    Stream.of(inputCodes).forEach(code -> {
                        ArrayList<String> idList = new ArrayList<>();
                        ArrayList<String> nameList = new ArrayList<>();
                        RealmResults<UtilCodeTable> results = realm.where(UtilCodeTable.class).
                                equalTo(CommonColumnName.PARENT_CODE, code).findAll();

                        Stream.of(results).forEach(utilCodeTable -> {
                            idList.add(utilCodeTable.getId());
                            nameList.add(utilCodeTable.getName());
                        });

                        comboIds.add(idList);
                        comboNames.add(nameList);

                    });
                    globalModel.setListStringArrayList(comboNames);
                    globalModel.setListStringArrayList2(comboIds);
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
                    ThrowableLoggerHelper.logMyThrowable("GetComboIdNameFromUtilCodeQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
