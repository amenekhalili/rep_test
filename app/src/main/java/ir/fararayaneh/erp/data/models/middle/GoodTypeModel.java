package ir.fararayaneh.erp.data.models.middle;

import ir.fararayaneh.erp.data.models.IModels;

/**
 * در ارتباط با فیلد goodTypeList در جدول goods
 *
 * GTCode : برای فیلتر کردن این موضوع که این واریته از جنس کدام دسته بندی است
 * مثلا اگر کالای اصلی پسته است
 * این واریته بر اساس محل تولید پسته است (رفسنجان یا دامغان)
 * یا این واریته بر اساس نوع پسته است
 * (کله قوچی یا ...)
 * اتچمنت مربوط به این داده ها در اتچمنت مربوط به کالا قرار داه شده است
 */
public class GoodTypeModel implements IModels {

    private String id, GTCode,typeCode,typeName;

    public GoodTypeModel(String id, String GTCode, String typeCode, String typeName) {
        this.id = id;
        this.GTCode = GTCode;
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGTCode() {
        return GTCode;
    }

    public void setGTCode(String GTCode) {
        this.GTCode = GTCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
