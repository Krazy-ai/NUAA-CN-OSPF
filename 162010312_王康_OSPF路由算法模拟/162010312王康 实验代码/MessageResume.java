import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class MessageResume implements Runnable {//接收报文

    public Socket s;
    public Router r;
    int temp;//接收到的字节暂存于此

    public MessageResume(Socket s, Router r) {
        this.s = s;
        this.r = r;
    }
 /* 实现Runnable类，重写run方法
    Thread线程就是Runnable接口的实现类（子类）
    Runnable缺乏线程启动的方法，需要封装Thread才能运行*/
    @Override
    public void run() {
        try {
            //System.out.println(Thread.currentThread().getName());
            //存入一个输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //System.out.println(s.isConnected());
            ArrayList<Integer> message = new ArrayList<>();//用Integer数组表示报文
            while ((temp = br.read()) != -1) {
                message.add(temp);
            }
            //System.out.println(message);
            Router.AnalysisMessage(message, r);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}