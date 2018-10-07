package com.jamescho.game.state;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;
import com.jamescho.game.model.Block;
import com.jamescho.game.model.Cloud;
import com.jamescho.game.model.Player;

public class PlayStateHard extends State {
	private static final int PLAYER_WIDTH = 66;
	private static final int PLAYER_HEIGHT = 92;
	private static final int INITIAL_BLOCK_SPEED = -200;
	private static final int BLOCK_SPEED_INCREASE = -10;
	private static final int BLOCK_SPEED_MAXIMUM = -280;
	private static final int BLOCK_CLOUD_SPEED_RATIO = 7;
	private static final int SPEED_INCREASE_PERIOD = 500;
	private static final int INITIAL_PLAYER_X = 160;
	private static final double SPEED_MULTIPLIER = 1.5;
	
	private Player player;
	private ArrayList<Block> blocks;
	private Cloud cloud1, cloud2;
	
	private Font scoreFont;
	private int playerScore = 0;
	private int blockSpeed = INITIAL_BLOCK_SPEED;
	private int cloudSpeed = blockSpeed / BLOCK_CLOUD_SPEED_RATIO;

	@Override
	public void init() {
		System.out.println("Entered PlayState");
		player = new Player(INITIAL_PLAYER_X , GameMain.GAME_HEIGHT - 45 - PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
		blocks = new ArrayList<Block>();
		cloud1 = new Cloud(100);
		cloud2 = new Cloud(600);
		scoreFont = new Font("SansSerif", Font.BOLD, 25);
		
		for (int i = 0; i < 5; i++) {
			Block b = new Block(i * 200);
			blocks.add(b);
		}
		
	}

	@Override
	public void update(float delta) {
		delta *= SPEED_MULTIPLIER;
		if (!player.isAlive()) {
			setCurrentState(new GameOverState(playerScore / 10));
		}
		playerScore += 1;
		if (playerScore % SPEED_INCREASE_PERIOD == 0 && blockSpeed > BLOCK_SPEED_MAXIMUM) {
			blockSpeed += BLOCK_SPEED_INCREASE;
			cloudSpeed = blockSpeed / BLOCK_CLOUD_SPEED_RATIO;
		}
		updateObjects(delta);
		Resources.runAnim.update(delta);
	}
	
	private void updateObjects(float delta) {
		player.update(delta, INITIAL_PLAYER_X);
		updateBlocks(delta);
		cloud1.update(delta, cloudSpeed);
		cloud2.update(delta, cloudSpeed);
	}
	
	private void updateBlocks(float delta) {
		for (Block b : blocks) {
			b.update(delta, blockSpeed);
			if (b.isVisible()) {
				if (player.isDucked() && b.getRect().intersects(player.getDuckRect())) {
					b.onCollide(player);
				} else if (!player.isDucked() && b.getRect().intersects(player.getRect())) {
					b.onCollide(player);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		renderSky(g);
	//	renderSun(g);
		renderClouds(g);
		renderBlocks(g);
		renderPlayer(g);
		g.drawImage(Resources.grass, 0, 405, null);
		renderScore(g);
	}
	
	private void renderScore(Graphics g) {
		g.setFont(scoreFont);
		g.setColor(Color.GRAY);
		g.drawString(playerScore / 10 + "m", 20, 30);
	}
	
	private void renderPlayer(Graphics g) {
		if (player.isGrounded()) {
			if (player.isPunching()) {
				g.drawImage(Resources.punch, (int) player.getX(), (int) player.getY(), null);
			} else if (player.isDucked()) {
				g.drawImage(Resources.duck, (int) player.getX(), (int) player.getY(), null);
			} else {
				Resources.runAnim.render(g, (int) player.getX(), (int) player.getY(), player.getWIdth(), player.getHeight());
			}
		} else {
			g.drawImage(Resources.jump, (int) player.getX(), (int) player.getY(), player.getWIdth(), player.getHeight(), null);
		}
	}
	
	private void renderBlocks(Graphics g) {
		for (Block b : blocks) {
			if (b.isVisible()) {
				if (b.isLarge()) {
					g.drawImage(Resources.blockLarge, (int) b.getX(), (int) b.getY(), Block.getBlockWidth(), Block.getBlockLargeHeight(), null);
				} else {
					g.drawImage(Resources.block, (int) b.getX(), (int) b.getY(), Block.getBlockWidth(), Block.getBlockHeight(), null);
				}
			}
		}
	}
	
	private void renderClouds(Graphics g) {
		g.drawImage(Resources.cloud1, (int) cloud1.getX(), (int) cloud1.getY(), 100, 60, null);
		g.drawImage(Resources.cloud2, (int) cloud2.getX(), (int) cloud2.getY(), 100, 60, null);
	}
	
	/*
	private void renderSun(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval(715, -85, 170,170);
		g.setColor(Color.YELLOW);
		g.fillOval(725, -75, 150, 150);
	}
	*/
	
	private void renderSky(Graphics g) {
		g.setColor(Resources.skyBlue);
		g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);
	}

	@Override
	public void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			player.jump();
		} else if (key == KeyEvent.VK_DOWN) {
			player.duck();
		} else if (key == KeyEvent.VK_RIGHT) {
			player.punch();
		}
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
