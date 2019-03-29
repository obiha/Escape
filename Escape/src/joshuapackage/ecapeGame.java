package joshuapackage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import acm.program.*;
import acm.graphics.GImage;
import acm.graphics.GRect;
import javax.swing.Timer;

public class ecapeGame extends GraphicsProgram implements ActionListener{

	/*STUDENT - JOSHUA OBIHA
	 *TEACHER - MR.REA
	 *COURSE - COMPUTER SCIENCE (ISC4U0)
	 *DATE - 6/13/2016 
	 */


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static void main(String[] args){
		new ecapeGame().start(args);
	}

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 775;
	public static final int REFRESH = 50;
	public static final int MAXVEL = 10;
	public static final double GRAVITY = 0.01;
	public static int  floor = 670;
	public static String direction = "down";

	/* VolleyVector is a volleyball game involving two players 
	 * The game allows for Background and character modification  
	 *  
	 */

	GRect square = new GRect(SCREEN_WIDTH,100);
	GRect square2 = new GRect(0,0);
	GImage ball = new GImage("yellow_circle.gif");
	GImage player1 = new GImage("player1.png");
	GImage player2 = new GImage("player2.png");
	GImage rightPicture = new GImage("rightPicture.gif");
	GImage infoBar = new GImage("scoreBoard.png");


	boolean onFloor = false; // FLOOR CHECKER FOR PLAYER1 
	boolean onFloor2 = false; // FLOOR CHECKER FOR PLAYER2 

	/// JUMPLAG(GIVES TIME FOR WHEN THE OBJECT HITS THE FLOOR 
	int jumpLag = 0; 
	int jumpLag2 = 0;

	double velX, velY;  ////    VELOCITY OF PLAYER1 
	double velX2, velY2; ////// VELOCITY OF PLAYER2 
	public void run(){

		Timer t = new Timer(1, this);
		t.start();

		addKeyListeners();


		// WINDOW SETTINGS //

		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setTitle("Volley");

		///////////////////////////////



		// BACKGROUND SETTINGS //

		rightPicture.setSize(SCREEN_WIDTH,SCREEN_HEIGHT - 90);
		rightPicture.setLocation(0, 0);
		add(rightPicture);

		///////////////////////////////

		// VOLLEYBALL POLE SETTINGS //

		square2.setSize(10,140);
		square2.setColor(Color.BLACK);
		square2.setFilled(true);
		square2.setLocation(SCREEN_WIDTH/2,floor -square2.getHeight());
		add(square2);

		//////////////////////////////

		//INFO BAR SETTINGS //

		infoBar.setSize(300, 90);
		infoBar.setLocation(SCREEN_WIDTH/2-infoBar.getWidth()/2, 40);
		add(infoBar);

		/////////////////////////////

		// SQAURE FLOOR SETTINGS //

		square.setLocation(0,floor+1);
		square.setColor(Color.BLACK);
		square.setFilled(true);
		square.sendToFront();
		add(square);

		// SQUARE 3 //

		ball.setSize(50,50);
		ball.setLocation(300,300);
		add(ball);

		// PLAYER 1  & PLAYER 2 SETTINGS //

		player1.setLocation(SCREEN_WIDTH/2 - 350,floor -66);
		player1.setSize(170, 170);
		player1.sendToFront();
		add(player1);

		player2.setLocation(SCREEN_WIDTH/2 + 200,floor -66);
		player2.setSize(170, 170);
		player2.sendToFront();

		add(player2);

		//////////////////////////////


	}

	public void actionPerformed(ActionEvent e){

		player1.setLocation(player1.getX()+velX, player1.getY()+velY);

		if(player1.getX() > getWidth()){
			player1.setLocation(-player1.getWidth(), player1.getY());
			player1.sendToFront();
			rightWall();

		}

		if(player1.getX() + player1.getWidth() < 0){
			player1.setLocation(getWidth(), player1.getY());

			leftWall();
			player1.sendToFront();
		}

		if(player1.getY() > getHeight()){
			player1.setLocation(player1.getX(), -player1.getHeight());
		}

		if(player1.getY() + player1.getHeight() < 0){

			player1.setLocation(player1.getX(), getHeight());
		}

		if(jumpLag > 0){
			jumpLag--;
		}

		if(! onFloor){
			velY+=GRAVITY;
		}

		if(onFloor && jumpLag <= 0){
			velY = 0;
		}

		if(player1.getY()+player1.getHeight() >= floor){
			onFloor = true;
			player1.setLocation(player1.getX(), floor - player1.getHeight());
		}
		else{onFloor = false;


		}
		checkCollision();

		rightWall();

		leftWall();

		roof();

		player2.setLocation(player2.getX()+velX2, player2.getY()+velY2);

		if(player2.getX() > getWidth()){
			player2.setLocation(-player2.getWidth(), player2.getY());
			player2.sendToFront();
			rightWall();

		}

		if(player2.getX() + player2.getWidth() < 0){
			player2.setLocation(getWidth(), player2.getY());

			leftWall();
			player2.sendToFront();
		}

		if(player2.getY() > getHeight()){
			player2.setLocation(player2.getX(), -player2.getHeight());
		}

		if(player2.getY() + player2.getHeight() < 0){

			player2.setLocation(player2.getX(), getHeight());
		}

		if(jumpLag2 > 0){
			jumpLag2--;
		}

		if(! onFloor2){
			velY2+=GRAVITY;
		}

		if(onFloor2 && jumpLag2 <= 0){
			velY2 = 0;
		}

		if(player2.getY()+player2.getHeight() >= floor){
			onFloor2 = true;
			player2.setLocation(player2.getX(), floor - player2.getHeight());
		}
		else{
			onFloor2 = false;
		}




	}


	public void checkCollision(){

		if(player1.getX() + player1.getWidth() > square2.getX() && square2.getX() + square2.getWidth() > player1.getX() && player1.getY() + player1.getHeight() > square2.getY()  && square2.getY() + square2.getHeight() > player1.getY()){

			if(player1.getX() < square2.getX()){
				player1.setLocation(square2.getX() - player1.getWidth(), player1.getY());
			}
			if(player1.getX() + player1.getWidth()> square2.getX()){
				player1.setLocation(square2.getX() + square2.getWidth(), player1.getY());
			}


		}
		if(player2.getX() + player2.getWidth() > square2.getX() && square2.getX() + square2.getWidth() > player2.getX() && player2.getY() + player2.getHeight() > square2.getY()  && square2.getY() + square2.getHeight() > player2.getY()){

			if(player2.getX() < square2.getX()){
				player2.setLocation(square2.getX() - player2.getWidth(), player2.getY());
			}
			if(player2.getX() + player2.getWidth()> square2.getX()){
				player2.setLocation(square2.getX() + square2.getWidth(), player2.getY());
			}


		}
	}

	public void keyPressed(KeyEvent e){	///////////////////////////PLAYER1 KEYEVENT

		if (e.getKeyCode()== 87 && onFloor){
			// velY -1 takes the object up 	
			jumpLag = 10;
			velY = -MAXVEL;
		}

		if(e.getKeyCode() == 68){
			//velX +1 takes the object to the right 
			velX = +MAXVEL;
		}
		if (e.getKeyCode()== 65){
			//velX -1 takes the object to the left
			velX= -MAXVEL;
		}
		if (e.getKeyCode() == 83 && !onFloor){
			velY = +MAXVEL * 2;
		}
		if (e.getKeyCode() == 38 && onFloor2){
			// velY -1 takes the object up 	
			jumpLag2 = 10;
			velY2 = -MAXVEL;
		}

		if(e.getKeyCode() == 39){
			//velX +1 takes the object to the right 
			velX2 = +MAXVEL;
		}
		if (e.getKeyCode()== 37){
			//velX -1 takes the object to the left
			velX2= -MAXVEL;
		}
	}


	public void keyReleased(KeyEvent e){
		if (e.getKeyCode()==68 || e.getKeyCode() == 65){
			velX = 0;
		}
		if (e.getKeyCode() ==39 || e.getKeyCode() == 37){
			velX2 = 0;

		}


	}

	public void rightWall(){
		if (player1.getLocation().getX()+ player1.getWidth()>= SCREEN_WIDTH){

			velX=-10;

			System.out.println("right wall has been touched");
		}
		if (player2.getLocation().getX()+ player2.getWidth()>= SCREEN_WIDTH){

			velX2=-10;

			System.out.println("right wall has been touched");
		}
	}

	public void leftWall(){

		if (player1.getLocation().getX()<=0){
			velX=+10;
			System.out.println("left wall has been touched");
		}
	}

	public void roof(){

		if (player1.getLocation().getY() <= 100){
			velY= +10;
		}
		if (player2.getLocation().getY() <= 100){

			velY2= +10;
		}	
	}

}




