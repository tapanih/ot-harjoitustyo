package pixeleditor.domain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

/**
 * An utility class that handles saving and opening image files.
 */
public class FileService {
    private final static Set<String> SUPPORTED_EXTENSIONS = Set.of("png", "gif", "bmp", "tiff", "tif");

    private String getExtension(String fileName) {
        if (!fileName.contains(".")) {
            return null;
        } else {
            return fileName.substring(fileName.lastIndexOf('.') + 1);
        }
    }

     /**
     * Imports the selected image file.
     * @param file image file
     * @param ext extension selected
     * @throws java.io.IOException throws IOException if file could not be opened
     */
    public void importFrom(File file, String ext) throws IOException {
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            CanvasService.drawImageAndResize(image);
        }
    }
    
    /**
     * Exports canvas to a selected image file.
     * @param file image file
     * @param selectedExtension extension selected in the save dialog
     * @return true if exporting was successful, false otherwise
     * @throws java.io.IOException throws IOException if file could not be saved
     */
    public boolean exportTo(File file, String selectedExtension) throws IOException {
        WritableImage image;
        BufferedImage bimg = null;
        if (file != null) {
            String fileName = file.getCanonicalPath().toLowerCase();
            String extension = getExtension(fileName);

            // if filename doesn't contain a valid extension,
            // extension selected from save dialog is used to choose the image type
            if (extension == null || !SUPPORTED_EXTENSIONS.contains(extension)) {
                extension = selectedExtension;
            }

            // BMP and GIF don't support an alpha channel so store the returned pixel data
            // to an object that does not have one
            if (extension.equals("bmp") || extension.equals("gif")) {
                image = CanvasService.getCanvasAsImage(Color.MAGENTA);
                bimg = new BufferedImage((int) image.getWidth(), (int) image.getHeight(), BufferedImage.TYPE_INT_RGB);
            } else {
                image = CanvasService.getCanvasAsImage(Color.TRANSPARENT);
            }
            return ImageIO.write(SwingFXUtils.fromFXImage(image, bimg), extension, file);
        }
        return false;
    }
}
