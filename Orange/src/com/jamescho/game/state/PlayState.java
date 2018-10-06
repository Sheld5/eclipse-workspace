package com.jamescho.game.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;
import com.jamescho.game.model.Block;
import com.jamescho.game.model.Catcher;
import com.jamescho.framework.util.RNG;

public class PlayState extends State {
	
	private static final int CATCHER_SIZE = 80;
	private static final int BLOCK_SIZE = 50;
	private static final int INITIAL_SPEED = 3;
	
	private Block testBlock;
	private Catcher yellowCatcher, redCatcher;
	private Rectangle line;
	private String state; // resumed / paused / gameOver
	private int score;
	private double speed;

	@Override
	public void init() {
		System.out.println("Entered PlayState");
		speed = INITIAL_SPEED;
		testBlock = new Block(RNG.getRandomIntBetween(1, 3), RNG.getRandomIntBetween(1, 3), BLOCK_SIZE, (int) speed); 
		yellowCatcher = new Catcher(1, 1, CATCHER_SIZE);
		redCatcher = new Catcher(3, 3, CATCHER_SIZE);
		line = new Rectangle(0, GameMain.GAME_HEIGHT - (GameMain.GAME_WIDTH / 3 + 4), GameMain.GAME_WIDTH, 6);
		state = "paused";
		score = 0;
	}

	@Override
	public void update() {
		updateBlock(testBlock);
	}
	
	private void updateBlock(Block b) {
		b.update();
		onCollision(b);
	}
	
	private void onCollision(Block b) {
		if (b.getRect().intersects(line)) {
			if (matches(b)) {
				Resources.hitSound.play();
				score++;
				speed = INITIAL_SPEED + (java.lang.Math.sqrt(score) - 1);
				b.reset(RNG.getRandomIntBetween(1, 3), RNG.getRandomIntBetween(1, 3), (int) speed);
			} else {
				if (state == "resumed") {
					Resources.missSound.play();
					gameOver();
				}
			}
		}
	}
	
	private boolean matches(Block b) {
		if (b.getColor() == 1) {
			if (b.getLine() == yellowCatcher.getPosition() && b.getLine() != redCatcher.getPosition()) {
				return true;
			} else {
				return false;
			}
		} else if (b.getColor() == 3) {
			if (b.getLine() == redCatcher.getPosition() && b.getLine() != yellowCatcher.getPosition()) {
				return true;
			} else {
				return false;
			}
		} else if (b.getColor() == 2) {
			if (b.getLine() == yellowCatcher.getPosition() && b.getLine() == redCatcher.getPosition()) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new IllegalArgumentException("Block.color can only contain values 1,2,3");
		}
	}

	@Override
	public void render(Graphics g) {
		renderBackground(g);
		testBlock.render(g);
		if (yellowCatcher.getPosition() == redCatcher.getPosition()) {
			renderOrangeCatcher(g);
		} else {
			yellowCatcher.render(g);
			redCatcher.render(g);
		}
		renderScore(g);
		if (state != "resumed") {
			renderHint(g);
		}
		if (state == "gameOver") {
			renderGameOver(g);
		}
	}
	
	private void renderBackground(Graphics g) {
		// Draw background
		g.setColor(Color.gray);
		g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);
		// Draw lines
		g.setColor(Color.black);
		g.fillRect(0, 0, 6, GameMain.GAME_HEIGHT);
		g.fillRect((GameMain.GAME_WIDTH / 3 - 2) * 1, 0, 6, GameMain.GAME_HEIGHT);
		g.fillRect((GameMain.GAME_WIDTH / 3 - 2) * 2, 0, 6, GameMain.GAME_HEIGHT);
		g.fillRect((GameMain.GAME_WIDTH / 3 - 2) * 3, 0, 6, GameMain.GAME_HEIGHT);
		g.fillRect(0, GameMain.GAME_HEIGHT - (GameMain.GAME_WIDTH / 3 + 4), GameMain.GAME_WIDTH, 6);
		g.fillRect(0, GameMain.GAME_HEIGHT - 6, GameMain.GAME_WIDTH, 6);
	}
	
	private void renderOrangeCatcher(Graphics g) {
		g.setColor(Color.orange);
		g.fillOval(yellowCatcher.getX(), yellowCatcher.getY(), CATCHER_SIZE, CATCHER_SIZE);
	}
	
	private void renderScore(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("SansSerif", Font.BOLD, 25));
		g.drawString("" + score, GameMain.GAME_WIDTH / 2 - 7, 32);
	}
	
	private void renderHint(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("ESC: exit to menu", 10, 20);
		g.drawString("SPACE: play", 10, 35);
		g.drawString("Controls: A,D,J,L", GameMain.GAME_WIDTH / 2 - 45, GameMain.GAME_HEIGHT / 2 - 20);
	}
	
	private void renderGameOver(Graphics g) {
		g.setColor(Color.magenta);
		g.setFont(new Font("SansSerif", Font.BOLD, 42));
		g.drawString("Game Over", GameMain.GAME_WIDTH / 2 - 120, GameMain.GAME_HEIGHT / 2 - 50);
	}

	@Override
	public void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			if (state == "resumed") {
				yellowCatcher.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			if (state == "resumed") {
				yellowCatcher.moveRight();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_J) {
			if (state == "resumed") {
				redCatcher.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_L) {
			if (state == "resumed") {
				redCatcher.moveRight();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (state == "paused") {
				resume();
			} else if (state == "gameOver") {
				restartPlayState();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (state != "resumed") {
				setCurrentState(new MenuState());
			}
		}
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void gameOver() {
		System.out.println("Game Paused");
		state = "gameOver";
		testBlock.stop();
	}
	
	private void resume() {
		System.out.println("Game Resumed");
		state = "resumed";
		testBlock.go();
	}
	
	private void restartPlayState() {
		System.out.println("Game Restarted");
		setCurrentState(new PlayState());
	}

}
