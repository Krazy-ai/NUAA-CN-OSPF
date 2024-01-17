import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.Thread.sleep;

//路由器类
public class Router {
    private char ID;//路由器标号
    public char[] EndID = {'m','m','m','m','m','m','m','m'};//目的路由器
    public char[] NextID = {'m','m','m','m','m','m','m','m'};//下一跳路由器
    public int[][] DrawChart = new int[8][8];
    public int[][] real_chart = {{0, 2, -1, -1, -1, -1, 6, -1},
            {2, 0, 7, -1, 2, -1, -1, -1},
            {-1, 7, 0, 3, -1, 3, -1, -1},
            {-1, -1, 3, 0, -1, -1, -1, 2},
            {-1, 2, -1, -1, 0, 2, 1, -1},
            {-1, -1, 3, -1, 2, 0, -1, 2},
            {6, -1, -1, -1, 1, -1, 0, 4},
            {-1, -1, -1, 2, -1, 2, 4, 0}};

    public int[][] chart;//路由器拓扑图
    private int index;//路由器在拓扑图中下表的映射(A-F映射至0-7)
    public ArrayList<Handshake> handArr;//握手报文
    public ServerSocket ss;//服务器端Socket
    public LinkState ls = new LinkState();//链路状态报文

    public int[] Shortest = {9999, 9999, 9999, 9999, 9999, 9999, 9999, 9999};//最短路径
    public int[] Path = {-1, -1, -1, -1, -1, -1, -1, -1};//路径
    public ArrayList<ReceivedMessage> receivedMessages = new ArrayList<>();//标识已接收的报文

    //路由器构造方法,也负责完成初始化工作
    Router(char ID, ServerSocket ss) throws InterruptedException {
        this.ID = ID;
        this.ss = ss;
        chart = new int[8][8];
        handArr = new ArrayList<>();
        init_handArr();
        //init_chart();

        new Thread(new ReceiveMessage(ss, this)).start();
    }

    public void SendHandShakeMessage() {
        new Thread(new SendHandShake(handArr)).start();
    }

    public void PrintChart() {
        System.out.println("chart to" + ID);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(chart[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    private void init_handArr() {
        index = ID - 'A';
        //System.out.println(index);
        for (int i = 0; i < 8; i++) {
            if (real_chart[index][i] > 0) {
                handArr.add(new Handshake(ID, real_chart[index][i], 10086 + i));
            }
        }
        /**for(Handshake h:handArr){
         System.out.println(h);
         }*/
    }

    //解析报文
    public static void AnalysisMessage(ArrayList<Integer> message, Router r) {
        //判断报文类型
        if (message.get(0) == 0) {
            ReceivedMessage rm = new ReceivedMessage(message.get(1), message.get(2));
            //判断该报文是否已被接收
            if (!r.receivedMessages.contains(rm)) {
                //未接收则记录为接收，完善chart数据并转发，否则直接丢弃不做处理
                r.receivedMessages.add(rm);
                LinkState tempLS = new LinkState(message.get(1), message.get(2), message.get(3));//将信息重新组装为报文
                int srcID = message.get(1) - 'A';
                int index = 4;
                for (int i = 0; i < message.get(3); i++) {
                    tempLS.AdjacentNodes.add(message.get(index));
                    tempLS.distance.add(message.get(index + 1));
                    //更新chart数组
                    r.chart[srcID][message.get(index)] =
                            r.chart[message.get(index)][srcID] = message.get(index + 1);
                    index = index + 2;
                }
                for (int j = 0; j < 8; j++) {
                    if (r.chart[r.ID - 'A'][j] > 0) {
                        tempLS.tar_port.add(j);
                    }
                }
                //System.out.println(tempLS);
                new Thread(new SendLinkState(tempLS)).start();
            }

        } else if (message.get(0) == 1) {
            r.chart[message.get(1) - 'A'][r.ID - 'A'] =
                    r.chart[r.ID - 'A'][message.get(1) - 'A'] = message.get(2);
        } else {
            System.out.println("报文格式有误");
        }
    }

    public void OrganizeLinkStateMessage() {
        if (ls.count > 0) {
            ls.init();
        }
        ls.srcID = ID;
        ls.count++;
        for (int i = 0; i < 8; i++) {
            if (chart[ID - 'A'][i] != 0) {
                ls.num++;
                ls.tar_port.add(i);
                ls.AdjacentNodes.add(i);
                ls.distance.add(chart[ID - 'A'][i]);
            }
        }
        //System.out.println(ls);
        new Thread(new SendLinkState(ls)).start();
    }

    //迪杰斯特拉算法
    public void Dijkstra() {
        for (int i = 0; i < 8; i++) {
            Shortest[i] = 9999;
            Path[i] = -1;
        }
        int index = ID - 'A';
        boolean[] Selected = {false, false, false, false, false, false, false, false};
        Shortest[index] = 0;
        Path[index] = index;
        Selected[index] = true;
        for (int i = 0; i < 8; i++) {
            if (chart[index][i] > 0) {
                Shortest[i] = chart[index][i];
                Path[i] = index;
            }
        }

        int nextNode = 0;
        for (int k = 0; k < 7; k++) {
            int min = 9999;
            for (int i = 0; i < 8; i++) {
                if (Selected[i]) {
                    for (int j = 0; j < 8; j++) {
                        if (!Selected[j] && chart[i][j] > 0 && chart[i][j] < min) {
                            min = chart[i][j];
                            nextNode = j;
                        }
                    }
                }
            }
            //System.out.println(nextNode);
            Selected[nextNode] = true;
            for (int i = 0; i < 8; i++) {
                if (chart[i][nextNode] > 0 && Shortest[i] > Shortest[nextNode] + chart[nextNode][i]) {
                    Shortest[i] = Shortest[nextNode] + chart[nextNode][i];
                    Path[i] = nextNode;

                    System.out.println(Path[i]);
                }
            }
        }
    }

    public void ChartAfterDijkstra(){
        int[][] newChart = new int[8][8];  // 创建新矩阵并初始化为 0
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newChart[i][j] = 0; // 先初始化为 0
            }
        }
        for (int i = 0; i < 8; i++) {
            int j = Path[i];
            if (j != -1) {
                newChart[j][i] = Shortest[i];
                newChart[i][j] = Shortest[i];
            }
        }
        for(int i = 0; i < 8; i++ )
        {
            for(int j = 0; j < 8; j++ )
            {
                if(newChart[i][j] != 0)
                    newChart[i][j] = 1;
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                DrawChart[i][j] = newChart[i][j];
             //   System.out.print(newChart[i][j] + " ");
            }
          //  System.out.print("\n");
        }
      //  System.out.print("\n");

    }
    //输出迪杰斯特拉算法
    public void PrintDijkstra() {
        System.out.println("Shortest Path for " + ID + ":");
        int temp = ID - 'A';
        int[] stack = new int[8];
        int top = 0;
        for (int i = 0; i < 8; i++) {
            top = 0;
            if (i != temp) {
                int x = i;
                while (x!=-1&&x != Path[x]) {
                    stack[top] = x;
                    top++;
                    x = Path[x];
                }

                //用栈的方式输出路径
                System.out.print(ID);
                top--;
                NextID[i] = (char) (stack[top] + 'A');
                for (; top >= 0; top--) {
                    System.out.print("--" + (char) (stack[top] + 'A'));
                    if(top == 0)
                        EndID[i] = (char) (stack[top]+'A');
                }
              //  shortestLength = Shortest[i];
                System.out.println("\n两路由器最短距离为" + Shortest[i] + "\n");
            }
        }
    }

    public void initChart() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chart[i][j] = 0;
            }
        }
    }

    public void Break(int a) {
        int x = a - 'A';
        int y = ID - 'A';
        chart[x][y] = chart[y][x] = 9999;
    }

}
