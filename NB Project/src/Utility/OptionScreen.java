package Utility;

import Main.Globals;
import java.util.ArrayList;

import org.newdawn.slick.BigImage;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author David
 */
public abstract class OptionScreen extends BasicGameState
{
    // Background image
    private BigImage menuBG;
    
    // Button Manager
    private ButtonManager buttonMan;
    private float[] buttonParams;
    private ArrayList<String> buttonLabels;
    
    // Cursor components
    private Circle cursorCircle;
    private Image cursor;
    
    @Override
    public abstract int getID();

    
    /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     * @param gc
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
       
       // Get random menu background and adjust
       menuBG = (new BGBank()).getRandomBG();
       
       // Initialise button manager
       buttonMan = new ButtonManager("gamefont");
       
       // Create parameters for buttons
       buttonParams = initButtonParams();
       
       // Creates labels for buttons
       buttonLabels = new ArrayList<>();
       buttonLabels.addAll(initButtonLabels());
       
       // Use info to create buttons
       buttonMan.createButtonGrid(buttonParams, buttonLabels);
               
       // Initialise cursor follower
       cursorCircle = new Circle(0, 0, 10);

       // Modify cursor
       cursor = new Image("res/misc/cursor.png");
       cursor = cursor.getScaledCopy(0.75f);
       gc.setMouseCursor(cursor,0,0); 
       
    }
    
    
    /**
     * Input button parameters here
     */
    public abstract float[] initButtonParams();

    /**
     * Input button labels here
     */
    public abstract ArrayList<String> initButtonLabels();
    
  
    public float[] getButtonParams()
    {
        return buttonParams;
    }

    public ArrayList<String> getButtonLabels()
    {
        return buttonLabels;
    }
    

    public ButtonManager getButtonManager()
    {
        return buttonMan;
    }
    
    
    /**
     * The method is called each game loop to cause your game to update it's logic. 
     * This is where you should make objects move.
     * This is also where you should check input and change the state of the game.
     * @param gc Holds the game
     * @param game
     * @param delta Amount of time since last update
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
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
       
       if (mouseClicked)
       {
         clickAction(sbg, labelClicked);
       }
       
       // Act on back button presses
       boolean escClicked = gc.getInput().isKeyDown(Input.KEY_ESCAPE);
       boolean middleClicked = gc.getInput().isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON);
       if ( escClicked || middleClicked)
       {
           // Go to MainMenu if not already there
           int curID = sbg.getCurrentStateID();
           int menuID = Globals.states.get("MAINMENU");
           if (curID != menuID)
           {
               sbg.enterState(menuID);
           }
           
       }
      
    }

     

    /**
     * Gets called before update
     * Created for possible override
     */
    public void customPreUpdate()
    {
    }
    
    /**
     * Actions that occurs when a button is clicked
     * @param label
     */
    public abstract void clickAction(StateBasedGame sbg, String label);
    
    
    
    /**
     * This method should be used to draw to the screen. 
     * All of your game's rendering should take place in this method (or via calls)
     * It is called constantly. Items are constantly redrawn
     * @param gc
     * @param game
     * @param g
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g)
    {
        
        // Draw background
        g.drawImage(menuBG, 0, 0);
        
        // Draw buttons
        buttonMan.drawButtonGrid(g, Color.black);
        
    }
    
  
}
