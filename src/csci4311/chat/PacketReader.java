package csci4311.chat;

import java.io.*;
import java.net.Socket;

/**
 * Created by nazar on 11/10/15.
 */
public class PacketReader extends Thread{

    public Socket socket;
    private boolean isRunning;

    @Override
    public void run() {

        InputStreamReader isr;
        BufferedReader br;
        isRunning = true;
        String packetStr;

        try{

            InputStream is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            while (!socket.isClosed() && isRunning) {

                try{
                    packetStr = br.readLine();
                    if(packetStr != null){
                        System.out.println(packetStr);
                        OutputStream ostream = socket.getOutputStream();
                        PrintWriter pwrite = new PrintWriter(ostream, true);
                        pwrite.println("Server Response");
                        pwrite.flush();

                    }
                }
                catch(Exception ex){

                }

            }

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
