/*
 * The entry point of the game.
 * This class loads up a JFrame window and puts a GamePanel into it.
 */

package code.Main;


import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game 
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
          URL url = Game.class.getResource(iconPath);
          Image image = ImageIO.read(url);
          window.setIconImage(image);
         } 
          catch (Exception e) 
         {
          throw new Exception("Path was " + iconPath, e);    
         }
        
    }
    
	
}
