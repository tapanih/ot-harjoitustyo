package pixeleditor.domain.tools;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.PixelReader;
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
import pixeleditor.domain.CanvasService;
import pixeleditor.domain.ColorService;
import pixeleditor.domain.utils.TestUtils;


@RunWith(JfxRunner.class)
public class PenToolTest {
    private static PenTool pen;
    
    @BeforeClass
    public static void setUpClass() {
        pen = new PenTool();
        ColorService.setColorPicker(new ColorPicker());
        ColorService.setCurrentColor(Color.BLACK);
    }
    
    @Before
    public void setUp() {
        CanvasService.setLayers(new Canvas[]{new Canvas(30, 30)});
    }
    
    @Test
    @TestInJfxThread
    public void mousePressDrawsASinglePixel() {
        MouseEvent e = TestUtils.createMouseEvent(MOUSE_PRESSED, 20.0, 10.0);
        
        pen.mousePressed(e);
        PixelReader pixelReader = CanvasService.getPixelReader(Color.TRANSPARENT);
        
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
        MouseEvent e1 = TestUtils.createMouseEvent(MOUSE_DRAGGED, 20.0, 10.0);
        MouseEvent e2 = TestUtils.createMouseEvent(MOUSE_DRAGGED, 23.0, 10.0);
        MouseEvent e3 = TestUtils.createMouseEvent(MOUSE_DRAGGED, 27.0, 10.0);
        MouseEvent e4 = TestUtils.createMouseEvent(MOUSE_RELEASED, 27.0, 10.0);

        pen.mouseDragged(e1);
        pen.mouseDragged(e2);
        pen.mouseDragged(e3);
        pen.mouseReleased(e4);

        PixelReader pixelReader = CanvasService.getPixelReader(Color.TRANSPARENT);

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
