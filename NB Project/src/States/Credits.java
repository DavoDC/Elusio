package States;

import Main.Globals;
import Components.ScreenTemplates.InfoScreen;
import java.util.ArrayList;

/**
 * Displays info about those who contributed to the game
 *
 * @author David
 */
public class Credits extends InfoScreen
{

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
        text.add("header_CREDITS_" + Globals.headerFont);
        text.add("David Charkey (Programmer, Designer, Tester, Start Logo)");
        text.add("Rawdanitsu (Menu Backgrounds)");
        text.add("hiddenone, Kadokawa (Alien sprites)");
        text.add("Ivan Voirol, Aweryn (Tiles)");
        text.add("MillionthVector (Alien ship sprite)");
        text.add("Clint Bellanger (Gold Item Sprites)");
        text.add("Aaron D. Chand (Special pixelated font)");
        text.add("Tuomo Untinen (Sailing Ship Sprites)");
        text.add("Jorge Avila (HUD icons)");
        text.add("phoenix1291 (Sound effects)");
        text.add("Dural (https://icons8.com/music/author/dural), (Background music)");
        text.add("J.W. Bjerk (Magic item sprites)");
        text.add("Avery (Enemy sprites)");

        return text;
    }

    @Override
    public int getStartXPos()
    {
        return (int) (InfoScreen.lineX - 200);
    }

//    @Override
//    public int getStartYPos()
//    {
//        return (int) (InfoScreen.lineY + 40);
//    }
    @Override
    public int getXSpacing()
    {
        return -750;
    }

    @Override
    public int getColumnNo()
    {
        return 2;
    }

    @Override
    public int getYSpacing()
    {
        return 0;
    }

    @Override
    public String getLineFontString()
    {
        return "Segoe UI-Plain-18";
    }

}
