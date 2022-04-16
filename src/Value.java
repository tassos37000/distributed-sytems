import java.io.Serializable;

public class Value implements Serializable{
    MultimediaFile multimediaFile;
    String message;

    /**
     * Constructor
    */
    public Value(){
        multimediaFile = new MultimediaFile();
    }
    public Value(String mes){
        this.message = mes;
    }

    @Override
    public String toString(){
        return message;
    }
}
