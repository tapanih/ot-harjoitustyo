package pixeleditor.domain;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;


public abstract class Tool {
    
    /**
     * An abstract method for updating the canvas when mouse is clicked on it.
     * @param gc GraphicsContext for updating the canvas
     * @param e  MouseEvent that was triggered on the canvas
     */
    public abstract void mousePressed(GraphicsContext gc, MouseEvent e);

    /**
     * An abstract method for updating the canvas when mouse is dragged on it.
     * @param gc GraphicsContext for updating the canvas
     * @param e  MouseEvent that was triggered on the canvas
     */
    public abstract void mouseDragged(GraphicsContext gc, MouseEvent e);

    /**
     * An abstract method for updating the canvas when mouse is released on it.
     * @param gc GraphicsContext for updating the canvas
     * @param e  MouseEvent that was triggered on the canvas
     */
    public abstract void mouseReleased(GraphicsContext gc, MouseEvent e);  
}
