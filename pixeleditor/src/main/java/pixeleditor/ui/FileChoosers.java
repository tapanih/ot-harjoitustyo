package pixeleditor.ui;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * A wrapper class for JavaFX FileChoosers.
 */
public class FileChoosers {
    private final ExtensionFilter allFilter = new ExtensionFilter("All files", "*");
    private final ExtensionFilter pngFilter = new ExtensionFilter("PNG files (*.png)", "*.png");
    private final ExtensionFilter bmpFilter = new ExtensionFilter("BMP files (*.bmp)", "*.bmp");
    private final ExtensionFilter gifFilter = new ExtensionFilter("GIF files (*.gif)", "*.gif");
    private final ExtensionFilter tiffFilter = new ExtensionFilter("TIFF files (*.tiff, *.tif)", "*.tif", "*.tiff");
    private final ExtensionFilter projectFileFilter = new ExtensionFilter("Project files (*.proj)", "*.proj");
    private final FileChooser saveImageFileChooser = new FileChooser();
    private final FileChooser openImageFileChooser = new FileChooser();
    private final FileChooser saveProjectFileChooser = new FileChooser();
    private final FileChooser openProjectFileChooser = new FileChooser();
    private String selectedExtension = null;

    /**
     * A default constructor.
     */
    public FileChoosers() {
        saveImageFileChooser.getExtensionFilters().addAll(pngFilter, bmpFilter, gifFilter, tiffFilter);
        openImageFileChooser.getExtensionFilters().addAll(allFilter, pngFilter, bmpFilter, gifFilter);
        saveProjectFileChooser.getExtensionFilters().add(projectFileFilter);
        openProjectFileChooser.getExtensionFilters().addAll(allFilter, projectFileFilter);
    }

    /**
     * Shows a file open dialog. Returns the selected file or null if no selection was made.
     * @param primaryStage stage that owns this file dialog
     * @return selected file or null if none selected
     */
    public File showOpenImageDialog(Stage primaryStage) {
        File file = openImageFileChooser.showOpenDialog(primaryStage);
        ExtensionFilter filter = openImageFileChooser.getSelectedExtensionFilter();
        if (file != null && !filter.equals(allFilter)) {
            selectedExtension = filter.getExtensions().get(0).substring(2);
        }
        return file;
    }

    /**
     * Shows a file save dialog. Returns the selected file or null if no selection was made.
     * @param primaryStage stage that owns this file dialog
     * @return selected file or null if none selected
     */
    public File showSaveImageDialog(Stage primaryStage) {
        File file = saveImageFileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            selectedExtension = saveImageFileChooser.getSelectedExtensionFilter().getExtensions().get(0).substring(2);
        }
        return file;
    }
    
    /**
     * Shows a file open dialog. Returns the selected file or null if no selection was made.
     * @param primaryStage stage that owns this file dialog
     * @return selected file or null if none selected
     */   
    public File showOpenProjectDialog(Stage primaryStage) {
        return openProjectFileChooser.showOpenDialog(primaryStage);
    }
    
    /**
     * Shows a file save dialog. Returns the selected file or null if no selection was made.
     * @param primaryStage stage that owns this file dialog
     * @return selected file or null if none selected
     */    
    public File showSaveProjectDialog(Stage primaryStage) {
        return saveProjectFileChooser.showSaveDialog(primaryStage);
    }

    /**
     * Returns the last selected extension as a string (without a dot).
     * @return selected extension as a string without a dot
     */
    public String getSelectedExtensionAsString() {
        return selectedExtension;
    }
}
