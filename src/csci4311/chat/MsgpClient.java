package csci4311.chat;

/**
 * Created by nazar on 11/9/15.
 */
public interface MsgpClient {

    //The csci4311.chat.TextMsgpClient class must only implement the msgp protocol specified
//    above and have no user agent functionality.

    //Protocol


    public boolean isValidJoinProtocol(String clientMessage);
    public boolean isValidLeaveProtocol(String clientMessage);
    public boolean isValidGroupsProtocol(String clientMessage);
    public boolean isValidUsersProtocol(String clientMessage);
    public boolean isValidHistoryProtocol(String clientMessage);
    public boolean isValidSendProtocol(String clientMessage);
}
