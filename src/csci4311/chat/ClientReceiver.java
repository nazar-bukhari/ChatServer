package csci4311.chat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by nazar on 11/11/15.
 */
public class ClientReceiver extends Thread {

    private Socket socket;
    InputStream istream;
    BufferedReader receiveRead;
    String receiveMessage;


    public ClientReceiver(Socket socket) {

        this.socket = socket;
    }


    @Override
    public void run() {

        try

        {
            istream = socket.getInputStream();
            receiveRead = new BufferedReader(new InputStreamReader(istream));

            while(true){

                if((receiveMessage = receiveRead.readLine()) != null) //receive from server
                {
                    System.out.println(receiveMessage); // displaying at DOS prompt
                }
            }


        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}
