package code.Utility;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author David
 */
public class FontBank 
{
    private TrueTypeFont gameFont;
    private TrueTypeFont mediumFont;
    private TrueTypeFont smallFont;
    
    public FontBank()
    {
        
        try 
        {
            Font temp3 = new Font("Times New Roman", Font.BOLD, 60);
            TrueTypeFont font = new TrueTypeFont(temp3, false);
            InputStream inputStream = ResourceLoader.getResourceAsStream("res/misc/3dventure.ttf");
            Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont2 = awtFont2.deriveFont(60f);
            gameFont = new TrueTypeFont(awtFont2, false);
            
            Font temp2 = new Font("Segoe UI", Font.PLAIN, 40);
            mediumFont = new TrueTypeFont(temp2, true);
           
            Font temp1 = new Font("Segoe UI", Font.PLAIN, 20);
            smallFont = new TrueTypeFont(temp1, true);
        } 
        catch (FontFormatException | IOException e )
        {
            System.err.println("Error loading fonts");
        }
        
        
        
    }
    
    
    public TrueTypeFont getGameFont()
    {
        return gameFont;
    }
    
    public TrueTypeFont getMediumFont()
    {
        return mediumFont;
    }
    
    public TrueTypeFont getSmallFont()
    {
        return smallFont;
    }
           
    
    
    
}
