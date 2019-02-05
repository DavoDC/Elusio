package Utility.UI;

import Main.Globals;
import static Main.Globals.screenW;
import java.util.ArrayList;

/**
 * Models a screen containing info
 *
 * @author David Charkey
 */
public abstract class InfoScreen extends InterfaceScreen
{

    // Line constants
    public static final float lineX = (Globals.screenW / 4) - 300;
    public static final String lineFont = "Segoe UI-Plain-30";

    // Header constants
    public static final float headerX = (Globals.screenW / 2) - 175;
    public static final String headerFont = "Gamefont-Plain-60";

    @Override
    public abstract int getID();

    @Override
    public ArrayList<Object> getButtonFeatures()
    {
        // Create AL
        ArrayList<Object> feats = new ArrayList<>();

        // Add to AL
        feats.add(getButtonLabels().size()); // Number of buttons
        feats.add("res/misc/nothing.png"); // Image Location
        feats.add((int) lineX); // startXpos
        feats.add(160); // startYpos
        feats.add((int) screenW); // Width
        feats.add(40); // Height
        feats.add(15); // XSpacing
        feats.add(5); // YSpacing
        feats.add(1); // NumberofColumns
        feats.add(lineFont); // FontString

        return feats;
    }

    @Override
    public abstract ArrayList<String> getButtonLabels();

    @Override
    public void customPostInit()
    {
        super.getButtonGrid().makeHeader();
    }

    @Override
    public boolean isDarkened()
    {
        return true;
    }

}
