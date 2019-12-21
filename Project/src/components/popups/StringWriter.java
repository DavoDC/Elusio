package components.popups;

import components.servers.FontServer;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 * Writes a string one character at a time
 *
 * @author David
 */
public class StringWriter {

    // Actual text
    private String line;
    private int curCharPos;

    // Text appearance
    private final Color textCol;
    private final TrueTypeFont textFont;

    // Type
    private final boolean isRepeating;

    // Text position
    private final int textX;
    private final int textY;

    // Time variables
    // The time it takes to add the next character
    private long addInterval;
    // The time of adding
    private long timeUntilAdd;

    /**
     * Create a Line Writer
     *
     * @param line Text line
     * @param textX xPos of text
     * @param textY yPos of text
     * @param isRepeating True if text keeps repeating
     * @param interval Time between characters
     * @param textCol Text color
     * @param fontS Font config
     */
    public StringWriter(String line,
            Color textCol, String fontS, boolean isRepeating,
            int textX, int textY, long interval) {

        // Save line
        this.line = line;

        // Initialize character position to first
        curCharPos = 0;

        // Save text color
        this.textCol = textCol;

        // Get and save text font
        textFont = FontServer.getFont(fontS);

        // Save type information
        this.isRepeating = isRepeating;

        // Save text position
        this.textX = textX;
        this.textY = textY;

        // If interval is divisible by ten
        if (interval % 10 == 0) {

            // Save it
            this.addInterval = interval;
        } else {

            // Otherwise, throw exception
            throw new IllegalArgumentException("DelayWriter interval must be mult of 10");
        }

        // Initialize time until add counters
        timeUntilAdd = 0;
    }

    /**
     * Draw the sub string
     */
    public void drawText() {

        // If popup is of the repeating type,
        // and line has been written once
        if (isRepeating && hasWrittenOnce()) {
            // Reset with original line
            setNewLine(line);
        }

        // Update internal variables
        // If the last char hasn't been reached
        if (curCharPos != line.length()) {

            // If it is time to add the next character 
            if (timeUntilAdd == 0) {

                // Add to character count
                curCharPos++;

                // Reset add time back to full interval
                timeUntilAdd = addInterval;
            }

            // Reduce time until next character is added
            timeUntilAdd -= 10;
        }

        // Get current substring
        String curSubStr = line.substring(0, curCharPos);

        // Draw substring
        textFont.drawString(textX, textY, curSubStr, textCol);
    }

    /**
     * Give the writer a new line and reset counters
     *
     * @param newText
     */
    public void setNewLine(String newText) {

        // Set text
        line = newText;

        // Reset counters
        curCharPos = 0;
        timeUntilAdd = 0;
    }

    /**
     * Return true if line has been fully written out
     *
     * @return True if written and false otherwise
     */
    public boolean hasWrittenOnce() {

        // Return true if last character encapsulates full string
        return (curCharPos >= line.length());
    }
}
