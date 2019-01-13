
package code.Utility;

import java.util.ArrayList;
import org.newdawn.slick.geom.Rectangle;

/**
 * Helps create GUI elements
 * 
 * @author David
 */
public class ShapeManager 
{
    
    
     /**
     * Create a grid of rectangles with a regular pattern and spacing
     * 
     * 
     * @param numbers 
     * There are 8 parameters required:
     * buttonNo, startXpos, startYpos, width, height, Xspace, Yspace, colNo
     * 
     * @return ArrayList of newly made "buttons"
     */
    public ArrayList<Rectangle> createRectangleGrid(float[] numbers)
    {
        // Create new ArraList
        ArrayList<Rectangle> Rects = new ArrayList<>();
        
        // Extract require information
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
            Rects.add(new Rectangle(curxpos, curypos, bW, bH));
            
            // Update variables
            counter++;
            curxpos += (bW + xspacing);
            if ( (counter%columns) == 0)
            {
                curxpos = xpos;
                curypos += (bH + yspacing);
            }
        }
        
        return Rects;
    }
}
