package Utility.UI;

import Utility.UI.FontServer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

/**
 * Helps writing text sequentially
 *
 * @author David Charkey
 */
public class DelayWriter
{

    // Text
    private String text;

    // End index of string 
    int endIndex = 0;

    // The time between character writes
    private long interval;

    // The time of adding
    private long addTime;

    // Font
    TrueTypeFont font;

    /**
     * Create a Time Writer
     *
     * @param text
     * @param fontS
     * @param interval
     */
    public DelayWriter(String text, String fontS, int interval)
    {
        this.text = text;

        font = FontServer.getFont(fontS);

        // If divisible by ten
        if (interval % 10 == 0)
        {
            this.interval = interval;
        } else //If not , throw exception
        {
            throw new IllegalArgumentException("TW Interval must be mult of 10");
        }

        addTime = 0;
    }

    public void update()
    {
        // If the last index hasn't been reached
        if (endIndex != text.length())
        {
            // When addTime hits 0
            if (addTime == 0)
            {
                // Initialise addTime to interval
                addTime = interval;

                // Add to index
                endIndex++;
            }

            // Subtract to simulate time passing
            addTime -= 10;
        }

    }

    /**
     * Get a part of the string The part gets longer over time
     */
    public String getText()
    {
        // Return part of the string
        return text.substring(0, endIndex);

    }

    /**
     * Draw the string
     *
     * @param x
     * @param y
     */
    public void drawText(int x, int y)
    {
        font.drawString(x, y, getText());
    }

}
