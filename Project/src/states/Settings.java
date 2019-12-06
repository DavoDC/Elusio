package states;

import java.util.ArrayList;

import main.Globals;
import states.screens.InterfaceScreen;

import org.newdawn.slick.gui.AbstractComponent;

/**
 * Provides screen for modifying game settings
 *
 * @author David
 */
public class Settings extends InterfaceScreen {

    // Font
    private final String settingsFont = "OCR A Extended-plain-35";

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public int getID() {
        return Globals.STATES.get("SETTINGS");
    }

    @Override
    public ArrayList<Object> getButtonFeatures() {
        // Create AL
        ArrayList<Object> feats = new ArrayList<>();

        // Add to AL
        // Number of buttons
        feats.add(getButtonLabels().size());
        // Image Location
        feats.add(Globals.buttonPanelRes);
        // startXpos
        feats.add(300);
        // startYpos
        feats.add(200);
        // width
        feats.add(550);
        // height
        feats.add(40);
        // XSpacing
        feats.add(0);
        // YSpacing
        feats.add(50);
        // NumberofColumns
        feats.add(1);
        // FontString
        feats.add(settingsFont);

        return feats;
    }

    @Override
    public ArrayList<String> getButtonLabels() {

        // Initialize list
        ArrayList<String> labels = new ArrayList<>();
        labels.add("header_SETTINGS_" + Globals.headerFont);

        // Add music toggle
        boolean musicB = Globals.agc.isMusicOn();
        String musicS = processSwitchString("MUSIC: X", musicB);
        labels.add(musicS);

        // Return lists
        return labels;
    }

    /**
     * Do custom initialization
     */
    @Override
    public void customPostInit() {

        // Add action to Music toggle
        super.getButtonGrid().getButtonByPos(1).addListener((AbstractComponent source)
                -> {

            Globals.agc.setMusicOn(!Globals.agc.isMusicOn());
            switchLabel(1, Globals.agc.isMusicOn());
        });

    }

    /**
     * Alter a certain button's label to reflect a new boolean state
     *
     * @param prev previous label
     * @param pos in button list
     * @param state boolean status
     */
    private void switchLabel(int pos, boolean state) {

        // Get previous string
        String prev = super.getButtonGrid().getButtonByPos(pos).getLabel();

        // Generate new string
        String newS = processSwitchString(prev, state);

        // Replace old label with new
        super.getButtonGrid().replaceButtonLabel(prev, newS);
    }

    /**
     * Return a setting label string that reflects a given boolean
     *
     * @param prev
     * @param state
     * @return
     */
    private String processSwitchString(String prev, boolean state) {

        // Initialise new label with first part
        String newS = prev.split(":")[0];

        // Add semicolon and space
        newS += ": ";

        // Add true if ON, and OFF if false
        if (state) {
            newS += "ON";
        } else {
            newS += "OFF";
        }

        // Return new string
        return newS;
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
