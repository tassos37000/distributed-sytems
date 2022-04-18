import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BrokerActionsForClient extends Thread {
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket connection;
    Broker broker;

    public BrokerActionsForClient(Socket connection) {
        this.connection = connection;
        try {
            System.out.println("Got a connection...Opening streams....");
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("[Broker]: Connection is made at port: " + connection.getPort());
        try {

            while(true){
                Object mes = in.readObject();
                System.out.println("Message Received: "+mes);

                out.writeObject(mes);
                out.flush();
            }
 
 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException classNFException) {
            classNFException.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
