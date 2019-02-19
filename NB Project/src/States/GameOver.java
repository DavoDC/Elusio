package States;

import Main.Globals;
import Components.Helpers.FontServer;
import Components.ScreenTemplates.InfoScreen;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Shown just before exit
 *
 * @author David
 */
public class GameOver extends InfoScreen
{

    // Font 
    private TrueTypeFont lineFont;

    @Override
    public int getID()
    {
        return Globals.STATES.get("GAMEOVER");
    }

    @Override
    public void customPostInit()
    {
        lineFont = FontServer.getFont(getLineFontString());
    }

    @Override
    public ArrayList<String> getButtonLabels()
    {
        //Create AL
        ArrayList<String> text = new ArrayList<>();

        // Add to text
        text.add("GAMEOVER");

        return text;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
    {

    }

    @Override
    public void customPostRender(Graphics g)
    {
        // Show status
        g.setColor(Color.white);
        lineFont.drawString(
                InfoScreen.lineX + 200,
                200f,
                "Some text here"
        );
    }

    @Override
    public boolean isDarkened()
    {
        return true;
    }

}
