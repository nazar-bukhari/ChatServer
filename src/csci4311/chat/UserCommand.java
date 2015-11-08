package csci4311.chat;

import java.io.*;
import java.util.Scanner;

/**
 * Created by nazar on 11/8/15.
 */
public class UserCommand {

    private final String firstKey = "msgp";


    public UserCommand() {

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String userInput = bufferRead.readLine();
            String[] inputSet = userInput.split(" ");

            if(inputSet[0].equals(firstKey)){
                verifySecondKey(inputSet,inputSet[1]);
            }
            else{
                System.out.println(ReplyCode.NORESULT.getReplyType());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verifySecondKey(String[] inputSet,String secondKey) throws IOException {

        String user;
        String group;
        String fileName;
        File file;
        boolean isUserPresent = false;

        switch(secondKey){

            case "join" :

                System.out.println("join command");
                user = inputSet[2];
                group = inputSet[3];
                file = new File(group+".txt");
                fileName = file.getName();


                if(file.exists()){

                    Scanner scanner = new Scanner(file);
                    while(scanner.hasNextLine()){
                        /**
                         * User Already Member
                         */
                        if(user.equals(scanner.nextLine().trim())){
                            // same user found
                            System.out.println(ReplyCode.NORESULT.getReplyType());
                            isUserPresent = true;
                            break;
                        }else{

                        }

                    }

                    if(isUserPresent == false){
                        System.out.println(ReplyCode.OK.getReplyType());
                        FileWriter ostream = new FileWriter(fileName,true);
                        BufferedWriter out = new BufferedWriter(ostream);
                        out.write("\n");
                        out.write(user);
                        out.flush();
                        out.close();
                    }

                }
                /**
                 * Group does not exist
                 */
                else{
                    System.out.println(ReplyCode.OK.getReplyType());
                    try {
                        FileWriter writer = new FileWriter(fileName);
                        writer.write(user);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;

            case "leave" :

                System.out.println("leave command");
                user = inputSet[2];
                group = inputSet[3];
                file = new File(group+".txt");
                fileName = file.getName();
                boolean isMember = true;

                //Group exists
                if(file.exists()){

                    Scanner scanner = new Scanner(file);
                    while(scanner.hasNextLine()){

                        if(user.equals(scanner.nextLine().trim())){
                            // User is a member of the group

                            //Delete the user
                            System.out.println(ReplyCode.OK.getReplyType());
                            isMember = true;
                            break;
                        }else{
                            isMember = false; //user is not memeber
                        }

                    }

                    if(isMember == false){
                        System.out.println(ReplyCode.NORESULT.getReplyType()); //User is not a member of the group

                    }
                }
                //Group not exists
                else{
                    System.out.println(ReplyCode.ERROR.getReplyType());
                }

                break;
            case "groups" :
                System.out.println("groups command");
                break;
            case "users" :
                System.out.println("users command");
                break;
            case "send" :
                System.out.println("send command");
                break;
            case "history":
                System.out.println("history command");
                break;
            default:
                System.out.println("Comamnd not found");

        }
    }
}
