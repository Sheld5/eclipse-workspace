package com.jamescho.game.model;

import java.awt.Rectangle;

import com.jamescho.game.main.GameMain;

public class Paddle {

	private int x, y, width, height, velY;
	private Rectangle rect;
	private final static int MOVE_SPEED_Y = 5;
	
	public Paddle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		rect = new Rectangle(x, y, width, height);
		velY = 0;
	}
	
	public void update() {
		y+= velY;
		if (y < 0) {
			y = 0;
		} else if (y + height > GameMain.GAME_HEIGHT) {
			y = GameMain.GAME_HEIGHT - height;
		}
		
		updateRect();
	}
	
	public void updateRect() {
		rect.setBounds(x, y, width, height);
	}
	
	public void moveUp() {
		velY = -MOVE_SPEED_Y;
	}
	
	public void moveDown() {
		velY = MOVE_SPEED_Y;
	}
	
	public void stop() {
		velY = 0;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
}
