package code.GameStates.Core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author David
 */
public class PauseState extends BasicGameState
{

    @Override
    /**
     * Used to identify states
     * Used to switch to state
     */
    public int getID() {
        //id
        return 4;
    }

    @Override
     /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     */
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
       //tba
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
        g.drawString("PAUSED", 400, 300);
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
       //tbc
    }
    
}
