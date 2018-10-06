package com.jamescho.game.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jamescho.framework.util.RNG;
import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;
import com.jamescho.game.model.Block;
import com.jamescho.game.model.Cloud;
import com.jamescho.game.model.Player;

public class PlayState extends State {
	private static final int BLOCK_WIDTH = 20;
	private static final int BLOCK_HEIGHT = 50;
	private static final int PLAYER_WIDTH = 66;
	private static final int PLAYER_HEIGHT = 92;
	private static final int INITIAL_BLOCK_SPEED = -200;
	private static final int BLOCK_CLOUD_SPEED_RATIO = 13;
	private static final int INITIAL_PLAYER_X = 160;
	
	private Player player;
	private Block[] blocks;
	private Cloud cloud1, cloud2;
	
	private Font scoreFont;
	private int playerScore = 0;
	private int blockSpeed = INITIAL_BLOCK_SPEED;
	private int cloudSpeed = blockSpeed / BLOCK_CLOUD_SPEED_RATIO;

	@Override
	public void init() {
		player = new Player(INITIAL_PLAYER_X , GameMain.GAME_HEIGHT - 45 - PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
		blocks = new Block[5];
		cloud1 = new Cloud(100);
		cloud2 = new Cloud(600);
		scoreFont = new Font("SansSerif", Font.BOLD, 25);
		
		for (int i = 0; i < 5; i++) {
			int y;
			if (RNG.getRandomInt(Block.getChanceForUpper() - 1) == 0) {
				y = Block.getUpperY();
			} else {
				y = Block.getLowerY();
			}
			Block b = new Block(i * 200, y, BLOCK_WIDTH, BLOCK_HEIGHT);
			blocks[i] = b;
		}
		
	}

	@Override
	public void update(float delta) {
		if (!player.isAlive()) {
			setCurrentState(new GameOverState(playerScore / 100));
		}
		playerScore += 1;
		if (playerScore % 500 == 0 && blockSpeed > -280) {
			blockSpeed -= 10;
			cloudSpeed = blockSpeed / BLOCK_CLOUD_SPEED_RATIO;
		}
		updateObjects(delta);
		Resources.runAnim.update(delta);
	}
	
	private void updateObjects(float delta) {
		player.update(delta);
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
		renderSun(g);
		renderClouds(g);
		renderBlocks(g);
		renderPlayer(g);
		g.drawImage(Resources.grass, 0, 405, null);
		renderScore(g);
	}
	
	private void renderScore(Graphics g) {
		g.setFont(scoreFont);
		g.setColor(Color.GRAY);
		g.drawString("" + playerScore / 100, 20, 30);
	}
	
	private void renderPlayer(Graphics g) {
		if (player.isGrounded()) {
			if (player.isDucked()) {
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
				g.drawImage(Resources.block, (int) b.getX(), (int) b.getY(), BLOCK_WIDTH, BLOCK_HEIGHT, null);
			}
		}
	}
	
	private void renderClouds(Graphics g) {
		g.drawImage(Resources.cloud1, (int) cloud1.getX(), (int) cloud1.getY(), 100, 60, null);
		g.drawImage(Resources.cloud2, (int) cloud2.getX(), (int) cloud2.getY(), 100, 60, null);
	}
	
	private void renderSun(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval(715, -85, 170,170);
		g.setColor(Color.YELLOW);
		g.fillOval(725, -75, 150, 150);
	}
	
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
		}
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
