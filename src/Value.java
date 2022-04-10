import java.io.Serializable;

public class Value implements Serializable{
    MultimediaFile multimediaFile;

    /**
     * Constructor
    */
    public Value(){
        multimediaFile = new MultimediaFile();
    }
}
