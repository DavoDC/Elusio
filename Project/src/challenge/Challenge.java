package challenge;

import components.screentemps.InfoScreen;
import main.Globals;

import java.util.ArrayList;

/**
 * Base class for challenge states
 *
 * @author David
 */
public class Challenge extends InfoScreen {

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public int getID() {
        return Globals.STATES.get("CHALLENGE");
    }

    /**
     * Set button labels
     *
     * @return
     */
    @Override
    public ArrayList<String> getButtonLabels() {
        ArrayList<String> lines = new ArrayList<>();

        lines.add("CHALLENGE");
        lines.add("This is where a boss battle would be!");
        lines.add("Would be quite complex though...");
        lines.add("May come in next version! ");
        lines.add(" ");
        lines.add("To resume play: Press ESC to go to the MainMenu,");
        lines.add("and click on CONTINUE");

        return lines;
    }

}
