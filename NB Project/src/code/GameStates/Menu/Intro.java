package code.GameStates.Menu;

import code.Globals;
import code.Utility.FontBank;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

/**
 *
 * @author David
 */
public class Intro extends BasicGameState
{
    // Background
    private Image introLogo;
    
    // Font
    private FontBank fb;  //must be here otherwise logo wont show
    private TrueTypeFont font;

    // Transitions
    private Transition leave;
    private Transition enter;
    
   
    /**
     * Used to identify states
     * Used to switch to state
     */
    @Override
    public int getID() { return Globals.INTRO; }

    
     /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException 
    {
       // Initialise and adjust bg
       introLogo = new Image("res/misc/intro.png");
       introLogo = introLogo.getScaledCopy(Globals.screenW, Globals.screenH);
       
       // Initialise font
       fb = new FontBank();
       font = fb.getGameFont();
       
       // Initialise transitions
       leave = new FadeOutTransition();
       enter = new FadeInTransition();
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
       // Add all states
       sbg.addState(new About());
       sbg.addState(new Controls());
       sbg.addState(new Credits());
       sbg.addState(new GameOver());
       // Skip intro because already added
       sbg.addState(new MainMenu());
       sbg.addState(new Pause());
       sbg.addState(new Play());
       sbg.addState(new Settings());

       // Initialise resources
       sbg.init(gc);

       // Enter main menu
       sbg.enterState(Globals.MAIN_MENU, leave , enter); 
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
        // Draw intro/logo background
        g.drawImage(introLogo, 0, 0);
        
        // Write "loading"
        int Xpos = (Globals.screenW/2)-150;
        int Ypos = Globals.screenH - 100;
        font.drawString(Xpos, Ypos, "LOADING");
    }

    
    
    
    
    
}
