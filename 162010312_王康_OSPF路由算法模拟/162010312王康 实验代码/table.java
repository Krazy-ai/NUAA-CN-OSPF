import javax.swing.*;
import java.awt.*;

public class table extends JPanel {
    private int[][] graph;
    private int radius = 20;
    private int[][] locations;

    public table(){}

    public table(int[][] graph) {
        this.graph = graph;
        locations = new int[graph.length][2];
        locations[0] = new int[]{50, 200};
        locations[1] = new int[]{150, 100};
        locations[2] = new int[]{300, 100};
        locations[3] = new int[]{400, 200};
        locations[4] = new int[]{190, 200};
        locations[5] = new int[]{260, 200};
        locations[6] = new int[]{150, 300};
        locations[7] = new int[]{300, 300};
        setPreferredSize(new Dimension(500, 500));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        Font font = new Font("Arial", Font.BOLD, 16);
        g2d.setFont(font);

        for (int i = 0; i < graph.length; i++) {
            if (locations[i] == null) {
                int x = getWidth() / 2 + (int) (Math.cos(2 * Math.PI * i / graph.length) * getWidth() / 4);
                int y = getHeight() / 2 + (int) (Math.sin(2 * Math.PI * i / graph.length) * getHeight() / 4);
                locations[i] = new int[]{x, y};
            }

            int x = locations[i][0];
            int y = locations[i][1];
            // 设置圆的颜色为红色
            g2d.setColor(Color.black);
            g2d.fillOval(x - radius / 2, y - radius / 2, radius, radius);
            g2d.setColor(Color.WHITE);
            g2d.drawOval(x - radius / 2, y - radius / 2, radius, radius);
            // 设置顶点编号的颜色为白色
            g2d.setColor(Color.WHITE);
            g2d.drawString(Character.toString((char) (i + 65)), x - 5, y + 5);

            for (int j = i + 1; j < graph[i].length; j++) {
                if (graph[i][j] == 1) {
                    int x1 = locations[i][0];
                    int y1 = locations[i][1];
                    int x2 = locations[j][0];
                    int y2 = locations[j][1];
                    // 绘制边的颜色为默认黑色
                    g2d.setColor(Color.BLACK);
                  //  g2d.setStroke(new BasicStroke(2));
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
        }

        g2d.dispose();
    }

    public void draw(int graph[][]) {
        table graphDrawer = new table(graph);
        JFrame frame = new JFrame("Graph Drawer");
        frame.getContentPane().add(graphDrawer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


//    int[][] graph = {{0,1,0,0,0,0,1,0}, {1,0,1,0,1,0,0,0}, {0,1,0,1,0,1,0,0}, {0,0,1,0,0,0,0,1},{0,1,0,0,0,1,1,0},
//                {0,0,1,0,1,0,0,1},{1,0,0,0,1,0,0,1},{0,0,0,1,0,1,1,0}};