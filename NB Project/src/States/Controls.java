package States;

import Main.Globals;
import Components.ScreenTemplates.InfoScreen;

import java.util.ArrayList;

/**
 * Provides information about the game's controls
 *
 * @author David
 */
public class Controls extends InfoScreen
{

    @Override
    public int getID()
    {
        return Globals.STATES.get("CONTROLS");
    }

    @Override
    public ArrayList<String> getButtonLabels()
    {
        //Create AL
        ArrayList<String> text = new ArrayList<>();

        // Add to text
        text.add("header_CONTROLS_" + Globals.headerFont);
        text.add("BACK TO MAINMENU = Escape");
        text.add("MOVEMENT = Arrow Keys");
        text.add("GRAB ITEM = Q");
        text.add("USE ITEM(S) = E");
        // text.add("SHOW HINT = H or");
        // text.add("SHOW INVENTORY = I or");

        return text;
    }

}
