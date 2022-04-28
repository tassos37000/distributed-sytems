import java.util.ArrayList;
import java.util.Objects;
import java.net.UnknownHostException;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.net.SocketException;


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
            if (Objects.isNull(mes)){
                return;
            }
            System.out.println("5."+client.getSocket().isClosed()); //-0
            if(mes.gethasMultimediaFile()){
                ArrayList<Value> chunks = chunkMultimediaFile(mes.message);
                for (Value chunk : chunks) {
                    out.writeObject(chunk);
                    out.flush();
                }
            }
            else{
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

    public ArrayList<Value> chunkMultimediaFile(String fileName) {
        ArrayList<Value> chunks = new ArrayList<>();
        int sizeOfChunk = 1024 * 512;// 0.5MB = 512KB
        byte[] buffer;
        try {
            File myFile = new File(fileName);
            FileInputStream fis = new FileInputStream(fileName);
            int chunkID = 0;
            int data_bytes;
            
            for (int i = 0; i < myFile.length(); i += sizeOfChunk) {
                System.out.println(myFile.length());
                buffer = new byte[sizeOfChunk];
                data_bytes = fis.read(buffer);
                MultimediaFile chunk = new MultimediaFile(fileName, buffer, chunkID, data_bytes);
                Value chunkVal= new Value(client.getUsername(), fileName, true, false);
                chunkVal.setMultimediaFile(chunk);
                chunks.add(chunkVal);
                chunkID++;
            }
            Value chunkVal= new Value(client.getUsername(), Integer.toString(chunkID), true, false);
            chunks.add(chunkVal);
            fis.close();
            return chunks;
        } catch ( IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
	}	
    
    @Override
    public void run(){
        //Value mes = new Value(client.getUsername(), "",false,false);
        Scanner myObj2 = new Scanner(System.in);
        while (true){
            System.out.println("Do you want to send a message? Press y for yes n for no"); 
            String answer= myObj2.nextLine().toUpperCase();
            if (answer.equals("Y")){
                while (true){
                System.out.println("Press 't' to send a text ,'p' for a photo ,'v' for a video and 'b' to go back if u changed your mind");   
                String answer2= myObj2.nextLine().toUpperCase();
                if (answer2.equals("T")){
                    System.out.println("Please type your text"); 
                    String mytext =  myObj2.nextLine().toUpperCase();
                    Value mestext = new Value(client.getUsername(),mytext,false,false);
                    push(mestext);
                    break;
                }   
                else if (answer2.equals("P")){
                    // jpg j5
                    System.out.println("Please type your name of photo with its extension"); 
                    String mytext =  myObj2.nextLine();
                    Value photo = new Value(client.getUsername(),mytext,true,false);
                    push(photo);
                    break;
                }
                else if (answer2.equals("V")){
                     //  .mp4
                    System.out.println("Please type your name of video with its extension"); 
                    String mytext =  myObj2.nextLine();
                    Value mediaa = new Value(client.getUsername(),mytext,true,false);
                    push(mediaa);
                    break;
                }
                  
                else if (answer2.equals("B")){
                    break;
                }
                else{
                    System.out.println("Invalid choice!");  
                }
               continue;
                }     
            }
            else if(answer.equals("N")) {
                myObj2.close();
                client.stopthreads=true;
                Value exitmes = new Value();
                push(exitmes);
                try {
                    client.closeClient();
                }catch (SocketException a){}
                catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            else{
                System.out.println("Invalid choice! Press y for yes n for no"); 
                
            }  
        }
        //push(mes);
    }

    public void closee(){
        try {
            if (!Objects.isNull(out)){
                out.close();
                out = null;
                System.out.println("[Publisher]: Output closed.");
            }
        }catch (SocketException a){}
         catch (IOException ioException) {
            ioException.printStackTrace();
        } 
    }
}
