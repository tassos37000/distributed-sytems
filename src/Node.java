import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Node implements Serializable {
    //Broker Addresses (ip, port)
    static public final ArrayList<Address> brokerList = new ArrayList<>(Arrays.asList(
            new Address("192.168.1.4", 6000),
            new Address("192.168.1.4", 7000),
            new Address("192.168.1.4", 8000)));

    //Zookeeper Address (ip, port)
    //static public final Address ZOOKEEPER_ADDRESS = new Address("192.168.1.4", 10000);

    //backlog
    //static public final int BACKLOG = 250;

    public ArrayList<Address> getBrokerList(){
        return brokerList;
    }

    public void connect(){}
    public void disconnect(){}
    public void init(int n){}
    public void updateNodes(){}

}

