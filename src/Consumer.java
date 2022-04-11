import java.net.Socket;
import java.net.UnknownHostException;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Consumer extends Node {
    public void disconnect(String str){}

    public void register(String str){}

    public void showConversationData(String str, Value val){
        Socket requestSocket = null;
        ObjectInputStream in = null;
        try{
            ArrayList<Address> brokerAddresses = readAddresses();
            int rnd = new Random().nextInt(brokerAddresses.size());
            requestSocket = new Socket(brokerAddresses.get(rnd).getIp(), 
                                       brokerAddresses.get(rnd).getPort());
            in = new ObjectInputStream(requestSocket.getInputStream());
            System.out.println("<Server>" + in.readObject());
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException){
            classNotFoundException.printStackTrace();
        } finally {
            try {
                in.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

}
