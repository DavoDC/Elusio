package code.GameStates.Core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.sun.glass.ui.Cursor;
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

    private Image menuBg;
    private Rectangle button;
    private Circle cursorCircle;
    private Image cursor;
    
    @Override
    /**
     * Used to identify states
     * Used to switch to state
     */
    public int getID() { return 2; }

    
    @Override
     /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     */
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
       menuBg = new Image("res/States/menu.png");
       
       button = new Rectangle(400, 400, 100, 30);
       
       cursorCircle = new Circle(0, 0, 10);
       
       
       cursor = new Image("res/Special/cursor.png");
       cursor = cursor.getScaledCopy(0.75f);
       gc.setMouseCursor(cursor,0,0); 
       
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
      
        
        g.drawImage(menuBg, 0, 0);

        
        
        g.setColor(Color.blue);
        g.draw(button);
        
        g.setColor(Color.white);
        g.drawString("Play", button.getX()+10, button.getY()+3);
        

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

       Input input = container.getInput();
       
       cursorCircle.setCenterX(input.getMouseX());
       cursorCircle.setCenterY(input.getMouseY());
       
       Boolean onButton = cursorCircle.intersects(button);
       Boolean mouseClicked = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
       
       
       Transition leave = new FadeOutTransition();
       Transition enter = new FadeInTransition();
       
       if (onButton && mouseClicked)
       {
         game.enterState(3, leave, enter);
       }
       
    }
    
}
