package Utility.UI;

import Utility.TiledMapPlus;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

/**
 * Helps to display information
 *
 * @author David Charkey
 */
public class Popup
{

    // Button
    private Button button;
    
    // Text lines
    private ArrayList<String> textLines;
    
    // Current textLines line
    private int curLineNo;
    
    // Text font
    private TrueTypeFont font;

    // Render status
    private boolean visible;
    
    // Delay writer
    private DelayWriter dw;
    
    // Text position
    private int textX;
    private int textY;
    

    /**
     * Create a popup
     *
     * @param feats 
     * - Tile grid row
     * - Tile grid column
     * - Width as number of tiles
     * - Height as number of tiles
     * - FontS or "default"
     * 
     * @param textLines Text in lines
     */
    public Popup(ArrayList<Object> feats, ArrayList<String> textLines)
    {         
        // Extract info
        int r = (int) feats.get(0);
        int c = (int) feats.get(1);
        int tileW = (int) feats.get(2);
        int tileH = (int) feats.get(3);
        String fontS = (String) feats.get(4);
        
        // Process font string
        if (fontS.equals("default")) { fontS = "Candara-Bold-26"; }
                
        // Initialise fields
        this.textLines = textLines;
        curLineNo = 0;
        font = FontServer.getFont(fontS);
        visible = false;
        
        // Initialise button
        initialiseButton(r, c, tileW, tileH);
      
        // Text position
        textX = button.getX() + 12;
        textY = button.getY() + 12;
        
        // Initialise DW
        dw = new DelayWriter(70);
        dw.setText(textLines.get(curLineNo));
    }
    
    private void initialiseButton(int r, int c, int tileW, int tileH)
    {
        // Calculate bounds
        int tileSide = TiledMapPlus.tileSize;
        int x = c * tileSide;
        int y = r * tileSide;
        int w = tileW * tileSide;
        int h = tileH * tileSide;

        // Image
        Image img = null;
        try
        {
            img = new Image("res/ui/popup.png");
            img = img.getScaledCopy(w, h);
        }
        catch (SlickException e)
        {
        }

        // Rectangle
        Rectangle rect = new Rectangle(x, y, w, h);

        // Create button
        button = new Button(img, rect, font);
        
        // Remove label
        button.setLabel("");

        // Add action
        // Switch to next line when clicked on
        button.addListener((source) ->
                {
                    if (textLines.size()-1 == curLineNo)
                    {
                        visible = false;
                    }
                    else
                    {
                        curLineNo += 1;
                        dw.setText(textLines.get(curLineNo));
                    }
                }
        );
    }
    
    /**
     * Change visible status
     * @param newStatus
     */
    public void setVisible(boolean newStatus)
    {
        visible = newStatus;
    }
    
    
    /**
     * Show the popup on the screen
     * @param g 
     */
    public void show(Graphics g)
    {
        // Dont render if not visible
        if (!visible) { return; } 

        // Draw button
        button.drawFull(g);
        
        // Update slow writer
        dw.update();

        // Draw text
        dw.drawText(font, textX, textY);
  
    }
    

}
