package states.menu.settings;

import components.buttons.Button;
import components.buttons.ButtonGrid;

/**
 * Models a setting with numerical modes
 *
 * @author David
 */
public abstract class NumSetting extends Setting {

    /**
     * Create numerical setting
     *
     * @param rawName
     * @param bg
     * @param bPos
     */
    public NumSetting(String rawName, ButtonGrid bg, int bPos) {
        super(rawName, bg, bPos);
    }

    /**
     * Get value of setting
     *
     * @return
     */
    @Override
    public abstract Object getValue();

    /**
     * The new value = current + 0.1
     *
     * @return
     */
    @Override
    public Object getNewValue() {
        return (Object) (((float) getValue() + 0.1f) % 1.1f);
    }

    /**
     * Change value of setting
     *
     * @param newValue
     */
    @Override
    public abstract void setValue(Object newValue);

    /**
     * Update label to reflect new numerical value
     *
     * @param name
     * @param b
     */
    @Override
    public void updateLabel(String name, Button b) {

        // Initialize first part
        String newLabel = name + ": ";

        // Add amplified value 
        newLabel += (int) ((float) getValue() * 100.0);

        // Apply to button with percentage
        b.setLabel(newLabel + "%");
    }

}
