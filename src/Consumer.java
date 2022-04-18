import java.net.Socket;
import java.net.UnknownHostException;
import java.io.ObjectInputStream;
import java.io.IOException;

public class Consumer extends Node {
    Socket requestSocket = null;

    public Consumer(Socket sock){
        this.requestSocket = sock;
    }

    public void disconnect(String str){}

    public void register(String str){}

    public void showConversationData(){
        Socket requestSocket = null;
        ObjectInputStream in = null;
        try{
            in = new ObjectInputStream(requestSocket.getInputStream());

            Value mes = (Value)in.readObject();
            System.out.println("Server>" + mes);

            //System.out.println("<Server>" + in.readObject());
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

    public void run(){
        showConversationData();
    }

}
