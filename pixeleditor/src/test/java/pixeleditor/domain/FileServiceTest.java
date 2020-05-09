package pixeleditor.domain;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
public class FileServiceTest {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();
    private static final Canvas[] LAYERS = new Canvas[10];
    private static FileService fileService;

    @BeforeClass
    public static void setUpClass() {
        fileService = new FileService();
        for (int i = 0; i < 10; i++) {
            LAYERS[i] = new Canvas(4, 4);
        }
        CanvasService.setLayers(LAYERS);
    }
    
    @Test
    @TestInJfxThread
    public void projectFileSavesAndOpensCorrectly() throws IOException, URISyntaxException {
        String fileName = "newproject.proj";
        File file = folder.newFile(fileName);
        
        Color[] colors = new Color[]{Color.ALICEBLUE, Color.ANTIQUEWHITE, Color.AQUA, Color.AQUAMARINE,
            Color.AZURE, Color.BEIGE, Color.BISQUE, Color.BLACK, Color.BLANCHEDALMOND, Color.BLUE};
        
        // fill each layer with a different color
        for (int i = 0; i < 10; i++) {
            CanvasService.setCurrentLayer(LAYERS[i]);
            CanvasService.fill(colors[i]);
        }
        
        // save project
        fileService.saveProject(file);
        
        // create new image
        CanvasService.clearAndResize(2, 2);
        
        // every layer should be blank
        for (int i = 0; i < 10; i++) {
            CanvasService.setCurrentLayer(LAYERS[i]);
            PixelReader reader = CanvasService.getPixelReader(Color.TRANSPARENT);
            assertEquals(Color.TRANSPARENT, reader.getColor(1, 1));
        }
        
        // open project
        fileService.openProject(file);

        // all the colors should be there again
        for (int i = 0; i < 10; i++) {
            CanvasService.setCurrentLayer(LAYERS[i]);
            PixelReader reader = CanvasService.getPixelReader(Color.TRANSPARENT);
            assertEquals(colors[i], reader.getColor(1, 1));
        }
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
        String fileName = "newfile." + ext;
        File file = folder.newFile(fileName);
        fileService.exportTo(file, ext);

        // File created is an image file
        assertTrue(ImageIO.read(file) != null);
    }
}
