package pixeleditor.ui;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pixeleditor.domain.CanvasService;
import pixeleditor.domain.FileService;
import pixeleditor.domain.Tool;
import pixeleditor.domain.ToolService;

/**
 * A class that contains the main user interface.
 */
public class PixelEditorUi extends Application {

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final double DEFAULT_CANVAS_WIDTH = 400;
    public static final double DEFAULT_CANVAS_HEIGHT = 300;
    private final Canvas canvas = new Canvas(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT);
    private ToolService toolService;
    private FileService fileService;

    @Override
    public void init() throws Exception {
        this.toolService = new ToolService();
        this.fileService = new FileService();
        CanvasService.setCanvas(canvas);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Pixel editor");
        final BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));

        final MenuBar menuBar = new MenuBar();
        final Menu fileMenu = new Menu("File");

        final MenuItem newMenuItem = new MenuItem("New");
        final MenuItem importMenuItem = new MenuItem("Import from PNG...");
        final MenuItem exportMenuItem = new MenuItem("Export to PNG...");
        final MenuItem exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(newMenuItem, importMenuItem, exportMenuItem, exitMenuItem);
        menuBar.getMenus().add(fileMenu);

        // Bind menu bar width to window width so it doesn't cut off and look weird when window is resized
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

        final ToolBar toolBar = new ToolBar();
        toolBar.setPadding(new Insets(4, 0, 4, 4));

        final ToggleButton penButton = new ToolButton(ToolService.PEN_TOOL);
        final ToggleButton eraserButton = new ToolButton(ToolService.ERASER_TOOL);
        final ToggleButton colorPickerButton = new ToolButton(ToolService.COLOR_PICKER_TOOL);
        final ToggleButton bucketToolButton = new ToolButton(ToolService.BUCKET_FILL_TOOL);
        final ColorPicker colorChooser = new ColorChooser();

        toolBar.getItems().addAll(penButton, eraserButton, colorPickerButton, bucketToolButton, colorChooser);

        final ToggleGroup toolGroup = new ToggleGroup();

        penButton.setToggleGroup(toolGroup);
        eraserButton.setToggleGroup(toolGroup);
        colorPickerButton.setToggleGroup(toolGroup);
        bucketToolButton.setToggleGroup(toolGroup);

        // Add a listener that updates the currently selected tool when selected button changes
        toolGroup.selectedToggleProperty().addListener((ov, toggle, newToggle) -> {
            if (newToggle != null) {
                toolService.setCurrentTool((Tool) toolGroup.getSelectedToggle().getUserData());
            }
        });

        // Bind tool bar height to window height so it doesn't cut off and look weird when window is resized
        toolBar.prefHeightProperty().bind(primaryStage.heightProperty());

        final VBox vBox = new VBox(toolBar);
        final HBox hBox = new HBox(menuBar);

        // background for the canvas
        final StackPane canvasHolder = new StackPane();
        canvasHolder.setStyle("-fx-background-color: white");
        canvasHolder.getChildren().add(canvas);

        // Make sure the background matches the size of the canvas
        canvasHolder.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        borderPane.setTop(hBox);
        borderPane.setLeft(vBox);
        borderPane.setCenter(canvasHolder);

        newMenuItem.setOnAction(e -> {
            Dialog<Dimension2D> dialog = new NewImageDialog(primaryStage);
            // result contains dimensions of new image or null depending on user action
            Optional<Dimension2D> result = dialog.showAndWait();
            if (result.isPresent()) {
                CanvasService.clearAndResize(result.get().getWidth(), result.get().getHeight());
            }
        });

        importMenuItem.setOnAction(e -> {
            fileService.importToPNG(primaryStage);
        });

        exportMenuItem.setOnAction(e -> {
            fileService.exportFromPNG(primaryStage);
        });

        exitMenuItem.setOnAction(e -> {
            Platform.exit();
        });

        canvas.setOnMouseClicked(e -> {
            toolService.mousePressed(e);
        });

        canvas.setOnMouseDragged(e -> {
            toolService.mouseDragged(e);
        });

        canvas.setOnMouseReleased(e -> {
            toolService.mouseReleased(e);
        });

        final Scene scene = new Scene(borderPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add("/css/stylesheet.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
