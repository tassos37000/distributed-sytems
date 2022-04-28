import java.io.Serializable;

public class Value implements Serializable{
    MultimediaFile multimediaFile;
    String message = null;
    Boolean exit = null;
    Boolean fromBroker = false;
    String senter = null;

    /**
     * Constructor
    */
   
    public Value(String senter, String mes, Boolean mm, Boolean fromBroker){
        this.senter = senter;
        this.message = mes;
        this.fromBroker = fromBroker;
        this.exit = false;
        //if (mm){
        //    multimediaFile = new MultimediaFile(mes);
        //}
    }

    public Value(){
        this.exit = true;
    }

    public Boolean getExit() { return exit; }

    @Override
    public String toString(){
        return message;
    }

    public void setFromBroker(Boolean val) { this.fromBroker = val; }
}
