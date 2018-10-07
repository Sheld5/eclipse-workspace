package com.jamescho.game.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.jamescho.framework.animation.Animation;
import com.jamescho.framework.animation.Frame;

public class Resources {
	
	public static BufferedImage welcomeEasy, welcomeHard, welcomeNightmare, iconimage, block, blockLarge, cloud1, cloud2, duck, punch, grass, jump, run1, run2, run3, run4, run5, selector, dance1, dance2;
	public static AudioClip hit, onJump, punchThrough, onDuck, punchSound;
	public static Color skyBlue;
	public static Animation runAnim, danceAnim;
	
	public static void load() {
		welcomeEasy = loadImage("welcomeEasy.png");
		welcomeHard = loadImage("welcomeHard.png");
		welcomeNightmare = loadImage("welcomeNightmare.png");
		iconimage = loadImage("iconimage.png");
		block = loadImage("block.png");
		blockLarge = loadImage("blocklarge.png");
		cloud1 = loadImage("cloud1.png");
		cloud2 = loadImage("cloud2.png");
		duck = loadImage("duck.png");
		punch = loadImage("punch.png");
		grass = loadImage("grass.png");
		jump = loadImage("jump.png");
		run1 = loadImage("run_anim1.png");
		run2 = loadImage("run_anim2.png");
		run3 = loadImage("run_anim3.png");
		run4 = loadImage("run_anim4.png");
		run5 = loadImage("run_anim5.png");
		selector = loadImage("selector.png");
		dance1 = loadImage("dance1.png");
		dance2 = loadImage("dance2.png");
		hit = loadSound("hit.wav");
		onJump = loadSound("onjump.wav");
		onDuck = loadSound("duck.wav");
		punchSound = loadSound("punch.wav");
		punchThrough = loadSound("punchthrough.wav");
		skyBlue = new Color(208, 244, 247);
		
		Frame f1 = new Frame(run1, .1f);
		Frame f2 = new Frame(run2, .1f);
		Frame f3 = new Frame(run3, .1f);
		Frame f4 = new Frame(run4, .1f);
		Frame f5 = new Frame(run5, .1f);
		runAnim = new Animation(f1, f2, f3, f4, f5, f3, f2);
		
		Frame d1 = new Frame(dance1, .2f);
		Frame d2 = new Frame(dance2, .2f);
		danceAnim = new Animation(d1, d2);
	}
	
	private static AudioClip loadSound(String filename) {
		URL fileURL = Resources.class.getResource("/resources/" + filename);
		return Applet.newAudioClip(fileURL);
	}
	
	private static BufferedImage loadImage(String filename) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(Resources.class.getResourceAsStream("/resources/" + filename));
		} catch (IOException e) {
			System.out.println("Error while reading: " + filename);
			e.printStackTrace();
		}
		return img;
	}
	
}
