import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
 
public class Client extends Node {
    Address address = null;
    String username = null;
    Socket requestSocket = null;

    Client() {
        address = getRandomBroker();
    }

    public Socket getSocket(){ return requestSocket; }

    public Address getRandomBroker(){
        ArrayList<Address> brokerAddresses = readAddresses();
        int rnd = new Random().nextInt(brokerAddresses.size());
        return new Address(brokerAddresses.get(rnd).getIp(), brokerAddresses.get(rnd).getPort());
        //return new Address("localhost", 6000);
    }
            
 
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Choose a username: ");
        username = sc.nextLine();
 
        try {
            requestSocket = new Socket(address.getIp(), address.getPort());
            System.out.println("1."+requestSocket.isClosed()); //-0
            Thread consumer = new Consumer(this);
            Thread publisher = new Publisher(this);

            while(true){
                System.out.println("2."+requestSocket.isClosed()); //-0
                consumer.start();
                System.out.println("4."+requestSocket.isClosed()); //-0
                publisher.start();
                System.out.println("6."+requestSocket.isClosed()); //-0
            }
 
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                requestSocket.close();
                sc.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}