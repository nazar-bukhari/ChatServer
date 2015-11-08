package csci4311.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by nazar on 11/8/15.
 */
public class UserCommand {

    private final String startingWord = "msgp";
//    private String secondKey = null;

    public UserCommand() {

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            String userInput = bufferRead.readLine();
//            System.out.println("Text="+userInput);

            String[] inputSet = userInput.split(" ");

            if(inputSet[0].equals(startingWord)){
                System.out.println("Valid ");
                verifySecondKey(inputSet[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verifySecondKey(String secondKey){

        switch(secondKey){
            case "join" :
                System.out.println("join command");
                break;
            case "leave" :
                System.out.println("leave command");
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
                System.out.println("Comamnd not allowed");

        }
    }
}
