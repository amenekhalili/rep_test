package ir.fararayaneh.erp.commons;

/**
 * set number of realm results, that send to ram in report and selection activities
 * and use in "SpinnerDialogQueries.class"  and "ReportQueries.class"
 */
public class CommonReportSelectTableNumbers {

    public static final byte REPORT_CARTABLE_TABLE_NUMBER = 1;
    public static final byte REPORT_CHAT_LIST_TABLE_NUMBER = 4;//chtRoom,lastMessage,utilCode(for find chat type),chatMember(those member that is in private room with me and those chatRoom that we are in them)
    public static final byte REPORT_MESSAGE_TABLE_NUMBER = 4;//message,chatRoom, member,utilCode(for find messageType)
    public static final byte REPORT_GOOD_TRANS_TABLE_NUMBER = 2;
    public static final byte REPORT_GOODS_TABLE_NUMBER = 1;
    public static final byte REPORT_GOODS_TABLE_SELECT_NUMBER = 2;//for selection activity
    public static final byte REPORT_TASK_TABLE_NUMBER = 2;
    public static final byte REPORT_FUEL_MASTER_TABLE_NUMBER = 1;
    public static final byte REPORT_FUEL_DETAIL_TABLE_NUMBER = 2;
    public static final byte REPORT_TIME_TABLE_NUMBER = 1;
    public static final byte REPORT_WEIGHTING_TABLE_NUMBER = 1;
    public static final byte REPORT_SELECT_PURCHASABLE_GOODS_TABLE_NUMBER = 1;
    public static final byte REPORT_SELECT_SALABLE_GOODS_TABLE_NUMBER = 1;
    public static final byte SPINNER_GOODS_NAME_TABLE_NUMBER = 1;
    public static final byte SPINNER_ADDRESS_NAME_TABLE_NUMBER = 1;
    public static final byte SPINNER_CUSTOMER_GROUP_FROM_GR_TABLE_NUMBER = 1;
    public static final byte SPINNER_GOODS_SUB_UNIT_TABLE_NUMBER = 1;
    public static final byte ONE_CONFIG_BASE_CODING_TABLE_NUMBER = 1;
    public static final byte ONE_CONFIG_UTIL_CODE_TABLE_NUMBER = 1;


}
