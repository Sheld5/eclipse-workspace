package com.jamescho.game.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.jamescho.game.main.GameMain;

public class Block {
	
	private int color; // 1:yellow 2:orange 3:red
	private int line; // 1:left 2:middle 3:right
	private int x, y, size, speed;
	private boolean isMoving;
	private Rectangle rect;
	
	public Block(int line, int color, int size, int speed) {
		if (color < 1 || color > 3) {
			throw new IllegalArgumentException("Block.color can only contain values 1,2,3");
		}
		if (line < 1 || line > 3) {
			throw new IllegalArgumentException("Block.line can only contain values 1,2,3");
		}
		this.color = color;
		this.line = line;
		this.x = line * (GameMain.GAME_WIDTH / 3 - 2) - GameMain.GAME_WIDTH / 6 + 4 - size / 2;
		this.y = -size;
		this.size = size;
		this.speed = speed;
		isMoving = false;
		rect = new Rectangle(x, y, size, size);
	}
	
	public void update() {
		if (isMoving) {
			y += speed;
			updateRect();
		}
	}
	
	private void updateRect() {
		rect.setBounds(x , y, size, size);
	}
	
	public void render(Graphics g) {
		if (color == 1) {
			g.setColor(Color.yellow);
		} else if (color == 2) {
			g.setColor(Color.orange);
		} else if (color == 3) {
			g.setColor(Color.red);
		}
		g.fillRect(x, y, size, size);
	}
	
	public void go() {
		isMoving = true;
	}
	
	public void stop() {
		isMoving = false;
	}
	
	public void reset(int line, int color, int speed) {
		setLine(line);
		setColor(color);
		setSpeed(speed);
		y = -size;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public void setLine(int line) {
		this.line = line;
		updateX();
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	private void updateX() {
		x = line * (GameMain.GAME_WIDTH / 3 - 2) - GameMain.GAME_WIDTH / 6 + 4 - size / 2;
	}
	
	public int getColor() {
		return color;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
}
