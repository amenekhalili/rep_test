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

public class GetComboIdCodeFromUtilCodeQuery extends BaseQueries {


    private ArrayList<String> inputCodes;
    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        GlobalModel globalModel = (GlobalModel) iModels;
        inputCodes = globalModel.getStringArrayList();

        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {

                    //create for result
                    ArrayList<ArrayList<String>> comboIds = new ArrayList<>();
                    ArrayList<ArrayList<String>> comboCodes = new ArrayList<>();

                    Stream.of(inputCodes).forEach(code -> {
                        ArrayList<String> idList = new ArrayList<>();
                        ArrayList<String> codeList = new ArrayList<>();
                        RealmResults<UtilCodeTable> results = realm.where(UtilCodeTable.class).
                                equalTo(CommonColumnName.PARENT_CODE, code).findAll();

                        Stream.of(results).forEach(utilCodeTable -> {
                            idList.add(utilCodeTable.getId());
                            codeList.add(utilCodeTable.getCode());
                        });

                        comboIds.add(idList);
                        comboCodes.add(codeList);

                    });
                    globalModel.setListStringArrayList(comboCodes);
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
                    ThrowableLoggerHelper.logMyThrowable("GetComboIdCodeFromUtilCodeQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}
