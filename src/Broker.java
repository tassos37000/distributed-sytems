import java.util.*;
import java.io.*;
import java.net.*;

public class Broker extends Node {
    List<Consumer> registeredUsers;
    List<Publisher> registeredPublishers;

    private Address address;
    ServerSocket brokerServerSocket;

    public Broker(Address address){
        this.address = address;
        System.out.println("[Broker]: Broker Initialized ("+address.toString()+")");
    }

    public void init(){
        calculateKeys();
        // Thread zookeeperThread = new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //         updateID();
        //         updateID = false;
        //     }
        // });
        // zookeeperThread.start();
        openServer();
    }

    public void openServer(){
        try{
            brokerServerSocket = new ServerSocket(address.getPort());
            System.out.println("[Broker]: Ready to accept requests.");
            Socket appNodeSocket;
            while (true){
                appNodeSocket = brokerServerSocket.accept();
                Thread appNodeThread = new BrokerActionsForClient(appNodeSocket);
                appNodeThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                brokerServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Consumer acceptConnection(Consumer con){
        return con;
    }

    public Publisher acceptConnection(Publisher pub){
        return pub;
    }

    public void calculateKeys(){
        ArrayList<Address> brokerList = getBrokerList();
        ArrayList<Pair<Address,Integer>> brokerHash = new ArrayList<new Pair<>>();
        for (Address ad : brokerList){
            brokerHash.add(Pair(ad,(ad.getIp()+ad.getPort()).hashCode()));
        }
        //TODO: sort by the hash
    }

    public void filterConsumers(String f_con){
        int conHash = f_con.hashCode();

    }

    public void notifyBrokersOnChanges(){}

    public void notifyPublisher(String n_pub){}

    public void pull(String str){}
}

