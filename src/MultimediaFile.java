import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;



public class MultimediaFile implements Serializable {
    String multimediaFileName;
    String dateCreated;
    String length;
    String framerate;
    String frameWidth;
    String frameHeight;
    byte[] multimediaFileChunk;
    int chunkID;
    int data_bytes;
    
    /**
     * Constructor
    */

    public MultimediaFile(String fileName, byte[] buffer, int chunkID, int data_bytes) {
        this.multimediaFileName = fileName;
        File m_file = new File(multimediaFileName);
        Path m_path = Paths.get(multimediaFileName);
        try {
            BasicFileAttributes attr = Files.readAttributes(m_path, BasicFileAttributes.class);
            this.dateCreated = attr.creationTime().toString();
            this.length = Long.toString(attr.size()) ;
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        String extension = "";
        int index = fileName.lastIndexOf('.');
        if(index > 0) {
            extension = fileName.substring(index + 1);
        }

        if (extension.equals("mp4")){
            this.frameWidth = "";
            this.frameHeight = "";
            this.framerate = "";
        }
        if (extension.equals("jpg") || extension.equals("jpeg")){
            BufferedImage img = null;
            try {
                img = ImageIO.read(m_file);
                this.frameWidth = Integer.toString(img.getWidth());
                this.frameHeight = Integer.toString(img.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.multimediaFileChunk = buffer;
        this.chunkID = chunkID;
        this.data_bytes = data_bytes;
    }

    public int getChunkID(){ return this.chunkID; }

    public byte[] getChunkData(){ return this.multimediaFileChunk; }
   
}
