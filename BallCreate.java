package physics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;


public class BallCreate  extends JPanel{
	
	Timer timer;
	int diam;
	Color color;
	boolean hitBottom = false;
	double vX, vY, posX, posY, v2Y, dX, dY, mass;
	double botBound = 450;
	final double G = 15;
	public BallCreate(double posX, double posY, double vX, double vY){
		this.posX = posX;
		this.posY = posY;
		this.vX = vX;
		this.vY = vY;
		diam = getRandomDiam();
		mass = getRandomMass();
		color = getRandomColor();
	}
	public void drawing(){
		repaint();
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(BallCreate ball : FrameCreation.ballObjects){
			g.setColor(ball.color);
				//System.out.println("pos X: " + posX);
				//System.out.println("pos Y: " + posY);
				g.fillOval((int)ball.posX, (int)ball.posY, 
					ball.diam ,ball.diam);
			}
		}
	public Color getRandomColor(){
		int R = (int)(255*Math.random());
		int G = (int)(255*Math.random());
		int B = (int)(255*Math.random());
		Color c = new Color(R, G , B);
		return c;
		
	}
	public int getRandomDiam(){ 
		int randDiam = (int) (85 * Math.random());
		if(randDiam < 30){
			randDiam+= 20;
		}
		return randDiam;
	}
	public int getRandomMass(){ 
		int randMass = (int) (300 * Math.random());
		if(randMass <= 50){
			randMass+=50;
		}
		return randMass;
	}
	
}