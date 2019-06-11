package Challenge;

import Components.ScreenTemplates.InfoScreen;
import Main.Globals;
import java.util.ArrayList;

/**
 * Base class for challenge states
 *
 * @author David Charkey
 */
public class Challenge extends InfoScreen
{

    //
    // In update = Increase time using "timeElapsed += delta;"
    //    
    // For timing events:    
    //    private boolean atTime(int time)
    //    {
    //        boolean before = (time - 40) < timeElapsed;
    //        boolean after = (time + 40) > timeElapsed;
    //        
    //        return (before && after);
    //    }
    @Override
    public int getID()
    {
        return Globals.STATES.get("CHALLENGE");
    }

    @Override
    public ArrayList<String> getButtonLabels()
    {
        ArrayList<String> lines = new ArrayList<>();

        lines.add("CHALLENGE");
        lines.add("This is where a boss battle would be!");
        lines.add("Would be quite complex though...");
        lines.add("May come in next version! ");
        lines.add(" ");
        lines.add("To resume play: Press ESC to go to the MainMenu,");
        lines.add("and click on CONTINUE");

        return lines;
    }

}
