package states.challenges;

import states.base.InfoScreen;

import java.util.ArrayList;

/**
 * Base class for challenge states
 *
 * @author David
 */
public class Challenge extends InfoScreen {

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
        lines.add("To resume play: Press ESC to go to the Menu,");
        lines.add("and click on CONTINUE");

        return lines;
    }

}
