package com.jamescho.game.model;

import java.awt.Rectangle;

import com.jamescho.framework.util.RNG;
import com.jamescho.game.main.GameMain;

public class Block {
	private static final int UPPER_Y = GameMain.GAME_HEIGHT - 175;
	private static final int LOWER_Y = GameMain.GAME_HEIGHT - 95;
	private static final int CHANCE_FOR_UPPER = 3;
	private static final int PUSHBACK = 30;
	
	private float x, y;
	private int width, height;
	private Rectangle rect;
	private boolean visible;
	
	public Block(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		rect = new Rectangle((int) x, (int) y, width, height);
		visible = false;
	}
	
	public void update(float delta, float velX) {
		x += velX * delta;
		if (x <= -50) {
			reset();
		}
		updateRect();
	}
	
	public void updateRect() {
		rect.setBounds((int) x, (int) y, width, height);
	}
	
	public void reset() {
		visible = true;
		if (RNG.getRandomInt(CHANCE_FOR_UPPER - 1) == 0) {
			y = UPPER_Y;
		} else {
			y = LOWER_Y;
		}
	x += 1000;
	}
	
	public void onCollide(Player p) {
		visible = false;
		p.pushBack(PUSHBACK);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	public static int getUpperY() {
		return UPPER_Y;
	}
	
	public static int getLowerY() {
		return LOWER_Y;
	}
	
	public static int getChanceForUpper() {
		return CHANCE_FOR_UPPER;
	}

}
