package pixeleditor.domain.tools;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;


@RunWith(JfxRunner.class)
public class PenToolTest {
    private static PenTool pen;
    private static Canvas canvas;
    private static GraphicsContext gc;
    
    @BeforeClass
    public static void setUpClass() {
        pen = new PenTool();
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
}
