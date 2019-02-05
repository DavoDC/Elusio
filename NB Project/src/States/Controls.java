package States;

import Main.Globals;
import Utility.UI.Button;
import Utility.UI.InfoScreen;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Displays the game's controls
 *
 * @author David
 */
public class Controls extends InfoScreen
{

    // Res folder
    private final String folder = "res/ui/hud/";

    // Parameters
    private final float SCALE = 0.45f;
    private final int SHIFT = 540;

    // Images
    private Image menu;
    private Image inv;
    private Image hint;

    // Positions
    private int menuX;
    private int menuY;
    private int invX;
    private int invY;
    private int hintX;
    private int hintY;

    @Override
    public int getID()
    {
        return Globals.STATES.get("CONTROLS");
    }

    @Override
    public void customPostInit()
    {
        super.getButtonGrid().makeHeader();

        try
        {

            menu = new Image(folder + "menu.png");
            menu = menu.getScaledCopy(SCALE);

            inv = new Image(folder + "inv.png");
            inv = inv.getScaledCopy(SCALE);

            hint = new Image(folder + "hint.png");
            hint = hint.getScaledCopy(SCALE);

        } catch (Exception e)
        {
            System.err.println("Image load issue");
        }

        Button menuRef = super.getButtonGrid().getButtonByLabel("MAINMENU");
        menuX = menuRef.getX() + SHIFT + 145;
        menuY = menuRef.getY();

        Button invRef = super.getButtonGrid().getButtonByLabel("INV");
        invX = invRef.getX() + SHIFT + 80;
        invY = invRef.getY();

        Button hintRef = super.getButtonGrid().getButtonByLabel("HINT");
        hintX = hintRef.getX() + SHIFT;
        hintY = hintRef.getY();

    }

    @Override
    public ArrayList<String> getButtonLabels()
    {
        //Create AL
        ArrayList<String> text = new ArrayList<>();

        // Add to text
        text.add("CONTROLS");
        text.add("MOVEMENT = Arrow Keys");
        text.add("GRAB ITEM = G");
        text.add("BACK TO MAINMENU = Esc or");
        text.add("SHOW HINT = H or");
        text.add("SHOW INVENTORY = I or");

        return text;
    }

    @Override
    public void customPostRender(Graphics g)
    {
        // Draw hint icon
        g.drawImage(hint, hintX, hintY);

        // Draw inv icon
        g.drawImage(inv, invX, invY);

        // Draw menu icon
        g.drawImage(menu, menuX, menuY);
    }

}
