package csci4311.chat;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by nazar on 11/10/15.
 */
public class PacketReader extends Thread {

    public Socket socket;
    private boolean isRunning;
//    List<Socket> sockets = new CopyOnWriteArrayList<Socket>();
    List<Socket> sockets ;
    int length;

    public PacketReader( List<Socket> sockets){

        this.sockets = sockets;
    }

    @Override
    public void run() {

        InputStreamReader isr;
        BufferedReader br;
        isRunning = true;
        String packetStr;


        if(!sockets.contains(socket)) {
            sockets.add(socket);
        }
//        System.out.println("size="+sockets.size());

        try {

            InputStream is = socket.getInputStream();

            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

//            System.out.println(br.readLine() +" from : "+socket);


            while (!socket.isClosed() && isRunning) {

                try {

                    packetStr = br.readLine();
//                    System.out.println(packetStr+"(b4 null check) " +" from : "+socket);

                    if (packetStr != null) {

                        System.out.println(packetStr +" from : "+socket);

                        if(packetStr != null){

                            String[] clientRequest = packetStr.split(" ");
                            String baseCommand = clientRequest[0];
                            response(baseCommand,clientRequest);
                        }

//                        Thread.sleep(3000);
//                        for (Socket s : sockets)  {
////                            System.out.println("s="+s);
//                            try {
//                                OutputStream ostream = s.getOutputStream();
//                                PrintWriter pwrite = new PrintWriter(ostream, true);
//                                pwrite.println("Server Response");
//                                pwrite.flush();
//                                System.out.println("Sending response at : "+s);
//
//                            } catch (Exception ex) {
//                                // handle exception, close the socket.
////                                sockets.remove(s);
//                                ex.printStackTrace();
//                            }
//                    }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void response(String baseCommand,String[] packet) {

        try {

            switch(baseCommand){

               case "join":

                   if(packet[1] != null) {

                       String group = packet[1];
                       File file = new File(group + ".txt");
                       int counter = 0;
                       if (file.exists()) {

                           try {

                               BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getName())));
                               //Checking null group
                               if (br.readLine() != null) {

                                   System.out.println(ReplyCode.OK.getReplyType());
                                   br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getName())));
                                   while ((br.readLine()) != null) {
                                       counter++;
                                   }
//                                   System.out.println("counter=" + counter);
                                   br.close();
                               } else {
                                   System.out.println(ReplyCode.NORESULT.getReplyType()); //User is not a member of the group
                               }
                           } catch (Exception ex) {
                               ex.printStackTrace();
                           }

                       if (packet[2] != null) {

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
                                   for (Socket socket : sockets) {
                                       OutputStream ostream = socket.getOutputStream();
                                       PrintWriter pwrite = new PrintWriter(ostream, true);
                                       pwrite.println("joined #" + group + " with " + counter + " current members");
                                       pwrite.flush();
                                   }

                               } catch (IOException e) {
                                   e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                               }

                           }

                       }
                   }
                   }
                   break;

               case "leave" :
                   break;
                case "groups" :

                    length = packet.length;
                    String groupname;
                    BufferedReader br = null;
                    BufferedReader individualGroupbr;
//                    if(length == 1){
//                        groups
//                        #4311 has 12 members

                        try {


                            String groupValue;

                            br = new BufferedReader(new FileReader("Group.txt"));

                            while ((groupValue = br.readLine()) != null) {

                                groupname = groupValue + ".txt";
                                individualGroupbr = new BufferedReader(new FileReader(groupname));

                                int counter = 0;
                                while(individualGroupbr.readLine()!=null){
                                    counter++;
                                }
//                                System.out.println("#"+groupValue +" has "+counter+" members");

                                OutputStream ostream = socket.getOutputStream();
                                PrintWriter pwrite = new PrintWriter(ostream, true);
                                pwrite.println("#"+groupValue +" has "+counter+" members");
                                pwrite.flush();
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

//                    }

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
