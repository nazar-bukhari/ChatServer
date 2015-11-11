package csci4311.chat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nazar on 11/9/15.
 */
public class TextMsgpClient implements  MsgpClient{

    //    The csci4311.chat.TextMsgpClient class must only implement the msgp protocol specified
//            above and have no user agent functionality.

//    csci4311.chat.TextMsgpClient, which implements the csci4311.chat.MsgpClient
//    interface;

    private String clientMessage;
    Pattern p;
    Matcher m;
    String regEx;

    public boolean isCommandValid(String clientMessage){

        this.clientMessage = clientMessage;

        String[] clientRequest = clientMessage.split(" ");
        String baseCommand = clientRequest[0];

        if(baseCommand.equals("join")){
            return isValidJoinProtocol(clientMessage);
        }
        else if(baseCommand.equals("leave")){
            return  isValidLeaveProtocol(clientMessage);
        }
        else if(baseCommand.equals("groups")){
            return isValidGroupsProtocol(clientMessage);
        }
        else if(baseCommand.equals("users")){
            return isValidUsersProtocol(clientMessage);
        }
        else if(baseCommand.equals("history")){
            return isValidHistoryProtocol(clientMessage);
        }else if(baseCommand.equals("send")){
            return isValidSendProtocol(clientMessage);
        }
        return false;
    }


    @Override
    public boolean isValidJoinProtocol(String clientMessage) {

        regEx = "join [A-Za-z0-9]{1,25}"; //from:XXXXX
        p = Pattern.compile(regEx);
        m = p.matcher(clientMessage);

        if (m.matches()) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean isValidLeaveProtocol(String clientMessage) {
        return false;
    }

    @Override
    public boolean isValidGroupsProtocol(String clientMessage) {
        return false;
    }

    @Override
    public boolean isValidUsersProtocol(String clientMessage) {
        return false;
    }

    @Override
    public boolean isValidHistoryProtocol(String clientMessage) {
        return false;
    }

    @Override
    public boolean isValidSendProtocol(String clientMessage) {
        return false;
    }
}
