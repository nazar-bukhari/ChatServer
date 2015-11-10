package csci4311.chat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Thread {


    public void run() {

        new UserCommand().run();
    }

    public static void main(String[] args) {


        Main m = new Main();
        m.start();

        try {
            PacketReceiver pr = new PacketReceiver(1025);
            pr.start();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
