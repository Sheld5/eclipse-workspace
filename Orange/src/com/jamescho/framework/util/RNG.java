package com.jamescho.framework.util;

import java.util.Random;

public class RNG {

	private static Random random = new Random();
	
	public static int getRandomIntBetween(int lowerBound, int upperBound) {
		return random.nextInt(upperBound + 1 - lowerBound) + lowerBound;
	}
	
	public static int getRandomInt(int upperBound) {
		return random.nextInt(upperBound + 1);
	}
	
}
