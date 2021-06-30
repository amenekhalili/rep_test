package ir.fararayaneh.erp.data.models.tables.sync_tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.CommonSyncState;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * in jadval tavasot rest avalie va ham tavasote socket por mishavad,
 * ama tavasote user por nemishavad
 * chon kartable be user namayesh dade mishavad vali user
 * nemitavanad baraye khod kartabl ijad namayad pas ehtiaj be fild haye old value va ... nadarad
 *
 * ama field syncState niaz ast baray activity sync
 *
 * definitionName : flow name of oracle db that were be ran behind of this cartable
 *
 * syncState : for use in activity sync
 *
 * guid : for use in report activity(id=guid)(dar rest vojod nadarad)
 *
 *  attach :  {attachmentGUID+suffix(fileName),columnNumber,B5HCTypeId} (AttachmentJsonModel.class)
 */



 public class CartableTable extends RealmObject implements IModels {

    public CartableTable(String id, String insertDate, String subject, String b5HCPriorityName, String definitionName, String senderName, String b5FormRefTitle, String syncState, String attach) {
        this.id = id;
        this.insertDate = insertDate;
        this.subject = subject;
        B5HCPriorityName = b5HCPriorityName;
        this.definitionName = definitionName;
        this.senderName = senderName;
        B5FormRefTitle = b5FormRefTitle;
        this.syncState = syncState;
        this.attach = attach;
        this.guid = String.valueOf(id);
    }

    public CartableTable() {
    }

     @PrimaryKey
     private String id;

     private String insertDate,subject,B5HCPriorityName,definitionName,senderName,B5FormRefTitle,syncState,attach,guid;


    public String getId() {
        return id == null ? Commons.NULL_INTEGER : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsertDate() {
         return insertDate==null? Commons.NULL:insertDate;
     }

     public void setInsertDate(String insertDate) {
         this.insertDate = insertDate;
     }

     public String getSubject() {
         return subject==null? Commons.NULL:subject;
     }

     public void setSubject(String subject) {
         this.subject = subject;
     }

     public String getB5HCPriorityName() {
         return B5HCPriorityName==null? Commons.NULL:B5HCPriorityName;
     }

     public void setB5HCPriorityName(String b5HCPriorityName) {
         B5HCPriorityName = b5HCPriorityName;
     }

     public String getDefinitionName() {
         return definitionName==null? Commons.NULL:definitionName;
     }

     public void setDefinitionName(String definitionName) {
         this.definitionName = definitionName;
     }

     public String getSenderName() {
         return senderName==null? Commons.NULL:senderName;
     }

     public void setSenderName(String senderName) {
         this.senderName = senderName;
     }

     public String getB5FormRefTitle() {
         return B5FormRefTitle==null? Commons.NULL:B5FormRefTitle;
     }

     public void setB5FormRefTitle(String b5FormRefTitle) {
         B5FormRefTitle = b5FormRefTitle;
     }

    public String getSyncState() {
        return syncState==null? CommonSyncState.SYNCED :syncState;//chon hame data az oracle be ma reside ast
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public String getAttach() {
        return attach==null?Commons.NULL_ARRAY:attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getGuid() {
        return String.valueOf(id);
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}

