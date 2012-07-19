package group;

import java.awt.Color;

public class DuplicateImageFinder {

    public static final int GRID_SIZE = 4;

    /**
     * Returns the difference between two image fingerprints. The value will be 0 to 255. Very
     * similar pictures might be about 15 or less, while 30 appears to give good results.
     * 
     * @param one the first image fingerprint
     * @param two the second image fingerprint
     * @return a number indicating the difference
     */
    public static int getDifference(final ImageFingerprint one, final ImageFingerprint two) {
        int difference = 0;
        for (int i = 0; i < (GRID_SIZE * GRID_SIZE); i++) {
            final Color imageOneColor = one.getColours().get(i);
            final Color imageTwoColor = two.getColours().get(i);

            final int rdiff = Math.abs(imageOneColor.getRed() - imageTwoColor.getRed());
            final int gdiff = Math.abs(imageOneColor.getGreen() - imageTwoColor.getGreen());
            final int bdiff = Math.abs(imageOneColor.getBlue() - imageTwoColor.getBlue());

            if (rdiff > difference) {
                difference = rdiff;
            }
            if (gdiff > difference) {
                difference = gdiff;
            }
            if (bdiff > difference) {
                difference = bdiff;
            }
        }
        return difference;
    }
}
