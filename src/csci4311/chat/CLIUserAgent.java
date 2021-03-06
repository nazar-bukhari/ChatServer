package csci4311.chat;

import java.io.*;
import java.net.Socket;

/**
 * Created by nazar on 11/9/15.
 */
public class CLIUserAgent implements UserAgent {

    //    The csci4311.chat.CLIUserAgent class must only implement user interaction, and no
//            protocol details.
    private static String userName;
    private static String server;
    private static int port;
    Socket socket;

    public static void main(String args[]) {

        userName = args[0];
        server = args[1];
        port = Integer.parseInt(args[2]);

        try {
            Socket socket = new Socket(server, port);
//            System.out.println("user=" + userName + " server=" + server + " port=" + port);
            new CLIUserAgent().packetReceiver(socket);
            new CLIUserAgent().packetSender(socket,userName);
        } catch (Exception ex) {
                ex.printStackTrace();
        }

    }


    @Override
    public void packetReceiver(Socket socket) {

        try {

            new ClientReceiver(socket,userName).start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void packetSender(Socket socket,String userName) {

        try {

            new ClientSender(socket, userName).start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
