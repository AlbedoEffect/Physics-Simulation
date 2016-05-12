package physics;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FrameCreation extends JPanel {

	/////CONSTANT DECLARATIONS///
	//Gravitational constant
	final double G = 40;
	//Speed threshold for bottom-screen removal
	final double minSpeed = 10;
	
	//ArrayList of ball objects
	static Vector<BallCreate> ballObjects = new Vector<BallCreate>();
	static Timer timer;
	static Dimension frameDim = new Dimension(520,540);
	static final int botBordTol = 20;
	static JFrame frame1;
	static mouseHandler mouse;

	public static void main(String args[]) {
		FrameCreation frame = new FrameCreation();
		frame1 = new JFrame("Phyiscs Test");
		frame1.setSize(frameDim);
		frame1.setVisible(true);
		frame1.setDefaultCloseOperation(frame1.EXIT_ON_CLOSE);
		mouse = frame.new mouseHandler();
		frame1.addMouseListener(mouse);
		timer = new Timer(100, frame.new MovementUpdate());
		timer.start();
	}

	public void ballCollisionCheck() {
		double posX1;
		double posY1;
		double posX2;
		double posY2;
		double m1X;
		double m1Y;
		double m2X;
		double m2Y;
		int distCenter;
		for (int i = 0; i < ballObjects.size(); i++) {
			posX1 = ballObjects.get(i).posX + (ballObjects.get(i).diam / 2);
			posY1 = ballObjects.get(i).posY + (ballObjects.get(i).diam / 2);
			for (int j = i + 1; j < ballObjects.size(); j++) {
				if (i == (ballObjects.size() - 1) && (j == ballObjects.size() - 1)) {

				} else {
					posX2 = ballObjects.get(j).posX + (ballObjects.get(j).diam / 2);
					posY2 = ballObjects.get(j).posY + (ballObjects.get(j).diam / 2);
					distCenter = (int) (Math.sqrt(Math.pow(posX2 - posX1, 2) + Math.pow(posY2 - posY1, 2)));
					if (Math.abs(distCenter - ((ballObjects.get(i).diam / 2) + (ballObjects.get(j).diam / 2))) <= 5) {
						ballObjects.get(i).vX = -0.65 * ballObjects.get(i).vX;
						ballObjects.get(i).vY = -0.65 * ballObjects.get(i).vY;
						ballObjects.get(j).vX = -0.65 * ballObjects.get(j).vX;
						ballObjects.get(j).vY = -0.65 * ballObjects.get(j).vY;
						ballObjects.get(i).posX = ballObjects.get(i).posX + (ballObjects.get(i).vX * 0.1);
						ballObjects.get(j).posX = ballObjects.get(j).posX + (ballObjects.get(j).vX * 0.1);
						ballObjects.get(i).posY = ballObjects.get(i).posY + (ballObjects.get(i).vY * 0.1);
						ballObjects.get(j).posY = ballObjects.get(j).posY + (ballObjects.get(j).vY * 0.1);
						ballObjects.get(j).drawing();
						ballObjects.get(i).drawing();
						
					}
				}
			}
		}
	}

	public class mouseHandler extends JPanel implements MouseListener {
		Point m1;
		Point m2;
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			m1 = e.getPoint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			m2 = e.getPoint();
			double vX = m2.getX() - m1.getX();
			double vY = m2.getY() - m1.getY();
			createBall(m1.getX(), m1.getY(), vX, vY);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
	}

	public void createBall(double posX, double posY, double vX, double vY) {
		BallCreate newBall = new BallCreate(posX, posY,vX, vY);
		ballObjects.add(newBall);
		frame1.add(newBall);
		frame1.revalidate();
		newBall.repaint();
	}

	public void motionUpdate() {
		for (int i = 0; i < ballObjects.size(); i++) {
			double t = 0.1;
			ballObjects.get(i).dX = ballObjects.get(i).vX * t;
			ballObjects.get(i).dY = (ballObjects.get(i).vY + G * t) * t;
			ballObjects.get(i).v2Y = G * t + ballObjects.get(i).vY;
			ballObjects.get(i).posX +=  ballObjects.get(i).dX;
			ballObjects.get(i).posY += ballObjects.get(i).dY;
			ballObjects.get(i).vY = ballObjects.get(i).v2Y;
			ballObjects.get(i).drawing();
			wallCollisionCheck();
			ballCollisionCheck();
		}
	}

	public void wallCollisionCheck() {
		int removedI = 0;
		boolean remove = false;
		for(BallCreate ball : ballObjects){
			double botBound = frameDim.height-(frame1.getInsets().bottom + ball.posY + ball.diam);
			if((botBound <= botBordTol && ball.vY>=0)|| (ball.posY <=0 && ball.vY <=0 )){
				ball.vY *= -0.65;
			}
			
			if((ball.posX <= 0 && ball.vX <= 0) || ((Math.abs(frameDim.width - ball.posX) <= 40)&& ball.vX >=0)){
				ball.vX = ball.vX * -0.65;
				
			}
			
			ball.drawing();
			if((botBound <= botBordTol) && (Math.abs(ball.vY) <= minSpeed)){
				remove = true;
				removedI = ballObjects.indexOf(ball);
				break;
			}
		}
		if(remove)ballObjects.remove(removedI);
	}

	public class MovementUpdate implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			motionUpdate();
		}
	}
}