package components.popups;

import java.util.ArrayList;

import components.buttons.Button;
import components.structures.DelayWriter;
import components.servers.FontServer;
import main.Globals;
import org.newdawn.slick.Color;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

/**
 * Models interactive text boxes that come up during game play
 *
 * @author David
 */
public class Popup {

    // Underlying button
    private Button button;

    // Status
    private boolean visible;
    private boolean shown;

    // Popup text (delayed)
    private final DelayWriter textDW;
    private final int textX;
    private final int textY;
    private final TrueTypeFont textFont;
    private final ArrayList<String> textLines;
    private int curLineNo;

    // Instructions (delayed)
    private final DelayWriter instDW;
    private final int instX;
    private final int instY;
    private final TrueTypeFont instFont;
    private final String instS = "Click here to continue!";

    /**
     * Create a popup
     *
     * @param feats Tile grid row, Tile grid column, Width in tiles, Height in
     * tiles, Interval for DW, FontS or "default", Text color
     * @param textLines Text in lines
     */
    public Popup(ArrayList<Object> feats, ArrayList<String> textLines) {

        // Check arguments
        if (feats.size() != 7 || textLines.isEmpty()) {
            throw new IllegalArgumentException("Popup Constr: Argument Issue");
        }

        // Extract info
        int r = (int) feats.get(0);
        int c = (int) feats.get(1);
        int tileW = (int) feats.get(2);
        int tileH = (int) feats.get(3);
        int interval = (int) feats.get(4);
        String fontS = (String) feats.get(5);
        Color col = (Color) feats.get(6);

        // Process font string
        if ("default".equals(fontS)) {
            fontS = "Candara-Bold-26";
        }

        // Initialise status
        visible = false;
        shown = false;

        // Initialise font (must be before button init)
        textFont = FontServer.getFont(fontS);

        // Initialise button (must before text pos init)
        initialiseButton(r, c, tileW, tileH);

        // Initialise text DW
        textDW = new DelayWriter(interval, col);
        textDW.setText(textLines.get(curLineNo));
        textX = button.getX() + 24;
        textY = button.getY() + 24;
        this.textLines = textLines;
        curLineNo = 0;

        // Initialise instruction DW
        instDW = new DelayWriter(interval + 10, col);
        instDW.setText(instS);
        instX = button.getX() + button.getWidth() / 2 - 100;
        instY = button.getY() + button.getHeight() - 48;
        instFont = FontServer.getFont("Corbel-italic-22");
    }

    /**
     * Initialize the underlying button
     *
     * @param r row
     * @param c column
     * @param tileW tile width
     * @param tileH tile height
     */
    private void initialiseButton(int r, int c, int tileW, int tileH) {

        // Calculate bounds
        int tileSide = Globals.tileSize;
        int x = c * tileSide;
        int y = r * tileSide;
        int w = tileW * tileSide;
        int h = tileH * tileSide;

        // Initialize image
        Image img = null;
        try {
            img = new Image(Globals.getFP("popup"));
            img = img.getScaledCopy(w, h);
        } catch (SlickException e) {
        }

        // Initialize rectangle
        Rectangle rect = new Rectangle(x, y, w, h);

        // Create button
        button = new Button(img, rect, textFont);

        // Remove label
        button.setLabel("");

        // Add action when popup is clicked
        button.addListener((source)
                -> {

            // If on last line
            if (textLines.size() - 1 == curLineNo) // If on last line
            {
                // If text has finished writing once
                if (textDW.hasWrittenOnce()) // Has finished writing out
                {
                    // Update status
                    visible = false;
                    shown = true;

                    // Re-enable keys
                    Globals.inputIgnored = false;

                    // Do custom action
                    postAction();
                }

            } else if (textDW.hasWrittenOnce()) {

                // If line has been shown,
                // increase line position and load new line
                curLineNo++;
                textDW.setText(textLines.get(curLineNo));
            }
        }
        );
    }

    /**
     * Can be overridden to give popups a custom post action
     */
    public void postAction() {
        // For over-riding
    }

    /**
     * Change visible status
     *
     * @param newStatus
     */
    public void setVisible(boolean newStatus) {
        visible = newStatus;
    }

    /**
     * Show the popup on the screen
     *
     * @param g
     */
    public void show(Graphics g) {

        // If not visible
        if (!visible) {

            // Do not continue with rendering
            return;
        }

        // Draw button
        button.drawFull(g);

        // Update delay writers
        textDW.update();
        instDW.update();

        // Draw delayed popup text
        textDW.drawText(textFont, textX, textY);

        // Show instruction repeatedly
        // If instruction has finished writing
        if (instDW.hasWrittenOnce()) {
            // Reset instruction
            instDW.setText(instS);
        }

        // Draw delayed instruction text
        instDW.drawText(instFont, instX, instY);
    }

    /**
     * Return true if the popup has been shown
     *
     * @return
     */
    public boolean hasBeenShown() {
        return shown;
    }

}
