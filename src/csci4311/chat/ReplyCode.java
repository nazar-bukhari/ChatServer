package csci4311.chat;

/**
 * Created by nazar on 11/8/15.
 */
public enum ReplyCode {

    OK("200 OK"),
    NORESULT("201 NO result"),
    ERROR("400 Error");

    private String replyType;

    private ReplyCode(String replyType) {
        this.replyType = replyType;
    }

    public String getReplyType(){
        return replyType;
    }
}
