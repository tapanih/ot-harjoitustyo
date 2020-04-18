package pixeleditor.ui;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import pixeleditor.domain.Tool;
import pixeleditor.domain.ToolService;

public class PixelEditorUi extends Application {

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final double DEFAULT_CANVAS_WIDTH = 400;
    public static final double DEFAULT_CANVAS_HEIGHT = 300;
    private ToolService toolService;
    private Canvas canvas;
    private GraphicsContext gc;

    @Override
    public void init() throws Exception {
        this.toolService = new ToolService();
        this.canvas = new Canvas(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT);
        this.gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);
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
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

        final ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setPadding(new Insets(4, 0, 4, 4));

        final ToggleButton penButton = new ToolButton("pen.png");
        final ToggleButton eraserButton = new ToolButton("eraser.png");
        final ToggleButton colorPickerButton = new ToolButton("colorpicker.png");
        final ToggleButton bucketToolButton = new ToolButton("bucketfill.png");
        final ColorPicker colorChooser = new ColorChooser();

        // An ugly way to connect buttons to tools
        penButton.setUserData(ToolService.PEN_TOOL);
        eraserButton.setUserData(ToolService.ERASER_TOOL);
        colorPickerButton.setUserData(ToolService.COLOR_PICKER_TOOL);

        toolBar.getItems().addAll(penButton, eraserButton, colorPickerButton, bucketToolButton, colorChooser);

        final ToggleGroup toolGroup = new ToggleGroup();
        toolGroup.selectedToggleProperty().addListener((ov, toggle, newToggle) -> {
            if (newToggle != null) {
                toolService.setCurrentTool((Tool) toolGroup.getSelectedToggle().getUserData());
            }
        });
        penButton.setToggleGroup(toolGroup);
        eraserButton.setToggleGroup(toolGroup);
        colorPickerButton.setToggleGroup(toolGroup);
        bucketToolButton.setToggleGroup(toolGroup);

        toolBar.prefHeightProperty().bind(primaryStage.heightProperty());

        final VBox vBox = new VBox(toolBar);
        final HBox hBox = new HBox(menuBar);

        final StackPane canvasHolder = new StackPane();
        canvasHolder.setStyle("-fx-background-color: white");
        canvasHolder.getChildren().add(canvas);
        canvasHolder.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        borderPane.setTop(hBox);
        borderPane.setLeft(vBox);
        borderPane.setCenter(canvasHolder);

        newMenuItem.setOnAction(e -> {
            Dialog<Dimension2D> dialog = new NewImageDialog(primaryStage);
            Optional<Dimension2D> result = dialog.showAndWait();
            if (result.isPresent()) {
                canvas.setHeight(result.get().getHeight());
                canvas.setWidth(result.get().getWidth());
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }
        });

        importMenuItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showOpenDialog(primaryStage);

            try {
                if (file != null) {
                    Image image = new Image(file.toURI().toString());
                    canvas.setHeight(image.getHeight());
                    canvas.setWidth(image.getWidth());
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    gc.drawImage(image, 0, 0);
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });

        exportMenuItem.setOnAction(e -> {
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            WritableImage image = canvas.snapshot(params, null);

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
        });

        exitMenuItem.setOnAction(e -> {
            Platform.exit();
        });

        canvas.setOnMouseClicked(e -> {
            toolService.mousePressed(gc, e);
        });

        canvas.setOnMouseDragged(e -> {
            toolService.mouseDragged(gc, e);
        });

        canvas.setOnMouseReleased(e -> {
            toolService.mouseReleased(gc, e);
        });

        final Scene scene = new Scene(borderPane, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
