package states;

import java.util.ArrayList;

import main.Globals;
import states.screens.InfoScreen;

/**
 * A screen that provides information about the game
 *
 * @author David
 */
public class About extends InfoScreen {

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public int getID() {
        return Globals.STATES.get("ABOUT");
    }

    /**
     * Used to get text of screen
     *
     * @return
     */
    @Override
    public ArrayList<String> getButtonLabels() {

        // Create AL
        ArrayList<String> text = new ArrayList<>();

        // Add to text
        text.add("header_ABOUT_" + Globals.headerFont);
        text.add("Main Creator: David C");
        text.add("First Created: Dec 2018");
        text.add("Language: Java");
        text.add("IDE: NetBeans");
        text.add("Engine: Slick2D");

        // Return list
        return text;
    }

}
