import java.util.HashMap;
import java.util.ArrayList;

public class ProfileName {
    String profileName;
    HashMap<String, ArrayList<Value>> userVideoFilesMap;
    HashMap<String, Integer> subscribedConversations;

    /**
     * Constructor
    */
    public ProfileName(){
        profileName = "";
        userVideoFilesMap = new HashMap<String, ArrayList<Value>>();
        subscribedConversations = new HashMap<String, Integer>();
    }
}
