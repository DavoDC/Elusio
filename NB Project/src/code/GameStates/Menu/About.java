package code.GameStates.Menu;

import code.Globals;
import code.Utility.TextScreen;
import java.util.ArrayList;

/**
 *
 * @author David
 */
public class About extends TextScreen
{
    
    /**
     * Override ID
     */
    @Override
    public int getID() { return Globals.ABOUT; }


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
       text.add("ABOUT");
       text.add("Main Creator: David Charkey");
       text.add("Date of Creation: Early 2019");
       text.add("Language: Java");
       text.add("IDE: NetBeans");
       text.add("Engine: Slick2D");
       text.add("    ");
       
       return text;
    }
    


    
    
    
}
