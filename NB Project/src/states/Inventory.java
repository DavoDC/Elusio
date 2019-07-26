package states;

import components.helpers.FontServer;
import components.screentemps.InterfaceScreen;
import entity.item.Item;
import main.Globals;

import java.util.ArrayList;
import org.newdawn.slick.Color;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

/**
 * Displays the player's current inventory
 *
 * @author David
 */
public class Inventory extends InterfaceScreen
{

    // Normal font
    private TrueTypeFont normFont = null;

    @Override
    public int getID()
    {
        return Globals.STATES.get("INVENTORY");
    }

    @Override
    public ArrayList<Object> getButtonFeatures()
    {
        // Create AL
        ArrayList<Object> feats = new ArrayList<>();

        // Add to AL
        feats.add(1); // Number of buttons
        feats.add(Globals.emptyImgRes); // Image Location
        feats.add(100); // startXpos
        feats.add(100); // startYpos 
        feats.add(350); // width
        feats.add(80); // height
        feats.add(0); // XSpacing
        feats.add((int) Globals.screenH / 25); // YSpacing //always fits
        feats.add(1); // NumberofColumns
        feats.add("Segoe UI-Plain-30"); // FontString

        return feats;
    }

    @Override
    public ArrayList<String> getButtonLabels()
    {
        // Create AL
        ArrayList<String> text = new ArrayList<>();

        // Add to text
        text.add("");

        return text;
    }

    @Override
    public boolean isDarkened()
    {
        return true;
    }

    /**
     * Add additional initialization tasks here
     */
    public void customPostInit()
    {
        normFont = FontServer.getFont("Segoe UI-Plain-18");
        
        // TESTING
        Globals.alien.addItem(new Item("Apple", "Desc", 0, 0));
        Globals.alien.addItem(new Item("Orange", "Desc", 0, 0));
    }

    /**
     * Do custom post rendering here
     */
    public void customPostRender(Graphics g)
    {
        // Retrieve inventory
        ArrayList<Item> inv = Globals.alien.getInv();

//        // Say empty when empty
//        if (inv.isEmpty())
//        {
//            normFont.drawString(100, 100, "The inventory is empty");
//        }


        // Draw every item
        float curX = 100;
        float curY = 100;
        Rectangle box = null;
        for (Item curItem : inv)
        {
            // Draw box
            g.setColor(Color.darkGray);
            g.fillRect(curX - 10, curY - 10, Globals.screenW - 400, 40);

            // Draw text
            g.setColor(Color.white);
            normFont.drawString(curX, curY, curItem.getName());

            curY += 60;
        }

        // Will have to add button that shows next "lot" of items 
        // Will need customisable button for info
        // Will need customisable button for use
    }

}
