package vn.byt.qlds.model.system;

public class NotificationResponse {
    private String messageId;
    private String description;

    public NotificationResponse() {
    }

    public NotificationResponse(String messageId, String description) {
        this.messageId = messageId;
        this.description = description;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
