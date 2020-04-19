package pixeleditor.domain;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * A static utility class that provides limited access to the canvas
 * through various utility methods.
 */
public class CanvasService {
    private static Canvas canvas;
    private static GraphicsContext gc;

    /**
     * Sets the canvas and initializes it with correct settings.
     * @param newCanvas new canvas
     */
    public static void setCanvas(Canvas newCanvas) {
        canvas = newCanvas;
        gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);
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
        return canvas.snapshot(params, null).getPixelReader();
    }

    /**
     * Returns an object that is used for writing color values of individual
     * pixels.
     *
     * @return PixelWriter for writing color values to individual pixels
     */
    public static PixelWriter getPixelWriter() {
        return gc.getPixelWriter();
    }

    /**
     * Returns the height of the canvas.
     *
     * @return height of the canvas
     */
    public static int getHeight() {
        return (int) canvas.getHeight();
    }

    /**
     * Returns the width of the canvas.
     *
     * @return width of the canvas
     */
    public static int getWidth() {
        return (int) canvas.getWidth();
    }

    /**
     * Clears the current canvas and resizes it to match parameters.
     *
     * @param width  width of the new canvas
     * @param height height of the new canvas
     */
    public static void clearAndResize(double width, double height) {
        canvas.setHeight(height);
        canvas.setWidth(width);
        gc.clearRect(0, 0, width, height);
    }

    /**
     * Draws an image on the canvas and resizes the canvas to match the
     * dimensions of the image.
     *
     * @param image image to draw on the canvas
     */
    public static void drawImageAndResize(Image image) {
        canvas.setHeight(image.getHeight());
        canvas.setWidth(image.getWidth());
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(image, 0, 0);
    }

    /**
     * Fills the canvas with the provided color. Used in tests.
     *
     * @param color color used to fill the canvas
     */
    public static void fill(Color color) {
        gc.setFill(color);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Returns the canvas as a WritableImage that can be exported to a file.
     *
     * @return WritableImage that can be exported
     */
    public static WritableImage getCanvasAsImage() {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return canvas.snapshot(params, null);
    }
}
