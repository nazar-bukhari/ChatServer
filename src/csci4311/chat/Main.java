package csci4311.chat;

public class Main extends Thread {


    public void run() {

        new UserCommand().run();
    }

    public static void main(String[] args) {


        Main m = new Main();
        m.start();

        try {
            PacketReceiver pr = new PacketReceiver(3001);
            pr.start();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
