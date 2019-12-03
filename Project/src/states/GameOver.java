package states;

import java.util.ArrayList;

import main.Globals;
import components.helpers.FontServer;
import components.screentemps.InfoScreen;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

/**
 * Provides a screen for game-over situations
 *
 * @author David
 */
public class GameOver extends InfoScreen {

    // Font 
    private TrueTypeFont lineFont;

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public int getID() {
        return Globals.STATES.get("GAMEOVER");
    }

    /**
     * Do custom post initialization
     */
    @Override
    public void customPostInit() {
        lineFont = FontServer.getFont(getLineFontString());
    }

    /**
     * Set button labels
     *
     * @return
     */
    @Override
    public ArrayList<String> getButtonLabels() {
        //Create AL
        ArrayList<String> text = new ArrayList<>();

        // Add to text
        text.add("GAMEOVER");

        return text;
    }

    /**
     * Do custom post rendering
     *
     * @param g
     */
    @Override
    public void customPostRender(Graphics g) {

        // Show status
        g.setColor(Color.white);
        lineFont.drawString(
                InfoScreen.lineX + 200,
                200f,
                "Some text here"
        );
    }

    /**
     * Set darkened option
     *
     * @return
     */
    @Override
    public boolean isDarkened() {
        return true;
    }

}
