import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
 
public class Client extends Node {
    Address address = null;
    String username = null;

    Client() {
        address = getRandomBroker();
    }

    public Address getRandomBroker(){
        ArrayList<Address> brokerAddresses = readAddresses();
        int rnd = new Random().nextInt(brokerAddresses.size());
        //return new Address(brokerAddresses.get(rnd).getIp(), brokerAddresses.get(rnd).getPort());
        return new Address("localhost", 6000);
    }
            
 
    public void run() {
 
            /* Create socket for contacting the server on port 4321*/
            Socket requestSocket = null;
 
            /* Create the streams to send and receive data from server */
            // ObjectOutputStream out = null;
            // ObjectInputStream in = null;

            Scanner sc = new Scanner(System.in);
            System.out.print("Choose a username: ");
            username = sc.nextLine();
 
        try {
            requestSocket = new Socket(address.getIp(), address.getPort());

            Thread consumer = new Consumer(requestSocket);
            Thread publisher = new Publisher(requestSocket);

            consumer.start();
            publisher.start();

            // out = new ObjectOutputStream(requestSocket.getOutputStream());
            // in = new ObjectInputStream(requestSocket.getInputStream());
            
            // out.writeObject(new Value("My message, pls get it :("));
            // out.flush();

            // Value mes = (Value)in.readObject();

            // /* Print the received result from server */
            // System.out.println("Server>" + mes);
 
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        // } catch (ClassNotFoundException classNFException) {
        //     classNFException.printStackTrace();
        } finally {
            try {
                // in.close(); out.close();
                requestSocket.close();
                sc.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}