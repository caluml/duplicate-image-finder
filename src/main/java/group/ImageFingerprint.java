package group;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

public class ImageFingerprint implements Serializable {

    private static final long serialVersionUID = -96818756073313102L;

    private final List<Color> colours = new ArrayList<Color>();
    private String name;

    public ImageFingerprint() {
        // Default constructor
    }

    public ImageFingerprint(final String hash) {
        if (hash.length() != 96) {
            throw new IllegalArgumentException("hashes are 96 chars long");
        }
        for (int i = 0; i < hash.length(); i = i + 6) {
            final int r = Integer.parseInt(hash.substring(i, i + 2), 16);
            final int g = Integer.parseInt(hash.substring(i + 2, i + 4), 16);
            final int b = Integer.parseInt(hash.substring(i + 4, i + 6), 16);
            this.colours.add(new Color(r, g, b));
        }
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void add(final Color pixelColour) {
        this.colours.add(pixelColour);
    }

    public List<Color> getColours() {
        return this.colours;
    }

    /**
     * Returns a string representation of the fingerprint. This is suitable for storing
     * 
     * @return a string representation of the fingerprint.
     */
    public String getFingerprintHash() {
        final byte[] hash = new byte[48];
        int pos = 0;
        for (final Color color : this.colours) {
            hash[pos] = (byte) color.getRed();
            hash[pos + 1] = (byte) color.getGreen();
            hash[pos + 2] = (byte) color.getBlue();
            pos = pos + 3;
        }
        return DatatypeConverter.printHexBinary(hash);
    }

}