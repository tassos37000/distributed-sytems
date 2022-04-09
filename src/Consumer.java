import java.net.Socket;
import java.io.IOException;
public class Consumer extends Node {
    String username = "";
    private Address address;
    Socket connection = null;
    
    public Consumer(Address address){
        this.address = address;
    }

    public void disconnect(String str){}

    public void register(String name){
        System.out.println("[Consumer]: Created new.");
        username = name;

        init(username.hashCode());

        try{
            connection = new Socket(address.getIp(), address.getPort());
            Thread appNodeConsumer = new ActionsForConsumer(connection);
            appNodeConsumer.start();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void showConversationData(String str, Value val){}

    // Getters

    public Address getAddress(){
        return address;
    }

}
