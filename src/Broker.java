import java.util.*;

public class Broker extends Node {
    List<Consumer> registeredUsers;
    List<Publisher> registeredPublishers;

    public Consumer acceptConection(Consumer con){
        return con;
    }

    public Publisher acceptConection(Publisher pub){
        return pub;
    }

    public void calculateKeys(){}

    public void filterConsumers(String f_con){}

    public void notifyBrokersOnChanges(){}

    public void notifyPublishers(String n_pub){}

    public void pull(String str){}
}

