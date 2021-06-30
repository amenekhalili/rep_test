package ir.fararayaneh.erp.utils.data_handler;

import java.util.ArrayList;

import ir.fararayaneh.erp.commons.CommonKind;

public class KindListForSyncActHelper {


    public static ArrayList<String> getAllKindsList() {
        ArrayList<String> arrayList = new ArrayList<>();
       //arrayList.add(CommonKind.SYNCCARTABLE);
       //arrayList.add(CommonKind.SYNCMESSAGE);
       //arrayList.add(CommonKind.SYNCTIME);
        arrayList.add(CommonKind.SYNCUTILCODE);
       //arrayList.add(CommonKind.SYNCAGROFIELD);
        arrayList.add(CommonKind.SYNCBASECODING);
        //arrayList.add(CommonKind.SYNCCHATROOM);
        //arrayList.add(CommonKind.SYNCCHATROOMMEMBER);
        arrayList.add(CommonKind.SYNCFORMREF);
        //arrayList.add(CommonKind.SYNCGOODS);
        arrayList.add(CommonKind.ATTACH);
        arrayList.add(CommonKind.SYNCGROUPRELATED);
        arrayList.add(CommonKind.SYNCWAREHOUSEHANDLING);
        //arrayList.add(CommonKind.SYNCKARTABLERECIEVER);
        //arrayList.add(CommonKind.SYNCTASK);
        //arrayList.add(CommonKind.SYNCWEIGHING);
        arrayList.add(CommonKind.SYNCWAREHOUSEHANDLINGLISTOUT);
        //arrayList.add(CommonKind.SYNCGOODTRANCE);
        //arrayList.add(CommonKind.SYNCPURCHASABLEGOODS);
        //arrayList.add(CommonKind.SYNCSALABLEGOODS);
        //arrayList.add(CommonKind.SYNCCONTACTBOOK);
        //arrayList.add(CommonKind.SYNCADDRESSBOOK);
        //arrayList.add(CommonKind.SYNCCOUNTRYDIVISION);
        //arrayList.add(CommonKind.SYNCFUELLISTMASTER);
        //arrayList.add(CommonKind.SYNCFUELLISTDETAIL);
        //arrayList.add(CommonKind.SYNCACCIDENT);
        return arrayList;
    }

    public static ArrayList<String> getWarehouseKindsList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(CommonKind.SYNCWAREHOUSEHANDLING);
        arrayList.add(CommonKind.SYNCWAREHOUSEHANDLINGLISTOUT);
        return arrayList;
    }

    public static ArrayList<String> getChatKindsList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(CommonKind.SYNCCHATROOM);
        arrayList.add(CommonKind.SYNCMESSAGE);
        arrayList.add(CommonKind.SYNCCHATROOMMEMBER);
        arrayList.add(CommonKind.ATTACH);

        return arrayList;
    }


}
