import java.util.*;

public class Publisher {
    ProfileName profileName;

    ArrayList<Value> generateChunks(MultimediaFile mf){
        ArrayList<Value> a = new ArrayList<Value>(); 
        return a;
    }

    public void getBrokerList(){}

    public Broker hasTopic(String str){
        Broker b = new Broker();
        return b;
    }

    public void notifyBrokersNewMessage(String str){}

    public void notifyFailure(Broker br){}

    public void push(String str, Value val){}
}
