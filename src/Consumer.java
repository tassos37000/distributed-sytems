import java.net.UnknownHostException;
import java.io.ObjectInputStream;
import java.io.IOException;

public class Consumer extends Node {
    Client client = null;
    ObjectInputStream in = null;

    public Consumer(Client client){
        this.client = client;
        try {
            in = new ObjectInputStream(client.getSocket().getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void disconnect(String str){}

    public void register(String str){}

    public synchronized void showConversationData(){
        //ObjectInputStream in = null;
        try{
            System.out.println("3."+client.getSocket().isClosed()); //-0
            while(true){
                //in = new ObjectInputStream(client.getSocket().getInputStream());

                Value mess = (Value)in.readObject();
                System.out.println("Server>" + mess);
            }

        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException){
            classNotFoundException.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void run(){
        showConversationData();
    }

}
