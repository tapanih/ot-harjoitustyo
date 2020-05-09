package pixeleditor.domain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

/**
 * An utility class that handles saving and opening image and project files.
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
     * Opens a project file.
     * @param file project file
     * @throws FileNotFoundException throws FileNotFoundException if file was not found
     * @throws IOException throws IOexception if image files could not be read
     */
    public void openProject(File file) throws FileNotFoundException, IOException {
        if (file != null) {
            int index = 0;
            ZipFile zipFile = new ZipFile(file);
            for (Enumeration e = zipFile.entries(); e.hasMoreElements();) {
                ZipEntry entry = (ZipEntry) e.nextElement();
                InputStream entryStream = zipFile.getInputStream(entry);
                BufferedImage bufferedImage = ImageIO.read(entryStream);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                CanvasService.drawImageToLayer(image, index);
                index++;
            }
        }
    }

    /**
     * Saves the layers to a project file which is a zip folder with PNG image for each layer.
     * @param file project file
     * @throws FileNotFoundException throws FileNotFoundException if file was not found
     * @throws IOException throws IOexception if image files could not be written
     */
    public void saveProject(File file) throws FileNotFoundException, IOException {
        if (file != null) {
            List<WritableImage> images = CanvasService.getLayersAsImages();
            try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file))) {
                int counter = 0;
                for (WritableImage image : images) {
                    ZipEntry entry = new ZipEntry("layer" + counter + ".png");
                    counter++;
                    out.putNextEntry(entry);
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", out);
                    out.closeEntry();
                }
            }
        }
    }

     /**
     * Imports the selected image file.
     * @param file image file
     * @throws java.io.IOException throws IOException if file could not be opened
     */
    public void importFrom(File file) throws IOException {
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            CanvasService.drawImageAndResize(image);
        }
    }
    
    /**
     * Exports canvas to a selected image file.
     * @param file image file
     * @param selectedExtension extension selected in the save dialog
     * @throws java.io.IOException throws IOException if file could not be saved
     */
    public void exportTo(File file, String selectedExtension) throws IOException {
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
            ImageIO.write(SwingFXUtils.fromFXImage(image, bimg), extension, file);
        }
    }
}
