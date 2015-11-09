package csci4311.chat;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
public class Client extends Frame implements ActionListener
{
    TextField msg;
    TextArea history;
    Button enter,clear;
    Socket socket;
    BufferedReader in ;
    PrintWriter out ;
    Panel p1,p2,p3;
    Label l;
    Client()
    {
        super("SChat-Client");
        setLayout(new FlowLayout());
        setSize(300,350);
        msg=new TextField(15);
        history=new TextArea(15,35);
        enter=new Button("Send");
        clear=new Button("Clear");
        l=new Label("");
        p1=new Panel();
        p2=new Panel();
        p3=new Panel();
        p1.setLayout(new GridLayout(1,2,4,4));
        p2.setLayout(new GridLayout(1,1));
        p3.setLayout(new GridLayout(1,2));
        add(p2);
        add(p1);
        p1.add(msg);
        p3.add(enter);
        p3.add(l);
        p1.add(p3);

        p2.add(history);
        setVisible(true);
        enter.addActionListener(this);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        try
        {
            socket=new Socket(InetAddress.getLocalHost(),6789);
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            Thread reads = new Thread(new sread());
            reads.start();

        }

        catch(Exception e){
        }

    }
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==enter)
        {
            try
            {
//                history.append("Client:"+msg.getText()+"\n");
                System.out.println("Client:"+msg.getText()+"\n");
                out.println(msg.getText());
                out.flush();
                msg.setText("");
            }
            catch(Exception e){}
        }
        else
        {
            history.setText("");
        }
    }
    public static void main(String args[])
    {
        new Client();
    }


    public class sread implements Runnable
    {
        public void run()
        {
            String ms="";
            try
            {
                while(((ms=in.readLine())!=null))
                {
//                    history.append("Server: "+ms+"\n");
                    System.out.println("Server: "+ms+"\n");
                }

            }
            catch(Exception e){}
        }
    }
}
