package states;

import java.util.ArrayList;

import main.Globals;
import components.screentemps.InfoScreen;

/**
 * Provides information about the game's controls
 *
 * @author David
 */
public class Controls extends InfoScreen {

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public int getID() {
        return Globals.STATES.get("CONTROLS");
    }

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
        text.add("header_CONTROLS_" + Globals.headerFont);
        text.add(getControlPair("Back/Mainmenu", "Esc"));
        text.add(getControlPair("Grab Item", "Q"));
        text.add(getControlPair("Use Grabbed Item(s)", "E"));
        text.add(getControlPair("Movement", "Arrow Keys"));
        text.add(getControlPair("Show Inventory", "I"));

        return text;
    }

    /**
     * Get a control pairing line
     *
     * @param desc
     * @param key
     * @return
     */
    public String getControlPair(String desc, String key) {

        // Pad description
        String result = String.format("%-1s", desc.toUpperCase());

        // Add equals and key
        result += " = " + key;

        // Return
        return result;
    }

}
