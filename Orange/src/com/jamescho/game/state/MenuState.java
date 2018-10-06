package com.jamescho.game.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jamescho.game.main.Resources;

public class MenuState extends State {

	@Override
	public void init() {
		System.out.println("Entered MenuState");
	}

	@Override
	public void update() {
		// Do nothing
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Resources.menuImg, 0, 0, null);
	}

	@Override
	public void onClick(MouseEvent e) {
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			setCurrentState(new PlayState());
		}
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		
	}

}
