package csci4311.chat;

/**
 * Created by nazar on 11/9/15.
 */
public class TextMsgpClient implements  MsgpClient{

    //    The csci4311.chat.TextMsgpClient class must only implement the msgp protocol specified
//            above and have no user agent functionality.

//    csci4311.chat.TextMsgpClient, which implements the csci4311.chat.MsgpClient
//    interface;

    @Override
    public boolean isValidJoinProtocol() {
        return false;
    }

    @Override
    public boolean isValidLeaveProtocol() {
        return false;
    }

    @Override
    public boolean isValidGroupsProtocol() {
        return false;
    }

    @Override
    public boolean isValidUsersProtocol() {
        return false;
    }

    @Override
    public boolean isValidHistoryProtocol() {
        return false;
    }

    @Override
    public boolean isValidSendProtocol() {
        return false;
    }


}
