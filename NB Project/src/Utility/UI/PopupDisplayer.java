package Utility.UI;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;

/**
 * Helps to display information in-game
 *
 * @author David Charkey
 */
public class PopupDisplayer
{

    // Time
    private int timeElapsed;
    
    // Popup list
    private ArrayList<Popup> popupList;

    /**
     * Create a popup displayer
     */
    public PopupDisplayer()
    {
        // Initialise time
        timeElapsed = 0;

        // Initialise popuplist
        popupList = new ArrayList<>();
        popupList.add(getIntroPopup());

    }
    
    /**
     * Returns the intro popup
     */
    private Popup getIntroPopup()
    {
        // Features
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(8); //Tile grid row
        feats.add(2); //Tile grid column 
        feats.add(16); // Width as number of tiles 
        feats.add(2); // Height as number of tiles 
        feats.add("default"); // FontS or "default"
        
        // Text
        ArrayList<String> textLines = new ArrayList<>();
        textLines.add("You: Where am I? …");
        textLines.add("You: I was supposed to awake from cryosleep on my home planet");
        textLines.add("You: Something must have happened to Xadu ... ");
        
        // Return
        return new Popup(feats, textLines);
    }

    /**
     * Manages popups Handles input
     *
     * @param delta
     */
    public void updatePD(int delta)
    {
        // Increase time
        timeElapsed += delta;
        
        // Enable popups based off time
        if (atTime(2000))
        {
            popupList.get(0).setVisible(true);
        }
    }
    
    /**
     * Helps interpreting time data
     */
    private boolean atTime(int time)
    {
        boolean before = (time - 40) < timeElapsed;
        boolean after = (time + 40) > timeElapsed;
        
        return (before && after);
    }

    /**
     * Renders popups
     */
    public void renderPopups(Graphics g)
    {
        // Call render method of all popups
        for (Popup curP : popupList)
        {
            curP.show(g);
        }
    }

}
