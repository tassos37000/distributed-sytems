import java.util.ArrayList;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Publisher extends Node {
    ProfileName profileName;
    Socket requestSocket = null;

    public Publisher(Socket sock){
        this.requestSocket = sock;
    }

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

    public void push(Value mes){
        Socket requestSocket = null;
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            
            out.writeObject(mes);
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

    public void run(){
        Value mes = new Value("My message, pls get it :("); // TODO: ask for what to sent
        push(mes);
    }
    
}
