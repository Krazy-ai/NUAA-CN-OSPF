public class ReceivedMessage {
    int ID;
    int count;
    public ReceivedMessage(int ID, int count) {
        this.ID = ID;
        this.count = count;
    }

    @Override
    public String toString() {
        return "ReceivedMessage{" +
                "ID=" + ID +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReceivedMessage that = (ReceivedMessage) o;

        if (ID != that.ID) return false;
        return count == that.count;
    }

}
