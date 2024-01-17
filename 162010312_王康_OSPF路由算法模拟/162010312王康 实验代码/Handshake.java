public class Handshake {//握手报文
    int type=1;//报文类型
    char srcID;//本路由器标识
    int distance;//距离
    int tar_port;//下一跳端口号

    public Handshake(char srcID, int distance, int tar_port) {
        this.srcID = srcID;
        this.distance = distance;
        this.tar_port = tar_port;
    }

    @Override
    public String toString() {
        return "Handshake{" +
                "type=" + type +
                ", srcID=" + srcID +
                ", distance=" + distance +
                ", tar_port=" + tar_port +
                '}';
    }
}
