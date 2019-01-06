package code.Main;


import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


/**
 * The entry point of the game.
 * This class loads up a JFrame window and puts a GamePanel into it.
 * 
 * @author David Charkey
 */
public class MainGame 
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

    
    
}
