
package code.Utility;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;



/**
 * Makes rendering large amounts of text easier
 * 
 * Utilizes ButtonManger sneakily 
 * 
 * @author David
 */
public class InfoWriter 
{
    // Button Manager
    private ButtonManager bMan;
    
    // Font 
    private TrueTypeFont smallFont;
    
    // Header 
    private ArrayList<String> header;
    private float[] hParams;
    
    // Paragraph
    private ArrayList<String> paragraph;
    private float[] pParams;
    
    
    public InfoWriter()
    {
        // Create button manager
        bMan = new ButtonManager();
        
        // Load small font
        Font awtFont = new Font("Segoe UI", Font.PLAIN, 25);
        smallFont = new TrueTypeFont(awtFont, true);
        
        // Initialise ArrayLists
        header = new ArrayList<>();
        paragraph = new ArrayList<>();
        
        // Get needed info
        int screenW = code.MainGame.screenW;
        float startX = screenW/3;
        
        // Separate ArrayList into header and paragraph


        // Initialise header parameters
        // Order = startXpos, startYpos, width, height, Xspace, Yspace, colNo
        hParams = new float[] {startX, 100f, screenW, 50f, 0f, 0f, 1f};
        
        // Initalise paragraph parameters
        // Order = startXpos, startYpos, width, height, Xspace, Yspace, colNo
        pParams = new float[] {startX, 100f, screenW, 40f, 10f, 0f, 1f};
        
    }
    
    public void addText(ArrayList<String> text)
    {
        // Separate array into header and paragraph
        String curS = "";
        for (int i = 0; i < text.size(); i++)
        {
          // test
          System.out.println("Size:" + text.size() + "  Cur i: " + i);

          curS = text.get(i);
          if (i == 0) { header.add(curS); }
          else { paragraph.add(curS); }
        }
        
        // Initialise button manager
        bMan.createButtonGrid(hParams, header);
        bMan.createButtonGrid(pParams, paragraph);
    }
    
    /**
     * First string will be the header
     */
    public void writeParagraph(Graphics g)
    {
      // Draw header
      bMan.drawButtonGrid(g, Color.transparent, Color.black);
      
      // Make button manager use small font
      bMan.changeFont(smallFont);
      
      // Draw paragraphs
      bMan.drawButtonGrid(g, Color.transparent, Color.black);
        
    }
    
    

 
    

    
}
