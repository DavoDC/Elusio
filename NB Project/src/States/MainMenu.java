package States;

import Main.Globals;
import Utility.GUI.ButtonManager;
import Utility.GUI.FontServer;
import Utility.GUI.InfoScreen;
import Utility.GUI.InterfaceScreen;
import Utility.TimedWriter;

import java.util.ArrayList;
import java.util.Arrays;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;


/**
 *
 * @author David
 */
public class MainMenu extends InterfaceScreen
{
    // Helps to write AAA slowly
    private TimedWriter tw;
    
    // Font
    private TrueTypeFont font;
    
    
    @Override
    public void customInit()
    {
        tw = new TimedWriter("Alien Aztec Adventure", 150);
        font = FontServer.getFont("gamefont-plain-70");
    }
    
    @Override
    public int getID() 
    {
        return Globals.states.get("MAINMENU");
    }
    
    @Override
    public ButtonManager initButtonManager() 
    {
        String gfs = "gamefont-plain-50";
        return new ButtonManager(gfs, gfs);
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
    public float[] initHeaderParams() 
    {
        return 
           new float[] 
           {
           300, // start X pos
           250, // start Y pos
           300, // Width
           40,  // Height
           };
    }

    @Override
    public float[] initLineParams() 
    {
        // Get header params
        float[] header = initHeaderParams();
        
        // Make array with last few variables
        float[] last = new float[] 
           {
           0, //Xspace
           Globals.screenH/17, //Yspace //dynamic to fit on screen
           1 //colNo
           };
        
        // Join header and last
        float[] join= Arrays.copyOf(header, header.length + last.length);
        System.arraycopy(last, 0, join, header.length, last.length);
        
        return join;
           
    }

    @Override
    public void customPreUpdate() 
    {
 
        // If paused, change "PLAY" to "CONTINUE"
        if (Globals.isPaused)
        {
            getButtonLabels().set(0, "CONTINUE");
            getButtonManager().createButtonGrid
            (getHeaderParams(), getLineParams(), getButtonLabels());

            int pos = Globals.states.get("PLAY");
            Globals.states.put("CONTINUE", pos);
          
        }
        
        //Update timewriter
        tw.update();
        
    }

    @Override
    public void clickAction(StateBasedGame sbg, String label) 
    {
        // Figure out next state ID
        int newStateID = Globals.states.get(label);

        // Transition to that state
        sbg.enterState(
                newStateID, 
                Globals.getLeave(),
                Globals.getEnter()
        );    
    }
    

    @Override
    public void customPostRender(Graphics g)
    {
        g.setColor(Color.white);
        font.drawString(200, 50, tw.getText() );
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
    
    
    
}
    