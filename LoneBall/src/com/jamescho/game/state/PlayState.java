package com.jamescho.game.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;

import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;
import com.jamescho.game.model.Ball;
import com.jamescho.game.model.Paddle;

public class PlayState extends State {
	
	private static final int PADDLE_WIDTH = 15;
	private static final int PADDLE_HEIGHT = 60;
	private static final int BALL_DIAMETER = 20;
	
	private Paddle paddleLeft, paddleRight;
	private Ball ball;
	private int playerScore = 0;
	private Font font;
	
	private boolean upPressed = false;
	private boolean downPressed = false;
	

	@Override
	public void init() {
		System.out.println("Entered PlayState");
		paddleLeft = new Paddle(0, (GameMain.GAME_HEIGHT - PADDLE_HEIGHT) / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddleRight = new Paddle(GameMain.GAME_WIDTH - PADDLE_WIDTH, (GameMain.GAME_HEIGHT - PADDLE_HEIGHT) / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
		ball = new Ball((GameMain.GAME_WIDTH - BALL_DIAMETER) / 2, (GameMain.GAME_HEIGHT - BALL_DIAMETER) / 2, BALL_DIAMETER, BALL_DIAMETER);
		font = new Font("SansSerif", Font.BOLD, 25);
	}

	@Override
	public void update() {
		paddleLeft.update();
		paddleRight.update();
		ball.update();
		
		if (ballCollides(paddleLeft)) {
			playerScore++;
			ball.onCollideWith(paddleLeft);
			Resources.hit.play();
		} else if (ballCollides(paddleRight)) {
			playerScore++;
			ball.onCollideWith(paddleRight);
			Resources.hit.play();
		} else if (ball.isDead()) {
			ball.stop();
			ball.resetPosition();
		}
	}

	@Override
	public void render(Graphics g) {
		
		// Draw Background
		g.setColor(Resources.darkBlue);
		g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);
		g.setColor(Resources.darkRed);
		g.fillRect(GameMain.GAME_WIDTH / 2, 0, GameMain.GAME_WIDTH / 2, GameMain.GAME_HEIGHT);
		// Draw Separator Line
		g.drawImage(Resources.line, GameMain.GAME_WIDTH / 2 - 2, 0, null);
		// Draw Paddles
		g.setColor(Color.white);
		g.fillRect(paddleLeft.getX(), paddleLeft.getY(), paddleLeft.getWidth(), paddleLeft.getHeight());
		g.fillRect(paddleRight.getX(), paddleRight.getY(), paddleRight.getWidth(), paddleRight.getHeight());
		// Draw Ball
		g.setColor(Color.orange);
		g.fillRect(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
		// Draw Score
		g.setColor(Color.yellow);
		font = new Font("SansSerif", Font.BOLD, 25);
		g.setFont(font);
		g.drawString("" + playerScore, GameMain.GAME_WIDTH / 2 - 50, 50);
		// Draw hint
		if (!ball.isMoving()) {
			g.setColor(Color.white);
			font = new Font("Arial", Font.PLAIN, 12);
			g.setFont(font);
			g.drawString("ESC: exit", 10, 20);
			g.drawString("SPACE: play", 10, 35);
		}
		
	}

	@Override
	public void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = true;
			paddleLeft.moveUp();
			paddleRight.moveDown();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = true;
			paddleLeft.moveDown();
			paddleRight.moveUp();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (!ball.isMoving()) {
				playerScore = 0;
				ball.go();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			setCurrentState(new MenuState());
		}
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
			if (downPressed) {
				paddleLeft.moveDown();
				paddleRight.moveUp();
			} else {
				paddleRight.stop();
				paddleLeft.stop();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
			if (upPressed) {
				paddleLeft.moveUp();
				paddleRight.moveDown();
			} else {
				paddleLeft.stop();
				paddleRight.stop();
			}
		}
	}
	
	private boolean ballCollides(Paddle p) {
		return ball.getRect().intersects(p.getRect());
	}

}
