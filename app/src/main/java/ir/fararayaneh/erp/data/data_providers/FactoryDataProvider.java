package ir.fararayaneh.erp.data.data_providers;

import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.data.data_providers.offline.AttachAlbumListDataProvider;
import ir.fararayaneh.erp.data.data_providers.offline.ConfigsListDataProvider;
import ir.fararayaneh.erp.data.data_providers.offline.FakeFacilitiesListDataProvider;
import ir.fararayaneh.erp.data.data_providers.offline.PrimitiveProgressListDataProvider;
import ir.fararayaneh.erp.data.data_providers.online.DownloadFileProvider;
import ir.fararayaneh.erp.data.data_providers.online.GetNodeServerDataProvider;
import ir.fararayaneh.erp.data.data_providers.online.NetDataProvider;
import ir.fararayaneh.erp.data.data_providers.online.UploadFileProvider;
import ir.fararayaneh.erp.data.data_providers.queries.AllDataBaseBackupQuery;
import ir.fararayaneh.erp.data.data_providers.queries.BaseCodingNameListFromBaseCodingListId;
import ir.fararayaneh.erp.data.data_providers.queries.CheckPassWordQuery;
import ir.fararayaneh.erp.data.data_providers.queries.ClearTableQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetAllAppNameQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetB5HCTypeIdQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetComboIdCodeFromUtilCodeQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetComboIdNameFromBaseCoddingQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetComboIdNameFromKartableRecieverQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetComboIdNameFromUtilCodeQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetCustomerGroupNameFromIdQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetDefaultCostumerGroupQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetFormCodeQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetFormIdQuery;
import ir.fararayaneh.erp.data.data_providers.queries.GetGoodIdFromWareHouseIdQuery;
import ir.fararayaneh.erp.data.data_providers.queries.HaveUnSyncQuery;
import ir.fararayaneh.erp.data.data_providers.queries.HaveUserQuery;
import ir.fararayaneh.erp.data.data_providers.queries.UtilCodeNameListFromUtilCodeListId;
import ir.fararayaneh.erp.data.data_providers.queries.accident.InsertUpdateAccidentTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.address_book.GetAddressNameFromIdQuery;
import ir.fararayaneh.erp.data.data_providers.queries.address_book.GetDefaultAddressQuery;
import ir.fararayaneh.erp.data.data_providers.queries.address_book.InsertUpdateAddressBookServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.agrofield.InsertUpdateAgrofieldTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.attach_query.ChangeStateToSyncedByGUIDQuery;
import ir.fararayaneh.erp.data.data_providers.queries.attach_query.FindFirstUnSyncAttachmentRowQuery;
import ir.fararayaneh.erp.data.data_providers.queries.attach_query.InsertAttachmentsFromClientQuery;
import ir.fararayaneh.erp.data.data_providers.queries.InsertJsonQuery;
import ir.fararayaneh.erp.data.data_providers.queries.base_codding.InsertUpdateBaseCoddingTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.cartable_queris.InsertUpdateCartableTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom.InsertUpdateChatroomChatRoomMemberTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom.InsertUpdateChatroomTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom_member.HandleMakeUserOfflineServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom_member.HandleMakeUserOnlineServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom_member.InsertUpdateChatroomMemberAddTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom_member.InsertUpdateChatroomMemberTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.chatroom_member.HandleAddUnreadMessageChatMembersQuery;
import ir.fararayaneh.erp.data.data_providers.queries.contact_book.InsertUpdateContactBookServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.country_division.InsertUpdateCountryDivisionServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.evacuate.InsertUpdateEvacuateTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.evacuate.InsertUpdateEvacuateTableInSendProcessQuery;
import ir.fararayaneh.erp.data.data_providers.queries.evacuate.SendAllEvacuatePacketQuery;
import ir.fararayaneh.erp.data.data_providers.queries.form_ref.GetFormLocationQuery;
import ir.fararayaneh.erp.data.data_providers.queries.form_ref.InsertUpdateFormRefTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.fuel_list_detail.FindMasterDriverIdFuelListDetailQuery;
import ir.fararayaneh.erp.data.data_providers.queries.fuel_list_detail.InsertUpdateFuelListDetailTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.fuel_list_detail.InsertUpdateFuelListTableQuery;
import ir.fararayaneh.erp.data.data_providers.queries.fuel_list_master.InsertUpdateFuelListMasterTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries.GetRowFuelDetailByGUIDQuery;
import ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries.GetRowGoodTranceByGUIDQuery;
import ir.fararayaneh.erp.data.data_providers.queries.good_trance.InsertUpdateGoodTrancTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.good_trance.InsertUpdateGoodTransTableChangePurchasableAmountQuery;
import ir.fararayaneh.erp.data.data_providers.queries.good_trance.InsertUpdateGoodTransTableChangeSalableAmountQuery;
import ir.fararayaneh.erp.data.data_providers.queries.good_trance.InsertUpdateGoodTransTableQuery;
import ir.fararayaneh.erp.data.data_providers.queries.good_trance.InsertUpdateGoodTransTableRefineAndChangePurchasableAmountQuery;
import ir.fararayaneh.erp.data.data_providers.queries.good_trance.InsertUpdateGoodTransTableRefineAndChangeSalableAmountQuery;
import ir.fararayaneh.erp.data.data_providers.queries.goods.GetGoodsListByIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.goods.InsertUpdateGoodsTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.group_related.InsertUpdateGroupRelatedTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.kartable_reciever.InsertUpdateKartableReceiverTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.message.InsertUpdateMessageEditTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.message.InsertUpdateMessageTableQuery;
import ir.fararayaneh.erp.data.data_providers.queries.message.InsertUpdateMessageTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.message.RefineLastMessageQuery;
import ir.fararayaneh.erp.data.data_providers.queries.purchasable_goods.GetPurchasableGoodsByRecallId;
import ir.fararayaneh.erp.data.data_providers.queries.purchasable_goods.PlusTempAmountPurchasableGoods;
import ir.fararayaneh.erp.data.data_providers.queries.purchasable_goods.SetAllRealPurchasableToTempAmount;
import ir.fararayaneh.erp.data.data_providers.queries.recycle_list_view_queries.report_quries.ReportQueries;
import ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries.GetRowCartableByGUIDQuery;
import ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries.GetRowGoodByGUIDQuery;
import ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries.GetRowTaskByGUIDQuery;
import ir.fararayaneh.erp.data.data_providers.queries.get_row_by_guid_fake_guid_queries.GetRowTimeByGUIDQuery;
import ir.fararayaneh.erp.data.data_providers.queries.purchasable_goods.InsertUpdatePurchasableGoodsServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.salable_goods.GetSalableByRecallId;
import ir.fararayaneh.erp.data.data_providers.queries.salable_goods.InsertUpdateSalableGoodsServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.salable_goods.PlusTempAmountSalableGoods;
import ir.fararayaneh.erp.data.data_providers.queries.salable_goods.SetAllRealSalableToTempAmount;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanAccidentQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanAddressBookQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanAgroFieldQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanBaseCodingQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanCartableQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanChatroomMemberQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanChatroomQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanContactBookQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanCountryDivisionQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanFormRefQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanFuelListDetailQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanGoodTrancQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanGoodsQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanGroupRelatedQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanKartableReceiverQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanMessageQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanPurchasableGoodsQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanSalableGoodsQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanTaskQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanTimeQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanUtilCodeQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanWareHouseQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanWarehouseOutQuery;
import ir.fararayaneh.erp.data.data_providers.queries.sync_queries.SyncOrCleanWeighingQuery;
import ir.fararayaneh.erp.data.data_providers.queries.task_queris.InsertUpdateTaskTableQuery;
import ir.fararayaneh.erp.data.data_providers.queries.InsertUserQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.CartableTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.FuelDetailTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.FuelMasterTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.GoodTransTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.PurchasableGoodsTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.SalableGoodsTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.TaskTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.TimeTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_filter_query.WeighingTimeFilteredIdListQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_queris.InsertUpdateTimeTableQuery;
import ir.fararayaneh.erp.data.data_providers.queries.task_queris.InsertUpdateTaskTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.time_queris.InsertUpdateTimeTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.recycle_list_view_queries.spinner_dialog_quries.SpinnerDialogQueries;
import ir.fararayaneh.erp.data.data_providers.queries.util_code.InsertUpdateUtilCodeTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warehouse_out_query.InsertUpdateWarehouseOutTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warehouse_out_query.InsertWarehouseOutTableQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.GetAllDataFromOneColumnWarehouseQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.GetAllPlaceShelfRowWarehouseQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.GetFirstEditableWarehouseRowQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.GetOneRowByBarCodeWarehouseQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.GetOneRowByIdWarehouseQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.InsertUpdateWarehouseTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.warhouse_queris.SetValueWarehouseQuery;
import ir.fararayaneh.erp.data.data_providers.queries.weighing.GetAllDataFromOneColumnWeighingQuery;
import ir.fararayaneh.erp.data.data_providers.queries.weighing.GetOneRowByIdWeighingQuery;
import ir.fararayaneh.erp.data.data_providers.queries.weighing.InsertUpdateWeighingTableServiceQuery;
import ir.fararayaneh.erp.data.data_providers.queries.weighing.SetValueWeighingQuery;


// TODO: 31/12/2018 make singleton
public class FactoryDataProvider {

    public static IDataProvider getDataProvider(String dataProviderName) {
        switch (dataProviderName) {
            case CommonsDataProviderFactory.CONFIGS_LIST_DATAPROVIDER:
                return new ConfigsListDataProvider();
            case CommonsDataProviderFactory.FACILITIES_LIST_DATAPROVIDER:
                return new FakeFacilitiesListDataProvider();
            case CommonsDataProviderFactory.CLEAR_TABLE_QUERY:
                return new ClearTableQuery();
            case CommonsDataProviderFactory.HAVE_USER_QUERY:
                return new HaveUserQuery();
            case CommonsDataProviderFactory.CHECK_PASSWORD_QUERY:
                return new CheckPassWordQuery();
            case CommonsDataProviderFactory.NET_DATA_PROVIDER:
                return new NetDataProvider();
            case CommonsDataProviderFactory.DOWNLOAD_FILE_PROVIDER:
                return new DownloadFileProvider();
            case CommonsDataProviderFactory.GET_NODE_SERVER_DATA_PROVIDER:
                return new GetNodeServerDataProvider();
            case CommonsDataProviderFactory.INSERT_JSON_QUERY:
                return new InsertJsonQuery();
            case CommonsDataProviderFactory.PRIMITIVE_PROGRESSLIST_DATAPROVIDER:
                return new PrimitiveProgressListDataProvider();
            case CommonsDataProviderFactory.INSERT_USER_QUERY:
                return new InsertUserQuery();
            case CommonsDataProviderFactory.GET_ALL_APP_NAME_QUERY:
                return new GetAllAppNameQuery();
            case CommonsDataProviderFactory.COMPRESS_IMAGE_PROVIDER:
                return new CompressImageProvider();
            case CommonsDataProviderFactory.GET_FORM_ID_QUERY:
                return new GetFormIdQuery();
            case CommonsDataProviderFactory.GET_COMBO_ID_NAME_FROM_BASE_CODDING_QUERY:
                return new GetComboIdNameFromBaseCoddingQuery();
            case CommonsDataProviderFactory.GET_COMBO_ID_NAME_FROM_UTIL_CODE_QUERY:
                return new GetComboIdNameFromUtilCodeQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_TIME_TABLE_QUERY:
                return new InsertUpdateTimeTableQuery();
            case CommonsDataProviderFactory.GET_B5HCTYPEID_QUERY:
                return new GetB5HCTypeIdQuery();
            case CommonsDataProviderFactory.INSERT_ATTACHMENTS_FROM_CLIENT_QUERY:
                return new InsertAttachmentsFromClientQuery();
            case CommonsDataProviderFactory.GET_COMBO_ID_NAME_FROM_KARTABLE_RECIEVER_QUERY:
                return new GetComboIdNameFromKartableRecieverQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_TASK_TABLE_QUERY:
                return new InsertUpdateTaskTableQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_TASK_TABLE_SERVICE_QUERY:
                return new InsertUpdateTaskTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_TIME_TABLE_SERVICE_QUERY:
                return new InsertUpdateTimeTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_CARTABLE_TABLE_SERVICE_QUERY:
                return new InsertUpdateAccidentTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_ACCIDENT_TABLE_SERVICE_QUERY:
                return new InsertUpdateCartableTableServiceQuery();
            case CommonsDataProviderFactory.GET_ALL_DATA_FROM_ONE_COLUMN_WAREHOUSE_QUERY:
                return new GetAllDataFromOneColumnWarehouseQuery();
            case CommonsDataProviderFactory.GET_ALL_PLACE_SHELF_ROW_WAREHOUSE_QUERY:
                return new GetAllPlaceShelfRowWarehouseQuery();
            case CommonsDataProviderFactory.GET_ONE_ROW_BY_ID_WAREHOUSE_QUERY:
                return new GetOneRowByIdWarehouseQuery();
            case CommonsDataProviderFactory.SET_VALUE_WAREHOUSE_QUERY:
                return new SetValueWarehouseQuery();
            case CommonsDataProviderFactory.GET_ONE_ROW_BY_BARCODE_WAREHOUSE_QUERY:
                return new GetOneRowByBarCodeWarehouseQuery();
            case CommonsDataProviderFactory.Insert_Update_Warehouse_Table_Service_Query:
                return new InsertUpdateWarehouseTableServiceQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_WAREHOUSE_QUERY:
                return new SyncOrCleanWareHouseQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_TIME_QUERY:
                return new SyncOrCleanTimeQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_TASK_QUERY:
                return new SyncOrCleanTaskQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_CHATROOM_MEMBER_TABLE_SERVICE_QUERY:
                return new InsertUpdateChatroomMemberTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_CHATROOM_TABLE_SERVICE_QUERY:
                return new InsertUpdateChatroomTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_MESSAGE_TABLE_SERVICE_QUERY:
                return new InsertUpdateMessageTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_AGROFIELD_TABLE_SERVICE_QUERY:
                return new InsertUpdateAgrofieldTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_BASE_CODDING_TABLE_SERVICE_QUERY:
                return new InsertUpdateBaseCoddingTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_FORM_REF_TABLE_SERVICE_QUERY:
                return new InsertUpdateFormRefTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_GOODS_TABLE_SERVICE_QUERY:
                return new InsertUpdateGoodsTableServiceQuery();

            case CommonsDataProviderFactory.SYNC_OR_CLEAN_ACCIDENT_QUERY:
                return new SyncOrCleanAccidentQuery();

            case CommonsDataProviderFactory.INSERT_UPDATE_GROUP_RELATED_TABLE_SERVICE_QUERY:
                return new InsertUpdateGroupRelatedTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_KARTABLE_RECEIVER_TABLE_SERVICE_QUERY:
                return new InsertUpdateKartableReceiverTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_UTIL_CODE_TABLE_SERVICE_QUERY:
                return new InsertUpdateUtilCodeTableServiceQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_CARTABLE_QUERY:
                return new SyncOrCleanCartableQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_CHATROOM_MEMBER_QUERY:
                return new SyncOrCleanChatroomMemberQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_MESSAGE_QUERY:
                return new SyncOrCleanMessageQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_CHATROOM_QUERY:
                return new SyncOrCleanChatroomQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_AGROFIELD_QUERY:
                return new SyncOrCleanAgroFieldQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_BASECODING_QUERY:
                return new SyncOrCleanBaseCodingQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_FORM_REF_QUERY:
                return new SyncOrCleanFormRefQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_GoodS_QUERY:
                return new SyncOrCleanGoodsQuery();

            case CommonsDataProviderFactory.SYNC_OR_CLEAN_GROUPRELATED_QUERY:
                return new SyncOrCleanGroupRelatedQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_KARTABLERECEIVER_QUERY:
                return new SyncOrCleanKartableReceiverQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_UTILCODE_QUERY:
                return new SyncOrCleanUtilCodeQuery();
            case CommonsDataProviderFactory.HAVE_UN_SYNC_QUERY:
                return new HaveUnSyncQuery();
            case CommonsDataProviderFactory.GET_ROW_TASK_BY_GUID_QUERY:
                return new GetRowTaskByGUIDQuery();
            case CommonsDataProviderFactory.GET_ROW_TIME_BY_GUID_QUERY:
                return new GetRowTimeByGUIDQuery();
            case CommonsDataProviderFactory.GET_FORM_CODE_QUERY:
                return new GetFormCodeQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_WEIGHING_QUERY:
                return new SyncOrCleanWeighingQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_WEIGHING_TABLE_SERVICE_QUERY:
                return new InsertUpdateWeighingTableServiceQuery();
            case CommonsDataProviderFactory.GetAllDataFromOneColumnWeighingQuery:
                return new GetAllDataFromOneColumnWeighingQuery();
            case CommonsDataProviderFactory.GET_ONE_ROW_BY_ID_WEIGHING_QUERY:
                return new GetOneRowByIdWeighingQuery();
            case CommonsDataProviderFactory.SET_VALUE_WEIGHING_QUERY:
                return new SetValueWeighingQuery();
            case CommonsDataProviderFactory.ATTACH_ALBUM_LIST_DATA_PROVIDER:
                return new AttachAlbumListDataProvider();
            case CommonsDataProviderFactory.GET_ROW_CARTABLE_BY_GUID_QUERY:
                return new GetRowCartableByGUIDQuery();
            case CommonsDataProviderFactory.GET_ROW_GOOD_BY_GUID_QUERY:
                return new GetRowGoodByGUIDQuery();
            case CommonsDataProviderFactory.GET_FIRST_EDITABLE_WAREHOUSE_ROW_QUERY:
                return new GetFirstEditableWarehouseRowQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_WAREHOUSE_OUT_QUERY:
                return new SyncOrCleanWarehouseOutQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_WAREHOUSE_OUT_TABLE_SERVICE_QUERY:
                return new InsertUpdateWarehouseOutTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_WAREHOUSE_OUT_TABLE_QUERY:
                return new InsertWarehouseOutTableQuery();
            case CommonsDataProviderFactory.ALL_DATA_BASE_BACKUP_QUERY:
                return new AllDataBaseBackupQuery();
            case CommonsDataProviderFactory.REPORT_QUERIES:
                return new ReportQueries();
            case CommonsDataProviderFactory.SPINNER_DIALOG_QUERIES:
                return new SpinnerDialogQueries();
            case CommonsDataProviderFactory.FIND_FIRST_UN_SYNC_ATTACHMENT_ROW_QUERY:
                return new FindFirstUnSyncAttachmentRowQuery();
            case CommonsDataProviderFactory.CHANGE_STATE_TO_SYNCED_BY_GUID_QUERY:
                return new ChangeStateToSyncedByGUIDQuery();
            case CommonsDataProviderFactory.GET_ROW_GOOD_TRANCE_BY_GUID_QUERY:
                return new GetRowGoodTranceByGUIDQuery();
            case CommonsDataProviderFactory.UTIL_CODE_NAME_LIST_FROM_UTIL_CODE_LIST_ID:
                return new UtilCodeNameListFromUtilCodeListId();
            case CommonsDataProviderFactory.BASE_CODING_NAME_LIST_FROM_BASE_CODING_LIST_ID:
                return new BaseCodingNameListFromBaseCodingListId();
            case CommonsDataProviderFactory.GET_GOODS_LIST_BY_ID_LIST_QUERY:
                return new GetGoodsListByIdListQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_GOOD_TRANS_TABLE_QUERY:
                return new InsertUpdateGoodTransTableQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_GOOD_TRANC_TABLE_SERVICE_QUERY:
                return new InsertUpdateGoodTrancTableServiceQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_GOOD_TRANC_QUERY:
                return new SyncOrCleanGoodTrancQuery();
            case CommonsDataProviderFactory.GET_COMBO_ID_CODE_FROM_UTIL_CODE_QUERY:
                return new GetComboIdCodeFromUtilCodeQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_PURCHASABLE_GOODS_QUERY:
                return new SyncOrCleanPurchasableGoodsQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_PURCHASABLE_GOODS_SERVICE_QUERY:
                return new InsertUpdatePurchasableGoodsServiceQuery();
            case CommonsDataProviderFactory.CARTABLE_TIME_FILTERED_ID_LIST_QUERY:
                return new CartableTimeFilteredIdListQuery();
            case CommonsDataProviderFactory.GOOD_TRANS_TIME_FILTERED_ID_LIST_QUERY:
                return new GoodTransTimeFilteredIdListQuery();
            case CommonsDataProviderFactory.TASK_TIME_FILTERED_ID_LIST_QUERY:
                return new TaskTimeFilteredIdListQuery();
            case CommonsDataProviderFactory.TIME_TIME_FILTERED_ID_LIST_QUERY:
                return new TimeTimeFilteredIdListQuery();
            case CommonsDataProviderFactory.WEIGHING_TIME_FILTERED_ID_LIST_QUERY:
                return new WeighingTimeFilteredIdListQuery();
            case CommonsDataProviderFactory.UPLOAD_FILE_PROVIDER:
                return new UploadFileProvider();
            case CommonsDataProviderFactory.PURCHASABLE_GOODS_TIME_FILTERED_ID_LIST_QUERY:
                return new PurchasableGoodsTimeFilteredIdListQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_SALABLE_GOODS_QUERY:
                return new SyncOrCleanSalableGoodsQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_SALABLE_GOODS_SERVICE_QUERY:
                return new InsertUpdateSalableGoodsServiceQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_CONTACT_BOOK_QUERY:
                return new SyncOrCleanContactBookQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_CONTACT_BOOK_SERVICE_QUERY:
                return new InsertUpdateContactBookServiceQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_ADDRESS_BOOK_QUERY:
                return new SyncOrCleanAddressBookQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_ADDRESS_BOOK_SERVICE_QUERY:
                return new InsertUpdateAddressBookServiceQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_COUNTRY_DIVISION_QUERY:
                return new SyncOrCleanCountryDivisionQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_COUNTRY_DIVISION_SERVICE_QUERY:
                return new InsertUpdateCountryDivisionServiceQuery();
            case CommonsDataProviderFactory.GET_CUSTOMER_GROUP_NAME_FROM_ID_QUERY:
                return new GetCustomerGroupNameFromIdQuery();
            case CommonsDataProviderFactory.GET_ADDRESS_NAME_FROM_ID_QUERY:
                return new GetAddressNameFromIdQuery();
            case CommonsDataProviderFactory.GET_DEFAULT_COSTUMER_GROUP_QUERY:
                return new GetDefaultCostumerGroupQuery();
            case CommonsDataProviderFactory.GET_DEFAULT_ADDRESS_QUERY:
                return new GetDefaultAddressQuery();
            case CommonsDataProviderFactory.GET_SALABLE_BY_RECALL_ID:
                return new GetSalableByRecallId();
            case CommonsDataProviderFactory.GET_PURCHASABLE_GOODS_BY_RECALL_ID:
                return new GetPurchasableGoodsByRecallId();
            case CommonsDataProviderFactory.SET_ALL_REAL_PURCHASABLE_TO_TEMP_AMOUNT:
                return new SetAllRealPurchasableToTempAmount();
            case CommonsDataProviderFactory.SET_ALL_REAL_SALABLE_TO_TEMP_AMOUNT:
                return new SetAllRealSalableToTempAmount();
            case CommonsDataProviderFactory.PLUS_TEMP_AMOUNT_SALABLE_GOODS:
                return new PlusTempAmountSalableGoods();
            case CommonsDataProviderFactory.PLUS_TEMP_AMOUNT_PURCHASABLE_GOODS:
                return new PlusTempAmountPurchasableGoods();
            case CommonsDataProviderFactory.INSERT_UPDATE_GOOD_TRANS_TABLE_CHANGE_PURCHASABLE_AMOUNT_QUERY:
                return new InsertUpdateGoodTransTableChangePurchasableAmountQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_GOOD_TRANS_TABLE_CHANGE_SALABLE_AMOUNT_QUERY:
                return new InsertUpdateGoodTransTableChangeSalableAmountQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_GOOD_TRANS_TABLE_REFINE_AND_CHANGE_PURCHASABLE_AMOUNT_QUERY:
                return new InsertUpdateGoodTransTableRefineAndChangePurchasableAmountQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_GOOD_TRANS_TABLE_REFINE_AND_CHANGE_SALABLE_AMOUNT_QUERY:
                return new InsertUpdateGoodTransTableRefineAndChangeSalableAmountQuery();
            case CommonsDataProviderFactory.SALABLE_GOODS_TIME_FILTERED_ID_LIST_QUERY:
                return new SalableGoodsTimeFilteredIdListQuery();
            case CommonsDataProviderFactory.GET_GOOD_ID_FROM_WAREHOUSE_ID_QUERY:
                return new GetGoodIdFromWareHouseIdQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_CHATROOM_CHATROOMMEMBER_TABLE_SERVICE_QUERY:
                return new InsertUpdateChatroomChatRoomMemberTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_CHATROOMMEMBER_ADD_TABLE_SERVICE_QUERY:
                return new InsertUpdateChatroomMemberAddTableServiceQuery();
            case CommonsDataProviderFactory.HANDLE_ADD_UNREAD_MESSAGE_CHAT_MEMBER_QUERY:
                return new HandleAddUnreadMessageChatMembersQuery();
            case CommonsDataProviderFactory.HANDLE_MAKE_USER_OFFLINE_SERVICE_QUERY:
                return new HandleMakeUserOfflineServiceQuery();
            case CommonsDataProviderFactory.HANDLE_MAKE_USER_ONLINE_SERVICE_QUERY:
                return new HandleMakeUserOnlineServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_MESSAGE_TABLE_QUERY:
                return new InsertUpdateMessageTableQuery();
            case CommonsDataProviderFactory.REFINE_LAST_MESSAGE_QUERY:
                return new RefineLastMessageQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_FUEL_LIST_MASTER_TABLE_SERVICE_QUERY:
                return new InsertUpdateFuelListMasterTableServiceQuery();
            case CommonsDataProviderFactory.FUEL_MASTER_TIME_FILTERED_ID_LIST_QUERY:
                return new FuelMasterTimeFilteredIdListQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_FUEL_LIST_DETAIL_TABLE_SERVICE_QUERY:
                return new InsertUpdateFuelListDetailTableServiceQuery();
            case CommonsDataProviderFactory.GET_ROW_FUEL_DETAIL_BY_GUID_QUERY:
                return new GetRowFuelDetailByGUIDQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_FUEL_LIST_TABLE_QUERY:
                return new InsertUpdateFuelListTableQuery();
            case CommonsDataProviderFactory.SYNC_OR_CLEAN_FUEL_LIST_DETAIL_QUERY:
                return new SyncOrCleanFuelListDetailQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_EVACUATE_TABLE_SERVICE_QUERY:
                return new InsertUpdateEvacuateTableServiceQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_EVACUATE_TABLE_IN_SEND_PROCESS:
                return new InsertUpdateEvacuateTableInSendProcessQuery();
            case CommonsDataProviderFactory.SEND_ALL_EVACUATE_PACKET_QUERY:
                return new SendAllEvacuatePacketQuery();
            case CommonsDataProviderFactory.GET_FORM_LOCATION_QUERY:
                return new GetFormLocationQuery();
            case CommonsDataProviderFactory.FIND_MASTER_DRIVER_ID_FUEL_LIST_DETAIL_QUERY:
                return new FindMasterDriverIdFuelListDetailQuery();
            case CommonsDataProviderFactory.FUEL_DETAIL_TIME_FILTERED_ID_LIST_QUERY:
                return new FuelDetailTimeFilteredIdListQuery();
            case CommonsDataProviderFactory.INSERT_UPDATE_MESSAGE_EDIT_TABLE_SERVICE_QUERY:
                return new InsertUpdateMessageEditTableServiceQuery();
            default:
                return null;
        }
    }
}
