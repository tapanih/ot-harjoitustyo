package pixeleditor.domain;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
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
    private static Canvas[] layers;
    private static Canvas currentLayer;

    /**
     * Sets the list of layers and initializes them with correct settings.
     * @param layerList list of canvas instances that acts as layers
     */
    public static void setLayers(Canvas[] layerList) {
        layers = layerList;
        for (Canvas layer : layers) {
            layer.getGraphicsContext2D().setImageSmoothing(false);
        }
        currentLayer = layers[0];
    }

     /**
     * Sets the current layer.
     * @param layer layer to be set as the current layer
     */
    public static void setCurrentLayer(Canvas layer) {
        currentLayer = layer;
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
        return currentLayer.snapshot(params, null).getPixelReader();
    }

    /**
     * Returns an object that is used for writing color values of individual
     * pixels.
     *
     * @return PixelWriter for writing color values to individual pixels
     */
    public static PixelWriter getPixelWriter() {
        return currentLayer.getGraphicsContext2D().getPixelWriter();
    }

    /**
     * Returns the height of the canvas.
     *
     * @return height of the canvas
     */
    public static int getHeight() {
        return (int) currentLayer.getHeight();
    }

    /**
     * Returns the width of the canvas.
     *
     * @return width of the canvas
     */
    public static int getWidth() {
        return (int) currentLayer.getWidth();
    }

    /**
     * Clears the layers and resizes them to match parameters.
     *
     * @param width  width of the new canvas
     * @param height height of the new canvas
     */
    public static void clearAndResize(double width, double height) {
        for (Canvas layer : layers) {
            layer.setHeight(height);
            layer.setWidth(width);
            layer.getGraphicsContext2D().clearRect(0, 0, width, height);
        }
        currentLayer = layers[0];
    }

    /**
     * Draws an image on the canvas and resizes the canvas to match the
     * dimensions of the image.
     *
     * @param image image to draw on the canvas
     */
    public static void drawImageAndResize(Image image) {
        for (Canvas layer : layers) {
            layer.setHeight(image.getHeight());
            layer.setWidth(image.getWidth());
            layer.getGraphicsContext2D().clearRect(0, 0, layer.getWidth(), layer.getHeight());
        }
        currentLayer = layers[0];
        currentLayer.getGraphicsContext2D().drawImage(image, 0, 0);
    }

    /**
     * Fills the current layer with the provided color. Used in tests.
     *
     * @param color color used to fill the canvas
     */
    public static void fill(Color color) {
        currentLayer.getGraphicsContext2D().setFill(color);
        currentLayer.getGraphicsContext2D().fillRect(0, 0, currentLayer.getWidth(), currentLayer.getHeight());
    }

    /**
     * Returns the drawing area as a WritableImage that can be exported to a file.
     *
     * @param fillColor color used to fill transparency (useful when exporting to a format that does not support an alpha channel)
     * @return WritableImage that can be exported
     */
    public static WritableImage getCanvasAsImage(Color fillColor) {
        Canvas combinedCanvas = new Canvas(currentLayer.getWidth(), currentLayer.getHeight());
        for (Canvas layer : layers) {
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            combinedCanvas.getGraphicsContext2D().drawImage(layer.snapshot(params, null), 0, 0);
        }
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(fillColor);
        return combinedCanvas.snapshot(params, null);
    }
}
