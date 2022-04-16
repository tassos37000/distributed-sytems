import java.io.*;
import java.net.*;
 
public class Client extends Thread {
    Address address = null;

    Client(String ip, int port) {
        address = new Address(ip,port);
    }
 
    public void run() {
 
            /* Create socket for contacting the server on port 4321*/
            Socket requestSocket = null;
 
            /* Create the streams to send and receive data from server */
            ObjectOutputStream out = null;
            ObjectInputStream in = null;
 
        try {
                requestSocket = new Socket(address.getIp(), address.getPort());
                out = new ObjectOutputStream(requestSocket.getOutputStream());
                in = new ObjectInputStream(requestSocket.getInputStream());
                
                /* Write the two integers */
                out.writeObject( new Value("My message, pls get it :("));
                //out.flush();

                Value mes = (Value)in.readObject();
 
            
            
 
            /* Print the received result from server */
            System.out.println("Server>" + mes);
 
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException classNFException) {
            classNFException.printStackTrace();
        } finally {
            try {
                in.close(); out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}