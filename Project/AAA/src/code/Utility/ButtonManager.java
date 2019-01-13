
package code.Utility;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Helps create buttons
 * 
 * @author David
 */
public class ButtonManager 
{
    // Holds shape of buttons
    private ArrayList<Rectangle> rectangles;
    
    // Holds text of buttons
    private ArrayList<String> labels;
    
    // The font used to write on the buttons
    private TrueTypeFont gamefont;
    
            
    /**
     * Constructor
     */
    public ButtonManager() 
    {
           // Initialise lists
           rectangles = new ArrayList<>();
           labels = new ArrayList<>();
           
           // Load font
           try 
           {
               Font awtFont = new Font("Times New Roman", Font.BOLD, 50);
               TrueTypeFont font = new TrueTypeFont(awtFont, false);
               InputStream inputStream = ResourceLoader.getResourceAsStream("res/misc/3dventure.ttf");
               Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
               awtFont2 = awtFont2.deriveFont(50f);
               gamefont = new TrueTypeFont(awtFont2, false);
           }
           catch (FontFormatException | IOException e)
           {
           }
    }
     
    
    
    /**
     * Get game font
     * @return gamefont
     */
    public TrueTypeFont getGamefont()
    {
        return gamefont;
    }
    
    /**
     * Add button label
     * @param newLabel
     */
    public void addButtonLabel(String newLabel)
    {
        labels.add(newLabel);
    }
    
    /**
     * Check if a button is touching another shape
     * Uses button index to find button
     * @param buttonIndex 
     * @param shape
     * @return True if shape
     */
    public boolean isTouching(int buttonIndex, Shape shape)
    {
        Rectangle comp = rectangles.get(buttonIndex);
        return shape.intersects(comp);
    }
    
    /**
     * Get number of buttons
     * @return 
     */
    public int getButtoNo()
    {
        return rectangles.size();
    }
  
     /**
     * Populates the ButtonManager with buttons/rectangles
     * 
     * @param numbers 
     * There are 8 parameters required:
     * buttonNo, startXpos, startYpos, width, height, Xspace, Yspace, colNo
     * 
     */
    public void createButtonGrid(float[] numbers)
    {
        // Extract required information
        float buttonNo = numbers[0];
        float xpos = numbers[1];
        float ypos = numbers[2];
        float bW = numbers[3];
        float bH = numbers[4];
        float xspacing = numbers[5];
        float yspacing = numbers[6];
        float columns = numbers[7];

        float curxpos = xpos;
        float curypos = ypos;

        // Add button rectangles and modify them
        int counter = 0;
        for (int i = 0; i < buttonNo; i++)
        {
            // Add new BRect
            rectangles.add(new Rectangle(curxpos, curypos, bW, bH));
            
            // Update variables
            counter++;
            curxpos += (bW + xspacing);
            if ( (counter%columns) == 0)
            {
                curxpos = xpos;
                curypos += (bH + yspacing);
            }
        }
        
    }
    
   
    
    /**
     * Draw buttons
     * Combines rectangles and strings to do so
     * (Button labels need to added first)
     * 
     * @param g
     * @param buttonCol
     * @param textCol 
     */
    public void drawButtonGrid(Graphics g, Color buttonCol, Color textCol)
    {
        for (int i = 0; i < rectangles.size(); i++)
        {
            // Retrieve info
            Rectangle curRect = rectangles.get(i);
            String curText = labels.get(i);
            
            // Draw rectangles
            g.setColor(buttonCol);
            g.draw(curRect);
            g.fill(curRect);
            
            // Draw text on top
            g.setColor(textCol);
            float X = curRect.getX() + 10;
            float Y = curRect.getY() - 3;
            gamefont.drawString(X, Y, curText); 
            
        }
    }
    
    
    
    
    
    
}
