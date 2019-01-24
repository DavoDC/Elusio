package Utility.GUI;

import Main.Globals;
import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 *
 * @author CHARKEYD
 */
public abstract class InterfaceScreen extends BasicGameState
{
    // Button Managers
    private ButtonManager buttonMan;
    private float[] hParams;
    private float[] lParams;
    private ArrayList<String> bLabels;
    
    // Cursor
    private Circle cursorCircle;
    
    // Background
    private BigImage bg;
    
    // Font
    private TrueTypeFont font;
    
    // States where you can't go back
    private ArrayList<Integer> specialStates;
    
    
    @Override
    public abstract int getID();
    

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
    {
        // If backgrounds have not been loaded, load them
        if (Globals.backgrounds.size() != Globals.BG_COUNT)
        {
            // Add every background to the array        
            for (int i = 1; i <= Globals.BG_COUNT; i++)
            {

                    String imgPath = "res/backgrounds/bg" + i + ".png";
                    BigImage curBG = new BigImage(imgPath);
                    curBG = (BigImage) curBG.getScaledCopy(Globals.screenW, Globals.screenH);
                    Globals.backgrounds.add(curBG);
            }
        }
        
       // Get random background
       Random rng = new Random();
       int ranIndex = rng.nextInt(Globals.BG_COUNT-1);
       bg = Globals.backgrounds.get(ranIndex);
        
       // Initialise button manager
       buttonMan = initButtonManager();
       
       // Create parameters for buttons
       hParams = initHeaderParams();
       lParams = initLineParams();
       
       // Creates labels for buttons
       bLabels = new ArrayList<>();
       bLabels.addAll(initButtonLabels());
       
       // Use info to create buttons
       buttonMan.createButtonGrid(hParams, lParams, bLabels);
               
       // Initialise cursor follower
       cursorCircle = new Circle(0, 0, 10);

       // Initialise image and replace cursor
       if (Globals.cursor == null) // Only do once
       {
           Globals.cursor = new Image("res/misc/cursor.png");
           Globals.cursor = Globals.cursor.getScaledCopy(0.75f);
       }
       gc.setMouseCursor(Globals.cursor,0,0);
       
       // Initialise font
       font = FontServer.getFont("Segoe UI-Italic-22");
       
       // States where you can't go back
       specialStates = new ArrayList<>();
       specialStates.add(Globals.states.get("MAINMENU"));
       specialStates.add(Globals.states.get("EXIT"));
       specialStates.add(Globals.states.get("GAMEOVER"));
       
       customInit();
    }
    
    /**
     * Setup button manager here
     */
    public abstract ButtonManager initButtonManager();
    
    /**
     * Input header parameters here
     */
    public abstract float[] initHeaderParams();
    
    /**
     * Input line parameters here
     */
    public abstract float[] initLineParams();

    /**
     * Input button labels here
     */
    public abstract ArrayList<String> initButtonLabels();
    
    /**
     * Add additional initialization tasks here
     */
    public void customInit()
    {
    }
    
    
    public float[] getHeaderParams() { return hParams; }
    
    public float[] getLineParams() { return lParams; }

    public ArrayList<String> getButtonLabels()
    {
        return bLabels;
    }
    
    public ButtonManager getButtonManager()
    {
        return buttonMan;
    }
    
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) 
    {
       customPreUpdate();
       
       // Get input
       Input input = gc.getInput();
       
       // Make circle follow cursor
       cursorCircle.setCenterX(input.getMouseX());
       cursorCircle.setCenterY(input.getMouseY());
       
       // Act on button presses
       boolean mouseClicked = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
       String labelClicked = buttonMan.getLabelClicked(cursorCircle);
       
       if (mouseClicked && (labelClicked.length() > 1) )
       {
         clickAction(sbg, labelClicked);
       }
       
       // Act on back button presses
       boolean escClicked = gc.getInput().isKeyDown(Input.KEY_ESCAPE);
       boolean middleClicked = gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);
       if ( escClicked || middleClicked)
       {
           // Unless there, go to main menu
           int curID = sbg.getCurrentStateID();
           int menuID = Globals.states.get("MAINMENU");
           if (curID != menuID)
           {
                           // Transition to that state
            sbg.enterState(
                    menuID, 
                    Globals.getLeave(),
                    Globals.getEnter()
            );
           }
           
       }
    }
    
    /**
     * Do custom pre updating here
     */
    public void customPreUpdate()
    {
    }
    
    /**
     * Customize the action taken for button presses
     */
    public void clickAction(StateBasedGame sbg, String label)
    {
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
    {
        // Draw background darkened
        if (isDarkened()) { g.drawImage(bg, 0, 0, Color.darkGray); }
        else { g.drawImage(bg, 0, 0); }
        
        // Draw buttons
        buttonMan.drawButtonGrid(g, getButtonCol());
        
        // On certain states, reveal back button 
        int curStateID = game.getCurrentStateID(); 
        if ( !specialStates.contains(curStateID) )
        {
            g.setColor(Color.white);
            font.drawString(10, Globals.screenH-40, "Press ESC or Right Click to go back");
        }
        
        
        customPostRender(g);
    }
    
    /**
     * True = Background will be darkened
     */
    public abstract boolean isDarkened();
    
    /**
     * Return the button color you want
     */
    public abstract Color getButtonCol();

    /**
     * Do custom post rendering here
     */
    public void customPostRender(Graphics g)
    {
    }
   
    
    
            
    
}
