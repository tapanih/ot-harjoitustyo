package pixeleditor.domain.tools;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.MOUSE_DRAGGED;
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;
import static javafx.scene.input.MouseEvent.MOUSE_RELEASED;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import pixeleditor.domain.ColorService;


@RunWith(JfxRunner.class)
public class PenToolTest {
    private static PenTool pen;
    private static Canvas canvas;
    private static GraphicsContext gc;
    
    @BeforeClass
    public static void setUpClass() {
        pen = new PenTool();
        ColorService.setColorPicker(new ColorPicker());
        ColorService.setCurrentColor(Color.BLACK);
    }
    
    @Before
    public void setUp() {
        canvas = new Canvas(50, 50);
        gc = canvas.getGraphicsContext2D();
    }
    
    @Test
    @TestInJfxThread
    public void mousePressDrawsASinglePixel() {
        MouseEvent e = new MouseEvent(MOUSE_PRESSED, 20.0, 10.0, 0, 0, 
                MouseButton.PRIMARY, 1, false, false, false, false, 
                true, false, false, false, false, false, null);
        
        pen.mousePressed(gc, e);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        PixelReader pixelReader = canvas.snapshot(params, null).getPixelReader();
        
        // Selected pixel is colored
        assertEquals(Color.BLACK, pixelReader.getColor(20, 10));
        
        // Surrounding pixels are unaffected
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(19,  9));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(19, 10));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(19, 11));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(20,  9));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(20, 11));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(21,  9));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(21, 10));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(21, 11));
    }

    @Test
    @TestInJfxThread
    public void mouseDragDrawsALine() {
        MouseEvent e1 = new MouseEvent(MOUSE_DRAGGED, 20.0, 10.0, 0, 0,
                MouseButton.PRIMARY, 1, false, false, false, false,
                true, false, false, false, false, false, null);
        MouseEvent e2 = new MouseEvent(MOUSE_DRAGGED, 23.0, 10.0, 0, 0,
                MouseButton.PRIMARY, 1, false, false, false, false,
                true, false, false, false, false, false, null);
        MouseEvent e3 = new MouseEvent(MOUSE_DRAGGED, 27.0, 10.0, 0, 0,
                MouseButton.PRIMARY, 1, false, false, false, false,
                true, false, false, false, false, false, null);
        MouseEvent e4 = new MouseEvent(MOUSE_RELEASED, 27.0, 10.0, 0, 0,
                MouseButton.PRIMARY, 1, false, false, false, false,
                true, false, false, false, false, false, null);

        pen.mouseDragged(gc, e1);
        pen.mouseDragged(gc, e2);
        pen.mouseDragged(gc, e3);
        pen.mouseReleased(gc, e4);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        PixelReader pixelReader = canvas.snapshot(params, null).getPixelReader();

        assertEquals(Color.BLACK, pixelReader.getColor(20, 10));
        assertEquals(Color.BLACK, pixelReader.getColor(21, 10));
        assertEquals(Color.BLACK, pixelReader.getColor(22, 10));
        assertEquals(Color.BLACK, pixelReader.getColor(23, 10));
        assertEquals(Color.BLACK, pixelReader.getColor(24, 10));
        assertEquals(Color.BLACK, pixelReader.getColor(25, 10));
        assertEquals(Color.BLACK, pixelReader.getColor(26, 10));
        assertEquals(Color.BLACK, pixelReader.getColor(27, 10));

        assertEquals(Color.TRANSPARENT, pixelReader.getColor(19, 10));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(28, 10));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(20, 11));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(21, 11));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(22, 11));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(23, 11));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(24, 11));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(25, 11));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(26, 11));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(27, 11));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(20, 9));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(21, 9));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(22, 9));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(23, 9));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(24, 9));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(25, 9));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(26, 9));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(27, 9));
    }
}
