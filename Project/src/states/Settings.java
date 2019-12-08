package states;

import components.buttons.Button;
import java.util.ArrayList;

import main.Globals;
import org.newdawn.slick.gui.AbstractComponent;
import states.screens.InterfaceScreen;

/**
 * Provides screen for modifying game settings
 *
 * @author David
 */
public class Settings extends InterfaceScreen {

    // Font
    private final String settingsFont = "OCR A Extended-plain-35";

    // The total number of settings
    private final int NUM_SETTINGS = 2;

    // The number of settings made
    private int numSettMade = 0;

    /**
     * Models a setting
     */
    private abstract class Setting {

        /**
         * Initialize setting object
         *
         * @param labelStart
         * @param bPos
         */
        public Setting(String rawName) {

            // Make name uppercase
            String name = rawName.toUpperCase();

            // Increase number of settings made
            numSettMade++;

            // Get button
            Button b = Settings.super.
                    getButtonGrid().getButtonByPos(numSettMade);

            // Initialize full text
            updateLabel(name, b);

            // Attach action to button
            b.addListener((AbstractComponent source) -> {
                setStatus(!getStatus());
                updateLabel(name, b);
            });
        }

        /**
         * Get value of boolean status
         *
         * @return boolean status
         */
        public abstract boolean getStatus();

        /**
         * Set value of boolean status
         */
        public abstract void setStatus(boolean newStatus);

        /**
         * Update the setting button to reflect it status
         *
         * @param curLabel
         */
        private void updateLabel(String name, Button b) {

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

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public int getID() {
        return Globals.STATES.get("SETTINGS");
    }

    /**
     * Set button features
     */
    @Override
    public ArrayList<Object> getButtonFeatures() {

        ArrayList<Object> feats = new ArrayList<>();
        feats.add(NUM_SETTINGS + 1); // Button ammount
        feats.add(Globals.buttonPanelRes); // Image loc
        feats.add(300); // StartXpos    
        feats.add(200); // StartYpos 
        feats.add(550); // Width   
        feats.add(40);  // Height    
        feats.add(0);   // XSpacing      
        feats.add(50);  // YSpacing   
        feats.add(1);   // NumberofColumns
        feats.add(settingsFont); // FontString

        return feats;
    }

    @Override
    public ArrayList<String> getButtonLabels() {

        // Initialize list
        ArrayList<String> labels = new ArrayList<>();
        labels.add("header_SETTINGS_" + Globals.headerFont);

        // Add dummy labels
        for (int i = 0; i < NUM_SETTINGS; i++) {
            labels.add(" ");
        }

        // Return list
        return labels;
    }

    /**
     * Do custom post initialization
     */
    @Override
    public void customInit() {

        // Make music setting
        new Setting("Music") {
            @Override
            public boolean getStatus() {
                return Globals.agc.isMusicOn();
            }

            @Override
            public void setStatus(boolean newStatus) {
                Globals.agc.setMusicOn(newStatus);
            }
        };

        // Make sound setting
        new Setting("Sound") {
            @Override
            public boolean getStatus() {
                return Globals.agc.isSoundOn();
            }

            @Override
            public void setStatus(boolean newStatus) {
                Globals.agc.setSoundOn(newStatus);
            }
        };
    }

    /**
     * Set darkened to true
     *
     * @return
     */
    @Override
    public boolean isDarkened() {
        return true;
    }

}
