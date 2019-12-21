package states.menu.settings;

import components.buttons.Button;
import components.buttons.ButtonGrid;
import org.newdawn.slick.gui.AbstractComponent;

/**
 * Models a setting
 *
 * @author David
 */
public abstract class Setting {

    /**
     * Initialize setting object
     *
     * @param rawName
     * @param bg
     * @param bPos
     *
     */
    public Setting(String rawName, ButtonGrid bg, int bPos) {

        // Make name uppercase
        String name = rawName.toUpperCase();

        // Get button
        Button b = bg.getButtonByPos(bPos);

        // Initialize full text
        updateLabel(name, b);

        // Attach action to button
        b.addListener((AbstractComponent source) -> {
            setValue(getNewValue());
            updateLabel(name, b);
        });
    }

    /**
     * Get value of setting
     *
     * @return value
     */
    public abstract Object getValue();

    /**
     * Get new value for setting
     *
     * @return new value
     */
    public abstract Object getNewValue();

    /**
     * Set value of setting
     *
     * @param newValue
     */
    public abstract void setValue(Object newValue);

    /**
     * Update the setting button to reflect the setting's value
     *
     * @param name
     * @param b
     */
    public abstract void updateLabel(String name, Button b);

}
