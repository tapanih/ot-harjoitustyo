package pixeleditor.domain.tools;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.MOUSE_RELEASED;
import javafx.scene.paint.Color;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import pixeleditor.domain.CanvasService;
import pixeleditor.domain.ColorService;
import pixeleditor.domain.utils.TestUtils;


@RunWith(JfxRunner.class)
public class BucketFillToolTest {
    private static BucketFillTool bucketFill;
    
    @BeforeClass
    public static void setUpClass() {
        bucketFill = new BucketFillTool();
    }
    
    @Before
    public void setUp() {
        CanvasService.init();
        CanvasService.clearAndResize(4, 3);
        ColorService.setColorPicker(new ColorPicker());
    }

    @Test
    @TestInJfxThread
    public void bucketToolFillsTheWholeCanvasWhenItIsBlank() {
        MouseEvent e = TestUtils.createMouseEvent(MOUSE_RELEASED, 1, 1);
        ColorService.setCurrentColor(Color.BLUE);
        bucketFill.mouseReleased(e);
        PixelReader reader = CanvasService.getPixelReader(Color.TRANSPARENT);
        assertEquals(Color.BLUE, reader.getColor(0, 0));
        assertEquals(Color.BLUE, reader.getColor(0, 1));
        assertEquals(Color.BLUE, reader.getColor(0, 2));
        assertEquals(Color.BLUE, reader.getColor(1, 0));
        assertEquals(Color.BLUE, reader.getColor(1, 1));
        assertEquals(Color.BLUE, reader.getColor(1, 2));
        assertEquals(Color.BLUE, reader.getColor(2, 0));
        assertEquals(Color.BLUE, reader.getColor(2, 1));
        assertEquals(Color.BLUE, reader.getColor(2, 2));
        assertEquals(Color.BLUE, reader.getColor(3, 0));
        assertEquals(Color.BLUE, reader.getColor(3, 1));
        assertEquals(Color.BLUE, reader.getColor(3, 2));
    }
}
