import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws Exception {
        ServerSocket ss1 = new ServerSocket(10086);
        ServerSocket ss2 = new ServerSocket(10087);
        ServerSocket ss3 = new ServerSocket(10088);
        ServerSocket ss4 = new ServerSocket(10089);
        ServerSocket ss5 = new ServerSocket(10090);
        ServerSocket ss6 = new ServerSocket(10091);
        ServerSocket ss7 = new ServerSocket(10092);
        ServerSocket ss8 = new ServerSocket(10093);

        Router r1 = new Router('A', ss1);
        Router r2 = new Router('B', ss2);
        Router r3 = new Router('C', ss3);
        Router r4 = new Router('D', ss4);
        Router r5 = new Router('E', ss5);
        Router r6 = new Router('F', ss6);
        Router r7 = new Router('G', ss7);
        Router r8 = new Router('H', ss8);

        ServerSocket []ss = {ss1, ss2, ss3, ss4, ss5, ss6, ss7, ss8};
        Router []r = {r1, r2, r3, r4, r5, r6, r7, r8};

        /*
         * 对窗口的操作，包括创建，设置标题，设置大小以及位置
         */
        JFrame frame = new JFrame();// 创建一个窗口
        frame.setTitle("路由器");// 设置窗口标题
        frame.setBounds(250, 100, 825, 600);// 设置窗口位置和大小

        frame.setLayout(new BorderLayout());

        /*
         * 创建面板，以达到良好的布局
         */
        JPanel panel = new JPanel();// JPanel：面板组件，非顶层容器
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel panel6 = new JPanel();
        JPanel panel7 = new JPanel();
        JPanel panel8 = new JPanel();
        JPanel panel9 = new JPanel();

        //设置标签
        Font font = new Font("宋体", Font.BOLD, 16);
        JLabel labCard = new JLabel("本进程标识 ");
        labCard.setFont(font);
        panel.add(labCard);// 将lable标签添加到面板上

        // 设置下拉框
        JComboBox<String> jcb = new JComboBox<String>();
        Dimension dim = new Dimension(50, 30);//设置组件的宽和高
        jcb.setPreferredSize(dim);

        jcb.addItem("A");
        jcb.addItem("B");
        jcb.addItem("C");
        jcb.addItem("D");
        jcb.addItem("E");
        jcb.addItem("F");
        jcb.addItem("G");
        jcb.addItem("H");
        jcb.setFont(font);
        panel.add(jcb);
        panel.setBounds(0, 250, 200, 50);
        frame.add(panel);

        JLabel labName = new JLabel("端口号  ");
        labName.setFont(font);
        panel6.add(labName);

        //创建一个文本框，并设置大小
        JTextField textName = new JTextField();
        textName.setPreferredSize(dim);
        String textPort = String.valueOf(ss[0].getLocalPort());
        textName.setText(textPort);
        panel6.add(textName);
        panel6.setBounds(215, 250, 150, 50);// 设置面板的位置和大小
        frame.add(panel6);// 添加面板到窗口中


        //设置按钮
        Dimension dim1 = new Dimension(80, 30);
        Dimension dim2 = new Dimension(110, 30);
        JButton jb1 = new JButton("连接");
        JButton jb2 = new JButton("断开");
        JButton jb3 = new JButton("开始工作");
        JButton jb4 = new JButton("开始计算");
        JButton jb5 = new JButton("关闭");
        JButton jb6 = new JButton("更新路由");
        JButton jb7 = new JButton("图像");
        jb1.setFont(font);
        jb2.setFont(font);
        jb3.setFont(font);
        jb4.setFont(font);
        jb5.setFont(font);
        jb6.setFont(font);
        jb7.setFont(font);
        jb1.setPreferredSize(dim1);
        jb2.setPreferredSize(dim1);
        jb3.setPreferredSize(dim2);
        jb4.setPreferredSize(dim2);
        jb5.setPreferredSize(dim1);
        jb6.setPreferredSize(dim2);
        jb7.setPreferredSize(dim1);
        panel4.add(jb1);
        panel5.add(jb2);
        panel1.add(jb3);
        panel2.add(jb4);
        panel3.add(jb5);
        panel8.add(jb6);
        panel9.add(jb7);
        panel4.setBounds(480, 350, 80, 50);
        panel5.setBounds(600, 350, 80, 50);
        panel1.setBounds(420, 250, 110, 50);
        panel2.setBounds(570, 250, 110, 50);
        panel3.setBounds(710, 250, 80, 50);
        panel8.setBounds(460, 450, 110, 50);
        panel9.setBounds(630, 450, 80, 50);
        frame.add(panel4);
        frame.add(panel5);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel8);
        frame.add(panel9);

        JTextArea textArea = new JTextArea();
        Dimension dim3 = new Dimension(350, 200);
        textArea.setPreferredSize(dim3);
        textArea.append("欢迎来到ospf模拟程序");
        panel7.add(textArea);
        panel7.setBounds(30, 300, 350, 200);
        frame.add(panel7);

        //创建一维数组，存储标题
        Object[] titles = {"标识","IP地址","端口号","连接状态","距离","下一跳"};

        //创建二维数组，存储数据
        Object[][] data = {
                {" "," "," "," "," "," "},
                {" "," "," "," "," "," "},
                {" "," "," "," "," "," "},
                {" "," "," "," "," "," "},
                {" "," "," "," "," "," "},
                {" "," "," "," "," "," "},
                {" "," "," "," "," "," "}};

        //组装视图
        JTable  jTable = new JTable(data,titles);
        jTable.setRowHeight(30);
        frame.add(new JScrollPane(jTable));

        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String input = JOptionPane.showInputDialog("请输入想要连接的路由器（未连接）:");

                if (input == null) {

                } else {
                    String input2 = JOptionPane.showInputDialog("");
                    if (input2 == null) {

                    } else {
                        String input3 = JOptionPane.showInputDialog("请输入两路由器间的距离");
                        if(input3 == null){}
                        else{
                            int routerNum = 0;
                            int routerNum2 = 0;
                            int NewDistance = Integer.parseInt(input3);
                            switch (input){
                                case "A":
                                    routerNum = 0;
                                    break;
                                case "B":
                                    routerNum = 1;
                                    break;
                                case "C":
                                    routerNum = 2;
                                    break;
                                case "D":
                                    routerNum = 3;
                                    break;
                                case "E":
                                    routerNum = 4;
                                    break;
                                case "F":
                                    routerNum = 5;
                                    break;
                                case "G":
                                    routerNum = 6;
                                    break;
                                case "H":
                                    routerNum = 7;
                                    break;
                            }
                            switch (input2){
                                case "A":
                                    routerNum2 = 0;
                                    break;
                                case "B":
                                    routerNum2 = 1;
                                    break;
                                case "C":
                                    routerNum2 = 2;
                                    break;
                                case "D":
                                    routerNum2 = 3;
                                    break;
                                case "E":
                                    routerNum2 = 4;
                                    break;
                                case "F":
                                    routerNum2 = 5;
                                    break;
                                case "G":
                                    routerNum2 = 6;
                                    break;
                                case "H":
                                    routerNum2 = 7;
                                    break;
                            }
                            textArea.append("\n在"+input+"与"+input2+"之间建立连接");
                            r[routerNum].chart[routerNum][routerNum2] = r[routerNum].chart[routerNum2][routerNum] = r[routerNum2].chart[routerNum][routerNum2] = r[routerNum2].chart[routerNum2][routerNum] = NewDistance;
                            r[routerNum].OrganizeLinkStateMessage();
                            r[routerNum2].OrganizeLinkStateMessage();
                            try {
                                sleep(1000);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            }
        });//连接
        jb2.addActionListener(new ActionListener() {
            /**断开连接后路由器将改变自身Chart数组
             * 并重新组织LinkState报文进行洪泛发送
             * 其他路由器收到报文后也更新chart数组并重新用Dijkstra算法进行计算**/
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String input = JOptionPane.showInputDialog("请输入想要断开的路由器（相邻且已连接）:");

                if (input == null) {

                } else {
                    String input2 = JOptionPane.showInputDialog("");
                    if (input2 == null) {

                    } else {
                        int   routerNum = 0;
                        int routerNum2 = 0;
                        int flag = 0;
                        switch (input){
                            case "A":
                                routerNum = 0;
                                break;
                            case "B":
                                routerNum = 1;
                                break;
                            case "C":
                                routerNum = 2;
                                break;
                            case "D":
                                routerNum = 3;
                                break;
                            case "E":
                                routerNum = 4;
                                break;
                            case "F":
                                routerNum = 5;
                                break;
                            case "G":
                                routerNum = 6;
                                break;
                            case "H":
                                routerNum = 7;
                                break;
                        }
                        switch (input2){
                            case "A":
                                routerNum2 = 0;
                                break;
                            case "B":
                                routerNum2 = 1;
                                break;
                            case "C":
                                routerNum2 = 2;
                                break;
                            case "D":
                                routerNum2 = 3;
                                break;
                            case "E":
                                routerNum2 = 4;
                                break;
                            case "F":
                                routerNum2 = 5;
                                break;
                            case "G":
                                routerNum2 = 6;
                                break;
                            case "H":
                                routerNum2 = 7;
                                break;
                        }
                        textArea.append("\n断开"+input+"与"+input2+"之间的连接");
                        r[routerNum].chart[routerNum][routerNum2] = r[routerNum].chart[routerNum2][routerNum] = r[routerNum2].chart[routerNum][routerNum2] = r[routerNum2].chart[routerNum2][routerNum] = 9999;
                    /*    for(int i = 0;i<8;i++)
                        {
                            if(routerNum != i){
                                if(r[routerNum].chart[routerNum][i] != 9999&&r[routerNum].chart[routerNum][i] != 0){
                                    flag = 1;
                                    break;
                                }
                                else
                                    r[routerNum].DrawChart[routerNum][i] = 0;
                            }
                        }*/
                     //   if(flag == 1){
                            r[routerNum].OrganizeLinkStateMessage();
                            r[routerNum2].OrganizeLinkStateMessage();
                            try {
                                sleep(1000);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                     //   }

                    }
                }
            }
        });//断开
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("开始工作" );
                send_hand_shakemessage();
                textArea.append("\n各路由器采用洪泛法交换链路状态信息");
                try {
                    sleep(2000);//让线程进入 2s 的阻塞状态，不然线程执行的很快,连接顺序会出错
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                organize_link_statemessage();
                for(int i=0;i<7;i++)
                {
                    data[i][3] = "已连接";
                }
                jTable.repaint();
            }

            public void send_hand_shakemessage() {
                r1.SendHandShakeMessage();
                r2.SendHandShakeMessage();
                r3.SendHandShakeMessage();
                r4.SendHandShakeMessage();
                r5.SendHandShakeMessage();
                r6.SendHandShakeMessage();
                r7.SendHandShakeMessage();
                r8.SendHandShakeMessage();
            }
            public void organize_link_statemessage(){
                r1.OrganizeLinkStateMessage();
                r2.OrganizeLinkStateMessage();
                r3.OrganizeLinkStateMessage();
                r4.OrganizeLinkStateMessage();
                r5.OrganizeLinkStateMessage();
                r6.OrganizeLinkStateMessage();
                r7.OrganizeLinkStateMessage();
                r8.OrganizeLinkStateMessage();
            }
        } );//开始工作
        jb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int routerNum=0;
                String key = (String)jcb.getSelectedItem();
                System.out.println(jcb.getSelectedItem());
                switch (key){
                    case "A":
                        routerNum = 1;
                        break;
                    case "B":
                        routerNum = 2;
                        break;
                    case "C":
                        routerNum = 3;
                        break;
                    case "D":
                        routerNum = 4;
                        break;
                    case "E":
                        routerNum = 5;
                        break;
                    case "F":
                        routerNum = 6;
                        break;
                    case "G":
                        routerNum = 7;
                        break;
                    case "H":
                        routerNum = 8;
                        break;
                }
                try {
                    sleep(2000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                textArea.append("\n当前为路由器(" + key + ")" + "计算最短路径");
                r[routerNum-1].Dijkstra();
                r[routerNum-1].PrintDijkstra();
                r[routerNum-1].ChartAfterDijkstra();
                int jump = 0;
                for(int i=0;i<7;i++)
                {
                    if(i == routerNum-1)
                    {
                        jump++;
                    }
                    data[i][5] = r[routerNum-1].NextID[jump];
                //    System.out.println(r1.EndID );
                    data[i][4] = r[routerNum-1].Shortest[jump];
                    jump++;
                }
                jTable.repaint();
            /*    for(int i = 0;i<8;i++){
                    for(int j = 0;j < 8; j++)
                    {
                        System.out.print(r1.chart[i][j]+ " ");
                    }
                    System.out.print("\n");
                }
                System.out.print("\n");*/
            }
        });//开始计算
        jb5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });//关闭
        jb6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int routerNum=0;
                int nextID = 0;
                String key = (String)jcb.getSelectedItem();
                System.out.println(jcb.getSelectedItem());
                switch (key){
                    case "A":
                        routerNum = 1;
                        break;
                    case "B":
                        routerNum = 2;
                        break;
                    case "C":
                        routerNum = 3;
                        break;
                    case "D":
                        routerNum = 4;
                        break;
                    case "E":
                        routerNum = 5;
                        break;
                    case "F":
                        routerNum = 6;
                        break;
                    case "G":
                        routerNum = 7;
                        break;
                    case "H":
                        routerNum = 8;
                        break;
                }
                textArea.append("\n当前路由器标识为：" + key);
                System.out.println(routerNum);
                for(int j=0;j<7;j++)
                {

                    if(j == (routerNum-1))
                        nextID++;
                    data[j][0] = (char)('A'+nextID);
                    data[j][1] = "localhost";
                    data[j][2] = ss[nextID].getLocalPort();
                    nextID++;
                }
                jTable.repaint();
                String textPort = String.valueOf(ss[routerNum-1].getLocalPort());
                textName.setText(textPort);
            }
        });//更新
        jb7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int routerNum=0;
                String key = (String)jcb.getSelectedItem();
                System.out.println(jcb.getSelectedItem());
                switch (key){
                    case "A":
                        routerNum = 1;
                        break;
                    case "B":
                        routerNum = 2;
                        break;
                    case "C":
                        routerNum = 3;
                        break;
                    case "D":
                        routerNum = 4;
                        break;
                    case "E":
                        routerNum = 5;
                        break;
                    case "F":
                        routerNum = 6;
                        break;
                    case "G":
                        routerNum = 7;
                        break;
                    case "H":
                        routerNum = 8;
                        break;
                }
                table tb = new table();
                tb.draw(r[routerNum-1].DrawChart);
            }
        });//图像

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);// 显示窗口

    }


}