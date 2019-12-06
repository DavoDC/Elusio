package components.popups;

import java.util.ArrayList;

import main.Globals;
import org.newdawn.slick.Color;

import org.newdawn.slick.Graphics;

/**
 * Handles the displaying of popups
 *
 * @author David
 */
public class PopupDisplayer {

    // Special popup variables
    private Popup curPopup;
    private boolean shouldShowPopup;

    /**
     * Create a popup displayer
     */
    public PopupDisplayer() {

        // Initialize with intro popup
        curPopup = getIntroPopup();

        // Show popup
        shouldShowPopup = true;
    }

    /**
     * Get the the intro popup
     *
     * @return
     */
    private Popup getIntroPopup() {

        // Features
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(8);  // Tile grid row
        feats.add(2);  // Tile grid column 
        feats.add(17); // Width as number of tiles 
        feats.add(2);  // Height as number of tiles 
        feats.add(20); // Interval for delay writer
        feats.add("default"); // FontS or "default"
        feats.add(Color.black); // Text color

        // Text
        ArrayList<String> textLines = new ArrayList<>();
        textLines.add("You: Argh ... my head ... Where am I?");
        textLines.add("Ehecatl: We appear to have materialised in Elusio. There was...");
        textLines.add("You: Ehecatl! You surprised me! I am so glad you are still alive!");
        textLines.add("Ehecatl: I do not dissipate that easily, my friend");
        textLines.add("You: What happened to you?");
        textLines.add("Ehecatl: Something disturbed my phase shift inhibitor");
        textLines.add("You: Perhaps something similar to electrovelox could help....");
        textLines.add("Ehecatl: Good idea! There should be some in this diverse dimension.");
        textLines.add("Ehecatl: I will help you navigate it. Listen closely to my messages");

        // Return
        return (new Popup(feats, textLines));
    }

    /**
     * Manage loaded popups
     */
    public void updatePD() {

        // If not in IDE
        if (Globals.inIDE) {

            // Never show popups for faster testing
            shouldShowPopup = false;
        }

        // If a popup should be shown
        if (shouldShowPopup) {

            // Disable input
            Globals.inputIgnored = true;

            // Make current popup visible
            curPopup.setVisible(true);

            // A popup should not be shown
            shouldShowPopup = false;
        }
    }

    /**
     * Renders current popup
     *
     * @param g
     */
    public void renderPopups(Graphics g) {

        // Render current popup
        curPopup.show(g);
    }

    /**
     * Load a popup for displayer
     *
     * @param newPopup
     */
    public void loadPopup(Popup newPopup) {

        // Save popup and activate the showing of it
        curPopup = newPopup;
        shouldShowPopup = true;
    }

}
