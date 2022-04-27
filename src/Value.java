import java.io.Serializable;

public class Value implements Serializable{
    MultimediaFile multimediaFile;
    String message;
    Boolean exit;

    /**
     * Constructor
    */
   
    public Value(String mes, Boolean mm){
        this.message = mes;
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
}
