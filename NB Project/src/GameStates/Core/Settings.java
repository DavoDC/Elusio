package GameStates.Core;

import Main.Globals;
import Utility.ButtonManager;
import Utility.InterfaceScreen;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author David
 */
public class Settings extends InterfaceScreen
{

    @Override
    public int getID() 
    {
        return Globals.states.get("SETTINGS");
    }
    
      @Override
    public ButtonManager initButtonManager() 
    {
        return new ButtonManager("Gamefont-Plain-65", "OCR A Extended-Plain-35");
    }

    @Override
    public float[] initHeaderParams() 
    {
        return 
           new float[] 
           {
           (Globals.screenW/2) - 175, // start X pos
           150, // start Y pos
           350, // Width
           60,  // Height
           };
    }

    @Override
    public float[] initLineParams() 
    {
        return 
           new float[] 
           {
           300, // start X pos
           200, // start Y pos
           450, // Width
           40,  // Height
           0, //Xspace
           50, //Yspace
           1 //colNo
           };
    }

    @Override
    public void customInit() 
    {
    }
  

    @Override
    public ArrayList<String> initButtonLabels() 
    {
       ArrayList<String> labels = new ArrayList<String>();
       labels.add("SETTINGS");
       labels.add("SHOW FPS: OFF");
       labels.add("SHOW MEMORY USE: OFF");
       labels.add("SHOW COORDS: OFF");
       
       return labels;
    }

    
    @Override
    public void clickAction(StateBasedGame sbg, String label) 
    {
       if (label.contains("FPS"))
           {
               Globals.showFPS = !Globals.showFPS;
               Globals.agc.setShowFPS(Globals.showFPS);
               switchLabel(1, Globals.showFPS);
           }
       else if(label.contains("MEMORY"))
           {
               Globals.showMemUse = !Globals.showMemUse;
               switchLabel(2, Globals.showMemUse);
           }
       else if (label.contains("COORD"))
           {
               Globals.showCoords = !Globals.showCoords;
               switchLabel(3, Globals.showCoords);
           }
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
       // Get previous label
       String prev = getButtonLabels().get(pos);
        
       // Initialise new label with first part
       String newS = prev.split(":")[0];
        
       // Add semicolon and space
       newS += ": ";
        
       // Add true if ON, OFF if false
       if (state) { newS += "ON"; }
       else { newS += "OFF"; }

       // Replace old label with new
       getButtonLabels().set(pos, newS);
       
       // Reinitialise buttons
       getButtonManager().createButtonGrid
       (getHeaderParams(), getLineParams(), getButtonLabels());
               
    }

  

    @Override
    public void customPreUpdate() 
    {
    }
    
    @Override
    public boolean isDarkened() 
    {
        return false;
    }

    @Override
    public Color getButtonCol() 
    {
        return Color.black;
    }

    @Override
    public void customPostRender() 
    {
    }


    
    
}
