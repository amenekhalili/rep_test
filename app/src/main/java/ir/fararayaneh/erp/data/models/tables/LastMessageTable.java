package ir.fararayaneh.erp.data.models.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.data.models.IModels;

/**
 * only for local use
 * چون این جدول ارسال و دریافت نمیشود بنا براین نیاز به سینک استیت نداریم
 * وقتی پکت مسیج به دست ما میرسد با بیزینس جدول چت روم این جدول را به روز میکنیم
 * نیازی به رست و سوکت ندارد
 * و در حکم یک
 * sharedPreference
 * عمل میکند
 *
 * در زمان سینک جدول چت روم این جدول نیز پاک میشود و بنابراین نیازی به کایند برای سینک موجود نیست
 *
 * در زمان ورود به چت لیست هم این جدول پاک و با مقادیر مناسب پر میشود
 *
 * اگر پیام نداریم اما فایل داریم :
 * message : if message=Commons.NULL || message=Commons.SPACE  ==> if haveFile = CommonsDownloadFile.HAVE_FILE >>> lastMessage = یک فایل ارسال شده است
 *
 */
public class LastMessageTable extends RealmObject implements IModels {

    @PrimaryKey
    private String chatroomId;
    private String messageDate,message,haveFile;


    public LastMessageTable() {
    }


    public LastMessageTable(String chatroomId, String messageDate, String message, String haveFile) {
        this.chatroomId = chatroomId;
        this.messageDate = messageDate;
        this.message = message;
        this.haveFile = haveFile;
    }

    public String getChatRoomId() {
        return chatroomId==null?Commons.NULL_INTEGER:chatroomId;
    }

    public void setChatRoomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getMessageDate() {
        return messageDate==null?Commons.NULL:messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessage() {
        return message==null?Commons.NULL:message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHaveFile() {
        return haveFile==null? CommonsDownloadFile.HAVE_NO_FILE:haveFile;
    }

    public void setHaveFile(String haveFile) {
        this.haveFile = haveFile;
    }
}
