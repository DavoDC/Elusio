package GameStates.Core;

import Main.Globals;
import Utility.OptionScreen;

import java.util.ArrayList;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author David
 */
public class MainMenu extends OptionScreen
{
    
    @Override
    public int getID() 
    {
        return Globals.states.get("MAINMENU");
    }
    
    @Override
    public float[] initButtonParams() 
    {
        return 
           new float[] 
           {
           300, // start X pos
           200, // start Y pos
           350, // Width
           50,  // Height
           0, //Xspace
           60, //Yspace
           1 //colNo
           };
    }

    @Override
    public ArrayList<String> initButtonLabels() 
    {
       ArrayList<String> labels = new ArrayList<String>();
       labels.add("PLAY");
       labels.add("CONTROLS");
       labels.add("SETTINGS");
       labels.add("CREDITS");
       labels.add("ABOUT");
       labels.add("EXIT");
       
       return labels;
    }

    @Override
    public void customPreUpdate() 
    {
 
    // If paused, change "PLAY" to "CONTINUE"
    if (Globals.isPaused)
    {
        getButtonLabels().set(0, "CONTINUE");
        getButtonManager().createButtonGrid(getButtonParams(), getButtonLabels());

        int pos = Globals.states.get("PLAY");
        Globals.states.put("CONTINUE", pos);
    }
    
    }

    @Override
    public void clickAction(StateBasedGame sbg, String label) 
    {
        // Close game if "EXIT" pressed
        if (!label.equals("EXIT"))  System.exit(0);

        // Figure out next state ID
        int newStateID = Globals.states.get(label);
        
        // Transition to that stae
        sbg.enterState(newStateID, Globals.leave, Globals.enter); 
    }
    
}
    
    

