package States;

import Main.Globals;
import Components.Buttons.Button;
import Components.Buttons.ButtonGrid;
import Components.Helpers.FontServer;
import Components.ScreenTemplates.InfoScreen;
import Components.ScreenTemplates.InterfaceScreen;

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
public class Exit extends InterfaceScreen
{

    // The absolute time of closing
    private long exitConstant = 3669 / 2;

    // The actual time of closing
    private long exitTime = 0;

    // The time left before closing
    private long timeLeft = exitConstant;

    // Percentage closed
    private int percentage = 0;

    // Text
    private String mainFontS = "Castellar-Bold-60";
    private String closeFontS = "Tahoma-Italic-16";
    private TrueTypeFont closeFont;
    private int closeX = (int) InfoScreen.lineX + 400;

    @Override
    public int getID()
    {
        return Globals.STATES.get("EXIT");
    }

    @Override
    public void customPostInit()
    {
        closeFont = FontServer.getFont(closeFontS);
    }

    @Override
    public ArrayList<Object> getButtonFeatures()
    {
        // Create AL
        ArrayList<Object> feats = new ArrayList<>();

        // Add to AL
        feats.add(getButtonLabels().size()); // Number of buttons
        feats.add(Globals.emptyImgRes); // Image Location
        feats.add(300); // startXpos
        feats.add(125); // startYpos 
        feats.add(350); // width
        feats.add(80); // height
        feats.add(0); // XSpacing
        feats.add((int) Globals.screenH / 25); // YSpacing //always fits
        feats.add(1); // NumberofColumns
        feats.add(mainFontS); // FontString

        return feats;
    }

    @Override
    public ArrayList<String> getButtonLabels()
    {
        //Create AL
        ArrayList<String> text = new ArrayList<>();

        // Add to text
        text.add("THANKYOU");
        text.add("FOR");
        text.add("PLAYING");

        return text;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
    {
        // Initialise time of closing
        // Must be initialised here
        if (exitTime == 0) // Only do once
        {
            exitTime = Globals.agc.getTime() + exitConstant;
        }

        // Get time right now
        long now = Globals.agc.getTime();

        // Calculate time left
        timeLeft = exitTime - now;

        // Calculate percentage
        float perF = (float) timeLeft / (float) exitConstant;
        perF = Math.round(perF * 100);
        percentage = 100 - (int) perF;

        // Exit after some time has elapsed
        if (percentage >= 100)
        {
            Globals.agc.exit();
        }

    }

    @Override
    public void customPostRender(Graphics g)
    {
        // Calculate position of text
        int x = closeX;
        int y = Globals.screenH - 75;

        // Make closing strings
        String close1 = "The game will close shortly";
        String close2 = percentage + "% Closed";

        // Draw closing status
        g.setColor(Color.white);
        closeFont.drawString(x, y, close1);
        closeFont.drawString(x, y + 25, close2);
    }

    @Override
    public boolean isDarkened()
    {
        return true;
    }

}
