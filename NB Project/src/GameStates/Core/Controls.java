package GameStates.Core;

import Main.Globals;
import Utility.TextScreen;

import java.util.ArrayList;

/**
 * Displays the game's controls
 * @author David
 */
public class Controls extends TextScreen
{
    
    /**
     * Override ID
     */
    @Override
    public int getID() { return Globals.states.get("CONTROLS"); }


    /**
     * Override default text
     * @return 
     */
    @Override
    public ArrayList<String> initTextList()
    {
       //Create AL
       ArrayList<String> text = new ArrayList<>();
       
       // Add to text
       text.add("CONTROLS");
       text.add("MOVEMENT = Arrow Keys");
       text.add("BACK = Escape/Middle Mouse");
       text.add("    ");
       
       return text;
    }

    
    
    
}
