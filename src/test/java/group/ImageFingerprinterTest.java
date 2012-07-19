package group;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageFingerprinterTest {

    @Test
    public void Different_pics_are_different() throws IOException {
        final InputStream inputStream1 = getClass().getResourceAsStream("/pics/pic1.jpg");
        final BufferedImage bufferedImage1 = ImageIO.read(inputStream1);
        final ImageFingerprint one = ImageFingerprinter.getImageFingerprint(bufferedImage1, "pic1");

        final InputStream inputStream2 = getClass().getResourceAsStream("/pics/pic2.jpg");
        final BufferedImage bufferedImage2 = ImageIO.read(inputStream2);
        final ImageFingerprint two = ImageFingerprinter.getImageFingerprint(bufferedImage2, "pic2");

        final int difference = DuplicateImageFinder.getDifference(one, two);
        assertTrue(difference > 60);
    }

    @Test
    public void Similar_pics_are_similar() throws IOException {
        final InputStream inputStream1 = getClass().getResourceAsStream("/pics/pic1.jpg");
        final BufferedImage bufferedImage1 = ImageIO.read(inputStream1);
        final ImageFingerprint one = ImageFingerprinter.getImageFingerprint(bufferedImage1, "");

        final InputStream inputStream2 = getClass().getResourceAsStream("/pics/pic1-enlarged.jpg");
        final BufferedImage bufferedImage2 = ImageIO.read(inputStream2);
        final ImageFingerprint two = ImageFingerprinter.getImageFingerprint(bufferedImage2, "");

        final InputStream inputStream3 = getClass().getResourceAsStream(
                "/pics/pic1-worse-quality.jpg");
        final BufferedImage bufferedImage3 = ImageIO.read(inputStream3);
        final ImageFingerprint three = ImageFingerprinter.getImageFingerprint(bufferedImage3, "");

        assertTrue(DuplicateImageFinder.getDifference(one, two) < 60);
        assertTrue(DuplicateImageFinder.getDifference(one, three) < 60);
        assertTrue(DuplicateImageFinder.getDifference(two, three) < 60);
    }

}
