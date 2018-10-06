package com.jamescho.game.model;

import com.jamescho.framework.util.RNG;

public class Cloud {
	private float x, y;
	
	public Cloud(float x) {
		this.x = x;
		this.y = RNG.getRandomIntBetween(20, 100);
	}
	
	public void update(float delta, float velX) {
		x += velX * delta;
		if (x <= -200) {
			x += 1000;
			y = RNG.getRandomIntBetween(20, 100);
		}
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

}
