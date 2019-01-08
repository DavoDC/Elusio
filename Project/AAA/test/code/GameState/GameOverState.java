
package code.GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import code.Main.GamePanel;
import code.Manager.Content;
import code.Manager.Data;
import code.Manager.GameStateManager;
import code.Manager.JukeBox;
import code.Manager.Keys;

/**
 * Shows congratulations for finishing the game.
 * Gives you a rank based on how long it took you to beat the game.
 * 
 * @author David Charkey
 */
public class GameOverState extends GameState {
	
	private Color color;
	
	private int rank;
	private long ticks;
	
	public GameOverState(GameStateManager gsm) {
		super(gsm);
	}
	
        @Override
	public void init() {
		color = new Color(164, 198, 222);
		ticks = Data.getTime();
		if(ticks < 3600) rank = 1;
		else if(ticks < 5400) rank = 2;
		else if(ticks < 7200) rank = 3;
		else rank = 4;
	}
	
        @Override
	public void update() {}
	
        @Override
	public void draw(Graphics2D g) {
		
		g.setColor(color);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
		
		Content.drawString(g, "finish time", 20, 36);
		
		int minutes = (int) (ticks / 1800);
		int seconds = (int) ((ticks / 30) % 60);
		if(minutes < 10) {
			if(seconds < 10) Content.drawString(g, "0" + minutes + ":0" + seconds, 44, 48);
			else Content.drawString(g, "0" + minutes + ":" + seconds, 44, 48);
		}
		else {
			if(seconds < 10) Content.drawString(g, minutes + ":0" + seconds, 44, 48);
			else Content.drawString(g, minutes + ":" + seconds, 44, 48);
		}
		
		Content.drawString(g, "Rank", 48, 66);
                switch (rank) 
                {
                    case 1:
                        Content.drawString(g, "Sonic the Hedgedog", 20, 78);
                        break;
                    case 2:
                        Content.drawString(g, "Sprint Mage", 24, 78);
                        break;
                    case 3:
                        Content.drawString(g, "Relaaax", 32, 78);
                        break;
                    case 4:
                        Content.drawString(g, "Rigamortis Tortoise", 8, 78);
                        break;
                    default:
                        break;
                }

		Content.drawString(g, "Press any key", 12, 110);
		
	}
	
        @Override
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) {
			gsm.setState(GameStateManager.MENU);
			JukeBox.play("collect");
		}
	}
	
}