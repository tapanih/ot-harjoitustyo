package pixeleditor.domain;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
public class FileServiceTest {
    private static FileService fileService;

    @BeforeClass
    public static void setUpClass() {
        fileService = new FileService();
        CanvasService.setCanvas(new Canvas(2, 2));
    }
    
    @Test
    @TestInJfxThread
    public void pngImageOpensCorrectly() throws URISyntaxException, IOException {
        URI uri = getClass().getResource("/images/pngtest.png").toURI();
        File file = new File(uri);
        fileService.importFrom(file);

        PixelReader reader = CanvasService.getPixelReader(Color.TRANSPARENT);
        
        // Canvas size is correct
        assertEquals(2, CanvasService.getHeight());
        assertEquals(4, CanvasService.getWidth());
        
        // Colors are correct
        assertEquals(Color.BLACK, reader.getColor(0, 0));
        assertEquals(Color.WHITE, reader.getColor(1, 0));
        assertEquals(Color.YELLOW, reader.getColor(2, 0));
        assertEquals(Color.LIME, reader.getColor(3, 0));
        assertEquals(Color.RED, reader.getColor(0, 1));
        assertEquals(Color.BLUE, reader.getColor(1, 1));
        assertEquals(Color.CYAN, reader.getColor(2, 1));
        assertEquals(Color.TRANSPARENT, reader.getColor(3, 1));
    }

    @Test
    @TestInJfxThread
    public void exportingToASupportedFormatCreatesAFile() throws URISyntaxException, IOException {
        testExportingWithExtension("png");
        testExportingWithExtension("bmp");
        testExportingWithExtension("gif");
        testExportingWithExtension("tiff");
    }

    private void testExportingWithExtension(String ext) throws URISyntaxException, IOException {
        String uriStr = getClass().getResource("/images/").toURI().toString() + "newfile." + ext;
        URI uri = URI.create(uriStr);
        File file = new File(uri);

        // File was created
        assertTrue(fileService.exportTo(file, ext));

        // File created is an image file
        assertTrue(ImageIO.read(file) != null);

        // A file was removed
        assertTrue(Files.deleteIfExists(Path.of(uri)));
    }
}
