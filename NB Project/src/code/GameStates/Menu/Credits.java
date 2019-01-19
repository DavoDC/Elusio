package code.GameStates.Menu;

import code.Utility.TextScreen;
import java.util.ArrayList;

/**
 *
 * @author David
 */
public class Credits extends TextScreen
{
    
    /**
     * Override ID
     */
    @Override
    public int getID() { return code.MainGame.CREDITS; }


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
       text.add("CREDITS");
       text.add("David Charkey (Programmer, Designer, Tester, Start Logo)");
       text.add("Rawdanitsu (Menu Backgrounds)");
       text.add("hiddenone (Alien sprites)");
       text.add("Kadokawa (Alien sprites)");
       text.add("Ivan Voirol (Tileset)");
       text.add("Aweryn (Tileset)");
       text.add("MillionthVector (Alien ship sprite)");
       text.add("Clint Bellanger (Gold Item Sprites)");
       text.add("Aaron D. Chand (Special pixelated font)");
       text.add("Tuomo Untinen (Sailing Ship Sprites)");
       text.add("    ");
       text.add("    ");
       text.add("    ");
       text.add("    ");
       text.add("Use middle mouse button to go back");
       
       return text;
    }
    


    
    
    
}
