import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.util.Pair;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Broker extends Node {
    //List<Consumer> registeredUsers;
    List<Publisher> registeredPublishers;
    ArrayList<Pair<Address,Integer>> brokerHash; // Broker Addres, Broker Hash
    ArrayList<Pair<String,Integer>> topicHash;   // Topic Name, Topic Hash
    ArrayList<Pair<Integer,Integer>> topicBroker;     // Topic Hash, Broker Hash
    List<Integer> registeredUsers;

    Address address;

    //private Address address;
    ServerSocket brokerServerSocket;

    public Broker(String ip, int port){
        this.address = new Address(ip,port);
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

    // public Consumer acceptConnection(Consumer con){
    //     return con;
    // }

    // public Publisher acceptConnection(Publisher pub){
    //     return pub;
    // }

    public void calculateKeys(){
        ArrayList<Address> brokerList = readAddresses();
        brokerHash = new ArrayList<>();
        for (Address ad : brokerList){
            brokerHash.add((new Pair<Address,Integer> (ad,(ad.getIp()+ad.getPort()).hashCode())));
        }
        // Sort brokers
        Collections.sort(brokerHash, new Comparator<Pair<Address, Integer>>() {
            @Override 
            public int compare(final Pair<Address, Integer> left, final Pair<Address, Integer> right) {
                return left.getValue() - right.getValue();
            }
        });
        ArrayList<String> topics = readTopics();
        topicHash = new ArrayList<>();
        for (String t : topics){
            topicHash.add((new Pair<String,Integer> (t, t.hashCode())));
        }
        for (Pair<String,Integer> t : topicHash){
            for (Pair<Address,Integer> b : brokerHash){
                if (b.getValue() < t.getValue()){
                    topicBroker.add((new Pair<Integer,Integer> (b.getValue(), t.getValue())));
                }
            }
        }
    }

    public void filterConsumers(String f_con){}

    public void notifyBrokersOnChanges(){}

    public void notifyPublisher(String n_pub){}

    public void pull(String str){}

    /**
     * Helper method to read topic names from configuration file.
     * @return Arraylist with topic names.
     */
    private ArrayList<String> readTopics(){
        ArrayList<String> topics = new ArrayList<String>();
        try {
            File confFile = new File("conf.txt");
            Scanner confReader = new Scanner(confFile);
            String line = confReader.nextLine();
            while (confReader.hasNextLine() && line != "%") {
                line = confReader.nextLine();
            }
            while (confReader.hasNextLine()){
                line = confReader.nextLine();
                topics.add(line);
            }
            confReader.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return topics;
    }
}

