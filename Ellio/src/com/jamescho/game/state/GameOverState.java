package com.jamescho.game.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;

public class GameOverState extends State {
	private int playerScore;
	private Font font;
	
	public GameOverState(int playerScore) {
		this.playerScore = playerScore;
		font = new Font("SansSerif", Font.BOLD, 50);
	}

	@Override
	public void init() {
		System.out.println("Entered GameOverState");
		
	}

	@Override
	public void update(float delta) {
		Resources.danceAnim.update(delta);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);
		g.setColor(Color.DARK_GRAY);
		g.setFont(font);
		g.drawString("GAME OVER", 257, 175);
		g.drawString("Distance: " + playerScore + "m", 240, 250);
		g.drawString("Press Enter", 262, 350);
		Resources.danceAnim.render(g, 80, 230, 80, 97);
		Resources.danceAnim.render(g, 650, 230, 80, 97);
	}

	@Override
	public void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
			setCurrentState(new MenuState());
		}
		
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
