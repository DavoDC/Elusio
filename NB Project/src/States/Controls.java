package States;

import Main.Globals;
import Utility.Menu.InfoScreen;

import java.util.ArrayList;

/**
 * Displays the game's controls
 * @author David
 */
public class Controls extends InfoScreen
{
    
    /**
     * Override ID
     */
    @Override
    public int getID() { return Globals.states.get("CONTROLS"); }

    @Override
    public ArrayList<String> initButtonLabels() 
    {
       //Create AL
       ArrayList<String> text = new ArrayList<>();
       
       // Add to text
       text.add("CONTROLS");
       text.add("MOVEMENT = Arrow Keys");
       text.add("BACK = Escape");
       text.add("    ");
       
       return text;
    }

    
    
    
}
