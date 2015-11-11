package csci4311.chat;

import java.io.*;
import java.net.Socket;

/**
 * Created by nazar on 11/9/15.
 */
public class CLIUserAgent {

//    The csci4311.chat.CLIUserAgent class must only implement user interaction, and no
//            protocol details.

    public static void main(String args[]){

        String userName = args[0];
        String server = args[1];
        int port = Integer.parseInt(args[2]);

        System.out.println("user="+userName+" server="+server+" port="+port);

        try {
            Socket socket = new Socket(server, port);

            new ClientReceiver(socket).start();
            new ClientSender(socket,userName).start();

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
