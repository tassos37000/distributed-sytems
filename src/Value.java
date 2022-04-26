import java.io.Serializable;

public class Value implements Serializable{
    MultimediaFile multimediaFile;
    String message;

    /**
     * Constructor
    */
   
    public Value(String mes, Boolean mm){
        this.message = mes;
        //if (mm){
        //    multimediaFile = new MultimediaFile(mes);
        //}
    }

    @Override
    public String toString(){
        return message;
    }
}
