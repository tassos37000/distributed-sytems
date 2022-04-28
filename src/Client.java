import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
 
public class Client extends Node {
    Address address = null;
    String username = null;
    Socket requestSocket = null;
    Thread consumer = null;
    Thread publisher = null;
    public boolean stopthreads = false;

    Client() {
        address = getRandomBroker();
    }
    
    public String getUsername(){ return username; }

    public Socket getSocket(){ return requestSocket; }

    public Address getRandomBroker(){
        ArrayList<Address> brokerAddresses = readAddresses();
        int rnd = new Random().nextInt(brokerAddresses.size());
        return new Address(brokerAddresses.get(rnd).getIp(), brokerAddresses.get(rnd).getPort());
    }

    public Socket getConnection() { return requestSocket; }
            
 
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Choose a username: ");
        username = sc.nextLine();
        
 
        try {
            requestSocket = new Socket(address.getIp(), address.getPort());
            System.out.println("1."+requestSocket.isClosed()); //-0
            consumer = new Consumer(this);
            publisher = new Publisher(this);

            while(!stopthreads){
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
        } 
        sc.close();
    }


    public void closeClient (){
        try {
            ((Consumer)consumer).closee();
            ((Publisher)publisher).closee();
            //consumer.interrupt();
            //publisher.interrupt();
            System.out.println("[Client]: Client closed streams.");
            requestSocket.close();
            //requestSocket = null;
            System.out.println("[Client]: Client closed socket.");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        
    }
}