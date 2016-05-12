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
	boolean hitTop = false;
	double botBound;
	double vX, vY, posX, posY, v2Y, dX, dY;
	final double G = 15;
	
	public BallCreate(double posX, double posY, double vX, double vY){
		this.posX = posX;
		this.posY = posY;
		this.vX = vX;
		this.vY = vY;
		diam = getRandomDiam();
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
				g.fillOval((int)ball.posX, (int)ball.posY, 
					ball.diam ,ball.diam);
			}
		}
	
	
	public int getRandomDiam(){
		int diam2 = (int)(50*Math.random());
		if(diam2 > 15){
			return diam2;
		}else{
			return (diam2 + 15);
		}
	}
	
	public Color getRandomColor(){
		int R = (int)(255*Math.random());
		int G = (int)(255*Math.random());
		int B = (int)(255*Math.random());
		Color c = new Color(R, G , B);
		return c;	
	}
}