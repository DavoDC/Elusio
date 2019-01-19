package code.GameStates.Menu;

import code.Utility.TextScreen;
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
    public int getID() { return code.MainGame.CONTROLS; }


    /**
     * Override default text
     * @return 
     */
    @Override
    public ArrayList<String> getTextList()
    {
       //Create AL
       ArrayList<String> text = new ArrayList<>();
       
       // Add to text
       text.add("CONTROLS");
       text.add("MOVEMENT = Arrow Keys");
       text.add("PAUSE = Escape ");
       text.add("BACK = Middle Mouse");
       text.add("    ");
       
       return text;
    }
    


    
    
    
}
