import java.io.Serializable;
public class MultimediaFile implements Serializable {
    String multimediaFileName;
    String profileName;
    String dateCreated;
    String length;
    String framerate;
    String frameWidth;
    String frameHeight;
    byte[] multimediaFileChunk;
    
    /**
     * Constructor
    */
    public MultimediaFile(){
        multimediaFileName = "";
        profileName = "";
        dateCreated = "";
        length = "";
        framerate = "";
        frameWidth = "";
        frameHeight = "";
        multimediaFileChunk = new byte[0];
    }
}
