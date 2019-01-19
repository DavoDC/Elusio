
package code.Utility;


import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Circle;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


/**
 * Helps create buttons
 * 
 * Use in "init":
 * 1. Create button manager
 * 2. Create parameters Array
 * 3. Create labels ArrayList
 * 4. Input both into "CreateButtonGrid"
 * 
 * Use in render:
 * - Call drawButtonGrid
 * 
 * @author David
 */
public class ButtonManager 
{
    // Holds shape of buttons
    private ArrayList<Rectangle> rectangles;
    
    // Holds text of buttons
    private ArrayList<String> labels;
    
    // Font
    private FontBank fontbank;
    private TrueTypeFont font;
            
    /**
     * Constructor
     */
    public ButtonManager(String fontS) 
    {
           // Initialise lists
           rectangles = new ArrayList<>();
           labels = new ArrayList<>();
           
           // Load font
           fontbank = new FontBank();
           
           if (fontS.contains("game"))
           {
               font = fontbank.getGameFont();
           }
           else if (fontS.contains("med"))
           {
               font = fontbank.getMediumFont();
           }
           else if (fontS.contains("small"))
           {
               font = fontbank.getSmallFont();
           }

    }
     
    
    
    /**
     * Get number of buttons
     * @return 
     */
    public int getButtonNo()
    {
        return rectangles.size();
    }

    public String getLabelClicked(Circle cursor)
    {
        String label = "";
        for (int i = 0; i < labels.size(); i++)
        {
           Rectangle curButton = rectangles.get(i);
           Boolean touching = cursor.intersects(curButton);

           if (touching)
                   {
                       label =  labels.get(i);
                   }
        }
        return label;
    }
    

  
     /**
     * Populates the ButtonManager with buttons/rectangles
     * 
     * @param numbers 
     * There are 8 parameters required:
     * startXpos, startYpos, width, height, Xspace, Yspace, colNo
     * 
     */
    public void createButtonGrid(float[] numbers, ArrayList<String> labels)
    {
        // Save labels into field for drawing
        this.labels = labels;
        
        // Extract required information
        float xpos = numbers[0];
        float ypos = numbers[1];
        float bW = numbers[2];
        float bH = numbers[3];
        float xspacing = numbers[4];
        float yspacing = numbers[5];
        float columns = numbers[6];

        float curxpos = xpos;
        float curypos = ypos;

        // Add button rectangles and modify them
        int counter = 0;
        for (int i = 0; i < labels.size(); i++)
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
        for (int i = 0; i < labels.size(); i++)
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
            font.drawString(X, Y, curText); 
            
        }
    }
    
    
    
    
    
    
}
