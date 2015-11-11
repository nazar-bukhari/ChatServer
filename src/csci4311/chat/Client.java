package csci4311.chat;

import java.net.Socket;

/**
 * Created by nazar on 11/11/15.
 */
public class Client extends Thread {


    public void run() {

        new ClientReceiver(null).run();
    }

    public static void main(String[] args) {


        Client c = new Client();
        c.start();

        try {
            PacketReceiver pr = new PacketReceiver(3001);
            pr.start();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
