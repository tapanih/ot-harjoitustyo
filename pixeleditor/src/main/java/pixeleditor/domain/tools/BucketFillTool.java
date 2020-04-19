package pixeleditor.domain.tools;

import java.util.ArrayDeque;
import java.util.Deque;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixeleditor.domain.CanvasService;
import pixeleditor.domain.ColorService;
import pixeleditor.domain.Point;
import pixeleditor.domain.Tool;

public class BucketFillTool extends Tool {

    /**
     * A default constructor for BucketFillTool.
     */
    public BucketFillTool() {
        super("bucketfill.png");
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point point = new Point((int) e.getX(), (int) e.getY());
        Color replacement = ColorService.getCurrentColor();
        floodFill(point, replacement);
    }

    /**
     * Queue-based implementation of flood fill algorithm from
     * https://en.wikipedia.org/wiki/Flood_fill
     */
    private void floodFill(Point point, Color replacement) {
        PixelReader reader = CanvasService.getPixelReader(Color.TRANSPARENT);
        PixelWriter writer = CanvasService.getPixelWriter();
        int height = CanvasService.getHeight();
        int width = CanvasService.getWidth();
        Color target = reader.getColor(point.getX(), point.getY());
        Deque<Point> stack = new ArrayDeque<>();
        boolean[][] checked = new boolean[width][height];
        stack.push(point);
        writer.setColor(point.getX(), point.getY(), replacement);

        while (!stack.isEmpty()) {
            point = stack.pop();
            int x = point.getX();
            int y = point.getY();
            if (x + 1 < width && !checked[x + 1][y] && reader.getColor(x + 1, y).equals(target)) {
                writer.setColor(x + 1, y, replacement);
                checked[x + 1][y] = true;
                stack.push(new Point(x + 1, y));
            }
            if (x - 1 >= 0 && !checked[x - 1][y] && reader.getColor(x - 1, y).equals(target)) {
                writer.setColor(x - 1, y, replacement);
                checked[x + 1][y] = true;
                stack.push(new Point(x - 1, y));
            }
            if (y + 1 < height && !checked[x][y + 1] && reader.getColor(x, y + 1).equals(target)) {
                writer.setColor(x, y + 1, replacement);
                checked[x][y + 1] = true;
                stack.push(new Point(x, y + 1));
            }
            if (y - 1 >= 0 && !checked[x][y - 1] && reader.getColor(x, y - 1).equals(target)) {
                writer.setColor(x, y - 1, replacement);
                checked[x][y - 1] = true;
                stack.push(new Point(x, y - 1));
            }
        }
    }
}
