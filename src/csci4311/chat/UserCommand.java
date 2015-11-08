package csci4311.chat;

import java.io.*;

/**
 * Created by nazar on 11/8/15.
 */
public class UserCommand {

    private final String firstKey = "msgp";

//    private String secondKey = null;

    public UserCommand() {

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            String userInput = bufferRead.readLine();
//            System.out.println("Text="+userInput);

            String[] inputSet = userInput.split(" ");

            if(inputSet[0].equals(firstKey)){
                verifySecondKey(inputSet,inputSet[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verifySecondKey(String[] inputSet,String secondKey) throws IOException {

        String user;
        String group;
        String fileName ;

        switch(secondKey){

            case "join" :
                System.out.println("join command");
                user = inputSet[2];
                group = inputSet[3];
                File f = new File(group+".txt");
                fileName = f.getName();
                if(f.exists()){

                    FileWriter ostream = new FileWriter(fileName,true);
                    BufferedWriter out = new BufferedWriter(ostream);
                    out.write("\n");
                    out.write(user);
                    out.flush();
                    out.close();
                }
                else{
                    System.out.println("No "+f.getName());
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
