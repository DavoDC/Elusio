package components.servers;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import main.Globals;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Serves custom fonts application-wide
 *
 * @author David
 */
public class FontServer {

    // The base font
    private static Font rawGameFont;

    // Game font load status
    private static boolean gameFontLoaded = false;

    /**
     * Initialize main game font (has large pixelated letters)
     */
    private static void initialiseGameFont() {

        // if game font has not been loaded yet
        if (!gameFontLoaded) {
            try {

                // Load up font file
                InputStream inStream = ResourceLoader.
                        getResourceAsStream(Globals.getFP("3dventure"));

                // Create and save font
                rawGameFont = Font.createFont(Font.TRUETYPE_FONT, inStream);

                // Set game font as loaded
                gameFontLoaded = true;

            } catch (FontFormatException | IOException e) {
                System.err.println("Error loading font");
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
    public static TrueTypeFont getFont(String rawFontS) {

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

        // Initialise font output
        TrueTypeFont fontOutput;

        // If special game font is wanted
        if (fontS.contains("game")) {

            // Ensure it is initialized
            initialiseGameFont();

            // Generate TrueTypeFont
            Font midFont = rawGameFont.deriveFont(size);
            fontOutput = new TrueTypeFont(midFont, false);

        } else {

            // Else if just a regular font, generate TrueTypeFont
            Font raw = new Font(fontS, styleID, (int) size);
            fontOutput = new TrueTypeFont(raw, true);
        }

        // Return font generated
        return fontOutput;
    }

    /**
     * Parse the style of a font configuration string
     *
     * @param style
     * @return
     */
    private static int interpretStyle(String style) {

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
