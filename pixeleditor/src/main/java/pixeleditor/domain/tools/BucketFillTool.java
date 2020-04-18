package pixeleditor.domain.tools;

import java.util.ArrayDeque;
import java.util.Deque;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixeleditor.domain.ColorService;
import pixeleditor.domain.Point;
import pixeleditor.domain.Tool;

public class BucketFillTool extends Tool {

    @Override
    public void mousePressed(GraphicsContext gc, MouseEvent e) {
    }

    @Override
    public void mouseDragged(GraphicsContext gc, MouseEvent e) {
    }

    @Override
    public void mouseReleased(GraphicsContext gc, MouseEvent e) {
        PixelWriter writer = gc.getPixelWriter();

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        PixelReader reader = gc.getCanvas().snapshot(params, null).getPixelReader();

        int x = (int) e.getX();
        int y = (int) e.getY();
        int height = (int) gc.getCanvas().getHeight();
        int width = (int) gc.getCanvas().getWidth();
        Color target = reader.getColor(x, y);
        Color replacement = ColorService.getCurrentColor();

        floodFill(writer, reader, x, y, height, width, target, replacement);
    }

    /**
     * Queue-based implementation of flood fill algorithm from
     * https://en.wikipedia.org/wiki/Flood_fill
     */
    private void floodFill(PixelWriter writer, PixelReader reader, int x, int y, int height, int width, Color target, Color replacement) {
        Deque<Point> stack = new ArrayDeque<>();
        boolean[][] checked = new boolean[width][height];
        stack.push(new Point(x, y));
        writer.setColor(x, y, replacement);

        while (!stack.isEmpty()) {
            Point point = stack.pop();
            x = point.getX();
            y = point.getY();
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
