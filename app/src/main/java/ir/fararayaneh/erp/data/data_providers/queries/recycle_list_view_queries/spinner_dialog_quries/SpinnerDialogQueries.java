package ir.fararayaneh.erp.data.data_providers.queries.recycle_list_view_queries.spinner_dialog_quries;

import androidx.annotation.NonNull;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Objects;

import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonColumnName;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.IDataProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.tables.BaseCodingTable;
import ir.fararayaneh.erp.data.models.tables.FormRefTable;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.data.models.tables.GroupRelatedTable;
import ir.fararayaneh.erp.data.models.tables.UtilCodeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AddressBookTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CartableTable;

import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.ONE_CONFIG_BASE_CODING_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.ONE_CONFIG_UTIL_CODE_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.REPORT_CARTABLE_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.SPINNER_ADDRESS_NAME_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.SPINNER_CUSTOMER_GROUP_FROM_GR_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.SPINNER_GOODS_NAME_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.CommonReportSelectTableNumbers.SPINNER_GOODS_SUB_UNIT_TABLE_NUMBER;
import static ir.fararayaneh.erp.commons.Commons.SPLITTER_OF_CONFIGS;

public class SpinnerDialogQueries implements IDataProvider {


    @Override
    public RealmResults data(IModels iModels) {
        return null;
    }

//----------------------------------------------------------------------------------------------
    public OrderedRealmCollection[] spinnerCartableMainQuery(@NonNull Realm realm) {
        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_CARTABLE_TABLE_NUMBER];
        realm.beginTransaction();
        orderedRealmCollections[0] = realm.where(CartableTable.class).findAll();
        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerCartableSearchQuery(@NonNull Realm realm, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[REPORT_CARTABLE_TABLE_NUMBER];
        realm.beginTransaction();
        orderedRealmCollections[0] = realm.where(CartableTable.class).contains(CommonColumnName.SENDER_NAME, searchValue,Case.INSENSITIVE).findAll();
        realm.commitTransaction();
        return orderedRealmCollections;
    }
//----------------------------------------------------------------------------------------------
    //only show non access denied rows for filtered warehouse
    public OrderedRealmCollection[] spinnerGoodsMainQuery(@NonNull Realm realm,ArrayList<String> filteredGoodidByWarehouseid) {
    OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[SPINNER_GOODS_NAME_TABLE_NUMBER];
    realm.beginTransaction();
    orderedRealmCollections[0] = realm.where(GoodsTable.class)
            .beginGroup()
            .in(CommonColumnName.ID, (String[]) filteredGoodidByWarehouseid.toArray(new String[0]))
            .endGroup()
            .notEqualTo(CommonColumnName.SYNCSTATE, CommonSyncState.ACCESS_DENIED)
            .findAll();
    realm.commitTransaction();
    return orderedRealmCollections;
}

    public OrderedRealmCollection[] spinnerGoodsSearchQuery(@NonNull Realm realm, String searchValue,ArrayList<String> filteredGoodidByWarehouseid) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[SPINNER_GOODS_NAME_TABLE_NUMBER];
        realm.beginTransaction();
        orderedRealmCollections[0] = realm.where(GoodsTable.class)
                .beginGroup()
                .in(CommonColumnName.ID, (String[]) filteredGoodidByWarehouseid.toArray(new String[0]))
                .endGroup()
                .notEqualTo(CommonColumnName.SYNCSTATE, CommonSyncState.ACCESS_DENIED)
                .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                .or()
                .contains(CommonColumnName.CODE, searchValue,Case.INSENSITIVE)
                .or()
                .contains(CommonColumnName.SERIAL, searchValue,Case.INSENSITIVE)
                .findAll();
        realm.commitTransaction();
        return orderedRealmCollections;
    }
//----------------------------------------------------------------------------------------------
    //only show non access denied rows for filtered warehouse
    public OrderedRealmCollection[] spinnerCustomerGroupMainQuery(@NonNull Realm realm,String b5IdRefId) {
    OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[SPINNER_CUSTOMER_GROUP_FROM_GR_TABLE_NUMBER];
    realm.beginTransaction();
    orderedRealmCollections[0] = realm.where(GroupRelatedTable.class)
            .equalTo(CommonColumnName.B5IDREFID,b5IdRefId)
            .notEqualTo(CommonColumnName.SYNCSTATE, CommonSyncState.ACCESS_DENIED)
            .findAll();
    realm.commitTransaction();
    return orderedRealmCollections;
}

    public OrderedRealmCollection[] spinnerCustomerGroupSearchQuery(@NonNull Realm realm,String b5IdRefId ,String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[SPINNER_CUSTOMER_GROUP_FROM_GR_TABLE_NUMBER];
        realm.beginTransaction();

        orderedRealmCollections[0] = realm.where(GroupRelatedTable.class)
                .beginGroup()
                .equalTo(CommonColumnName.B5IDREFID,b5IdRefId)
                .contains(CommonColumnName.PARENT_NAME, searchValue,Case.INSENSITIVE)
                .endGroup()
                .notEqualTo(CommonColumnName.SYNCSTATE, CommonSyncState.ACCESS_DENIED)
                .findAll();
        realm.commitTransaction();
        return orderedRealmCollections;
    }
//----------------------------------------------------------------------------------------------
    //only show non access denied rows
    public OrderedRealmCollection[] spinnerAddressListMainQuery(@NonNull Realm realm,String b5IdRefId) {
        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[SPINNER_ADDRESS_NAME_TABLE_NUMBER];
        realm.beginTransaction();
        orderedRealmCollections[0] = realm.where(AddressBookTable.class)
                .equalTo(CommonColumnName.B5IDREFID,b5IdRefId)
                .notEqualTo(CommonColumnName.SYNCSTATE, CommonSyncState.ACCESS_DENIED)
                .findAll();
        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerAddressListSearchQuery(@NonNull Realm realm,String b5IdRefId ,String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[SPINNER_ADDRESS_NAME_TABLE_NUMBER];
        realm.beginTransaction();

        orderedRealmCollections[0] = realm.where(AddressBookTable.class)
                .beginGroup()
                .equalTo(CommonColumnName.B5IDREFID,b5IdRefId)
                .contains(CommonColumnName.ADDRESS, searchValue,Case.INSENSITIVE)
                .endGroup()
                .notEqualTo(CommonColumnName.SYNCSTATE, CommonSyncState.ACCESS_DENIED)
                .findAll();
        realm.commitTransaction();
        return orderedRealmCollections;
    }
//----------------------------------------------------------------------------------------------
    /**
     * in attention to GetComboIdNameFromBaseCoddingQuery , below query only get one from group of configs result
     */
    public OrderedRealmCollection[] spinnerGetFirstConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef1();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetFirstConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef1();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetSecondConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef2();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetSecondConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef2();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetThirdConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef3();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetThirdConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef3();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetForthConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef4();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetForthConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef4();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetFifthConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef5();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetFifthConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef5();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetSixthConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef6();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetSixthConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef6();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {

                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetSeventhConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef7();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetSeventhConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef7();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetEighthConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef8();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetEighthConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef8();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetNinthConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef9();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetNinthConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef9();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetTenthConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef10();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetTenthConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef10();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetEleventhConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef11();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetEleventhConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef11();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetTwelfthConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef12();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetTwelfthConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef12();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetThirteenthConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef13();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetThirteenthConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef13();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetFourteenthConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef14();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetFourteenthConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef14();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetFifteenthConfigQuery(@NonNull Realm realm, String formCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef15();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetFifteenthConfigSearchQuery(@NonNull Realm realm, String formCode, String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_BASE_CODING_TABLE_NUMBER];
        realm.beginTransaction();

        RealmResults<FormRefTable> formRefTableResults = realm.where(FormRefTable.class).
                equalTo(CommonColumnName.FORM_CODE, formCode).findAll();
        if (formRefTableResults.size() != 0) {

            String configs = Objects.requireNonNull(formRefTableResults.get(0)).getConfigIdRef15();
            if (!configs.toLowerCase().equals(Commons.NULL)) {

                ArrayList<String> listConfigOfOneRow = new ArrayList<>();
                Stream.of(configs.split(SPLITTER_OF_CONFIGS)).forEach(listConfigOfOneRow::add);

                RealmResults<GroupRelatedTable> groupRelatedTableRealmResults = realm.where(GroupRelatedTable.class)
                        .in(CommonColumnName.PARENT_TYPE_ID, (String[]) listConfigOfOneRow.toArray(new String[0]))
                        .distinct(CommonColumnName.B5IDREFID)
                        .findAll();
                if (groupRelatedTableRealmResults.size() != 0) {
                    ArrayList<String> listGROfOneRow = new ArrayList<>();
                    Stream.of(groupRelatedTableRealmResults).forEach(groupRelatedTable -> listGROfOneRow.add(groupRelatedTable.getB5IdRefId()));

                    orderedRealmCollections[0] = realm.where(BaseCodingTable.class)
                            .in(CommonColumnName.ID, (String[]) listGROfOneRow.toArray(new String[0]))
                            .contains(CommonColumnName.NAME, searchValue,Case.INSENSITIVE)
                            .findAll();
                } else {
                    return commitRealmAndReturnNull(realm);
                }
            } else {
                return commitRealmAndReturnNull(realm);
            }
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

//----------------------------------------------------------------------------------------------
    public OrderedRealmCollection[] spinnerGetOneUtilCodeConfigQuery(@NonNull Realm realm, String utilParentCode) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_UTIL_CODE_TABLE_NUMBER];

        realm.beginTransaction();

        RealmResults<UtilCodeTable> utilCodeTables = realm.where(UtilCodeTable.class).
                equalTo(CommonColumnName.PARENT_CODE, utilParentCode).findAll();

        if (utilCodeTables.size() != 0) {
            orderedRealmCollections[0] = utilCodeTables;
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

    public OrderedRealmCollection[] spinnerGetOneUtilCodeConfigSearchQuery(@NonNull Realm realm, String utilParentCode,String searchValue) {

        OrderedRealmCollection[] orderedRealmCollections = new OrderedRealmCollection[ONE_CONFIG_UTIL_CODE_TABLE_NUMBER];

        realm.beginTransaction();

        RealmResults<UtilCodeTable> utilCodeTables = realm.where(UtilCodeTable.class).
                 equalTo(CommonColumnName.PARENT_CODE, utilParentCode)
                .contains(CommonColumnName.NAME,searchValue, Case.INSENSITIVE)
                .findAll();

        if (utilCodeTables.size() != 0) {
            orderedRealmCollections[0] = utilCodeTables;
        } else {
            return commitRealmAndReturnNull(realm);
        }

        realm.commitTransaction();
        return orderedRealmCollections;
    }

//----------------------------------------------------------------------------------------------
    private OrderedRealmCollection[] commitRealmAndReturnNull(Realm realm) {
        realm.commitTransaction();
        return null;
    }



}
