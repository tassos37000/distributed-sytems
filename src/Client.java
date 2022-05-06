import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
 
public class Client extends Node {
    ArrayList<Address> brokerAddresses;
    Address address = null;
    String username = null;
    Socket requestSocket = null;
    Thread consumer = null;
    Thread publisher = null;
    public boolean stopthreads = false;
    public boolean Alivesocket= false;
    String desiredTopic = null;
    Client() {
        address = getRandomBroker();
    }
    
    public String getUsername(){ return username; }

    public Socket getSocket(){ return requestSocket; }

    public Address getRandomBroker(){
        brokerAddresses = readAddresses();
        int rnd = new Random().nextInt(brokerAddresses.size());
        return new Address(brokerAddresses.get(rnd).getIp(), brokerAddresses.get(rnd).getPort());
    }

    public Socket getConnection() { return requestSocket; }
            
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Choose a username: ");
        username = sc.nextLine();
        boolean changeBroker = getTopicInfo();
        try {
            if (changeBroker){
                requestSocket = new Socket(address.getIp(), address.getPort());
                publisher = new Publisher(this);
                consumer = new Consumer(this);
                ((Publisher)publisher).push(new Value(this.getUsername(), desiredTopic, false, false)); 
            }
            Alivesocket = true;
            stopthreads = false;

            while(!stopthreads){
                consumer.start();

                ((Publisher)publisher).push(new Value(this.getUsername(), "", false, false)); 
                publisher.start();

            }
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (IllegalThreadStateException ithe){
        }
        //sc.close();
    }

    /**
     * Learn from the randomly chosen broker whether to change 
     * broker in order to connect to topic
     * @return true if broker changed,
     *         false if not
     */
    private boolean getTopicInfo(){
        Scanner sc = new Scanner(System.in);
        System.out.print("What topic would you like to access? ");
       desiredTopic = sc.nextLine();
        
        try {
            requestSocket = new Socket(address.getIp(), address.getPort());
            publisher = new Publisher(this);
            consumer = new Consumer(this);
                
            if (requestSocket!=null){
                Value message = new Value(this.username, desiredTopic, false, false);
                ((Publisher)publisher).push(message);
                String data[] = ((Consumer)consumer).register().split(" ");
                if (data[0].equals("yes")){
                    String ip = brokerAddresses.get(Integer.parseInt(data[1])).getIp();
                    address.setIp(ip);
                    int port = brokerAddresses.get(Integer.parseInt(data[1])).getPort();
                    address.setPort(port);
                    
                    Value exitmes = new Value(this.username);
                    ((Publisher)publisher).push(exitmes);
                    closeClient();
                    return true;
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }
    
    public void closeClient() throws IOException {
        try {
            System.out.println("[Client]: Attempting to close client..");
            Alivesocket= false;
            stopthreads=true;

            ((Publisher)publisher).closee();
            ((Consumer)consumer).closee();
            requestSocket.close();

            publisher = null;
            consumer = null;
            requestSocket = null;
            
            System.out.println("[Client]: Socket closed.");
            System.out.println("[Client]: Client closed connection to broker.");
        } catch(SocketException e){
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }
}


