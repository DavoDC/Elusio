package code;

import code.GameStates.Menu.Intro;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainGame extends StateBasedGame 
{

    
	public MainGame() 
        {
		super("Alien Aztec Adventure");
	}
        

	public static void main(String[] args) 
        {
            try 
            {
                // Create agc
                Globals.agc = new AppGameContainer(new MainGame());

                // Adjust AGC
                Globals.screenW = Globals.agc.getScreenWidth();
                Globals.screenH = Globals.agc.getScreenHeight();
                Globals.agc.setDisplayMode(Globals.screenW, Globals.screenH, true);
                Globals.agc.setAlwaysRender(true);
                Globals.agc.setShowFPS(true);

                // Start AGC
                Globals.agc.start();

            }
            catch(SlickException se) 
            {
                se.printStackTrace();
            }
	}
        
        

	@Override
	public void initStatesList(GameContainer gc) throws SlickException 
        {
		addState(new Intro());
	}
       
        
        
        
        
        
//        public void drawSpriteExample()
//        {
//            // SpriteSheet ss = new SpriteSheet("image", 16, 16); 
//            for(int i = 0; i != 6; i++)
//            {
//               ss.getSprite(i, 0).draw(50, i*90, 5);
//               ss.getSprite(i, 1).draw(200, i*90, 5);
//               ss.getSprite(0, 1).draw(300, i*80, 5);
//            }
//        }
          
          
          
          
          
}
