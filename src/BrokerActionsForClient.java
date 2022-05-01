import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import javafx.util.Pair;
import java.util.ArrayList;

public class BrokerActionsForClient extends Thread {
    ObjectInputStream in = null;
    ObjectOutputStream out = null;
    Socket connection = null;
    Broker broker = null;
    

    public BrokerActionsForClient(Broker broker, Socket connection) {
        this.broker = broker;
        this.connection = connection;
        try {
            System.out.println("[Broker]: Got a connection...Opening streams....");
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles first connection with the Client
     * @return true if to close connection with client after
     *         false if to keep connection with client after
     */
    private boolean firstConnect(){
        boolean topicalreadyin=false;
        try {
            Value receivedMes = (Value)in.readObject();
            String desiredTopic = receivedMes.getMessage();
            int manager = managerBroker(desiredTopic);

            Value message = null;
            if (manager != broker.brokerNum){
                message = new Value("Broker"+broker.brokerNum, "yes "+manager, false, true);
                out.writeObject(receivedMes);
                out.flush();
            } else{
                for (int i=0;i< broker.registerdTopicClients.size();i++){
                    if (desiredTopic == broker.registerdTopicClients.get(i).getKey()){
                        topicalreadyin=true;
                        break;
                    }
                } 
                if (!topicalreadyin){
                    broker.registerdTopicClients.add((new Pair<String ,ArrayList<String> >  (desiredTopic,(new ArrayList<>()))));
                }
                for (int i=0;i< broker.registerdTopicClients.size();i++){
                    if (desiredTopic == broker.registerdTopicClients.get(i).getKey()){
                        broker.registerdTopicClients.get(i).getValue().add (receivedMes.getSenter()) ;
                        break;
                    } 
                }
            }
            message = new Value("Broker"+broker.brokerNum, "no", false, true);
            out.writeObject(message);
            out.flush();
            return false;
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return true;
    }

    /**
     * Finds the appropriate broker to handle the topic
     * @param topic the topic name requested by the client
     * @return broker number responsible for topic
     */
    private int managerBroker(String topic){
        int topicHash = topic.hashCode();
        int brokerNum = -1;
        for (int i=0; i<broker.brokerHash.size(); i++){
            if (broker.brokerHash.get(i).getValue() > topicHash){ // -0 Might need < instead
                brokerNum = i;
            }
        }
        return brokerNum;
    }

    @Override
    public void run() {
        System.out.println("[Broker]: Connection is made at port: " + connection.getPort());
        try {
            firstConnect();
            
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
