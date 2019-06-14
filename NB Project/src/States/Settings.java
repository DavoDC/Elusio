package states;

import java.util.ArrayList;

import main.Globals;
import components.screentemps.InterfaceScreen;

import org.newdawn.slick.gui.AbstractComponent;

/**
 * Provides an interface for making setting adjustments
 *
 * @author David
 */
public class Settings extends InterfaceScreen
{

    // Font
    private final String settingsFont = "OCR A Extended-plain-35";

    @Override
    public int getID()
    {
        return Globals.STATES.get("SETTINGS");
    }

    @Override
    public ArrayList<Object> getButtonFeatures()
    {
        // Create AL
        ArrayList<Object> feats = new ArrayList<>();

        // Add to AL
        // Number of buttons
        feats.add(getButtonLabels().size());
        // Image Location
        feats.add(Globals.generalPanelRes);
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
    public ArrayList<String> getButtonLabels()
    {
        // Create setting strings
        boolean status = Globals.showDevData;
        String stats = processSwitchString("DEV DATA: X", status);

        // Create AL
        ArrayList<String> labels = new ArrayList<>();

        // Add to AL
        labels.add("header_SETTINGS_" + Globals.headerFont);
        labels.add(stats);

        return labels;
    }

    @Override
    public void customPostInit()
    {
        // Add action to stats button
        super.getButtonGrid().getButtonByPos(1).addListener((AbstractComponent source) ->
        {
            Globals.showDevData = !Globals.showDevData;
            switchLabel(Globals.showDevData);
        });

    }

    /**
     * Alter a label after it is clicked
     *
     * @param prev previous label
     * @param pos in button list
     * @param state boolean status
     * @return
     */
    private void switchLabel(boolean state)
    {
        // Get previous string
        String prev = super.getButtonGrid().getButtonByPos(1).getLabel();

        // Generate new string
        String newS = processSwitchString(prev, state);

        // Replace old label with new
        super.getButtonGrid().replaceButtonLabel(prev, newS);

    }

    private String processSwitchString(String prev, boolean state)
    {
        // Initialise new label with first part
        String newS = prev.split(":")[0];

        // Add semicolon and space
        newS += ": ";

        // Add true if ON, and OFF if false
        if (state)
        {
            newS += "ON";
        }
        else
        {
            newS += "OFF";
        }

        // Return new string
        return newS;
    }

    @Override
    public boolean isDarkened()
    {
        return true;
    }

}
