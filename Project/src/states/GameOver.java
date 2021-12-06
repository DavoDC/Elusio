package states;

import java.util.ArrayList;

import main.Globals;
import components.helpers.FontServer;
import components.screentemps.InfoScreen;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

/**
 * Shown when a mini-game is failed
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
