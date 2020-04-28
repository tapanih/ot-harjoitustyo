package pixeleditor.ui;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * A wrapper class for JavaFX FileChoosers.
 */
public class ImageFileChooser {
    private final ExtensionFilter allFilter = new ExtensionFilter("All files", "*");
    private final ExtensionFilter pngFilter = new ExtensionFilter("PNG files (*.png)", "*.png");
    private final ExtensionFilter bmpFilter = new ExtensionFilter("BMP files (*.bmp)", "*.bmp");
    private final ExtensionFilter gifFilter = new ExtensionFilter("GIF files (*.gif)", "*.gif");
    private final ExtensionFilter tiffFilter = new ExtensionFilter("TIFF files (*.tiff, *.tif)", "*.tif", "*.tiff");
    private final FileChooser saveFileChooser = new FileChooser();
    private final FileChooser openFileChooser = new FileChooser();
    private String selectedExtension = null;

    /**
     * A default constructor.
     */
    public ImageFileChooser() {
        saveFileChooser.getExtensionFilters().addAll(pngFilter, bmpFilter, gifFilter, tiffFilter);
        openFileChooser.getExtensionFilters().addAll(allFilter, pngFilter, bmpFilter, gifFilter);
    }

    /**
     * Shows a new file open dialog. Returns the selected file or null if no selection was made.
     * @param primaryStage stage that owns this file dialog
     * @return selected file or null if none selected
     */
    public File showOpenDialog(Stage primaryStage) {
        File file = openFileChooser.showOpenDialog(primaryStage);
        ExtensionFilter filter = openFileChooser.getSelectedExtensionFilter();
        if (file != null && !filter.equals(allFilter)) {
            selectedExtension = filter.getExtensions().get(0).substring(2);
        }
        return file;
    }
    
    /**
     * Shows a new file save open dialog. Returns the selected file or null if no selection was made.
     * @param primaryStage stage that owns this file dialog
     * @return selected file or null if none selected
     */
    public File showSaveDialog(Stage primaryStage) {
        File file = saveFileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            selectedExtension = saveFileChooser.getSelectedExtensionFilter().getExtensions().get(0).substring(2);
        }
        return file;
    }

    /**
     * Returns the last selected extension as a string (without a dot).
     * @return selected extension as a string without a dot
     */
    public String getSelectedExtensionAsString() {
        return selectedExtension;
    }
}
