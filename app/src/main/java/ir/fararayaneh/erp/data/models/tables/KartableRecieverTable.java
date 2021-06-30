package ir.fararayaneh.erp.data.models.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * This table show users that we can have
 * Official correspondence with them
 *
 * attach : "[{"attachmentGUID":"1a2b3","columnNumber":"123","B5HCTypeId":"123"},...]"
 */

public class KartableRecieverTable extends RealmObject implements IModels {

    public KartableRecieverTable() {
    }

    public KartableRecieverTable(String id, String name, String attach, String syncState) {
        this.id = id;
        this.name = name;
        this.attach = attach;
        this.syncState = syncState;
    }

    @PrimaryKey
    private String id;
    private String name,attach;

    //---------------------------------------------------------------------->>
    private String syncState;//for SyncActivity


    public String getSyncState() {
        return syncState==null? CommonSyncState.SYNCED :syncState;//chon hame data az database be  ma reside ast
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }
    //---------------------------------------------------------------------->>

    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public String getName() {
        return name==null?Commons.NULL:name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttach() {
        return attach==null?Commons.NULL_ARRAY:attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}
