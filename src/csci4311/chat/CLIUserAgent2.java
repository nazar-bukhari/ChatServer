package csci4311.chat;

import java.io.*;
import java.net.Socket;

/**
 * Created by nazar on 11/9/15.
 */
public class CLIUserAgent2 {

//    The csci4311.chat.CLIUserAgent class must only implement user interaction, and no
//            protocol details.

    public static void main(String args[]){

        String userName = args[0];
        String server = args[1];
        int port = Integer.parseInt(args[2]);

        System.out.println("user="+userName+" server="+server+" port="+port);

        try {
            Socket socket = new Socket(server, port);

            new ClientReceiver(socket,userName).start();

            BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
            OutputStream ostream = socket.getOutputStream();
            PrintWriter pwrite = new PrintWriter(ostream, true);

            InputStream istream = socket.getInputStream();
            BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

            System.out.println("type and press Enter key");

            String receiveMessage, sendMessage;
            while(true)
            {
                sendMessage = keyRead.readLine();  // keyboard reading
                pwrite.println(sendMessage+" "+userName);       // sending to server
                pwrite.flush();                    // flush the data
                if((receiveMessage = receiveRead.readLine()) != null) //receive from server
                {
                    System.out.println(receiveMessage); // displaying at DOS prompt
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
