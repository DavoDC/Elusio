package states.menu;

import java.util.ArrayList;

import base.Globals;
import states.base.InfoScreen;

/**
 * Provides information about the game's controls
 *
 * @author David
 */
public class Controls extends InfoScreen {

    /**
     * Set button labels
     *
     * @return
     */
    @Override
    public ArrayList<String> getButtonLabels() {

        // Create list
        ArrayList<String> text = new ArrayList<>();

        // Add text
        text.add("header_CONTROLS_" + headerFont);

        // Get cont info lines
        Object[] infoLines = Globals.conServer.getContInfo().valArray();

        // For every line
        for (Object rawLine : infoLines) {
            text.add((String) rawLine);
        }

        // Return list
        return text;
    }

    /**
     * Override and increase Y spacing
     *
     * @return Y spacing
     */
    @Override
    public int getYSpacing() {
        return 20;
    }
}
