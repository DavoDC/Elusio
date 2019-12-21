package states.menu.settings;

import components.buttons.ButtonGrid;
import java.util.ArrayList;

import main.Globals;
import states.base.InterfaceScreen;

/**
 * Provides screen for modifying game settings
 *
 * @author David
 */
public class Settings extends InterfaceScreen {

    // The total number of settings
    private final int NUM_SETTINGS = 4;

    /**
     * Set button features
     */
    @Override
    public ArrayList<Object> getButtonFeatures() {

        ArrayList<Object> feats = new ArrayList<>();
        feats.add(NUM_SETTINGS + 1); // Button ammount
        feats.add(Globals.getFP("button")); // Image loc
        feats.add(300); // StartXpos    
        feats.add(200); // StartYpos 
        feats.add(550); // Width   
        feats.add(50);  // Height    
        feats.add(0);   // XSpacing      
        feats.add(36);  // YSpacing   
        feats.add(1);   // NumberofColumns
        feats.add("Lucida Bright-Bold-32"); // FontString

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

        // Button position
        int bPos = 1;

        // Get button grid
        ButtonGrid bg = Settings.super.getButtonGrid();

        // Make music setting
        new BoolSetting("Music", bg, bPos) {
            @Override
            public boolean getStatus() {
                return Globals.agc.isMusicOn();
            }

            @Override
            public void setStatus(boolean newStatus) {
                Globals.agc.setMusicOn(newStatus);
            }
        };
        bPos++;

        // Make music volume setting
        new NumSetting("Music Volume", bg, bPos) {
            @Override
            public Object getValue() {
                return (Object) Globals.agc.getMusicVolume();
            }

            @Override
            public void setValue(Object newValue) {
                Globals.agc.setMusicVolume((float) newValue);
            }
        };
        bPos++;

        // Make sound setting
        new BoolSetting("Sound", bg, bPos) {
            @Override
            public boolean getStatus() {
                return Globals.agc.isSoundOn();
            }

            @Override
            public void setStatus(boolean newStatus) {
                Globals.agc.setSoundOn(newStatus);
            }
        };
        bPos++;

        // Make sound volume setting
        new NumSetting("Sound Volume", bg, bPos) {
            @Override
            public Object getValue() {
                return (Object) Globals.agc.getSoundVolume();
            }

            @Override
            public void setValue(Object newValue) {
                Globals.agc.setSoundVolume((float) newValue);
            }
        };
        bPos++;
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
