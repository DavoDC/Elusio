package code.GameStates.Menu;

import code.Globals;
import code.Utility.BGBank;
import code.Utility.ButtonManager;
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
import org.newdawn.slick.state.transition.*;

/**
 *
 * @author David
 */
public class MainMenu extends BasicGameState
{
    // Background image
    private BigImage menuBG;
    
    // Cursor components
    private Circle cursorCircle;
    private Image cursor;
    
    // Button Manager
    private ButtonManager buttonMan;
   
    
    /**
     * Used to identify states
     * Used to switch to state
     * @return stateID
     */
    @Override
    public int getID() { return Globals.MAIN_MENU; }

    
    
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
       float[] parameters = {
           300, // start X pos
           200, // start Y pos
           350, // Width
           50,  // Height
           0, //Xspace
           60, //Yspace
           1 //colNo
           };
       
       // Creates labels for buttons
       ArrayList<String> buttonLabels = new ArrayList<>();
       buttonLabels.add("PLAY");
       buttonLabels.add("CONTROLS");
       buttonLabels.add("SETTINGS");
       buttonLabels.add("CREDITS");
       buttonLabels.add("ABOUT");
       buttonLabels.add("EXIT");
       
       // Use info to create buttons
       buttonMan.createButtonGrid(parameters, buttonLabels);
               
       // Initialise cursor follower
       cursorCircle = new Circle(0, 0, 10);

       // Modify cursor
       cursor = new Image("res/misc/cursor.png");
       cursor = cursor.getScaledCopy(0.75f);
       gc.setMouseCursor(cursor,0,0); 
     
       
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
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException 
    {
       // Get input
       Input input = gc.getInput();
       
       // Make circle follow cursor
       cursorCircle.setCenterX(input.getMouseX());
       cursorCircle.setCenterY(input.getMouseY());
       
       // Conditions/Events
       boolean mouseClicked = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
       
       // Get label of clicked button
       String labelClicked = buttonMan.getLabelClicked(cursorCircle);
      
       // Make transitions available
       Transition leaveF = new FadeOutTransition();
       Transition enterF = new FadeInTransition();
       Transition leaveC = new CombinedTransition();
       Transition enterC = new CombinedTransition();
       
       // Check conditions
       if (mouseClicked)
       {
           switch(labelClicked)
           {
               // Clicked first button = play
               case "PLAY" : game.enterState(Globals.PLAY, leaveC, enterC);
               break;
               
               // Clicked on controls
               case "CONTROLS" : game.enterState(Globals.CONTROLS, leaveC, enterC);  
               break;
               
               // Clicked on credits
               case "CREDITS" : game.enterState(Globals.CREDITS, leaveC, enterC); 
               break;
               
               // Clicked on settings
               case "SETTINGS" : game.enterState(Globals.SETTINGS, leaveC, enterC); 
               break;
               
               // Clicked on about
               case "ABOUT" : game.enterState(Globals.ABOUT, leaveC, enterC);  
               break;
               
               // Click on exit
               case "EXIT" : System.exit(0);
               break;
       }
       
    }
}
    
    
    
    
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
