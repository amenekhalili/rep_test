package ir.fararayaneh.erp.utils.data_handler;

import java.util.ArrayList;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.tables.AgroFieldTable;
import ir.fararayaneh.erp.data.models.tables.BaseCodingTable;
import ir.fararayaneh.erp.data.models.tables.EvacuatePacketTable;
import ir.fararayaneh.erp.data.models.tables.FormRefTable;
import ir.fararayaneh.erp.data.models.tables.GoodsTable;
import ir.fararayaneh.erp.data.models.tables.GroupRelatedTable;
import ir.fararayaneh.erp.data.models.tables.KartableRecieverTable;
import ir.fararayaneh.erp.data.models.tables.UserTable;
import ir.fararayaneh.erp.data.models.tables.UtilCodeTable;
import ir.fararayaneh.erp.data.models.tables.attachment.AttachmentTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AccidentTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.AddressBookTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CartableTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomMemberTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ChatroomTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.ContactBookTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.CountryDivisionTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListDetailTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListMasterTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.GoodTranceTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.MessageTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.PurchasableGoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.SalableGoodsTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TaskTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.TimeTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingListOutTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WarehouseHandlingTable;
import ir.fararayaneh.erp.data.models.tables.sync_tables.WeighingTable;

public class AllTBLListHelper {

    /**
     * for delete table or check for unSync data
     */
    public static GlobalModel getListOfAllTables() {

        //todo درج اسامی تمام جداول
        GlobalModel globalModel = new GlobalModel();
        ArrayList<Class> list = new ArrayList<>();

       // list.add(AgroFieldTable.class);
        list.add(FormRefTable.class);
        list.add(UserTable.class);
        list.add(UtilCodeTable.class);
       // list.add(KartableRecieverTable.class);
        list.add(BaseCodingTable.class);
       // list.add(GoodsTable.class);
        list.add(GroupRelatedTable.class);
      //  list.add(TimeTable.class);
      //  list.add(TaskTable.class);
        list.add(AttachmentTable.class);
      //  list.add(ChatroomTable.class);
      //  list.add(ChatroomMemberTable.class);
      //  list.add(MessageTable.class);
      //  list.add(CartableTable.class);
        list.add(WarehouseHandlingTable.class);
     //  list.add(WeighingTable.class);
        list.add(WarehouseHandlingListOutTable.class);
     //   list.add(GoodTranceTable.class);
     //   list.add(PurchasableGoodsTable.class);
     //   list.add(SalableGoodsTable.class);
     //   list.add(ContactBookTable.class);
     //   list.add(AddressBookTable.class);
     //   list.add(CountryDivisionTable.class);
      //  list.add(FuelListMasterTable.class);
      //  list.add(FuelListDetailTable.class);
        list.add(EvacuatePacketTable.class);
      //  list.add(AccidentTable.class);
        globalModel.setClassArrayList(list);
        return globalModel;
    }



}
