package csci4311.chat;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nazar on 11/10/15.
 */
public class PacketReader extends Thread {

    public Socket socket;
    private boolean isRunning;
    List<Socket> sockets;
    int length;

    public PacketReader(List<Socket> sockets) {

        this.sockets = sockets;
    }

    @Override
    public void run() {

        InputStreamReader isr;
        BufferedReader br;
        isRunning = true;
        String packetStr;


        if (!sockets.contains(socket)) {
            sockets.add(socket);
        }
//        System.out.println("size="+sockets.size());

        try {

            InputStream is = socket.getInputStream();

            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            while (!socket.isClosed() && isRunning) {

                try {

                    packetStr = br.readLine();

                    if (packetStr != null) {

                        System.out.println(packetStr + " from : " + socket);

                        if (packetStr != null) {

                            String[] clientRequest = packetStr.split(" ");
                            String baseCommand = clientRequest[0];
                            response(baseCommand, clientRequest, packetStr);
                        }
                    }

                } catch (Exception ex) {
//                    ex.printStackTrace();
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void response(String baseCommand, String[] packet, String packetString) {

        String group = null;
        BufferedReader br;
        File file = null;
        FileWriter fWriter;
        OutputStream ostream;
        PrintWriter pwrite;

        try {

            switch (baseCommand) {

                case "join":

                    if (packet[1] != null) {

                        group = packet[1];
                        file = new File(group + ".txt");
                        int counter = 0;
                        if (file.exists()) {

                            try {

                                br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getName())));
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
                                    fWriter = null;
                                    try {
                                        //Writing into file
                                        fWriter = new FileWriter(file.getName(), true);
                                        BufferedWriter out = new BufferedWriter(fWriter);
                                        scanner = new Scanner(file);
                                        if (scanner.hasNext()) {
                                            out.write("\n");
                                        }
                                        out.write(user);
                                        out.flush();
                                        out.close();

                                        //Sending back confirmation message to client over socket
                                        ostream = socket.getOutputStream();
                                        pwrite = new PrintWriter(ostream, true);
                                        pwrite.println("joined #" + group + " with " + counter + " current members");
                                        pwrite.flush();


                                    } catch (IOException e) {
                                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                    }

                                }

                            }
                        }
                    }
                    break;

                case "leave":

                    group = packet[1];
                    String user = packet[2];
                    new UserCommand().leaveGroup(user, group, socket, true);

                    break;

                case "groups":

                    length = packet.length;
                    String groupname;
                    BufferedReader individualGroupbr;

                    try {

                        String groupValue;

                        br = new BufferedReader(new FileReader("Group.txt"));

                        while ((groupValue = br.readLine()) != null) {

                            groupname = groupValue + ".txt";
                            individualGroupbr = new BufferedReader(new FileReader(groupname));

                            int counter = 0;
                            while (individualGroupbr.readLine() != null) {
                                counter++;
                            }
//                                System.out.println("#"+groupValue +" has "+counter+" members");

                            ostream = socket.getOutputStream();
                            pwrite = new PrintWriter(ostream, true);
                            pwrite.println("#" + groupValue + " has " + counter + " members");
                            pwrite.flush();
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    break;

                case "users":

                    group = packet[1];
                    new UserCommand().usersCommand(group, socket, true);

                    break;

                case "history":

                    group = packet[1];
                    file = new File(group+"_history.txt");
                    System.out.println("History file = "+file.getName());
                    new UserCommand().showFileContents(file,true,socket);

                    break;

                case "send":

                    String groupRegEx = "#\\s*(\\w+)";

                    Pattern p = Pattern.compile(groupRegEx);
                    Matcher m = p.matcher(packetString);
                    String users;
                    List<String> userList = null;
                    String originalMessage = null;
                    String[] splitedMessage = new String[0];
                    boolean isGroupMessage = false;

                    while (m.find()) {

                        group = m.group(1);
                        file = new File( group+ ".txt");

                        if (file.exists()) {

                            isGroupMessage = true;
                            br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getName())));
                            userList = new LinkedList<>();

                            if (br.readLine() != null) {

                                br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getName())));
                                while ((users = br.readLine()) != null) {

                                    System.out.println(users);      //Displaying users name in server prompt
                                    userList.add(users);

                                }
                                br.close();
                            } else {
                                System.out.println(ReplyCode.NORESULT.getReplyType()); //User is not a member of the group
                            }


                            String messageWithoutGroupName = packetString.replaceAll("(#).*?\\S*", "");
                            splitedMessage = messageWithoutGroupName.split(" ", 2);
                            originalMessage = splitedMessage[1];

                            for (String s : userList) {

                                originalMessage = "@" + s + " " + originalMessage;
                            }

                            System.out.println("originalMessage=" + splitedMessage[0] + " " + originalMessage + " packetString=" + packetString);
                        }

                    }

                    if (isGroupMessage) {

                        for (Socket socket : sockets) {

                            new UserCommand().displayInClientWindow(splitedMessage[0] + " " + originalMessage, socket);
                        }

                        int lastWordPosition = splitedMessage[1].lastIndexOf(" ");
                        String mainMessage = splitedMessage[1].substring(0, lastWordPosition).trim();
                        String senderName = splitedMessage[1].substring((lastWordPosition)).trim();
                        System.out.println(mainMessage + " from " + senderName);
                        String historyMessage = "[" + senderName + "] " + mainMessage;
                        saveHistory(historyMessage, group);


                    } else if (!isGroupMessage) {

                        for (Socket socket : sockets) {
                            new UserCommand().displayInClientWindow(packetString, socket);
                        }
                    }

                    break;

                default:

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void saveHistory(String message, String groupName) {

        try {
            //Writing into file
            File file = new File(groupName+"_history.txt");
            FileWriter fWriter = new FileWriter(file.getName(), true);
            BufferedWriter out = new BufferedWriter(fWriter);
            Scanner scanner = new Scanner(file);
            if (scanner.hasNext()) {
                out.write("\n");
            }
            out.write(message);
            out.flush();
            out.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
