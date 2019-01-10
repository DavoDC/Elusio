package code.GameStates.Core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.InputStream;
import java.awt.Font;
import org.newdawn.slick.TrueTypeFont;

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
import org.newdawn.slick.util.ResourceLoader;

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
       
        // Choose random menu background
        int ranH = (int) (Math.random() * 100);
        String menuS = "";
        
        if (ranH <= 25)
        {
            menuS += "menu1";
        }
        else if (ranH <= 50)
        {
             menuS += "menu2";
        }
        else if (ranH <= 75)
        {
             menuS += "menu3";
        }
        else 
        {
             menuS += "menu4";
        }
        menuBg = new Image("res/States/" + menuS + ".png");
        
        
       // Initialise buttons
       button = new Rectangle(400, 400, 100, 30);
       
       // Initialise cursor follower
       cursorCircle = new Circle(0, 0, 10);

       // Modify cursor
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
      
        //correct for screensize
        menuBg= code.Main.MainGame.adjustImage(menuBg);
        
        g.drawImage(menuBg, 0, 0);

        // Draw buttons
            // Add to arraylist
            
            // D
        ///draw buttons
        //  for (JButton curButton : buttons)
        //{
        //    counter++;
        // curxpos += (bW + xspacing);
        //    if ( (counter%columns) == 0)
         //   {
          //      curxpos = xpos;
          //      curypos += (bH + yspacing);
           // }
        g.setColor(Color.blue);
        g.draw(button);
        g.fill(button);
        
        
        
        
        g.setColor(Color.white);
        
        float X = button.getX() + 3;
        float Y = button.getY() - 2;
        try
        {
            code.Main.MainGame.getGameFont(35f).drawString(X, Y, "play");
        }
        catch (Exception e)
        {
        }
        
        
       
        
      
        	
                  
 
	
            
     
             
      
        
        
        
       
        

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
