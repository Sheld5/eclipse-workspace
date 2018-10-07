package com.jamescho.game.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;

public class MenuState extends State {
	private int currentSelection = 0;
	private int currentDifficulty = 0;

	@Override
	public void init() {
		System.out.println("Entered MenuState");
	}

	@Override
	public void update(float delta) {
		// Do nothing
	}

	@Override
	public void render(Graphics g) {
		if (currentDifficulty == 0) {
			g.drawImage(Resources.welcomeEasy, 0, 0, null);
		} else if (currentDifficulty == 1) {
			g.drawImage(Resources.welcomeHard, 0, 0, null);
		} else if (currentDifficulty == 2) {
			g.drawImage(Resources.welcomeNightmare, 0, 0, null);
		}
		if (currentSelection == 0) {
			g.drawImage(Resources.selector, 335, 241, null);
		} else if (currentSelection == 1) {
			g.drawImage(Resources.selector, 335, 291, null);
		} else if (currentSelection == 2) {
			g.drawImage(Resources.selector, 335, 341, null);
		}
	}

	@Override
	public void onClick(MouseEvent e) {
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER) {
			if (currentSelection == 0) {
				if (currentDifficulty == 0) {
					setCurrentState(new PlayStateEasy());
				} else if (currentDifficulty == 1) {
					setCurrentState(new PlayStateHard());
				} else if (currentDifficulty == 2) {
					setCurrentState(new PlayStateNightmare());
				}
			} else if (currentSelection == 1) {
				if (currentDifficulty < 2) {
					currentDifficulty++;
				} else {
					currentDifficulty = 0;
				}
			} else if (currentSelection == 2) {
				GameMain.sGame.exit();
			}
		} else if (key == KeyEvent.VK_UP && currentSelection > 0) {
			currentSelection--;
		} else if (key == KeyEvent.VK_DOWN && currentSelection < 2) {
			currentSelection++;
		}
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		
	}

}
