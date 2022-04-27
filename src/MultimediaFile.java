import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.image.ImageMetadataExtractor;


public class MultimediaFile implements Serializable {
    String multimediaFileName;
    //String profileName;
    String dateCreated;
    String length;
    String framerate;
    String frameWidth;
    String frameHeight;
    byte[] multimediaFileChunk;
    Metadata metadata;
    int chunkID;
    int data_bytes;
    
    /**
     * Constructor
    */
    public MultimediaFile(String fileName){
        multimediaFileName = fileName;
        //profileName = "";
        File m_file = new File(multimediaFileName);
        Path m_path = Paths.get(multimediaFileName);
        try {
            BasicFileAttributes attr = Files.readAttributes(m_path, BasicFileAttributes.class);
            dateCreated = attr.creationTime().toString();
            length = Long.toString(attr.size()) ;
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        

        String extension = "";
        int index = fileName.lastIndexOf('.');
        if(index > 0) {
            extension = fileName.substring(index + 1);
            System.out.println("File extension is " + extension);
        }
        if (extension.equals("mp4")){
            frameWidth = "";
            frameHeight = "";
            framerate = "";
        }
        if (extension.equals("jpg") || extension.equals("jpeg")){
            BufferedImage img = null;
            try {
                img = ImageIO.read(m_file);
                frameWidth = Integer.toString(img.getWidth());
                frameHeight = Integer.toString(img.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        
                
        multimediaFileChunk = new byte[0];
    }

    public MultimediaFile(byte[] buffer, Metadata metadata, int chunkID, int data_bytes) {
        this.multimediaFileChunk = buffer;
        this.metadata = metadata;
        this.chunkID = chunkID;
        this.data_bytes = data_bytes;
    }

   
}
