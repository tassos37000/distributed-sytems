import java.io.Serializable;

public class Value implements Serializable, Comparable<Value>{
    MultimediaFile multimediaFile;
    String message = null;
    Boolean exit = null;
    Boolean fromBroker = false;
    Boolean hasMultimediaFile;
    String senter = null;

    /**
     * Constructor
    */
   
    public Value(String senter, String mes, Boolean mm, Boolean fromBroker){
        this.senter = senter;
        this.message = mes;
        this.fromBroker = fromBroker;
        this.exit = false;
        this.hasMultimediaFile = mm;

    }

    public Value(){
        this.exit = true;
    }

    public Boolean getExit() { return exit; }

    public void setMultimediaFile(MultimediaFile mf){
        this.multimediaFile = mf;
    }

    public void setFromBroker(Boolean val) { this.fromBroker = val; }

    @Override
    public String toString(){
        return message;
    }

    @Override
    public int compareTo(Value second) {
        if(this.multimediaFile.getChunkID() > second.multimediaFile.getChunkID())
            return 1;
        else
            return -1;
    }
}
