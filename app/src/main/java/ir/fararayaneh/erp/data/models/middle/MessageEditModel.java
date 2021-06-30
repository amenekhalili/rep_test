package ir.fararayaneh.erp.data.models.middle;


import ir.fararayaneh.erp.data.models.IModels;

/**
 * این کلاس به عنوان یک بادی ارسال میشود
 * تا در زمان دریافت توسط کلاینت تمام مقادیر مربوط به چت روم درج شده در روت این پکت
 * دارای مقدار سین دیت با زمان حاظر گردند
 * به دلیل ساختار دیتای بروزر در اینجا چتروم جی یو ای دی هم ارسال میشود
 */
public class MessageEditModel implements IModels {
    private String chatRoomGUID;

    public MessageEditModel(String chatRoomGUID) {
        this.chatRoomGUID = chatRoomGUID;
    }

    public String getChatRoomGUID() {
        return chatRoomGUID;
    }

    public void setChatRoomGUID(String chatRoomGUID) {
        this.chatRoomGUID = chatRoomGUID;
    }
}
