package pixeleditor.domain.tools;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;
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
public class ColorPickerToolTest {
    private static ColorPickerTool colorPicker;
    
    @BeforeClass
    public static void setUpClass() {
        colorPicker = new ColorPickerTool();
    }
    
    @Before
    public void setUp() {
        CanvasService.init();
        CanvasService.clearAndResize(20, 30);
        ColorService.setColorPicker(new ColorPicker());
        ColorService.setCurrentColor(Color.WHITE);
    }

    @Test
    @TestInJfxThread
    public void transparentColorIsInterpretedAsBlack() {
        MouseEvent e = TestUtils.createMouseEvent(MOUSE_PRESSED, 10.0, 25.0);
        colorPicker.mousePressed(e);
        assertEquals(Color.BLACK, ColorService.getCurrentColor());
    }
}
