package pixeleditor.domain;

import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class FileService {

    /**
     * Shows the file chooser dialog and saves canvas to a selected PNG file.
     * @param primaryStage stage used for the file chooser dialog
     */
    public void importToPNG(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(primaryStage);

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
     * Shows the file chooser dialog and imports the selected PNG file.
     * @param primaryStage stage used for the file chooser dialog
     */
    public void exportFromPNG(Stage primaryStage) {
        WritableImage image = CanvasService.getCanvasAsImage();

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(primaryStage);

        try {
            if (file != null) {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}
