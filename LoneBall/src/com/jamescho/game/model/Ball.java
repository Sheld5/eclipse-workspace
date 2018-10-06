package com.jamescho.game.model;

import java.awt.Rectangle;

import com.jamescho.framework.util.RNG;
import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;

public class Ball {
	
	private static final int MAX_Y_VELOCITY = 7;
	private static final int X_VELOCITY = 9;

	private int x, y, width, height, velX, velY;
	private Rectangle rect;
	private boolean isMoving;
	
	public Ball(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		velX = 0;
		velY = 0;
		rect = new Rectangle(x, y, width, height);
		isMoving = false;
	}
	
	public void update() {
		x += velX;
		y += velY;
		correctYCollisions();
		updateRect();
	}
	
	private void correctYCollisions() {
		if (y < 0) {
			y = 0;
		} else if (y + height > GameMain.GAME_HEIGHT) {
			y = GameMain.GAME_HEIGHT - height;
		} else {
			return;
		}
		
		velY = -velY;
		Resources.bounce.play();
	}
	
	private void updateRect() {
		rect.setBounds(x, y, width, height);
	}
	
	public void onCollideWith(Paddle p) {
		if (x < GameMain.GAME_WIDTH / 2) {
			x = p.getX() + p.getWidth();
		} else {
			x = p.getX() - p.getWidth();
		}
		velX = -velX;
		velY = RNG.getRandomIntBetween(-MAX_Y_VELOCITY, MAX_Y_VELOCITY);
	}
	
	public boolean isDead() {
		return(x < 0 || x + width > GameMain.GAME_WIDTH);
	}
	
	public void resetPosition() {
		x = (GameMain.GAME_WIDTH - width) / 2;
		y = (GameMain.GAME_HEIGHT - height) / 2;
	}
	
	public void stop() {
		isMoving = false;
		velX = 0;
		velY = 0;
	}
	
	public void go() {
		isMoving = true;
		if (RNG.getRandomInt(1) == 1) {
			velX = X_VELOCITY;;
		} else {
			velX = -X_VELOCITY;
		}
		velY = RNG.getRandomIntBetween(-MAX_Y_VELOCITY, MAX_Y_VELOCITY);
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
	
	public boolean isMoving() {
		return isMoving;
	}
	
}
