import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Node implements Serializable {
    //Broker Addresses (ip, port)
    public final ArrayList<Address> brokerList = readAddresses();

    public void connect(){}
    public void disconnect(){}
    public void init(int n){
        this.connect();
    }
    public void updateNodes(){}

    /**
     * Helper method to read IP addresses and port number for brokers
     * from configuration file.
     * @return Arraylist with Address Objects containing IP address 
     *         and port for every broker.
     */
    protected ArrayList<Address> readAddresses(){
        ArrayList<Address> addresses = new ArrayList<Address>();
        try {
            File confFile = new File("src\\conf.txt");
            Scanner confReader = new Scanner(confFile);
            String line = "";
            while (confReader.hasNextLine() && line != "%") {
                line = confReader.nextLine();
                String ipport[] = line.split("\\s+");
                Address address = new Address(ipport[0], Integer.parseInt(ipport[1]));
                addresses.add(address);
            }
            confReader.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return addresses;
    }

}

