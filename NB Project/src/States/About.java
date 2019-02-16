package States;

import Main.Globals;
import Components.ScreenTemplates.InfoScreen;
import java.util.ArrayList;

/**
 *
 * @author David
 */
public class About extends InfoScreen
{

    /**
     * Override ID
     *
     * @return ID
     */
    @Override
    public int getID()
    {
        return Globals.STATES.get("ABOUT");
    }

    @Override
    public ArrayList<String> getButtonLabels()
    {
        //Create AL
        ArrayList<String> text = new ArrayList<>();

        // Add to text
        text.add("header_ABOUT_" + Globals.headerFont);
        text.add("Main Creator: David Charkey");
        text.add("Date of Creation: Early 2019");
        text.add("Language: Java");
        text.add("IDE: NetBeans");
        text.add("Engine: Slick2D");
        text.add("    ");

        return text;
    }

}
