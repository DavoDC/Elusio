package code.Main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Game;


import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * The entry point of the game.
 * This class loads up a JFrame window and puts a GamePanel into it.
 * 
 * @author David
 */
public class MainGame extends BasicGame
{
    
    //Main method
    public static void main(String[] args) throws Exception 
    {
       //Make frame
	JFrame window = new JFrame("Alien Aztec Adventure");	
             
        // Adjust frame
        window.add(new GamePanel());
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        //Add icon
        String iconPath = "/res/Special/icon.gif";
         try 
         {
          Image image = ImageIO.read(code.Main.MainGame.class.getResourceAsStream(iconPath));
          window.setIconImage(image);
         } 
          catch (Exception e) 
         {
          System.err.println("Path was " + iconPath);    
         }
        
    }

    /**
     * Constructor from Slick Basic Game
     * @param title 
     */
    public MainGame(String title) {
        super(title);
    }

    
        
    // Abstract methods from Slick Basic Game
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        throw new UnsupportedOperationException("Not coded yet");
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        throw new UnsupportedOperationException("Not coded yet");
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        throw new UnsupportedOperationException("Not coded yet");
    }


    
    
	
}
