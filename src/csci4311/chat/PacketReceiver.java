package csci4311.chat;

import java.net.ServerSocket;

/**
 * Created by nazar on 11/10/15.
 */
public class PacketReceiver extends Thread{

    int port;
    public static volatile int readerCount;

    public PacketReceiver(int port){

        this.port = port;
        readerCount = 0;
    }

    @Override
    public void run() {

        ServerSocket sSock = null;

        try {

            sSock = new ServerSocket(port);

            while(true){
                try{

                    PacketReader pr = new PacketReader();
                    pr.socket  = sSock.accept(); // Connection block here,until another hardware comes to connect.Upto then,it executes the calling function.
                    readerCount++;
                    System.out.println("Total connected device: " + readerCount);
                    pr.start();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                    Thread.sleep(5000);
                }
            }
        }
        catch(Exception ex){

        }

    }
}
