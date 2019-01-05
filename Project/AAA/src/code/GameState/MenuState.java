
package code.GameState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import code.Manager.Content;
import code.Manager.GameStateManager;
import code.Manager.JukeBox;
import code.Manager.Keys;

/**
 * The main menu GameState.
 * @author David
 */
public class MenuState extends GameState {
	
	private BufferedImage bg;
	private BufferedImage diamond;
        private BufferedImage star;
	
	private int currentOption = 0;
	private String[] options = {
		"START",
		"QUIT"
	};
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
            
                //Initalise menu images
		bg = Content.MENUBG[0][0];
		diamond = Content.DIAMOND[0][0];
                star = Content.SPARKLE[0][3];
                star= star.getSubimage(0, 0, 9, 5);
                
                //Load menu sounds
		JukeBox.load("/res/SFX/collect.wav", "collect");
		JukeBox.load("/res/SFX/menuoption.wav", "menuoption");
	}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		
		//Draw background
                g.drawImage(bg, 0, 0, null);
                
                //Draw flashing stars
                int ranX = (int) (Math.random() * bg.getWidth());
                int ranY = (int) (Math.random() * bg.getHeight());
                
                 while(System.currentTimeMillis()%2 == 0){
                     g.drawImage(star, ranX, ranY, null);
                 }

                // Draw options
		Content.drawString(g, options[0], 44, 90);
		Content.drawString(g, options[1], 48, 100);
		
                // Draw option selector
		if(currentOption == 0) g.drawImage(diamond, 25, 86, null);
		else if(currentOption == 1) g.drawImage(diamond, 25, 96, null);
		
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.DOWN) && currentOption < options.length - 1) {
			JukeBox.play("menuoption");
			currentOption++;
		}
		if(Keys.isPressed(Keys.UP) && currentOption > 0) {
			JukeBox.play("menuoption");
			currentOption--;
		}
		if(Keys.isPressed(Keys.ENTER)) {
			JukeBox.play("collect");
			selectOption();
		}
	}
	
	private void selectOption() {
		if(currentOption == 0) {
			gsm.setState(GameStateManager.PLAY);
		}
		if(currentOption == 1) {
			System.exit(0);
		}
	}
	
}
