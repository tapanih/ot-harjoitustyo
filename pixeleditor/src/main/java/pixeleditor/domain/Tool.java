package pixeleditor.domain;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;


public abstract class Tool {
    
    public abstract void mousePressed(GraphicsContext gc, MouseEvent e);
    public abstract void mouseDragged(GraphicsContext gc, MouseEvent e);
    public abstract void mouseReleased(GraphicsContext gc, MouseEvent e);  
}
