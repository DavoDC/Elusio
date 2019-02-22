package Components.Popups;

import Components.Buttons.Button;
import Components.Structures.Map;
import Components.Helpers.DelayWriter;
import Components.Helpers.FontServer;
import Main.Globals;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

/**
 * Helps to display information
 *
 * @author David Charkey
 */
public class Popup
{

    // Underlying button
    private Button button;

    // Status
    private boolean visible;
    private boolean shown;

    // Popup text (delayed)
    private final DelayWriter textDW;
    private int textX;
    private int textY;
    private TrueTypeFont textFont;
    private ArrayList<String> textLines; // AL of text lines
    private int curLineNo; // Current line in AL above

    // Instructions (delayed)
    private DelayWriter instDW;
    private int instX;
    private int instY;
    private TrueTypeFont instFont;
    private final String instS = "Click here to continue!";

    /**
     * Create a popup
     *
     * @param feats - Tile grid row - Tile grid column - Width as number of
     * tiles - Height as number of tiles - Interval for DW - FontS or "default"
     *
     * @param textLines Text in lines
     */
    public Popup(ArrayList<Object> feats, ArrayList<String> textLines)
    {
        // Extract info
        int r = (int) feats.get(0);
        int c = (int) feats.get(1);
        int tileW = (int) feats.get(2);
        int tileH = (int) feats.get(3);
        int interval = (int) feats.get(4);
        String fontS = (String) feats.get(5);

        // Process font string
        if (fontS.equals("default"))
        {
            fontS = "Candara-Bold-26";
        }

        // Initialise status
        visible = false;
        shown = false;

        // Initialise font (Must be before button init)
        textFont = FontServer.getFont(fontS);

        // Initialise button (Must before text pos init)
        initialiseButton(r, c, tileW, tileH);

        // Initialise text DW
        textDW = new DelayWriter(interval);
        textDW.setText(textLines.get(curLineNo));
        textX = button.getX() + 24;
        textY = button.getY() + 24;
        this.textLines = textLines;
        curLineNo = 0;

        // Initialise instruction DW
        instDW = new DelayWriter(interval + 10);
        instDW.setText(instS);
        instX = button.getX() + button.getWidth() / 2 - 100;
        instY = button.getY() + button.getHeight() - 48;
        instFont = FontServer.getFont("Corbel-italic-22");

    }

    private void initialiseButton(int r, int c, int tileW, int tileH)
    {
        // Calculate bounds
        int tileSide = Globals.tileSize;
        int x = c * tileSide;
        int y = r * tileSide;
        int w = tileW * tileSide;
        int h = tileH * tileSide;

        // Image
        Image img = null;
        try
        {
            img = new Image(Globals.popupPanelRes);
            img = img.getScaledCopy(w, h);
        }
        catch (SlickException e)
        {
        }

        // Rectangle
        Rectangle rect = new Rectangle(x, y, w, h);

        // Create button
        button = new Button(img, rect, textFont);

        // Remove label
        button.setLabel("");

        // Add action
        button.addListener((source) ->
        {
            // When popup clicked =
            if (textLines.size() - 1 == curLineNo) // If on last line
            {
                if (textDW.hasWrittenOnce()) // Has finished writing out
                {
                    visible = false; // Hide popup
                    Globals.inputIgnored = false; // Re-enable keys
                    shown = true;
                }
            }
            else if (textDW.hasWrittenOnce()) // If line has been shown
            {
                // Allow the loading of a new line
                curLineNo += 1; // Increase line position
                textDW.setText(textLines.get(curLineNo)); // Load new line
            }
        }
        );
    }

    /**
     * Change visible status
     *
     * @param newStatus
     */
    public void setVisible(boolean newStatus)
    {
        visible = newStatus;
    }

    /**
     * Show the popup on the screen
     *
     * @param g
     */
    public void show(Graphics g)
    {
        // Dont render if not visible
        if (!visible)
        {
            return;
        }

        // Draw button
        button.drawFull(g);

        // Update dws
        textDW.update();
        instDW.update();

        // Draw text dw
        textDW.drawText(textFont, textX, textY);

        // Show instruction repeatedly
        if (instDW.hasWrittenOnce()) // Only reset dw after it has written once
        {
            instDW.setText(instS); // Reset dw
        }
        instDW.drawText(instFont, instX, instY);
    }

    public boolean hasBeenShown()
    {
        return shown;
    }

}
