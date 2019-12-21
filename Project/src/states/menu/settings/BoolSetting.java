package states.menu.settings;

import components.buttons.Button;
import components.buttons.ButtonGrid;

/**
 * Models a setting with only two modes
 *
 * @author David
 */
public abstract class BoolSetting extends Setting {

    /**
     * Make boolean setting
     *
     * @param rawName
     * @param bg
     * @param bPos
     */
    public BoolSetting(String rawName, ButtonGrid bg, int bPos) {
        super(rawName, bg, bPos);
    }

    @Override
    public Object getValue() {
        return (Object) getStatus();
    }

    /**
     * Get the boolean value of the setting
     *
     * @return
     */
    public abstract boolean getStatus();

    /**
     * The new boolean value is the reverse of the current boolean
     *
     * @return
     */
    @Override
    public Object getNewValue() {
        return !getStatus();
    }

    @Override
    public void setValue(Object newValue) {
        setStatus((boolean) newValue);
    }

    /**
     * Give the setting a new boolean value
     *
     * @param newStatus
     */
    public abstract void setStatus(boolean newStatus);

    /**
     * Update the setting to reflect a new boolean value
     *
     * @param name
     * @param b
     */
    @Override
    public void updateLabel(String name, Button b) {

        // Initialize first part
        String newLabel = name + ": ";

        // Add status as a string
        if (getStatus()) {
            newLabel += "ON";
        } else {
            newLabel += "OFF";
        }

        // Apply to button
        b.setLabel(newLabel);
    }
}
