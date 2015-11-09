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
            if (userInput != null) {
                String[] inputSet = userInput.split(" ");

                if (inputSet[0].equals(firstKey)) {
                    verifySecondKey(inputSet, inputSet[1]);
                } else {
                    System.out.println(ReplyCode.NORESULT.getReplyType());
                }
            }

        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public void verifySecondKey(String[] inputSet, String secondKey) {

        String user;
        String group;
        String fileName;
        File file;
        boolean isUserPresent = false;

        switch (secondKey) {

            case "join":

                System.out.println("join command");
                user = inputSet[2];
                group = inputSet[3];
                file = new File(group + ".txt");
                fileName = file.getName();

                if (file.exists()) {

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
                        FileWriter ostream = null;
                        try {
                            ostream = new FileWriter(fileName, true);
                            BufferedWriter out = new BufferedWriter(ostream);
                            out.write("\n");
                            out.write(user);
                            out.flush();
                            out.close();

                        } catch (IOException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }

                    }

                }
                /**
                 * Group does not exist
                 */
                else {
                    System.out.println(ReplyCode.OK.getReplyType());
                    try {
                        FileWriter writer = new FileWriter(fileName);
                        writer.write(user);
                        writer.close();

                        FileWriter groupWriter = new FileWriter("Group.txt", true);
//                        BufferedWriter groupWriter = new BufferedWriter(ostream2);
                        Scanner scanner = new Scanner(new File("Group.txt"));
                        if (scanner.hasNext()) {
                            groupWriter.write("\n");
                        }
                        groupWriter.write(group);
                        groupWriter.flush();
                        groupWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;

            case "leave":

                System.out.println("leave command");
                user = inputSet[2];
                group = inputSet[3];
                file = new File(group + ".txt");
                fileName = file.getName();
                boolean isMember = true;

                //Group exists
                if (file.exists()) {

                    Scanner scanner = null;
                    try {
                        scanner = new Scanner(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    while (scanner.hasNextLine()) {

                        if (user.equals(scanner.nextLine().trim())) {
                            // User is a member of the group

                            //Delete the user
                            System.out.println(ReplyCode.OK.getReplyType());
                            isMember = true;
                            break;
                        } else {
                            isMember = false; //user is not memeber
                        }

                    }

                    if (isMember == false) {
                        System.out.println(ReplyCode.NORESULT.getReplyType()); //User is not a member of the group

                    }
                }
                //Group not exists
                else {
                    System.out.println(ReplyCode.ERROR.getReplyType());
                }

                break;

            case "groups":
                System.out.println("groups command");

                File groupFile = new File("Group.txt");
                if (groupFile.exists()) {

                    try {

                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Group.txt")));
                        String line = null;
                        //Checking null group
                        if (br.readLine() != null) {

                            System.out.println(ReplyCode.OK.getReplyType());

                            br = new BufferedReader(new InputStreamReader(new FileInputStream("Group.txt")));
                            while ((line = br.readLine()) != null) {
                                System.out.println(line);
                            }
                            br.close();
                        } else {
                            System.out.println(ReplyCode.NORESULT.getReplyType()); //User is not a member of the group
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
//                    System.out.println(ReplyCode.NORESULT.getReplyType()); //No such group
                }

                break;
            case "users":
                System.out.println("users command");

                group = inputSet[2];
                file = new File(group + ".txt");
                fileName = file.getName();

                //Group exists
                if (file.exists()) {

                    try {

                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
                        String line = null;
                        //Checking null group
                        if (br.readLine() != null) {

                            System.out.println(ReplyCode.OK.getReplyType());
//                        System.out.println("br "+br.readLine());
                            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
                            while ((line = br.readLine()) != null) {
                                System.out.println(line);
                            }
                            br.close();
                        } else {
                            System.out.println(ReplyCode.NORESULT.getReplyType()); //User is not a member of the group
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println(ReplyCode.NORESULT.getReplyType()); //No such group
                }

                break;
            case "send":
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
