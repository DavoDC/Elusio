package components.helpers;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 * Helps writing text one character at a time
 *
 * @author David
 */
public class DelayWriter {

    // Text
    private String text;

    // Text color
    private Color col;

    // End index of string 
    private int endIndex;

    // The time between character writes
    private long interval;

    // The time of adding
    private long addTime;

    /**
     * Create a Delay Writer
     *
     * @param interval Time between characters
     * @param col
     */
    public DelayWriter(int interval, Color col) {

        // If interval is divisible by ten
        if (interval % 10 == 0) {

            // Save it
            this.interval = interval;

        } else {

            // Otherwise, throw exception
            throw new IllegalArgumentException("TW Interval must be mult of 10");
        }

        // Save color
        this.col = col;

        // Initialize to default values
        reset();
    }

    /**
     * Reset variables
     */
    private void reset() {
        endIndex = 0;
        addTime = 0;
    }

    /**
     * Update the writer's internal variables
     */
    public void update() {

        // If the last index hasn't been reached, increase index
        if (endIndex != text.length()) {

            // Add a character everytime an interval has passed
            if (addTime == 0) {

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
    public void setText(String newText) {
        text = newText;
        reset();
    }

    /**
     * Returns current segment of the string being displayed
     *
     * @return
     */
    public String getText() {

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
    public void drawText(TrueTypeFont font, int x, int y) {

        // Draw the current string segments
        font.drawString(x, y, getText(), col);
    }

    /**
     * Get written status
     *
     * @return True if text has been written out once
     */
    public boolean hasWrittenOnce() {
        return (endIndex >= text.length());
    }

}
