package code.GameStates.Core;

import code.Utility.BGBank;
import code.Utility.ShapeManager;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

/**
 *
 * @author David
 */
public class MenuState extends BasicGameState
{
    // Background image
    private Image menuBg;
    
    // Cursor components
    private Circle cursorCircle;
    private Image cursor;
    
    // Buttons
    private ArrayList<Rectangle> buttons;
    private ArrayList<String> buttonNames;
    
    // Records whether menu has been drawn yet
    private boolean drawn;
    
    /**
     * Used to identify states
     * Used to switch to state
     * @return stateID
     */
    @Override
    public int getID() { return 2; }

    
    
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
       menuBg = (new BGBank()).getRandomBG();
          
       // Initialise buttons
       // Parameters = buttonNo, startXpos, startYpos, width, height, 
       float[] parameters = {
           3, //number of buttons
           300, // start X pos
           300, // start Y pos
           300, // Width
           50,  // Height
           0, //Xspace
           80, //Yspace
           1 //colNo
           };
       buttons = (new ShapeManager()).createRectangleGrid(parameters);
       buttonNames = new ArrayList<>();
       buttonNames.add("PLAY");
       buttonNames.add("CREDITS");
       buttonNames.add("EXIT");
               
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
       boolean playClicked = cursorCircle.intersects(buttons.get(0));
       boolean creditsClicked = cursorCircle.intersects(buttons.get(1));
       boolean exitClicked = cursorCircle.intersects(buttons.get(2));
      
       // Make transitions available
       Transition leave = new FadeOutTransition();
       Transition enter = new FadeInTransition();
       
       // Check conditions
       if (mouseClicked)
       {
           if (playClicked)
           {
            game.enterState(3, leave, enter);  
           }
           else if(creditsClicked)
           {
            //game.enterState(?, leave, enter); 
           }
           else if(exitClicked)
           {
            System.exit(0);
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
        g.drawImage(menuBg, 0, 0);

        //Draw buttons and add text overlay
        for (int i = 0; i < buttons.size(); i++)
        {
            // Retrieve info
            Rectangle curRect = buttons.get(i);
            String curText = buttonNames.get(i);
            
            // Draw
            g.setColor(Color.black);
            g.draw(curRect);
            g.fill(curRect);
            
            g.setColor(Color.white);
            float X = curRect.getX() + 10;
            float Y = curRect.getY() - 3;
        
            try 
            { 
                code.Main.MainGame.getGameFont(60f).drawString(X, Y, curText); 
            }
            catch (Exception e) 
            {
                System.out.println("Issue with game font");
            }
        }
     
    }
        

    
    
    
   
    
    
    
    
   
    
}
