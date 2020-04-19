package pixeleditor.domain;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class CanvasService {
    public static final double DEFAULT_CANVAS_WIDTH = 400;
    public static final double DEFAULT_CANVAS_HEIGHT = 300;
    private static final Canvas CANVAS = new Canvas(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT);
    private static final GraphicsContext GC = CANVAS.getGraphicsContext2D();

    public static Canvas getCanvas() {
        return CANVAS;
    }

    public static void init() {
        GC.setImageSmoothing(false);
    }

    public static PixelReader getPixelReader(Color fillColor) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(fillColor);
        return CANVAS.snapshot(params, null).getPixelReader();
    }

    public static PixelWriter getPixelWriter() {
        return GC.getPixelWriter();
    }

    public static int getHeight() {
        return (int) CANVAS.getHeight();
    }

    public static int getWidth() {
        return (int) CANVAS.getWidth();
    }
    
    public static void clearAndResize(double width, double height) {
        CANVAS.setHeight(height);
        CANVAS.setWidth(width);
        GC.clearRect(0, 0, width, height);
    }
    
    public static void drawImageAndResize(Image image) {
        CANVAS.setHeight(image.getHeight());
        CANVAS.setWidth(image.getWidth());
        GC.clearRect(0, 0, CANVAS.getWidth(), CANVAS.getHeight());
        GC.drawImage(image, 0, 0);
    }

    public static void fill(Color color) {
        GC.setFill(color);
        GC.fillRect(0, 0, CANVAS.getWidth(), CANVAS.getHeight());
    }

    public static WritableImage getCanvasAsImage() {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return CANVAS.snapshot(params, null);
    }
}
