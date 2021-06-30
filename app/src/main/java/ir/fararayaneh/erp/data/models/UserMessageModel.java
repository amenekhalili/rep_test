package ir.fararayaneh.erp.data.models;


/*
for show some app message to user, like errors or ...
 */
public class UserMessageModel implements IModels {
    private String title;
    private String message;
    private String otherData;


    public UserMessageModel() {
    }
    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }
}
