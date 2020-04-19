package pixeleditor.domain.tools;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
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
import pixeleditor.domain.utils.TestUtils;

@RunWith(JfxRunner.class)
public class EraserToolTest {
    private static EraserTool eraser;

    @BeforeClass
    public static void setUpClass() {
        eraser = new EraserTool();
    }
    
    @Before
    public void setUp() {
        CanvasService.init();
        CanvasService.clearAndResize(30, 30);
        CanvasService.fill(Color.CYAN);
    }
    
    @Test
    @TestInJfxThread
    public void mousePressTurnsASinglePixelTransparent() {
        MouseEvent e = TestUtils.createMouseEvent(MOUSE_PRESSED, 20.0, 10.0);

        eraser.mousePressed(e);
        PixelReader pixelReader = CanvasService.getPixelReader(Color.TRANSPARENT);

        // Selected pixel is transparent
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(20, 10));

        // Surrounding pixels are unaffected
        assertEquals(Color.CYAN, pixelReader.getColor(19,  9));
        assertEquals(Color.CYAN, pixelReader.getColor(19, 10));
        assertEquals(Color.CYAN, pixelReader.getColor(19, 11));
        assertEquals(Color.CYAN, pixelReader.getColor(20,  9));
        assertEquals(Color.CYAN, pixelReader.getColor(20, 11));
        assertEquals(Color.CYAN, pixelReader.getColor(21,  9));
        assertEquals(Color.CYAN, pixelReader.getColor(21, 10));
        assertEquals(Color.CYAN, pixelReader.getColor(21, 11));
    }
    
    @Test
    @TestInJfxThread
    public void mouseDragTurnsALineOfPixelsTransparent() {
        MouseEvent e1 = TestUtils.createMouseEvent(MOUSE_DRAGGED, 20.0, 10.0);
        MouseEvent e2 = TestUtils.createMouseEvent(MOUSE_DRAGGED, 17.0, 7.0);
        MouseEvent e3 = TestUtils.createMouseEvent(MOUSE_DRAGGED, 15.0, 5.0);
        MouseEvent e4 = TestUtils.createMouseEvent(MOUSE_RELEASED, 15.0, 5.0);

        eraser.mouseDragged(e1);
        eraser.mouseDragged(e2);
        eraser.mouseDragged(e3);
        eraser.mouseReleased(e4);

        /* Expected shape of the line:
         *
         *    ----------
         *    --#-------
         *    ---#------
         *    ----#-----
         *    -----#----
         *    ------#---
         *    -------#--
         *    ----------
         */

        PixelReader pixelReader = CanvasService.getPixelReader(Color.TRANSPARENT);

        // Pixels along the line are transparent
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(20, 10));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(19, 9));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(18, 8));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(17, 7));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(16, 6));
        assertEquals(Color.TRANSPARENT, pixelReader.getColor(15, 5));

        // Surrounding pixels are unaffected
        assertEquals(Color.CYAN, pixelReader.getColor(21, 11));
        assertEquals(Color.CYAN, pixelReader.getColor(20,  9));
        assertEquals(Color.CYAN, pixelReader.getColor(20,  11));
        assertEquals(Color.CYAN, pixelReader.getColor(19, 10));
        assertEquals(Color.CYAN, pixelReader.getColor(19,  8));
        assertEquals(Color.CYAN, pixelReader.getColor(18,  7));
        assertEquals(Color.CYAN, pixelReader.getColor(18,  9));
        assertEquals(Color.CYAN, pixelReader.getColor(17,  6));
        assertEquals(Color.CYAN, pixelReader.getColor(17,  8));
        assertEquals(Color.CYAN, pixelReader.getColor(16,  5));
        assertEquals(Color.CYAN, pixelReader.getColor(16,  7));
        assertEquals(Color.CYAN, pixelReader.getColor(15,  4));
        assertEquals(Color.CYAN, pixelReader.getColor(15,  6));
        assertEquals(Color.CYAN, pixelReader.getColor(14,  4));
    }
}
