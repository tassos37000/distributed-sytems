import java.util.ArrayList;
import java.net.UnknownHostException;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Publisher extends Node {
    ProfileName profileName;
    Client client = null;
    ObjectOutputStream out = null;

    public Publisher(Client client){
        this.client = client;
        try {
            out = new ObjectOutputStream(client.getSocket().getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    public synchronized void push(Value mes){
        // ObjectOutputStream out = null;
        try{
            System.out.println("5."+client.getSocket().isClosed()); //-0
            for (int i=0; i<5; i++){
                // out = new ObjectOutputStream(client.getSocket().getOutputStream());
                
                out.writeObject(mes);
                out.flush();
            }
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } //finally {
        //     try {
        //         out.close();
        //     } catch (IOException ioException) {
        //         ioException.printStackTrace();
        //     }
        // }
    }

    @Override
    public void run(){

        Value mes = new Value(client.getUsername() + " My message, pls get it :("); // TODO: ask for what to sent
        push(mes);
    }
    
}
