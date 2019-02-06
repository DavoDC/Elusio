package States;

import Main.Globals;
import Utility.UI.InfoScreen;
import java.util.ArrayList;

/**
 * Displays info about those who contributed to the game
 *
 * @author David
 */
public class Credits extends InfoScreen
{

    /**
     * Override ID
     *
     * @return
     */
    @Override
    public int getID()
    {
        return Globals.STATES.get("CREDITS");
    }

    @Override
    public ArrayList<String> getButtonLabels()
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

        return text;
    }

}
