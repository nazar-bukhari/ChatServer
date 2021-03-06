package csci4311.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nazar on 11/9/15.
 */
public class SendMessage {


    private String sendMessage = null;
    ServerSocket sSocket;
    Socket socket;
    BufferedReader br ;
    PrintWriter write ;

    public SendMessage() {

        Scanner scanner;//= new Scanner(System.in);
        String input;
        int counter = 0;
        Pattern p;
        Matcher m;
        String regEx;
        boolean isFromValid = false;
        boolean isToActivated = false;
        List<String> userList = new LinkedList();
        List<String> groupList = new LinkedList();


        while (true) {

            scanner = new Scanner(System.in);
            input = scanner.nextLine();
//                    System.out.println("input="+input);
            if (input.equals("")) {

                //<cr><lf>
                if (isToActivated) {
                    counter++;
//                    System.out.println("Counter=" + counter);
                    if (counter == 2) {
//                    break;
                        if (sendMessage != null) {
                            System.out.println("Original text: " + sendMessage+" to: "+userList.get(0));

                            //Sending message over socket ..................
                            try {

                                Thread reads = new Thread(new cread());
                                reads.start();
                            }
                            catch(Exception ex){
                                ex.printStackTrace();
                            }

                            break;
                        } else {
                            System.out.println("Message not set. ");
                        }
                    }
                }
            } else {

                //Valid Message
                regEx = "from:[ A-Za-z0-9]{1,15}"; //from:XXXXX
                p = Pattern.compile(regEx);
                m = p.matcher(input);

                if (m.matches()) {
                    isFromValid = true;
//                    System.out.println("From Valid");
                } else if (counter < 1 && isFromValid) {

                    regEx = "to:(@|#)[A-Za-z0-9]{1,5}"; //to:XXXX
                    p = Pattern.compile(regEx);
                    m = p.matcher(input);

                    if (m.matches()) {
                        if(input.contains("@")){
                            String singleReceiver[] = input.split("@");
                            userList.add(singleReceiver[1]);
                        }
                        else if(input.contains("#")){
                            String groupReceiver[] = input.split("#");
                            groupList.add(groupReceiver[1]);
                        }
                        isToActivated = true;
//                        System.out.println("To Valid");
                    }
                } else if (counter >= 1) {

                    sendMessage = input;
                    if (counter == 2) {

                    }
                }

            }
        }
    }

    public class cread implements Runnable
    {

        public void run()
        {
            try
            {
                System.out.println("Try..");
                sSocket = new ServerSocket(1025);
                socket = sSocket.accept();
                System.out.println("sendMessage="+sendMessage);

//                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                write = new PrintWriter(socket.getOutputStream(),true);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(br.readLine());
                write.write(sendMessage);
                write.flush();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

    }
}
