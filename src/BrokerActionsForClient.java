import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class BrokerActionsForClient extends Thread {
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket connection;
    Broker broker;

    public BrokerActionsForClient(Socket connection) {
        this.connection = connection;
        try {
            System.out.println("[Broker]: Got a connection...Opening streams....");
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
                
                if (((Value)mes).getExit()){
                    System.out.println("[Broker]: Disconnecting Client..");
                    this.closee();
                    break;
                }
                // TODO: check if it's a message from another Broker
                System.out.println("[Broker]: Message Received: "+mes);
                
                out.writeObject(mes);
                out.flush();
            }
 
 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException classNFException) {
            classNFException.printStackTrace();
        } /*finally {
            try {
                in.close();
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }*/
    }

    public void closee(){
        try {
            if (!Objects.isNull(in)){
                in.close();
                in = null;
                System.out.println("[Broker]: Input stream from client closed.");
            }
            if (!Objects.isNull(out)){
                out.close();
                out = null;
                System.out.println("[Broker]: Output stream to client closed.");
            }
            connection.close();
            this.interrupt();
            System.out.println("[Broker]: Client disconnected.");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        
    }
}
