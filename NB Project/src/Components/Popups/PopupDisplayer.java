package Components.Popups;

import Main.Globals;
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
    
    // Special popup
    private Popup specialP;
    private boolean showSpecial;

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
        
        // Special popup
        specialP = null;
        showSpecial = false;

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
        // test - commented lines
//        String locLine = "Xaidu: We appear to have materialised in ";
//        locLine += Globals.dimensionName + ". There was...";
//        textLines.add(locLine);
//        textLines.add("You: Xaidu! You surprised me! I am so glad you are still alive!");
//        textLines.add("Xaidu: I do not dissipate that easily, my friend");
//        textLines.add("You: What happened to you?");
//        textLines.add("Xaidu: Something disturbed my phase-shift inhibitor"); 
//        textLines.add("You: Perhaps something similar to electrovelox could help....");
//        textLines.add("Xaidu: Good idea!. There should be some in this diverse dimension.");
        
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
        if (atTime(100))
        {
            Globals.inputIgnored = true;
            popupList.get(0).setVisible(true);
        }
        
        // Show special popup, if loaded
        if (showSpecial)
        {
            Globals.inputIgnored = true; //test
            specialP.setVisible(true);
            showSpecial = false;
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
     * @param g
     */
    public void renderPopups(Graphics g)
    {
        // Call render method of all pre-loaded popups
        for (Popup curP : popupList)
        {
            curP.show(g);
        }
        
        // Render special popup
        if (specialP != null)
        {
            specialP.show(g);
        }   
    }

    public void loadSpecialPopup(Popup itemInfo)
    {
        specialP = itemInfo;
        enableSpecialPopup();
    }

    public void enableSpecialPopup()
    {
        if (specialP != null)
        {
            showSpecial = true;
        }
    }

}
