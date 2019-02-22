package Components.Helpers;

import org.newdawn.slick.TrueTypeFont;

/**
 * Helps writing text sequentially, as if it were printed
 *
 * @author David Charkey
 */
public class DelayWriter
{

    // Text
    private String text;

    // End index of string 
    int endIndex;

    // The time between character writes
    private long interval;

    // The time of adding
    private long addTime;

    /**
     * Create a Delay Writer
     *
     * @param interval Time between characters
     */
    public DelayWriter(int interval)
    {
        // If divisible by ten
        if (interval % 10 == 0)
        {
            this.interval = interval;
        }
        else //If not , throw exception
        {
            throw new IllegalArgumentException("TW Interval must be mult of 10");
        }

        reset();
    }

    /**
     * Reset variables
     */
    private void reset()
    {
        endIndex = 0;
        addTime = 0;
    }

    public void update()
    {
        // If the last index hasn't been reached, increase index
        if (endIndex != text.length())
        {
            // Add a character everytime an interval has passed
            if (addTime == 0)
            {
                // Initialise addTime to interval
                addTime = interval;

                // Add to index
                endIndex++;
            }

            // Subtract from addTime to simulate time passing
            addTime -= 10;
        }
    }

    /**
     * Change text and reset variables
     *
     * @param newText
     */
    public void setText(String newText)
    {
        text = newText;
        reset();
    }

    /**
     * Returns current segment of the string Gets longer over time
     *
     * @return
     */
    public String getText()
    {
        // Return part of the string
        return text.substring(0, endIndex);

    }

    /**
     * Draw the string
     *
     * @param font
     * @param x
     * @param y
     */
    public void drawText(TrueTypeFont font, int x, int y)
    {
        font.drawString(x, y, getText());
    }

    /**
     * Get written status
     *
     * @return True if written once
     */
    public boolean hasWrittenOnce()
    {
        return (endIndex >= text.length());
    }

}
