package states.menu;

import components.servers.ControlServer.Control;
import java.util.ArrayList;
import java.util.Arrays;

import main.Globals;
import states.base.InfoScreen;

/**
 * Provides information about the game's controls
 *
 * @author David
 */
public class Controls extends InfoScreen {

    /**
     * Override and increase Y spacing
     *
     * @return Y spacing
     */
    @Override
    public int getYSpacing() {
        return 20;
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

        // Get list of controls
        ArrayList<Control> contL = Globals.conServer.getControlList();

        // Add placeholder for movement line
        text.add("");

        // For all controls
        for (Control cont : contL) {

            // Get control info
            String start = cont.getDesc().toUpperCase() + " =";
            String[] keys = cont.getKeyList();

            // If a movement control
            if (isMovCont(cont)) {

                // Concatenate to first text line
                text.set(1, text.get(1) + Arrays.toString(keys));

            } else {

                // Else if a normal control,
                // add normal line
                text.add(getControlLine(start, keys));
            }
        }

        // Fix movement line
        text.set(1, fixMovContLine(text.get(1)));

        // Return list
        return text;
    }

    /**
     * Return true if control is a movement control
     *
     * @param cont
     * @return
     */
    private boolean isMovCont(Control cont) {
        String[] dirs = new String[]{"Up", "Down", "Left", "Right"};
        return Arrays.stream(dirs).parallel().
                anyMatch(cont.getDesc()::contains);
    }

    /**
     * Get a control line
     *
     * @param start The start of the control = "DESC ="
     * @param keys The code names of the mapped keys
     * @return
     */
    public static String getControlLine(String start, String[] keys) {

        // If empty control
        if (start.length() <= 4 && keys[0].equals("")) {

            // Return empty
            return "";
        }

        // For all keys
        for (int i = 0; i < keys.length; i++) {

            // Add key
            start += " " + keys[i];

            // Add comma if not on last
            if (!(i == keys.length - 1)) {
                start += ",";
            }
        }

        // Return result
        return start;
    }

    /**
     * Get movement control line
     */
    private String fixMovContLine(String raw) {

        // Adjust
        raw = raw.replace("][", "-");
        raw = raw.replace("[", "-");
        raw = raw.replace("]", "-");
        raw = raw.replace(",", "-");
        raw = raw.replace(" ", "");

        // Split into keys
        String[] keys = raw.split("-");

        // Separate into parts
        String arrows = "";
        String keybd = "";
        for (int i = 1; i < keys.length; i++) {
            if (i % 2 == 0) {
                arrows += keys[i] + "-";
            } else {
                keybd += keys[i] + "-";
            }
        }

        // Get new ordered keys
        String[] newKeys = (arrows + keybd).split("-");

        // Return result
        return getControlLine("MOVEMENT =", newKeys);
    }
}
