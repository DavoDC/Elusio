package states.menu;

import java.util.ArrayList;

import base.Globals;
import states.base.InfoScreen;

/**
 * Provides credits screen
 *
 * @author David
 */
public class Credits extends InfoScreen {

    /**
     * Set button labels
     *
     * @return
     */
    @Override
    public ArrayList<String> getButtonLabels() {
        // Create AL
        ArrayList<String> text = new ArrayList<>();

        // Add header
        text.add("header_CREDITS_" + Globals.headerFont);

        // Add credits
        text.add("David C (Programmer, Designer, Playtester)");
        text.add("Alex Celenza (Playtester)");
        text.add("Rawdanitsu (Menu backgrounds)");
        text.add("Ivan Voirol, Aweryn (Tiles)");
        text.add("hiddenone, Kadokawa (Alien sprites)");
        text.add("MillionthVector (Alien ship sprite)");
        text.add("Clint Bellanger (Gold item Sprites)");
        text.add("J.W. Bjerk (Magic item sprites)");
        text.add("Tuomo Untinen (Sailing ship sprites)");
        text.add("Avery (Enemy sprites)");
        text.add("Jorge Avila (HUD icons)");
        text.add("Aaron D. Chand (Special pixelated font)");
        text.add("Ogrebane (SFX)");
        text.add("phoenix1291 (Power up SFX)");
        text.add("Iwan 'qubodup' Gabovitch (Ice SFX)");
        text.add("El Kirpitch from https://icons8.com (Ambient music)");

        // Add thankyou
        text.add("and thankyou to you!");

        return text;
    }

    /**
     * Set the font for the text
     *
     * @return
     */
    @Override
    public String getLineFontString() {
        return "Segoe UI-Plain-20";
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

}
