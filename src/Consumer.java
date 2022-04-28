import java.net.UnknownHostException;
import java.io.ObjectInputStream;
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
            ArrayList<Value> chunksOfMess = new ArrayList<>();
            while(true){
                if (Objects.isNull(in) || Objects.isNull(client.getConnection())){
                    break;
                }
                
                //in = new ObjectInputStream(client.getSocket().getInputStream());
                if (Client.Alivesocket){
                    Value mess = (Value)in.readObject();
                    if(mess.multimediaFile!= null){
                        chunksOfMess.add(mess);
                        Collections.sort(chunksOfMess);
                        continue;
                    }
                    System.out.println("last chunk: " + mess.message  + ", chunksOfMess.size(): " + chunksOfMess.size());
                    if(mess.hasMultimediaFile && Integer.parseInt(mess.message) == chunksOfMess.size()){
                        FileOutputStream fos = new FileOutputStream("test"+chunksOfMess.get(0).message);
                        for (Value chunk : chunksOfMess) {
                            System.out.println("chunkID: " + chunk.multimediaFile.getChunkID());
                            fos.write(chunk.multimediaFile.getChunkData());
                        }
                        fos.close();
                        chunksOfMess.clear();
                        continue;
                    }
                    System.out.println("Server>" + mess);
                }
            }

        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException){
            classNotFoundException.printStackTrace();
        } //finally {
        //     try {
        //         in.close();
        //     } catch (IOException ioException) {
        //         ioException.printStackTrace();
        //     }
        // }
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
