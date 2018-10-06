package com.jamescho.game.model;

import java.awt.Color;
import java.awt.Graphics;

import com.jamescho.game.main.GameMain;

public class Catcher {

	private int position; // 1:left 2:middle 3:right
	private int color; // 1:yellow 2:orange 3:red
	private int x, y, size;
	
	public Catcher(int position, int color, int size) {
		if (position < 1 || position > 3) {
			throw new IllegalArgumentException("Catcher.postion can only contain values 1,2,3");
		}
		if (color < 1 || color > 3) {
			throw new IllegalArgumentException("Catcher.color can only contain values 1,2,3");
		}
		this.position = position;
		this.color = color;
		this.x = (position - 1) * (GameMain.GAME_WIDTH - 6) / 3 + 6 + ((GameMain.GAME_WIDTH - 24) / 3 - size) / 2;
		this.y = GameMain.GAME_HEIGHT - (6 + (GameMain.GAME_WIDTH - 24) / 3 - ((GameMain.GAME_WIDTH - 24) / 3 - size) / 2);
		this.size = size;
	}
	
	public void updateX() {
		x = (position - 1) * (GameMain.GAME_WIDTH - 6) / 3 + 6 + ((GameMain.GAME_WIDTH - 24) / 3 - size) / 2;
	}
	
	public void render(Graphics g) {
		if (color == 1) {
			g.setColor(Color.yellow);
		} else if (color == 2) {
			g.setColor(Color.orange);
		} else if (color == 3) {
			g.setColor(Color.red);
		} else {
			throw new IllegalArgumentException("Catcher.color can only contain values 1,2,3");
		}
		g.fillOval(x, y, size, size);
	}
	
	public void moveRight() {
		if (position < 3) {
			position++;
		}
		updateX();
	}
	
	public void moveLeft() {
		if (position > 1) {
			position--;
		}
		updateX();
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
	
	public int getColor() {
		return color;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
