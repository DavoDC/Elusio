package Utility;


import Main.Globals;
import java.util.ArrayList;
import org.newdawn.slick.Color;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author David
 */
public abstract class TextScreen extends BasicGameState
{
    // Helps to display text
    private TextScreenHelper textscreen;
    
    // Stores text
    private ArrayList<String> text;
    
    // Background
    private Image bg;
    
    

    @Override
    public abstract int getID();

    
     /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     * @param container
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException 
    {
       // Initialise objects
       textscreen = new TextScreenHelper();
       text = new ArrayList<>();
       
       bg = (new BGBank()).getRandomBG();
       
       // Add text to Info Writer
       textscreen.addText(initTextList());
    }
    
     /**
     * The method is called each game loop to cause your game to update it's logic. 
     * This is where you should make objects move.
     * This is also where you should check input and change the state of the game.
     * @param gc
     * @param game
     * @param delta Amount of time since last update
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException 
    {
       // Enter main menu when back pushed
       Boolean escClicked = gc.getInput().isKeyDown(Input.KEY_ESCAPE);
       Boolean middleClicked = gc.getInput().isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON);
       
       if (escClicked || middleClicked)
       {
       game.enterState(Globals.states.get("MAINMENU"));
       }
       
    }
    
        /**
     * This method should be used to draw to the screen. 
     * All of your game's rendering should take place in this method (or via calls)
     * It is called constantly. Items are constantly redrawn
     * @param gc
     * @param game
     * @param g
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException 
    {
        // Draw bg
        bg.draw(0, 0, Color.darkGray);
        
        // Draw text on top
        textscreen.writeParagraph(g);
        
        // Reveal back button at top left
        g.setColor(Color.white);
        g.drawString("BACK = MIDMOUSE/ESC", 10, 35);
    }
    
    
    
    /**
     * Input text here
     * First entry is the header
     * @return 
     */
    public abstract ArrayList<String> initTextList();
    
    
    
}
