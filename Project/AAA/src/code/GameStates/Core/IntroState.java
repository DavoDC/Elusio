package code.GameStates.Core;



import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

/**
 *
 * @author David
 */
public class IntroState extends BasicGameState
{
    // Background
    private Image introLogo;
    
    // Time fields
    private int introTime = 3669;
    private int elapsedTime = 0;
    
    // Transitions
    private Transition leave;
    private Transition enter;
    
   
    /**
     * Used to identify states
     * Used to switch to state
     */
    @Override
    public int getID() { return 1; }

    
     /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException 
    {
       introLogo = new Image("res/misc/intro.png");
       introLogo = code.Main.MainGame.adjustImage(introLogo);
       introTime = 3669;
       elapsedTime = 0;
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
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException 
    {
       // Update time elapsed
       elapsedTime += delta;
       
       // Check conditions
       Boolean timeGone = elapsedTime > introTime;
       Boolean rightClicked = gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON);
       
       // Enter menu if a condition is satisfied
       if (timeGone || rightClicked) { game.enterState(2, leave , enter); }

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
    }

    
    
    
    
    
}
