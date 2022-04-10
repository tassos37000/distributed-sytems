import java.util.ArrayList;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Publisher {
    ProfileName profileName;

    ArrayList<Value> generateChunks(MultimediaFile mf){
        ArrayList<Value> a = new ArrayList<Value>(); 
        return a;
    }

    public void getBrokerList(){}

    // public Broker hasTopic(String str){
    //     Broker b = new Broker();
    //     return b;
    // }

    public void notifyBrokersNewMessage(String str){}

    public void notifyFailure(Broker br){}

    public void push(String str, Value val){
        Socket requestSocket = null;
        ObjectOutputStream out = null;
        try{
            requestSocket = new Socket("192.168.1.4", 6000); //TODO: sent to random
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.writeObject(val);
            out.flush();
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
