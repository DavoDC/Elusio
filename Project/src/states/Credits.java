package states;

import java.util.ArrayList;

import main.Globals;
import states.screens.InfoScreen;

/**
 * Provides credits screen
 *
 * @author David
 */
public class Credits extends InfoScreen {

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public int getID() {
        return Globals.STATES.get("CREDITS");
    }

    /**
     * Set button labels
     *
     * @return
     */
    @Override
    public ArrayList<String> getButtonLabels() {
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
        text.add("phoenix1291 (Power up SFX)");
        text.add("Iwan 'qubodup' Gabovitch (Ice SFX)");
        text.add("El Kirpitch from https://icons8.com (Ambient music)");
        text.add("J.W. Bjerk (Magic item sprites)");
        text.add("Avery (Enemy sprites)");
        text.add("and thankyou to you!");

        return text;
    }

    /**
     * Set custom X position
     *
     * @return
     */
    @Override
    public int getStartXPos() {
        return (int) (InfoScreen.lineX - 200);
    }

    /**
     * Set custom X spacing
     *
     * @return
     */
    @Override
    public int getXSpacing() {
        return -800;
    }

    /**
     * Set custom Y spacing
     *
     * @return
     */
    @Override
    public int getYSpacing() {
        return 0;
    }

    /**
     * Set the number of columns
     *
     * @return
     */
    @Override
    public int getColumnNo() {
        return 2;
    }

    /**
     * Set the font for the text
     *
     * @return
     */
    @Override
    public String getLineFontString() {
        return "Segoe UI-Plain-22";
    }

}
