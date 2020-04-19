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

    /**
     * Returns the canvas.
     * Note: Using this method outside of UI should not be necessary.
     *
     * @return Canvas
     */
    public static Canvas getCanvas() {
        return CANVAS;
    }

    /**
     * Initializes canvas with correct settings.
     */
    public static void init() {
        GC.setImageSmoothing(false);
    }

    /**
     * Returns an object that is used for reading color values of individual
     * pixels.
     *
     * Note: Reader requires a snapshot of the canvas to be created so
     * calls to this method should be kept to a minimum.
     *
     * @param fillColor color used for filling transparent areas
     * @return PixelReader for reading color values of individual pixels
     */
    public static PixelReader getPixelReader(Color fillColor) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(fillColor);
        return CANVAS.snapshot(params, null).getPixelReader();
    }

    /**
     * Returns an object that is used for writing color values of individual
     * pixels.
     *
     * @return PixelWriter for writing color values to individual pixels
     */
    public static PixelWriter getPixelWriter() {
        return GC.getPixelWriter();
    }

    /**
     * Returns the height of the canvas.
     *
     * @return height of the canvas
     */
    public static int getHeight() {
        return (int) CANVAS.getHeight();
    }

    /**
     * Returns the width of the canvas.
     *
     * @return width of the canvas
     */
    public static int getWidth() {
        return (int) CANVAS.getWidth();
    }

    /**
     * Clears the current canvas and resizes it to match parameters.
     *
     * @param width  width of the new canvas
     * @param height height of the new canvas
     */
    public static void clearAndResize(double width, double height) {
        CANVAS.setHeight(height);
        CANVAS.setWidth(width);
        GC.clearRect(0, 0, width, height);
    }

    /**
     * Draws an image on the canvas and resizes the canvas to match the
     * dimensions of the image.
     *
     * @param image image to draw on the canvas
     */
    public static void drawImageAndResize(Image image) {
        CANVAS.setHeight(image.getHeight());
        CANVAS.setWidth(image.getWidth());
        GC.clearRect(0, 0, CANVAS.getWidth(), CANVAS.getHeight());
        GC.drawImage(image, 0, 0);
    }

    /**
     * Fills the canvas with the provided color. Used in tests.
     *
     * @param color color used to fill the canvas
     */
    public static void fill(Color color) {
        GC.setFill(color);
        GC.fillRect(0, 0, CANVAS.getWidth(), CANVAS.getHeight());
    }

    /**
     * Returns the canvas as a WritableImage that can be exported to a file.
     *
     * @return WritableImage that can be exported
     */
    public static WritableImage getCanvasAsImage() {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return CANVAS.snapshot(params, null);
    }
}
