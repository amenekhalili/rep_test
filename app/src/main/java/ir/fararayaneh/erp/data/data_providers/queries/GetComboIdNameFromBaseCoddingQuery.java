package ir.fararayaneh.erp.data.data_providers.queries;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.Single;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.BaseCodingTable;
import ir.fararayaneh.erp.data.models.tables.FormRefTable;
import ir.fararayaneh.erp.data.models.tables.GroupRelatedTable;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

import static ir.fararayaneh.erp.commons.Commons.SPLITTER_OF_CONFIGS;

/**
 * global model should have formCode
 * <p>
 * for get all combo value and id of forms from form code
 * <p>
 * نتیجه نهایی 2 لیست است که هر کدام پانزده ردیف دارند و هر ردیف یا
 * خالی است یا حاوی لیستی است که در هر کمبوباکس باید نمایش داده شود
 * <p>
 * ترتیب اعضای لیست اصلی همان ترتیبی است که در مقصد مورد نیاز است
 * مثلا اگر در مقصد ما 4 کمبوباکس داریم
 * فقط کافی است که ردیف اول تا چهارم لیست ارسالی از این کویری بررسی شود و در صورتی که هر کدام از ردیفهای یک تا چهار
 * حاوی لیست خالی بود باید آن کمبو باکس خاموش شود
 * <p>
 * قبل از همه در مقصد چک شود که لیست ارسالی دارای سایز 0 نباشد
 */
public class GetComboIdNameFromBaseCoddingQuery extends BaseQueries {



    @Override
    public Single<IModels> data(IModels iModels) {
        super.data(iModels);
        GlobalModel globalModel = (GlobalModel) iModels;

        return Single.create(emitter -> realm.executeTransactionAsync(
                realm -> {

                    //create for result
                    ArrayList<ArrayList<String>> comboIds = new ArrayList<>();
                    ArrayList<ArrayList<String>> comboNames = new ArrayList<>();


                    RealmResults<FormRefTable> results = realm.where(FormRefTable.class).
                            equalTo(CommonColumnName.FORM_CODE, globalModel.getMyString()).findAll();
                    //if results.size()=0 --> comboId.size=0 that's it
                    if (results.size() != 0) {

                        ArrayList<String> configsList = new ArrayList<>();
                        addToConfigList(configsList, results);

                        ArrayList<ArrayList<String>> listOfConfigsIdList = new ArrayList<>(); //--->> حاوی لیست هایی که هرکدام یا خالی هستند یا حاوی آرایه هستند

                        Stream.of(configsList).forEach(configsListRow -> {
                            if (!configsListRow.toLowerCase().equals(Commons.NULL)) {
                                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                                Stream.of(configsListRow.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);
                                listOfConfigsIdList.add(listConfigOfOneRow);
                            } else {
                                listOfConfigsIdList.add(new ArrayList<>());
                            }
                        });


                        Stream.of(listOfConfigsIdList).forEach(configsIdList -> {
                            if (configsIdList.size() != 0) {
                                ArrayList<String> nameArrayList = new ArrayList<>();
                                ArrayList<String> idArrayList = new ArrayList<>();

                                Stream.of(configsIdList).forEach(id -> {
                                    RealmResults<GroupRelatedTable> results2 = realm.where(GroupRelatedTable.class)
                                            .equalTo(CommonColumnName.PARENT_TYPE_ID, id)
                                            .distinct(CommonColumnName.B5IDREFID)
                                            .findAll();

                                        Stream.of(results2).forEach(groupRelatedTable -> {
                                            RealmResults<BaseCodingTable> results3 = realm.where(BaseCodingTable.class)
                                                    .equalTo(CommonColumnName.ID, groupRelatedTable.getB5IdRefId())
                                                    .findAll();

                                                if(results3.size()!=0){
                                                    idArrayList.add(groupRelatedTable.getB5IdRefId());
                                                    nameArrayList.add(Objects.requireNonNull(results3.get(0)).getName());
                                                }
                                        });

                                });
                                comboIds.add(idArrayList);
                                comboNames.add(nameArrayList);

                            } else {
                                comboIds.add(new ArrayList<>());
                                comboNames.add(new ArrayList<>());

                            }

                        });
                    }
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
                    ThrowableLoggerHelper.logMyThrowable("GetComboIdNameFromBaseCoddingQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }

    private void addToConfigList(ArrayList<String> configsList, RealmResults<FormRefTable> results) {
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef1());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef2());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef3());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef4());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef5());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef6());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef7());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef8());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef9());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef10());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef11());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef12());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef13());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef14());
        configsList.add(Objects.requireNonNull(results.get(0)).getConfigIdRef15());
    }
}
