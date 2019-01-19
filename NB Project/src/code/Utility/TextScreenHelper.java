
package code.Utility;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;



/**
 * Makes filling a screen with text easy
 * 
 * Utilizes ButtonManger sneakily 
 * 
 * @author David
 */
public class TextScreenHelper 
{
    
    // Fonts
    private FontBank fontbank;
    
    // Headers
    private ButtonManager headerMan;
    private ArrayList<String> header;
    private float[] hParams;
    
    // Paragraphs
    private ButtonManager paraMan;
    private ArrayList<String> paragraph;
    private float[] pParams;
    
    
    public TextScreenHelper()
    {
        // Load fonts
        fontbank = new FontBank();
        
        // Create button managers
        headerMan = new ButtonManager("gamefont");
        paraMan = new ButtonManager("medium");
       
        // Initialise ArrayLists
        header = new ArrayList<>();
        paragraph = new ArrayList<>();
        
        // Get needed info
        int screenW = code.MainGame.screenW;
        float startX = screenW/4 - 200;

        // Initialise header parameters
        // Order = startXpos, startYpos, width, height, Xspace, Yspace, colNo
        hParams = new float[] {startX, 100f, screenW, 50f, 0f, 0f, 1f};
        
        // Initalise paragraph parameters
        // Order = startXpos, startYpos, width, height, Xspace, Yspace, colNo
        pParams = new float[] {startX, 160f, screenW, 40f, 15f, 0f, 1f};
        
    }
    
   

    public void addText(ArrayList<String> text)
    {
        // Separate AL into header and paragraph
        String curS = "";
        for (int i = 0; i < text.size(); i++)
        {
          curS = text.get(i);
          if (i == 0) { header.add(curS); }
          else { paragraph.add(curS); }
        }
        
        // Initialise button managers
        headerMan.createButtonGrid(hParams, header);
        paraMan.createButtonGrid(pParams, paragraph);
    }
    
    
    
    /**
     * First string will be the header
     */
    public void writeParagraph(Graphics g)
    {
      // Draw header
      headerMan.drawButtonGrid(g, Color.transparent, Color.black);
      
      // Draw paragraphs
      paraMan.drawButtonGrid(g, Color.transparent, Color.black);
        
    }
    
    

    
}
