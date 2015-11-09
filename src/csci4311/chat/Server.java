package csci4311.chat;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
public class Server implements ActionListener
{
    private JFrame chat=new JFrame("SChat-Server");
    TextField msg;
    TextArea history;

    Button enter;
    ServerSocket ser;
    Socket socket;
    BufferedReader in ;
    PrintWriter out ;
    Panel p1,p2,p3;
    Label l;
    Server()
    {
        chat.setLayout(new FlowLayout());
        chat.setSize(300,350);
        chat.setResizable(false);
        chat.setDefaultCloseOperation(chat.EXIT_ON_CLOSE);
        msg=new TextField(15);
        history=new TextArea(15,35);
        enter=new Button("Send");
        l=new Label("");
        p1=new Panel();
        p2=new Panel();
        p3=new Panel();
        p1.setLayout(new GridLayout(1,2,4,4));
        p2.setLayout(new GridLayout(1,1));
        p3.setLayout(new GridLayout(1,2));
        chat.add(p2);
        chat.add(p1);
        p1.add(msg);
        p3.add(enter);
        p3.add(l);
        p1.add(p3);

        p2.add(history);
        chat.setVisible(true);
        enter.addActionListener(this);
        try
        {
            ser=new ServerSocket(6789);
            socket=ser.accept();
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            Thread reads = new Thread(new cread());
            reads.start();
        }
        catch(Exception e)
        {}

    }
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==enter)
        {
            try
            {
                history.append("Server:"+msg.getText()+"\n");
                out.println(msg.getText());
                out.flush();
                msg.setText("");
            }
            catch(Exception e){}
        }
    }


    public static void main(String args[])
    {
        new Server();
    }


    public class cread implements Runnable
    {
        public void run()
        {
            String ms="";
            try
            {
                while((ms=in.readLine())!=null)
                {
                    history.append("Client: "+ms+"\n");
                }
            }
            catch(Exception e){}
        }

    }
}