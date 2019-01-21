package Utility;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Serves custom fonts application-wide
 * @author David
 */
public class FontServer 
{
    private static Font rawGameFont;
    private static int fontLoads = 0;
            
    private static void initialiseGameFont()
    {
        // Ensures font is loaded once
        if (fontLoads == 0)
        {
            try 
            {
            InputStream inputStream = ResourceLoader.getResourceAsStream("res/misc/3dventure.ttf");
            rawGameFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            fontLoads++;
            }
            catch (Exception e)
            {
                System.err.println("Error loading font");
            }
        }
    }
    
    /**
     * Get a font
     * Input Format: fontname-style-size
     * @return 
     */
    public static TrueTypeFont getFont(String rawFontS)
    {
        // Process font string
        rawFontS = rawFontS.toLowerCase();
        String[] parts = rawFontS.split("-");
        String fontS = parts[0];
        int styleID = interpretStyle(parts[1]);
        float size = Float.parseFloat(parts[2]);
                
        // Initialise font output
        TrueTypeFont fontOutput = null;
        
        //Special game font
        if (fontS.contains("game"))
        {
            initialiseGameFont();
            Font midFont = rawGameFont.deriveFont(size);
            fontOutput = new TrueTypeFont(midFont, false);
        }
        else // Regular fonts
        {
            Font raw = new Font(fontS, styleID, (int) size);
            fontOutput = new TrueTypeFont(raw, true);
        }
        
        return fontOutput;
    }
    

    private static int interpretStyle(String style)
    {
        int id = 0;
        if (style.contains("plain"))
        {
            id = Font.PLAIN;
        }
        else if (style.contains("ital"))
        {
            id = Font.ITALIC;
        }
        else if (style.contains("bold"))
        {
            id = Font.BOLD;
        }
        else 
        {
            throw new IllegalArgumentException("Invalid style");
        }
        return id;
    }


}
