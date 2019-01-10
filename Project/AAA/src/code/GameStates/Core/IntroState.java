package code.GameStates.Core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Image introLogo;
    private final long startTime = System.currentTimeMillis();
    private final long introTime = 3969;
    
    @Override
    /**
     * Used to identify states
     * Used to switch to state
     */
    public int getID() {
        //id
        return 1;
    }

    @Override
     /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     */
    public void init(GameContainer container, StateBasedGame game) throws SlickException 
    {
      introLogo = new Image("res/States/intro.png");
    }
    
   
    /**
     * This method should be used to draw to the screen. 
     * All of your game's rendering should take place in this method (or via calls)
     * It is called constantly. Items are constantly redrawn
     * @param gc
     * @param g
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawString("INTRO", 400, 300);
        
        //correct for screensize
        introLogo = code.Main.MainGame.adjustImage(introLogo);
        
        g.drawImage(introLogo, 0, 0);
        
    }

    
     /**
     * The method is called each game loop to cause your game to update it's logic. 
     * This is where you should make objects move.
     * This is also where you should check input and change the state of the game.
     * @param gc Holds the game
     * @param delta Amount of time since last update
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
      
        
       long timeElapsed = System.currentTimeMillis() - startTime;
       Boolean timeGone = timeElapsed > introTime;
       
       Boolean rightClicked =  container.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON);
       
       Transition leave = new FadeOutTransition();
       Transition enter = new FadeInTransition();
       
       if (timeGone || rightClicked) { game.enterState(2, leave , enter); }
 
        
    }
    
}
