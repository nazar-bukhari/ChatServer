package csci4311.chat;

import java.net.Socket;

/**
 * Created by nazar on 11/9/15.
 */
public interface UserAgent {

//    csci4311.chat.CLIUserAgent, which
//    implements
//    the
//    csci4311.chat.UserAgent
//    interface,

//    The csci4311.chat.CLIUserAgent class must only implement user interaction, and no
//            protocol details.

    //No protocol details
//    public void startReadWriteOperation();

    public void packetReceiver(Socket socket);
    public void packetSender(Socket socket,String clientName);

}
