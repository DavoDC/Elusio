package Utility.GUI;


import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Circle;

import org.newdawn.slick.geom.Rectangle;


/**
 * Helps create buttons
 * 
 * "CreateButtonGrid" is used in Initialization
 * 
 * "DrawButtonGrid is used in Render
 * 
 * @author David
 */
public class ButtonManager 
{
    // Holds shape of buttons
    private ArrayList<Rectangle> rectangles;
    
    // Holds text of buttons
    private ArrayList<String> labels;
    
    // Fonts
    private TrueTypeFont headerFont;
    private TrueTypeFont lineFont;
            
    /**
     * Create a Button Manager
     * Font string format: fontname-style-size
     * e.g. "Segoe UI-Plain-25"
     * 
     */
    public ButtonManager(String headerFontS, String lineFontS)
    {
           // Initialise lists
           rectangles = new ArrayList<>();
           labels = new ArrayList<>();
           
           // Load fonts
           headerFont = FontServer.getFont(headerFontS);
           lineFont = FontServer.getFont(lineFontS);
    }
     
     /**
     * Populates the ButtonManager with buttons/rectangles
     * They arranged regularly using the parameters
     * Saves the labels for later use
     * 
     * @param hParams
     * There are 4 parameters required:
     * startXpos, startYpos, width, height
     * 
     * @param lParams
     * There are 7 parameters required:
     * startXpos, startYpos, width, height, Xspace, Yspace, colNo
     * 
     * @param labels
     * The text on the buttons, in the order they will be displayed
     * 
     */
    public void createButtonGrid
        (float[] hParams, float[] lParams, ArrayList<String> labels)
    {
        // Save labels into field for drawing
        this.labels = labels;
        
        // Extract required information
        float xpos = lParams[0];
        float ypos = lParams[1];
        float bW = lParams[2];
        float bH = lParams[3];
        float xspacing = lParams[4];
        float yspacing = lParams[5];
        float columns = lParams[6];

        float curxpos = xpos;
        float curypos = ypos;

        // Add button rectangles and modify them
        int counter = 0;
        for (int i = 0; i < labels.size(); i++)
        {
            // Add new BRect
            if (i == 0) // If header use custom parameters
            {
                float hX = hParams[0];
                float hY = hParams[1];
                float hW = hParams[2];
                float hH = hParams[3];
                rectangles.add(new Rectangle(hX, hY, hW, hH));
            }
            else 
            {
                rectangles.add(new Rectangle(curxpos, curypos, bW, bH));
            }
            
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
     * Draw buttons after CreateButtonGrid
     * Combines rectangles and strings to do so
     * 
     * @param g
     * @param buttonCol 
     */
    public void drawButtonGrid(Graphics g, Color buttonCol)
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
            float X = curRect.getX() + 10;
            float Y = curRect.getY() - 3;
            
            // If header, use header font
            if (i == 0)
            {
                headerFont.drawString(X, Y, curText); 
            }
            else // If a normal line, use line font
            {
                lineFont.drawString(X, Y, curText); 
            }
            
            
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

    /**
     * For a given circle,
     * returns the label of the button it is touching
     * Otherwise, returns null
     * 
     * @param cursor
     * @return label/null
     */
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
                       break;
                   }
        }
        return label;
    }
    

    
    
    
    
}
