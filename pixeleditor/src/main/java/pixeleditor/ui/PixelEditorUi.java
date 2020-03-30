package pixeleditor.ui;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
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
    
    public final int WINDOW_WIDTH = 800;
    public final int WINDOW_HEIGHT = 600;
    private ToolService toolService;

    @Override
    public void init() throws Exception {
        this.toolService = new ToolService();
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Pixel editor");
        final BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));

        final MenuBar menuBar = new MenuBar();
        final Menu fileMenu = new Menu("File");

        final MenuItem exportMenuItem = new MenuItem("Export to PNG...");
        final MenuItem exitMenuItem = new MenuItem("Exit");  
        fileMenu.getItems().add(exportMenuItem);
        fileMenu.getItems().add(exitMenuItem);
        menuBar.getMenus().add(fileMenu);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

        final ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);

        final ToggleButton penButton = new ToggleButton("P");
        final ToggleButton eraserButton = new ToggleButton("E");
        // An ugly way to connect buttons to tools
        penButton.setUserData(toolService.PEN_TOOL);
        eraserButton.setUserData(toolService.ERASER_TOOL);

        toolBar.getItems().add(penButton);
        toolBar.getItems().add(eraserButton);

        final ToggleGroup toolGroup = new ToggleGroup();
        toolGroup.selectedToggleProperty().addListener((ov, toggle, newToggle) -> {
            if (newToggle != null)
                toolService.setCurrentTool((Tool) toolGroup.getSelectedToggle().getUserData());
        });
        penButton.setToggleGroup(toolGroup);
        eraserButton.setToggleGroup(toolGroup);

        toolBar.prefHeightProperty().bind(primaryStage.heightProperty());

        final VBox vBox = new VBox(toolBar);
        final HBox hBox = new HBox(menuBar);

        final StackPane canvasHolder = new StackPane();
        canvasHolder.setStyle("-fx-background-color: white");
        final Canvas canvas = new Canvas(400, 300);

        canvasHolder.getChildren().add(canvas);

        canvasHolder.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        borderPane.setTop(hBox);
        borderPane.setLeft(vBox);
        borderPane.setCenter(canvasHolder);
        
                
        exportMenuItem.setOnAction(e -> {
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            WritableImage image = canvas.snapshot(params, null);

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(primaryStage);

            try {
                if(file != null) {
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
