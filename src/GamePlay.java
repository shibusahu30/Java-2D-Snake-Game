import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePlay extends JPanel implements KeyListener, ActionListener {
	private ImageIcon titleImage;
	
	private int[] snakeXLength = new int[750];
	private int[] snakeYLength = new int[750];
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private boolean newGame = false;
	
	private ImageIcon rightmouth ;
	private ImageIcon leftmouth ;
	private ImageIcon upmouth ;
	private ImageIcon downmouth ;
	private ImageIcon snakeimage;
	private ImageIcon enemyimage;
	
	private int lengthofSnake = 3;
	private int moves = 0;
	
	private int score = 0;
	private int maxScore = 0;
	
	private Random rand = new Random();
	private int xpos = 25 + (rand.nextInt(34)*25);
	private int ypos = 75 + (rand.nextInt(23)*25);
	
	private boolean pause = false;
	private boolean[] arr = {false, false, false, false};
	
	private Timer timer;
	private int delay = 150;
	
	private Timer timer2;
	private int delay2 = 250;
	
	public GamePlay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		timer = new Timer(delay, this);
		timer.start();
		
		timer2 = new Timer(delay2, this);
		timer.start();
		
		
	}
	public void paint(Graphics g) {
		
		// setting initial image of snake 
		if(moves == 0) {
			snakeXLength[2] = 25;
			snakeXLength[1] = 50;
			snakeXLength[0] = 75;
			
			snakeYLength[2] = 100;
			snakeYLength[1] = 100;
			snakeYLength[0] = 100;
		}
		
		// draw title image border
		g.setColor(Color.white);
		g.drawRect(24, 10, 851, 55);
		
		// draw title image
		titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);
		
		// draw border gor playing are
		g.setColor(Color.white);
		g.drawRect(24, 74, 851, 577);
		
		// draw background
		g.setColor(Color.green);
		g.fillRect(25, 75, 850, 575);
		
		//draw the score card
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Scores: "+score, 780, 30 );
		
		//draw length of snake
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Length: "+lengthofSnake, 780, 50 );
		
		//draw maxscore
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Max Score: "+maxScore, 170, 30 );
		
		// initialize images
		rightmouth = new ImageIcon("rightmouth.png");
		rightmouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
		leftmouth = new ImageIcon("leftmouth.png");
		upmouth = new ImageIcon("upmouth.png");
		downmouth = new ImageIcon("downmouth.png");
		snakeimage = new ImageIcon("snakeimage.png");
		enemyimage = new ImageIcon("enemy.png");
		//enemyimage.paintIcon(this, g, xpos, ypos);
		//record break
		if(maxScore <= score && maxScore > 0) {
			g.setColor(Color.red);
			g.setFont(new Font("arial", Font.PLAIN, 25));
			g.drawString("New Record", 550, 48 );
		}
		
		// draw the full snake
		for(int i = 0; i < lengthofSnake; i++) {
			if(i == 0 && right) rightmouth.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
			if(i == 0 && left) leftmouth.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
			if(i == 0 && up) upmouth.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
			if(i == 0 && down) downmouth.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
			if(i!= 0) snakeimage.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
		}
		
		// If snake eats the fruit
		if(xpos == snakeXLength[0] && ypos == snakeYLength[0]) {
			lengthofSnake++;
			score++;
			if (maxScore < score) maxScore = score;
			xpos = 25 + (rand.nextInt(34)*25);
			ypos = 75 + (rand.nextInt(23)*25);
		}
		if (newGame) {
			xpos = 25 + (rand.nextInt(34)*25);
			ypos = 75 + (rand.nextInt(23)*25);
		}
		
		// paint fruit if moves greater than 0
		if (moves > 0) enemyimage.paintIcon(this, g, xpos, ypos);
		
		// collision of head with body
		for(int r = 1;r < lengthofSnake;r++){
			if(snakeXLength[r] == snakeXLength[0] && snakeYLength[r] == snakeYLength[0]){
				left = false;
				right = false;
				up = false;
				down = false;
				
				score = 0;
				lengthofSnake = 3;
				moves = 0;
				
				xpos = 25 + (rand.nextInt(34)*25);
				ypos = 75 + (rand.nextInt(23)*25);
				
				newGame = true;
				
				g.setColor(Color.white);
				g.setFont(new Font("arial",Font.BOLD,50));
				g.drawString("Game Over!!!", 300, 300);
				
				g.setFont(new Font("arial",Font.BOLD,30));
				g.drawString("Press ENTER...", 300, 340);
			}
		}
		
		// dispose the graphics paint
		g.dispose();
	}
	public void actionPerformed(ActionEvent e) {
		
		// delay the action performed by pressing key
		timer.start();
		
		if (!newGame) {
			if(right) {
				for (int i= lengthofSnake - 2; i >= 0; i--) {
					snakeYLength[i+1] = snakeYLength[i];
				}
				for (int i= lengthofSnake - 1; i >= 0; i--) {
					if(i == 0) snakeXLength[i] = snakeXLength[i] + 25;
					else snakeXLength[i] = snakeXLength[i-1];
					if(snakeXLength[0] > 850) snakeXLength[0] = 25; 
				}
				repaint();
			}
			if(left) {
				for (int i= lengthofSnake - 2; i >= 0; i--) {
					snakeYLength[i+1] = snakeYLength[i];
				}
				for (int i= lengthofSnake - 1; i >= 0; i--) {
					if(i == 0) snakeXLength[i] = snakeXLength[i] - 25;
					else snakeXLength[i] = snakeXLength[i-1];
					if(snakeXLength[0] < 25) snakeXLength[0] = 850; 
				}
				repaint();
			}
			
			if(up) {
				for (int i= lengthofSnake - 2; i >= 0; i--) {
					snakeXLength[i+1] = snakeXLength[i];
				}
				for (int i= lengthofSnake - 1; i >= 0; i--) {
					if(i == 0) snakeYLength[i] = snakeYLength[i] - 25;
					else snakeYLength[i] = snakeYLength[i-1];
					if(snakeYLength[0] < 75) snakeYLength[0] = 625; 
				}
				repaint();
			}
			if(down) {
				for (int i= lengthofSnake - 2; i >= 0; i--) {
					snakeXLength[i+1] = snakeXLength[i];
				}
				for (int i= lengthofSnake-1 ; i >= 0; i--) {
					if(i == 0) snakeYLength[i] = snakeYLength[i] + 25;
					else snakeYLength[i] = snakeYLength[i-1];
					if(snakeYLength[0] > 625) snakeYLength[0] = 75; 
				}
				repaint();
			}
		}
		
		
		
	}
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			score = 0;
			lengthofSnake = 3;
			moves = 0;
			
			left = false;
			right = false;
			up = false;
			down = false;
			
			newGame = false;
			
			xpos = 25 + (rand.nextInt(34)*25);
			ypos = 75 + (rand.nextInt(23)*25);
			
			repaint();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moves++;
			if(!left) right = true;
			else {
				right = false;
			}
			up = false;
			down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			moves++;
			if(!right) left = true;
			else {
				left = false;
			}
			up = false;
			down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			moves++;
			if(!down) up = true;
			else {
				up = false;
			}
			left = false;
			right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			moves++;
			if(!up) down = true;
			else {
				down = false;
			}
			left = false;
			right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			pause = !pause;
			if(pause) {
				arr[0] = left;
				arr[1] = right;
				arr[2] = up;
				arr[3] = down;
				left = false;
				right = false;
				up = false;
				down = false;
			}else {
				left = arr[0];
				right = arr[1];
				up = arr[2];
				down = arr[3];
			}
		}
		
	}
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
