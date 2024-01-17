import java.util.ArrayList;

public class LinkState {//链路状态信息报文
    public int type =0;//报文类型
    public int srcID;//发送者标识
    public int count=0;//报文标识，每发送一次+1
    public int num=0;//发送信息个数
    public ArrayList<Integer> AdjacentNodes = new ArrayList<>();//相邻节点
    public ArrayList<Integer> distance = new ArrayList<>();//距离
    public ArrayList<Integer> tar_port = new ArrayList<>();//这个报文需要发送的路由器对象，不属于报文的一部分

    public LinkState() {
    }

    public LinkState(int srcID, int count, int num) {
        this.srcID = srcID;
        this.count = count;
        this.num = num;
    }

    public void init() {
        tar_port.clear();
        AdjacentNodes.clear();
        distance.clear();
        num=0;
    }

    @Override
    public String toString() {
        return "LinkState{" +
                "type=" + type +
                ", srcID=" + srcID +
                ", count=" + count +
                ", num=" + num +
                ", AdjacentNodes=" + AdjacentNodes +
                ", distance=" + distance +
                ", tar_port=" + tar_port +
                '}';
    }
}
