package components.popups;

import main.Globals;
import org.newdawn.slick.Color;

/**
 * Writes a list of strings one character at a time
 *
 * @author David
 */
public abstract class ListWriter {

    // The text lines
    private final String[] lines;

    // The current line number
    private int curLineNo;

    // The line writer
    private final StringWriter curLineWriter;

    /**
     * Create a ListWriter
     *
     * @param lines The text lines
     * @param textCol
     * @param fontS
     * @param textX
     * @param textY
     * @param interval
     */
    public ListWriter(String[] lines, Color textCol,
            String fontS, int textX, int textY, long interval) {

        // Save text lines
        this.lines = lines;

        // Set current line to first
        curLineNo = 0;

        // Create and save writer
        this.curLineWriter = new StringWriter(getCurLine(), textCol,
                fontS, false, textX, textY, interval);
    }

    /**
     * Respond to requests for the next line
     */
    public void requestNextLine() {

        // If current line has been written OR if in IDE
        if (curLineWriter.hasWrittenOnce() || Globals.inIDE) {

            // If last line has not been reached
            if (!(lines.length - 1 == curLineNo)) {

                // Move to next line
                curLineNo++;

                // Load new line into writer
                curLineWriter.setNewLine(getCurLine());

            } else {

                // If last line has been reached,
                // do final action
                doFinalAction();
            }
        }
    }

    /**
     * Get part of the current line
     *
     * @param index
     * @return
     */
    private String getCurLinePart(int index) {

        // Get current line
        String curLine = lines[curLineNo];

        // Return split version
        return curLine.split(":")[index].trim();
    }

    /**
     * Get current speaker
     *
     * @return
     */
    public String getCurSpeaker() {
        return getCurLinePart(0);

    }

    /**
     * Get the current text line
     *
     * @return
     */
    private String getCurLine() {
        return getCurLinePart(1);
    }

    /**
     * Draw the current text line
     */
    public void drawText() {
        curLineWriter.drawText();
    }

    /**
     * Do action after all lines have been shown
     */
    public abstract void doFinalAction();
}
