package pixeleditor.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

public class PixelEditorUi extends Application {
    
    public final int WINDOW_WIDTH = 800;
    public final int WINDOW_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {       
        primaryStage.setTitle("Pixel editor");
        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(
                new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, 
                                   Insets.EMPTY)));
        
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        
        MenuItem exportMenuItem = new MenuItem("Export to PNG...");
        MenuItem exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().add(exportMenuItem);
        fileMenu.getItems().add(exitMenuItem);
        menuBar.getMenus().add(fileMenu);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        
        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        
        ToggleButton penButton = new ToggleButton("P");
        
        toolBar.getItems().add(penButton);

        ToggleButton eraserButton = new ToggleButton("E");
        
        toolBar.getItems().add(eraserButton);
        
        final ToggleGroup toolGroup = new ToggleGroup();
        penButton.setToggleGroup(toolGroup);
        eraserButton.setToggleGroup(toolGroup);
        
        toolBar.prefHeightProperty().bind(primaryStage.heightProperty());

        VBox vBox = new VBox(toolBar);
        HBox hBox = new HBox(menuBar);
        
        StackPane canvasHolder = new StackPane();
        canvasHolder.setStyle("-fx-background-color: white");
        Canvas canvas = new Canvas(400, 300);

        canvasHolder.getChildren().add(canvas);
        
        canvasHolder.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        borderPane.setTop(hBox);
        borderPane.setLeft(vBox);
        borderPane.setCenter(canvasHolder);
        
        canvas.setOnMouseClicked(e -> {
            if (penButton.isSelected()) {
                gc.fillRect(e.getX(), e.getY(), 2, 2);
            } else if (eraserButton.isSelected()) {
                gc.clearRect(e.getX(), e.getY(), 2, 2);
            }            
        });
        
        canvas.setOnMouseDragged(e -> {
            if (penButton.isSelected()) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            } else if (eraserButton.isSelected()) {
                gc.clearRect(e.getX(), e.getY(), 2, 2);
            }
        });
        
        canvas.setOnMouseReleased(e -> {
            gc.beginPath(); // reset current path to empty
        });
        
        Scene scene = new Scene(borderPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
          
    public static void main(String[] args) {
        launch(args);
    }
}
