package states;

import java.util.ArrayList;

import main.Globals;
import components.screentemps.InfoScreen;

/**
 * A screen that provides information about the game's contributors
 *
 * @author David 
 */
public class Credits extends InfoScreen
{

    /**
     * Used to identify the state
     * @return 
     */
    @Override
    public int getID()
    {
        return Globals.STATES.get("CREDITS");
    }

    /**
     * Provides the text for the screen
     * @return 
     */
    @Override
    public ArrayList<String> getButtonLabels()
    {
        // Create AL
        ArrayList<String> text = new ArrayList<>();

        // Add to text
        text.add("header_CREDITS_" + Globals.headerFont);
        text.add("David C (Programmer, Designer, Playtester, Logo)");
        text.add("Alex Celenza (Playtester)");
        text.add("Rawdanitsu (Menu Backgrounds)");
        text.add("hiddenone, Kadokawa (Alien sprites)");
        text.add("Ivan Voirol, Aweryn (Tiles)");
        text.add("MillionthVector (Alien ship sprite)");
        text.add("Clint Bellanger (Gold Item Sprites)");
        text.add("Aaron D. Chand (Special pixelated font)");
        text.add("Tuomo Untinen (Sailing Ship Sprites)");
        text.add("Jorge Avila (HUD icons)");
        text.add("phoenix1291 (Sound effects)");
        text.add("Dural from icons8.com (Background music)");
        text.add("J.W. Bjerk (Magic item sprites)");
        text.add("Avery (Enemy sprites)");
        text.add("and thankyou to you!");

        return text;
    }

    /**
     * Provides the X position
     * @return 
     */
    @Override
    public int getStartXPos()
    {
        return (int) (InfoScreen.lineX - 200);
    }

    /**
     * Provides the X Spacing
     * @return 
     */
    @Override
    public int getXSpacing()
    {
        return -900;
    }
    
    /**
     * Provides the Y Spacing
     * @return 
     */
    @Override
    public int getYSpacing()
    {
        return 0;
    }

    /**
     * Provides the number of columns
     * @return 
     */
    @Override
    public int getColumnNo()
    {
        return 2;
    }

    /**
     * Provides the font for the text
     * @return 
     */
    @Override
    public String getLineFontString()
    {
        return "Segoe UI-Plain-22";
    }

}
