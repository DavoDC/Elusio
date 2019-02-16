package Main;

import States.Loading;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Entry extends StateBasedGame
{

    public Entry()
    {
        super(Globals.gameTitle);
    }

    public static void main(String[] args)
    {
        try
        {
            // Create agc
            Globals.agc = new AppGameContainer(new Entry());

            // Adjust AGC
            Globals.screenW = Globals.agc.getScreenWidth();
            Globals.screenH = Globals.agc.getScreenHeight();
            Globals.agc.setDisplayMode(Globals.screenW, Globals.screenH, true);
            Globals.agc.setTargetFrameRate(60);
            Globals.agc.setAlwaysRender(true);
            Globals.agc.setSmoothDeltas(true);
            Globals.agc.setShowFPS(false);

            // Start AGC
            Globals.agc.start();

        }
        catch (SlickException e)
        {
            System.err.println("Main method error");
        }
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException
    {
        addState(new Loading());
    }

}
