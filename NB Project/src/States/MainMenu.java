package States;

import Main.Globals;
import Components.ScreenTemplates.InterfaceScreen;
import Components.Helpers.DelayWriter;
import Components.Buttons.Button;
import Components.Helpers.FontServer;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;

/**
 *
 * @author David
 */
public class MainMenu extends InterfaceScreen
{

    // Title fields
    private DelayWriter tw;
    private final String titleFontS = "gamefont-plain-70";
    private TrueTypeFont titleFont;

    // Menu fields
    private final String menuFont = "gamefont-plain-50";

    @Override
    public int getID()
    {
        return Globals.STATES.get("MAINMENU");
    }

    @Override
    public void customPostInit()
    {
        tw = new DelayWriter(70);
        tw.setText(Globals.gameTitle);
        titleFont = FontServer.getFont(titleFontS);

        super.getButtonGrid().applyActions(new ComponentListener()
        {
            @Override
            public void componentActivated(AbstractComponent source)
            {
                // Get label
                String label = ((Button) source).getLabel();

                // Figure out next state ID
                int newStateID = Globals.STATES.get(label);

                // Transition to that state
                Globals.SBG.enterState(
                        newStateID,
                        Globals.getLeave(),
                        Globals.getEnter());

            }
        });

    }

    @Override
    public ArrayList<Object> getButtonFeatures()
    {
        // Create AL
        ArrayList<Object> feats = new ArrayList<>();

        // Add to AL
        feats.add(getButtonLabels().size()); // Number of buttons
        feats.add("res/ui/general.png"); // Image Location
        feats.add(300); // startXpos
        feats.add(150); // startYpos 
        feats.add(350); // width
        feats.add(55); // height
        feats.add(0); // XSpacing
        feats.add((int) Globals.screenH / 25); // YSpacing //always fits
        feats.add(1); // NumberofColumns
        feats.add(menuFont); // FontString

        return feats;
    }

    @Override
    public ArrayList<String> getButtonLabels()
    {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("PLAY");
        labels.add("CONTROLS");
        labels.add("SETTINGS");
        labels.add("CREDITS");
        labels.add("ABOUT");
        labels.add("EXIT");

        return labels;
    }

    @Override
    public void customPreUpdate()
    {
        // Remove offset
        Globals.agc.getInput().setOffset(0, 0);

        // If paused, change "PLAY" to "CONTINUE"
        if (Globals.hasBeenPaused)
        {
            super.getButtonGrid().replaceButtonLabel("PLAY", "CONTINUE");

            int pos = Globals.STATES.get("PLAY");
            Globals.STATES.put("CONTINUE", pos);

        }

        // Update timewriter
        tw.update();

    }

    @Override
    public void customPostRender(Graphics g)
    {
        // Draw title
        g.setColor(Color.white);
        tw.drawText(titleFont, 200, 50);
    }

    @Override
    public boolean isDarkened()
    {
        return true;
    }

}
