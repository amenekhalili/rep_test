package ir.fararayaneh.erp.data.models.tables;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * مشخصات زمین یا قطعه کشاورزی یا محدوده های طبیعی دیگر مثل رودخانه
 * J5OrgChartId : آی دی سازمانی که سایت مربوط به آن است
 * surface      : مساحت سایت به متر مربع
 * rowCount     : تعداد ردیف موجود در سایت
 * segmentCount : تعداد کرت موجود در سایت
 * code         : شماره ناحیه بندی انجام شده روی نقشه
 * name         : اسم ناحیه بندی انجام شده روی نقشه
 * field        : شماره منطقه
 * part         : شماره ناحیه
 * <p>
 *  attach :  {attachmentGUID+suffix(fileName),columnNumber,B5HCTypeId} (AttachmentJsonModel.class)
 */

// TODO: 12/22/2018 use index
public class AgroFieldTable extends RealmObject implements IModels {

    public AgroFieldTable(String id, String j5OrgChartId, String surface, String rowCount, String segmentCount, String code, String name, String field, String part, String description, String geoLocation, String attach, String syncState) {
        this.id = id;
        J5OrgChartId = j5OrgChartId;
        this.surface = surface;
        this.rowCount = rowCount;
        this.segmentCount = segmentCount;
        this.code = code;
        this.name = name;
        this.field = field;
        this.part = part;
        this.description = description;
        this.geoLocation = geoLocation;
        this.attach = attach;
        this.syncState = syncState;
    }

    public AgroFieldTable() {

    }

    @PrimaryKey
    private String id;

    private String J5OrgChartId;

    private String surface;

    private String rowCount;

    private String segmentCount;

    private String code;

    private String name;

    private String field;

    private String part;

    private String description;

    private String geoLocation;

    private String attach;

    //---------------------------------------------------------------------->>
    private String syncState;//for SyncActivity


    public String getSyncState() {
        return syncState == null ? CommonSyncState.SYNCED : syncState;//chon hame data az database be  ma reside ast
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }
    //---------------------------------------------------------------------->>


    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJ5OrgChartId() {
        return J5OrgChartId == null ? Commons.NULL_INTEGER : J5OrgChartId;
    }

    public void setJ5OrgChartId(String j5OrgChartId) {
        J5OrgChartId = j5OrgChartId;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public void setSegmentCount(String segmentCount) {
        this.segmentCount = segmentCount;
    }


    public String getSurface() {
        return surface== null ? Commons.NULL_INTEGER : surface;
    }

    public String getRowCount() {
        return rowCount== null ? Commons.NULL_INTEGER : rowCount;
    }

    public String getSegmentCount() {
        return segmentCount== null ? Commons.NULL_INTEGER : segmentCount;
    }

    public String getCode() {
        return code == null ? Commons.NULL : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name == null ? Commons.NULL : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field == null ? Commons.NULL : field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPart() {
        return part == null ? Commons.NULL : part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getDescription() {
        return description == null ? Commons.NULL : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGeoLocation() {
        return geoLocation == null ? Commons.NULL : geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getAttach() {
        return attach == null ? Commons.NULL : attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}
