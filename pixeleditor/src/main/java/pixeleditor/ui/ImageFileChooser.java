package pixeleditor.ui;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * A wrapper class for JavaFX FileChoosers.
 */
public class ImageFileChooser {
    FileChooser openFileChooser = new FileChooser();
    FileChooser saveFileChooser = new FileChooser();

    /**
     * A default constructor.
     */
    public ImageFileChooser() {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        openFileChooser.getExtensionFilters().add(extFilter);
        saveFileChooser.getExtensionFilters().add(extFilter);
    }

    /**
     * Shows a new file open dialog. Returns the selected file or null if no selection was made.
     * @param primaryStage stage that owns this file dialog
     * @return selected file or null if none selected
     */
    public File showOpenDialog(Stage primaryStage) {
        return openFileChooser.showOpenDialog(primaryStage);
    }
    
    /**
     * Shows a new file save open dialog. Returns the selected file or null if no selection was made.
     * @param primaryStage stage that owns this file dialog
     * @return selected file or null if none selected
     */
    public File showSaveDialog(Stage primaryStage) {
        return saveFileChooser.showSaveDialog(primaryStage);
    }
}
