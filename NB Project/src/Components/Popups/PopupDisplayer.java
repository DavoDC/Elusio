package Components.Popups;

import Main.Globals;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

/**
 * Displays information in-game using interactive text boxes
 *
 * @author David Charkey
 */
public class PopupDisplayer
{

    // Special popup
    private Popup curPopup;
    private boolean showPopup;

    /**
     * Create a popup displayer
     */
    public PopupDisplayer()
    {
        // Load intro popup
        curPopup = getIntroPopup();
        showPopup = true;
    }

    /**
     * Returns the intro popup
     */
    private Popup getIntroPopup()
    {
        // Features
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(8);  // Tile grid row
        feats.add(2);  // Tile grid column 
        feats.add(17); // Width as number of tiles 
        feats.add(2);  // Height as number of tiles 
        feats.add(20); // Interval for delay writer
        feats.add("default"); // FontS or "default"

        // Text
        ArrayList<String> textLines = new ArrayList<>();
        textLines.add("You: Argh ... my head ... Where am I?");
        textLines.add("Ehecatl: We appear to have materialised in Ellusio. There was...");
        textLines.add("You: Ehecatl! You surprised me! I am so glad you are still alive!");
        textLines.add("Ehecatl: I do not dissipate that easily, my friend");
        textLines.add("You: What happened to you?");
        textLines.add("Ehecatl: Something disturbed my phase shift inhibitor");
        textLines.add("You: Perhaps something similar to electrovelox could help....");
        textLines.add("Ehecatl: Good idea! There should be some in this diverse dimension.");
        textLines.add("Ehecatl: I will help you navigate it. Listen closely to my messages");

        // Return
        return new Popup(feats, textLines);
    }

    /**
     * Manage loaded popups
     */
    public void updatePD()
    {
        // Show special popup, if loaded
        if (showPopup)
        {
            Globals.inputIgnored = true; // Disable input
            curPopup.setVisible(true); // Make popup visible
            showPopup = false; // Record the fact it was shown
        }
    }

    /**
     * Renders current popup
     *
     * @param g
     */
    public void renderPopups(Graphics g)
    {
        // Render special popup
        curPopup.show(g);
    }

    /**
     * Load a popup for displayer
     *
     * @param newPopup
     */
    public void loadPopup(Popup newPopup)
    {
        curPopup = newPopup;
        showPopup = true;
    }

}
