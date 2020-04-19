package pixeleditor.domain;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pixeleditor.domain.tools.BucketFillTool;
import pixeleditor.domain.tools.ColorPickerTool;
import pixeleditor.domain.tools.EraserTool;
import pixeleditor.domain.tools.PenTool;

/**
 * An utility class used for keeping track of the currently selected tool 
 * and forwarding input from the user interface to it.    
 */     
public class ToolService {  

    public static final PenTool PEN_TOOL = new PenTool();
    public static final EraserTool ERASER_TOOL = new EraserTool();
    public static final ColorPickerTool COLOR_PICKER_TOOL = new ColorPickerTool();
    public static final BucketFillTool BUCKET_FILL_TOOL = new BucketFillTool();

    private Tool currentTool;
    
    public void setCurrentTool(Tool tool) {
        currentTool = tool;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    /**
     * Forwards a mouse press event to the currently selected tool.
     * @param gc GraphicsContext for updating the canvas
     * @param e  MouseEvent that was triggered on the canvas
     */
    public void mousePressed(GraphicsContext gc, MouseEvent e) {
        if (currentTool != null) {
            currentTool.mousePressed(gc, e);
        }
    }

    /**
     * Forwards a mouse drag event to the currently selected tool.
     * @param gc GraphicsContext for updating the canvas
     * @param e  MouseEvent that was triggered on the canvas
     */
    public void mouseDragged(GraphicsContext gc, MouseEvent e) {
        if (currentTool != null) {
            currentTool.mouseDragged(gc, e);
        }
    }

    /**
     * Forwards a mouse release event to the currently selected tool.
     * @param gc GraphicsContext for updating the canvas
     * @param e  MouseEvent that was triggered on the canvas
     */
    public void mouseReleased(GraphicsContext gc, MouseEvent e) {
        if (currentTool != null) {
            currentTool.mouseReleased(gc, e);
        }
    }
}