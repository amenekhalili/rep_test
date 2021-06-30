package ir.fararayaneh.erp.data.models.tables;


import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * برای نگه داری پکت ها
 *
 * در فانکشن
 * SendPacket.sendEncryptionMessage()
 * هر پکت پیش از ارسال در این جدول ذخیره میشود و سپس ارسال میشود
 * و از طرف دیگر در سرویس
 * در فانکشن
 * workForUpdateEvacuateModel()
 * در صورتی که سینک استیت ما سینکد یا اکسس دیناید است از این جدول پاک میشود
 * در ضمن اگر پکتی فاقد guid
 * است  در این جدول نگه داری نمیشود

 * این جدول پس از سینک کلی جداول پاک میشود
 */
public class EvacuatePacketTable extends RealmObject implements IModels {

    @PrimaryKey
    @Index
    private String guid;
    private String jsonPacket,syncState;

    public EvacuatePacketTable(String guid, String jsonPacket) {
        this.guid = guid;
        this.jsonPacket = jsonPacket;
        syncState = CommonSyncState.SYNCED;//only for AllTBLListHelper.getListOfAllTables --in parametr baraye in ast ke agar bekhahim table ra pak konim, va na baraye check kardane sinc shodan
    }

    public EvacuatePacketTable() {
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getJsonPacket() {
        return jsonPacket;
    }

    public void setJsonPacket(String jsonPacket) {
        this.jsonPacket = jsonPacket;
    }

    public String getSyncState() {
        return syncState;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }
}
