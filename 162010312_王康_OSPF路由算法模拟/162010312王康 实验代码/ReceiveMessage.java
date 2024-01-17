import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ReceiveMessage implements Runnable {
    public ServerSocket ss;
    public Router r;
    int temp;//接收到的字节暂存于此


    public ReceiveMessage(ServerSocket ss, Router r) {
        this.ss = ss;
        this.r = r;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket s = ss.accept();//监听报文端
                //System.out.println(s.isConnected());
                new Thread(new MessageResume(s,r)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
