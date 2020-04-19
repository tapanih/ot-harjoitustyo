package pixeleditor.domain.utils;

import javafx.event.EventType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class TestUtils {

    /**
     * Utility method for creating a mouse event for tests.
     * @param eventType event type that should be equal to MOUSE_PRESSED, MOUSE_DRAGGED or MOUSE_RELEASED
     * @param x x-coordinate of the mouse event (relative to element)
     * @param y y-coordinate of the mouse event (relative to element)
     * @return
     */
    public static MouseEvent createMouseEvent(EventType<MouseEvent> eventType, double x, double y) {
        return new MouseEvent(eventType, x, y, 0, 0, 
                MouseButton.PRIMARY, 1, false, false, false, false, 
                true, false, false, false, false, false, null);
    }
}
