import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class SendHandShake implements Runnable {
    //private char ID;
    ArrayList<Handshake> handArr;//要发送的报文

    SendHandShake(ArrayList<Handshake> handArr) {
        this.handArr = handArr;
    }

    @Override
    public void run() {
        try {
            for (Handshake h : handArr) {//遍历数组
                Socket s = new Socket("192.168.56.1", h.tar_port);
                //System.out.println(s.isConnected());
                //写入一个输出流
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                bw.write(h.type);
                bw.write(h.srcID);
                bw.write(h.distance);
                bw.flush();//数据会被立即写入到 Socket 输出流中，并通过网络发送到目标主机
                s.shutdownOutput();//关闭输出流
                s.close();
                sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

