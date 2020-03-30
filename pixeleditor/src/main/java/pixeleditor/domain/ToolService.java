package pixeleditor.domain;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pixeleditor.domain.tools.EraserTool;
import pixeleditor.domain.tools.PenTool;

/**
 * An utility class used for keeping track of the currently selected tool 
 * and forwarding input from the user interface to it.    
 */     
public class ToolService {  

    public static final PenTool PEN_TOOL = new PenTool();
    public static final EraserTool ERASER_TOOL = new EraserTool();

    private Tool currentTool;
    
    public void setCurrentTool(Tool tool) {
        currentTool = tool;
    }

    public Tool getCurrentTool() {
        return currentTool;
    } 

    public void mousePressed(GraphicsContext gc, MouseEvent e) {
        if (currentTool != null) {
            currentTool.mousePressed(gc, e);
        }
    }

    public void mouseDragged(GraphicsContext gc, MouseEvent e) {
        if (currentTool != null) {
            currentTool.mouseDragged(gc, e);
        }
    }

    public void mouseReleased(GraphicsContext gc, MouseEvent e) {
        if (currentTool != null) {
            currentTool.mouseReleased(gc, e);
        }
    }
}