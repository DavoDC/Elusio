package Utility.UI;

import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

/**
 * Helps to display information
 * @author David Charkey
 */
public class Popup
{
    // Button
    private Button panel;
    
    
    public Popup(int r, int c, int tileW, int tileH)
    {
        // Image
        Image img = new Image("res/ui/menu.p");
        
        // Rectangle
        Rectangle rect = new Rectangle(0, 0, 0, 0);
        
        // Font
        TrueTypeFont font = FontServer.getFont("Candara-Plain-18");
        
        // Create button
        panel = new Button(img, shape, font);
    }
    
    
    
    
}
