import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

import javafx.util.Pair;
//import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Broker extends Node {
    //List<Publisher> registeredPublishers;
    ArrayList<Pair<Address,Integer>> brokerHash;    // Broker Address, Broker Hash
    ArrayList<Pair<String,Integer>> topicHash;      // Topic Name, Topic Hash
    ArrayList<Pair<Integer,Integer>> topicBroker;   // Topic Hash, Broker Hash
    HashMap<String ,BrokerActionsForClient> activeClients; // Username, connection with client 
    HashMap<String ,ArrayList<String>> registerdTopicClients; //Topic and registered Client 
    HashMap<String, ArrayList<Value>> topicHistory;             // Topic name - Topic history
    Address address;
    ServerSocket brokerServerSocket;
    ArrayList<Pair<Value, LocalDateTime>>topicStories;
    
    int brokerNum;  // Broker Number

    /**
     * Constructor for Broker
     * @param num Broker number, in order to retrieve address
     */
    public Broker(int num){
        this.brokerNum = num-1;
        this.address = brokerList.get(this.brokerNum);
        this.activeClients          = new HashMap<>();
        this.registerdTopicClients  = new HashMap<>();
        this.topicHistory           = new HashMap<>();
        this.topicStories           = new ArrayList<>();

        this.createLogFile("Broker"+(num)+"-log.txt");
        System.out.println("[Broker]: Broker log file created.");
        this.writeToFile("[Broker]: Broker Initialized ("+address+")", true);
    }

    public void init(){
        calculateKeys();
        //connectToBrokers();
        openServer();
    }

    private void openServer(){
        try{
            brokerServerSocket = new ServerSocket(address.getPort());
            this.writeToFile("[Broker]: Ready to accept requests.", true);
            Socket clientSocket;
            while (true){
                clientSocket = brokerServerSocket.accept();
                Thread clientThread = new BrokerActionsForClient(this, clientSocket);
                clientThread.start();
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

    private void calculateKeys(){
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

    /**
     * Every Broker creates a connection (like a client would)
     * for any broker that was created "before" them
     * (see broker order in configuration)
     */
    private void connectToBrokers(){
        try{
            for (int i=0; i<this.brokerNum; i++){
                Socket requestSocket = new Socket(brokerList.get(i).getIp(), brokerList.get(i).getPort());
                Thread brokerThread = new BrokerActionsForClient(this, requestSocket);
                brokerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return topics;
    }
}

