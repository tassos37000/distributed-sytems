import java.net.UnknownHostException;
import java.io.ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.net.SocketException;

public class Consumer extends Node {
    Client client = null;
    ObjectInputStream in = null;

    public Consumer(Client client){
        this.client = client;
        try {
            in = new ObjectInputStream(this.client.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(String str){}

    public String register(){
        try{
            Value response = (Value)in.readObject();
            return response.getMessage(); // message is in form "<change broker: yes/no> <brokernum if needed>"
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException cnfException){
            cnfException.printStackTrace();
        }
        return "";
    }

    public synchronized void showConversationData(){
        try{
            //System.out.println("3."+client.getSocket().isClosed()); //-0
            ArrayList<Value> chunksOfMess = new ArrayList<>();
            while(true){
                if (Objects.isNull(in) || Objects.isNull(client.getConnection())){
                    break;
                }
                
                if (client.Alivesocket){
                    Value mess = (Value)in.readObject();
                    if(mess.multimediaFile!= null){
                        chunksOfMess.add(mess);
                        Collections.sort(chunksOfMess);
                        continue;
                    }
                    //System.out.println("last chunk: " + mess.message  + ", chunksOfMess.size(): " + chunksOfMess.size());
                    if(mess.hasMultimediaFile && Integer.parseInt(mess.message) == chunksOfMess.size()){
                        if(chunksOfMess.get(0).message.equals(".STRING")){
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            for (Value chunk : chunksOfMess) {
                                baos.write(chunk.multimediaFile.getChunkData());
                                //System.out.println("chunk ID: " + chunk.multimediaFile.getChunkID());
                            }
                            byte[] strByteArray = baos.toByteArray();
                            String allMessage = new String(strByteArray);
                            System.out.println("Server>" + allMessage);
                            baos.close();
                        }
                        else{
                            FileOutputStream fos = new FileOutputStream("test"+chunksOfMess.get(0).message);
                            for (Value chunk : chunksOfMess) {
                                fos.write(chunk.multimediaFile.getChunkData());
                            }
                            fos.close();
                        }
                        chunksOfMess.clear();
                        continue;
                    }
                    System.out.println("Server>" + mess);
                }
            }

        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (SocketException se){
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException){
            classNotFoundException.printStackTrace();
        } 
    }

    @Override
    public void run(){
            if (!Objects.isNull(in)){
                showConversationData();
            }
    }
    
    public void closee(){
        try {
            if (!Objects.isNull(in)){
                in.close();
                in = null;
                System.out.println("[Consumer]: Input closed.");
            }
        }catch (SocketException a){}
         catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
