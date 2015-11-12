package csci4311.chat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nazar on 11/12/15.
 */
public class test {

    public static void main(String args[]){

        String sentence = "We are @all programmer";
        int lastWordPos = sentence.lastIndexOf( " " );
        System.out.println(lastWordPos);
        System.out.println(sentence.substring(0,lastWordPos));
        String[] baseCommandWithMessage = sentence.split(" ",2);
        System.out.println(sentence.substring((lastWordPos)).trim());

        Pattern p = Pattern.compile("@*");
        Matcher m = p.matcher(sentence);

        if (m.matches()) {
            System.out.println("if");
            System.out.println(m.group());
        }else {
        }
    }
}
