package components.servers.controls;

import java.util.HashMap;
import base.Globals;

/**
 * Models a control
 *
 * @author David
 */
public abstract class Control {

    // Name of control
    private final String desc;

    // The keys that activate the control
    private final String[] keyList;

    // The key map
    private final HashMap<String, Integer> keyMap;

    /**
     * Create a control
     *
     * @param desc
     * @param keyConfig Key names separated by "-"
     * @param keyMap
     */
    public Control(String desc, String keyConfig, HashMap<String, Integer> keyMap) {
        this.desc = desc;
        keyList = keyConfig.split("-");
        this.keyMap = keyMap;
    }

    /**
     * Get a formatted string describing this control
     *
     * @return
     */
    public String getInfoLine() {

        // If empty control
        if (keyList[0].equals("")) {

            // Return empty
            return "";
        }

        // Add start of line
        String info = desc.toUpperCase() + " =";

        // For all keys
        for (int i = 0; i < keyList.length; i++) {

            // Add key
            info += " " + keyList[i];

            // Add comma if not on last
            if (!(i == keyList.length - 1)) {
                info += ",";
            }
        }

        // Return info line
        return info;
    }

    /**
     * Returns true if any of the control's keys are pressed down
     *
     * @param keyConfig Official key names separated by dashes
     * @return True if any key is down, False otherwise
     */
    private boolean isActivated() {

        // If this control is the empty key
        if (desc.equals("")) {

            // The control is always activated
            return true;
        }

        // Else if the control is a normal control
        // For all keys
        for (String curKeyS : keyList) {

            // If current key is down
            if (Globals.agc.getInput().
                    isKeyDown(keyMap.get(curKeyS))) {

                // Return true
                return true;
            }
        }

        // If no keys were down
        return false;
    }

    /**
     * Do control action if activated
     *
     * @return Success status
     */
    public final boolean doActionIfActivated() {

        // If key activated
        if (isActivated()) {

            // Do action and return success
            doAction();
            return true;
        }

        // Return unsuccessful
        return false;
    }

    /**
     * Get key list
     *
     * @return
     */
    public String[] getKeyList() {
        return keyList;
    }

    /**
     * Get description
     *
     * @return
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Action done when activated (do not call directly)
     */
    public abstract void doAction();
}
