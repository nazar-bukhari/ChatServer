package csci4311.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
        final List<Socket> sockets = new CopyOnWriteArrayList<Socket>();

        try {

            sSock = new ServerSocket(port);
//            sockets.add(sSock.accept());

//            for (Socket s : sockets)  {
//                System.out.println(s);
//            }

            while(true){
                try{

                    PacketReader pr = new PacketReader();
                    pr.socket  = sSock.accept(); // Connection block here,until another hardware comes to connect.Upto then,it executes the calling function.
                    readerCount++;
                    System.out.println("Total connected device: " + readerCount);
//                    sockets.add(sSock.accept());
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
