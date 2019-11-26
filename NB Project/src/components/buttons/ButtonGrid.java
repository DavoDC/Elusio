package components.buttons;

import java.util.ArrayList;

import components.helpers.FontServer;
import static components.screentemps.InfoScreen.headerX;
import main.Globals;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.ComponentListener;

/**
 * Helps controlling large numbers of buttons
 *
 * @author David 
 */
public class ButtonGrid
{

    // Holds buttons
    private ArrayList<Button> buttons;

    /**
     * Create a ButtonGrid Input the values common to most buttons
     *
     * @param common 0 - NumberOfButtons, 1 - ImageLocationString, 2 -
     * startXpos, 3 - startYpos, 4 - width, 5 - height, 6 - XSpacing, 7 -
     * YSpacing, 8 - NumberofColumns, 9 - FontString
     *
     * @param labels
     */
    public ButtonGrid(ArrayList<Object> common, ArrayList<String> labels)
    {
        buttons = new ArrayList<>();

        // Create info for generic buttons
        // Extract image
        Image img = null;
        try
        {
            img = new Image((String) common.get(1));
        }
        catch (SlickException ex)
        {
            System.err.println("BGMan : image error");
        }

        // Get bounds
        int x = (int) common.get(2);
        int y = (int) common.get(3);
        int w = (int) common.get(4);
        int h = (int) common.get(5);

        // Change image
        img = img.getScaledCopy(w, h);

        // Extract font
        TrueTypeFont font = FontServer.getFont((String) common.get(9));

        // Create and add generic buttons
        int buttonNo = (int) common.get(0);
        for (int i = 0; i < buttonNo; i++)
        {
            buttons.add(new Button(img, new Rectangle(x, y, w, h), font));
        }

        // Extract required information
        int xpos = (int) common.get(2);
        int ypos = (int) common.get(3);
        int bW = (int) common.get(4);
        int bH = (int) common.get(5);
        int xspacing = (Integer) common.get(6);
        int yspacing = (Integer) common.get(7);
        int columns = (Integer) common.get(8);

        // Local loop variables
        int curxpos = xpos;
        int curypos = ypos;

        // Add button rectangles and modify them
        for (int i = 0; i < buttons.size(); i++)
        {
            // Get label
            String curLabel = labels.get(i);

            if (curLabel.contains("header")) // Special header case
            {
                // Replace button
                replaceButton(i, getHeader(curLabel));
            }
            else
            {
                // Apply current position to current button
                buttons.get(i).setBounds(curxpos, curypos, bW, bH);

                // Apply label
                buttons.get(i).setLabel(curLabel);

                // Shift curX by width + spacing
                curxpos += (bW + xspacing);

                // If current position is a multiple of columnNo
                if (((i % columns) == 0) && (columns != buttonNo))
                {
                    curxpos = xpos;  // Reset X
                    curypos += (bH + yspacing); // Increase Y
                }
            }
        }
    }

    private Button getHeader(String rawLabel)
    {
        // Process raw label
        String[] parts = rawLabel.split("_");
        String actualLabel = parts[1];
        String headerFont = parts[2];

        // Make new button 
        //  Make header image
        Image img = null;
        try
        {
            img = new Image(Globals.generalPanelRes);
        }
        catch (SlickException ex)
        {
        }

        //  Make Header rect
        //  startXpos, startYpos, width, height
        Rectangle rect = new Rectangle(headerX, 100, 450, 60);

        //  Get Header font 
        TrueTypeFont font = FontServer.getFont(headerFont);

        //  Adjust image
        img = img.getScaledCopy((int) rect.getWidth(), (int) rect.getHeight());

        //  Make header button
        Button header = new Button(img, rect, font);

        //  Set label
        header.setLabel(actualLabel);

        return header;
    }

    /**
     * Get the number of buttons in the grid
     * @return 
     */
    public int getSize()
    {
        return buttons.size();
    }

    /**
     * Draws all buttons fully
     *
     * @param g
     */
    public void drawButtons(Graphics g)
    {
        for (Button curB : buttons)
        {
            curB.drawFull(g);
        }
    }

    /**
     * Draw all buttons fully, with offset
     *
     * @param g
     * @param sX
     * @param sY
     */
    public void drawButtonsShifted(Graphics g, int sX, int sY)
    {
        for (Button curB : buttons)
        {
            curB.drawShifted(g, sX, sY);
        }
    }

    /**
     * Get a button using its position
     *
     * @param pos
     * @return
     */
    public Button getButtonByPos(int pos)
    {
        return (buttons.get(pos));
    }

    /**
     * Get a button using its part of its label
     *
     * @param labelPart
     * @return
     */
    public Button getButtonByLabel(String labelPart)
    {
        // Find the position of the button
        // Position 
        int pos = 0;
        // For every button
        for (int i = 0; i < buttons.size(); i++)
        {
            // If current button contains label
            if (getButtonByPos(i).getLabel().contains(labelPart))
            {
                // Save position
                pos = i;
                break;
            }
        }

        // Return by position
        return getButtonByPos(pos);
    }

    /**
     * Replace the label of a button
     *
     * @param oldLabel
     * @param newLabel
     */
    public void replaceButtonLabel(String oldLabel, String newLabel)
    {
        // Get the button using the old label
        // Change its label to the new label
        getButtonByLabel(oldLabel).setLabel(newLabel);
    }

    /**
     * Replace a button
     *
     * @param pos Position of old button
     * @param newB The new button
     */
    public final void replaceButton(int pos, Button newB)
    {
        buttons.set(pos, newB);
    }

    /**
     * Add actions to all buttons
     * @param cl
     */
    public void applyActions(ComponentListener cl)
    {
        for (Button b : buttons)
        {
            b.addListener(cl);
        }
    }

    /**
     * Apply a font to all buttons
     * @param fontS
     */
    public void applyFont(String fontS)
    {
        for (Button b : buttons)
        {
            b.setFont(fontS);
        }
    }

    /**
     * Apply a label to all buttons
     * @param newS
     */
    public void applyLabel(String newS)
    {
        for (Button b : buttons)
        {
            b.setLabel(newS);
        }
    }

}
