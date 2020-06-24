package components.servers;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import base.Globals;
import components.underlying.LooseMap;
import org.newdawn.slick.TrueTypeFont;

import org.newdawn.slick.util.ResourceLoader;

/**
 * Serves custom fonts application-wide
 *
 * @author David
 */
public class FontServer {

    // Map font names to fonts
    private LooseMap<Font> fontMap;

    /**
     * Initialize FontServer
     */
    public FontServer() {

        // Initialize font map
        fontMap = new LooseMap<>();

        // For all file paths
        for (String curPath : Globals.fileList) {

            // If path is for a sound
            if (curPath.contains("fonts")
                    && curPath.contains(".ttf")) {
                try {

                    // Get resource as input stream
                    InputStream inS = ResourceLoader.
                            getResourceAsStream(curPath);

                    // Convert to font
                    Font curFont = Font.createFont(Font.TRUETYPE_FONT, inS);

                    // Add path and font pair
                    fontMap.put(curPath, curFont);

                } catch (FontFormatException | IOException e) {
                    System.err.println("Error in FontServer constr.");
                }

            }
        }
    }

    /**
     * Get a custom font using a configuration string with the format:
     * 'fontname-style-size' (e.g. "Calibri-Plain-20")
     *
     * @param rawFontS Font configuration string
     * @return TrueTypeFont generated
     */
    public TrueTypeFont getFont(String rawFontS) {

        // Process font string
        String[] parts = rawFontS.toLowerCase().split("-");
        String fontS = parts[0];
        int styleID = interpretStyle(parts[1]);
        float size = Float.parseFloat(parts[2]);

        // If font size is above 60
        if (size > 60) {

            // Reduce to 60 as font sizes above 60 cannot be displayed
            size = 60;
        }

        // Attempt to retrieve as custom font 
        Font customFont = fontMap.get(fontS);

        // If a regular font 
        if (customFont == null) {

            // Create font
            Font font = new Font(fontS, styleID, (int) size);

            // Return as TTF
            return new TrueTypeFont(font, true);

        } else {

            // Else if is a custom font,
            // derive font with correct size
            Font font = customFont.deriveFont(size);

            // Return as TTF
            return new TrueTypeFont(font, true);
        }
    }

    /**
     * Parse the style of a font configuration string
     *
     * @param style
     * @return
     */
    private int interpretStyle(String style) {

        // ID holder
        int id = 0;

        // Handle each style
        if (style.contains("plain")) {
            id = Font.PLAIN;
        } else if (style.contains("ital")) {
            id = Font.ITALIC;
        } else if (style.contains("bold")) {
            id = Font.BOLD;
        } else {
            // Throw error for non existent style
            throw new IllegalArgumentException("Invalid style");
        }

        // Return 
        return id;
    }
}
