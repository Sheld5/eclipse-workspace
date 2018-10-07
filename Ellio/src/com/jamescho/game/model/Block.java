package com.jamescho.game.model;

import java.awt.Rectangle;

import com.jamescho.framework.util.RNG;
import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;

public class Block {
	private static final int BLOCK_WIDTH = 20;
	private static final int BLOCK_HEIGHT = 50;
	private static final int BLOCK_LARGE_HEIGHT = 75;
	private static final int UPPER_Y = GameMain.GAME_HEIGHT - 175;
	private static final int LOWER_Y = GameMain.GAME_HEIGHT - 95;
	private static final int MIDDLE_Y = GameMain.GAME_HEIGHT - 145;
	private static final int CHANCE_FOR_UPPER = 2;
	private static final int CHANCE_FOR_LARGE = 3;
	private static final int PUSHBACK = 55;
	
	private float x, y;
	private int width, height;
	private Rectangle rect;
	private boolean visible;
	private boolean isLarge;
	
	public Block(float x) {
		this.x = x;
		setSize();
		width = BLOCK_WIDTH;
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
		setSize();
	x += 1000;
	}
	
	private void setSize() {
		if (RNG.getRandomInt(CHANCE_FOR_LARGE - 1) == 0) {
			isLarge = true;
			y = MIDDLE_Y;
			height = BLOCK_LARGE_HEIGHT;
			rect = new Rectangle((int) x, (int) y, width, height);
		} else {
			isLarge = false;
			if (RNG.getRandomInt(CHANCE_FOR_UPPER - 1) == 0) {
				y = UPPER_Y;
			} else {
				y = LOWER_Y;
			}
			height = BLOCK_HEIGHT;
			rect = new Rectangle((int) x, (int) y, width, height);
		}
	}
	
	public void onCollide(Player p) {
		visible = false;
		if (isLarge && p.isPunching()) {
			Resources.punchThrough.play();
		} else {
			Resources.hit.play();
			p.pushBack(PUSHBACK);
		}
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
	
	public boolean isLarge() {
		return isLarge;
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
	
	public static int getBlockWidth() {
		return BLOCK_WIDTH;
	}
	
	public static int getBlockHeight() {
		return BLOCK_HEIGHT;
	}
	
	public static int getBlockLargeHeight() {
		return BLOCK_LARGE_HEIGHT;
	}

}
