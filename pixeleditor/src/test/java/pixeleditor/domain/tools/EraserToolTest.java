package pixeleditor.domain.tools;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;
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
        CanvasService.clearAndResize(100, 100);
    }
    
    @Test
    @TestInJfxThread
    public void mousePressRemovesASinglePixel() {
        MouseEvent e = TestUtils.createMouseEvent(MOUSE_PRESSED, 20.0, 10.0);
        
        CanvasService.fill(Color.CYAN);
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
}
