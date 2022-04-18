import java.net.UnknownHostException;
import java.io.ObjectInputStream;
import java.io.IOException;

public class Consumer extends Node {
    Client client = null;

    public Consumer(Client client){
        this.client = client;
    }

    public void disconnect(String str){}

    public void register(String str){}

    public void showConversationData(){
        ObjectInputStream in = null;
        try{
            System.out.println("3."+client.getSocket().isClosed()); //-0
            while(true){
                in = new ObjectInputStream(client.getSocket().getInputStream());

                Value mes = (Value)in.readObject();
                System.out.println("Server>" + mes);
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
