package csci4311.chat;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by nazar on 11/10/15.
 */
public class PacketReader extends Thread {

    public Socket socket;
    private boolean isRunning;

    @Override
    public void run() {

        InputStreamReader isr;
        BufferedReader br;
        isRunning = true;
        String packetStr;


        try {

            InputStream is = socket.getInputStream();

            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            while (!socket.isClosed() && isRunning) {

                try {

                    packetStr = br.readLine();
                    if (packetStr != null) {
                        System.out.println(packetStr);

                        if(packetStr != null){

                            String[] clientRequest = packetStr.split(" ");
                            String baseCommand = clientRequest[0];
                            response(baseCommand,clientRequest);
                        }

//                        Thread.sleep(3000);
//                        for (Socket s : sockets)  {
//                            System.out.println(s);
//                            try {
//                                OutputStream ostream = s.getOutputStream();
//                                PrintWriter pwrite = new PrintWriter(ostream, true);
//                                pwrite.println("Server Response");
//                            } catch (IOException ioe) {
//                                // handle exception, close the socket.
//                                sockets.remove(s);
//                            }
//                    }
                    }

                } catch (Exception ex) {

                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void response(String baseCommand,String[] packet) {

        try {
            OutputStream ostream = socket.getOutputStream();
            PrintWriter pwrite = new PrintWriter(ostream, true);

            switch(baseCommand){

               case "join":

                   if(packet[1] != null){

                       String group = packet[1];
                       File file = new File(group + ".txt");
                       int counter = 0;
                       if(file.exists()){

                           try {

                               BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getName())));
                               //Checking null group
                               if (br.readLine() != null) {

                                   System.out.println(ReplyCode.OK.getReplyType());
                                   br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getName())));
                                   while (( br.readLine()) != null) {
                                       counter++;
                                   }
                                   System.out.println("counter="+counter);
                                   br.close();
                               } else {
                                   System.out.println(ReplyCode.NORESULT.getReplyType()); //User is not a member of the group
                               }
                           } catch (Exception ex) {
                               ex.printStackTrace();
                           }
                       }
                       if(packet[2] != null){

                           String user = packet[2];
                           boolean isUserPresent = false;
                           Scanner scanner = null;

                           try {
                               scanner = new Scanner(file);
                           } catch (FileNotFoundException e) {
                               e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                           }
                           while (scanner.hasNextLine()) {
                               /**
                                * User Already Member
                                */
                               if (user.equals(scanner.nextLine().trim())) {
                                   // same user found
                                   System.out.println(ReplyCode.NORESULT.getReplyType());
                                   isUserPresent = true;
                                   break;
                               } else {

                               }

                           }

                           if (isUserPresent == false) {

                               System.out.println(ReplyCode.OK.getReplyType());
                               FileWriter fWriter = null;
                               try {
                                   fWriter = new FileWriter(file.getName(), true);
                                   BufferedWriter out = new BufferedWriter(fWriter);
                                   scanner = new Scanner(file);
                                   if (scanner.hasNext()) {
                                       out.write("\n");
                                   }
                                   out.write(user);
                                   out.flush();
                                   out.close();

                                   pwrite.println("joined #"+group+" with "+counter+" current members");
                                   pwrite.flush();

                               } catch (IOException e) {
                                   e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                               }

                           }

                       }
                   }
                   break;

               case "leave" :
                   break;
                case "groups" :
                    break;
                case "users" :
                    break;
                case "history" :
                    break;
                case "send" :
                    break;
                default:


            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
