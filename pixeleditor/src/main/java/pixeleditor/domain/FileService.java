package pixeleditor.domain;

import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

/**
 * An utility class that handles saving and opening image files.
 */
public class FileService {

     /**
     * Shows the file chooser dialog and imports the selected PNG file.
     * @param file image file
     */
    public void importFrom(File file) {
        try {
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                CanvasService.drawImageAndResize(image);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    /**
     * Shows the file chooser dialog and saves canvas to a selected PNG file.
     * @param file image file
     */
    public void exportTo(File file) {
        WritableImage image = CanvasService.getCanvasAsImage();
        try {
            if (file != null) {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}
