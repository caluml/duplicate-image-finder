package group;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageFingerprinter {

    public static final int GRID_SIZE = 4;

    public static ImageFingerprint getImageFingerprint(final File file) {
        try {
            final String imageName = file.getAbsolutePath();
            return getImageFingerprint(ImageIO.read(file), imageName);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ImageFingerprint getImageFingerprint(final BufferedImage bufferedImage,
            final String imageName) {
        final int height = bufferedImage.getHeight();
        final int width = bufferedImage.getWidth();

        final int widthBlock = (width / GRID_SIZE);
        final int heightBlock = (height / GRID_SIZE);

        final ImageFingerprint imageFingerprint = new ImageFingerprint();
        imageFingerprint.setName(imageName);

        for (int across = 0; across < GRID_SIZE; across++) {
            for (int down = 0; down < GRID_SIZE; down++) {
                final int x = across * widthBlock;
                final int y = down * heightBlock;
                final BufferedImage subimage = bufferedImage.getSubimage(x, y, widthBlock,
                        heightBlock);
                final Image image = subimage.getScaledInstance(1, 1, Image.SCALE_DEFAULT);
                imageFingerprint.add(getPixelColour(image));
            }
        }

        return imageFingerprint;
    }

    private static Color getPixelColour(final Image image) {
        if (image.getHeight(null) != 1 || image.getWidth(null) != 1) {
            throw new IllegalArgumentException("Image should be 1x1");
        }
        final int[] pixels = new int[1];
        final PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, pixels, 0, 1);
        try {
            pg.grabPixels();
        } catch (final InterruptedException e) {
        }

        // From here, individual pixel can be accessed via the
        // pixels array.
        final int c = pixels[0]; // or pixels[x * width + y]
        final int red = (c & 0x00ff0000) >> 16;
        final int green = (c & 0x0000ff00) >> 8;
        final int blue = c & 0x000000ff;
        return new Color(red, green, blue);
    }

}
