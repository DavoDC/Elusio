package components.popups;

import components.servers.FontServer;
import java.util.Timer;
import java.util.TimerTask;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 * Writes a string one character at a time
 *
 * @author David
 */
public class StringWriter {

    // Line 
    private String line;
    private int lineLen;
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

    // True when text has been written once
    private boolean writtenOnce;

    // Timer needed
    private boolean timerNeeded;

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

        // Save line and length
        this.line = line;
        this.lineLen = line.length();

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

        // No writes initially
        writtenOnce = false;

        // Timer not needed
        timerNeeded = true;
    }

    /**
     * Draw the sub string
     */
    public void updateAndDraw() {

        // If end has not been reached
        if (!(curCharPos - 1 == lineLen)) {

            // Draw text
            drawText();

            // If it is time to add the next character 
            if (timeUntilAdd == 0) {

                // Progress character count
                curCharPos++;

                // Reset add time back to full interval
                timeUntilAdd = addInterval;
            }

            // Reduce time until next character is added
            timeUntilAdd -= 10;

            // Timer is needed
            timerNeeded = true;
        } else {

            // Writing has occurred
            writtenOnce = true;

            // Else if end has been reached,
            // if repeating type 
            if (isRepeating) {

                // If timer is needed
                if (timerNeeded) {

                    // Reset line after some time
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            setNewLine(line);
                        }
                    }, 639);

                    // Timer is not needed
                    timerNeeded = false;
                }
            } else {

                // Draw text
                drawText();
            }
        }
    }

    /**
     * Give the writer a new line and reset counters
     *
     * @param newText
     */
    public void setNewLine(String newText) {

        // Set text
        line = newText;

        // Update length
        lineLen = line.length();

        // Reset counters
        timeUntilAdd = 0;

        // Reset to first
        curCharPos = 0;
    }

    /**
     * Draw the current substring
     */
    private void drawText() {

        // Protect against excessive values
        if (curCharPos >= lineLen) {
            curCharPos = lineLen;
        }

        // Draw left to right 
        String sub = line.substring(0, curCharPos);
        textFont.drawString(textX, textY, sub, textCol);
    }

    /**
     * Return true if the line has been written once
     *
     * @return
     */
    public boolean hasWrittenOnce() {
        return writtenOnce;
    }
}
