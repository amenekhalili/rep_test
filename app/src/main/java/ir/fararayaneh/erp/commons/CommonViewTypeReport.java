package ir.fararayaneh.erp.commons;

/**
 * for dynamic change view in report adaptor and selection adaptor
 */
public class CommonViewTypeReport {

    public static final int NORMAL_VIEW_TYPE = 0;
    public static final int DELETED_VIEW_TYPE = 1;//activity kind is deleted or row is deleted
    public static final int INSERTED_VIEW_TYPE = 2;//add by socket
    public static final int ACCESS_DENIED_VIEW_TYPE = 3;
    public static final int EDITED_VIEW_TYPE = 4;//all edited excepted deleted and access denied

}
