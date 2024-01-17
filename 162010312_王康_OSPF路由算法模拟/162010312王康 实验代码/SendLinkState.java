import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SendLinkState implements Runnable {//类实现接口

    LinkState ls;

    public SendLinkState(LinkState ls) {
        this.ls = ls;
    }

    @Override
    public void run() {
        //System.out.println(ls);
        try {
            for (int i : ls.tar_port) {
                Socket s = new Socket("192.168.56.1", 10086+i);
                //System.out.println(s.isConnected());
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                bw.write(ls.type);
                bw.write(ls.srcID);
                bw.write(ls.count);
                bw.write(ls.num);
                for(int j=0;j<ls.num;j++){
                    bw.write(ls.AdjacentNodes.get(j));
                    bw.write(ls.distance.get(j));
                }
                bw.flush();
                s.shutdownOutput();
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
