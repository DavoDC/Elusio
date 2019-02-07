package States;

import Main.Globals;
import Components.ScreenTemplates.InfoScreen;
import Components.ScreenTemplates.InterfaceScreen;

import java.util.ArrayList;
import org.newdawn.slick.gui.AbstractComponent;

/**
 *
 * @author David
 */
public class Settings extends InterfaceScreen
{

    // Font Strings
    private final String headerFont = InfoScreen.headerFont;
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
        feats.add("res/ui/general.png");
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
        // Create strings
        boolean status = Globals.showDevData;
        String stats = processSwitchString("DEV DATA: X", status);

        // Create AL
        ArrayList<String> labels = new ArrayList<>();

        // Add to AL
        labels.add("SETTINGS");
        labels.add(stats);

        return labels;
    }

    @Override
    public void customPostInit()
    {
        // Adjust header
        super.getButtonGrid().makeHeader();

        // Add action to stats button
        super.getButtonGrid().getButtonByPos(1).addListener((AbstractComponent source) ->
        {
            Globals.showDevData = !Globals.showDevData;
            switchLabel(1, Globals.showDevData);
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
    private void switchLabel(int pos, boolean state)
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
        return false;
    }

}
