package physics;

import java.awt.Color;
import java.awt.FlowLayout;
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

	final double G = 40;

	static Vector<BallCreate> ballObjects = new Vector<BallCreate>();
	static Timer timer;
	Point m1;
	Point m2;
	static JFrame frame1;
	static JPanel panel;
	static mouseHandler mouse;

	public static void main(String args[]) {
		FrameCreation frame = new FrameCreation();
		frame1 = new JFrame("Phyiscs Test");
		frame1.setVisible(true);
		frame1.setSize(520, 540);
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
					m1X = ballObjects.get(i).mass * ballObjects.get(i).vX
							+ (ballObjects.get(j).vX * ballObjects.get(j).mass);
					m1Y = ballObjects.get(i).mass * ballObjects.get(i).vY
							+ (ballObjects.get(j).vY * ballObjects.get(j).mass);
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
		BallCreate newBall = new BallCreate(posX, posY, vX, vY);
		ballObjects.add(newBall);
		frame1.add(newBall);
		frame1.revalidate();
		newBall.repaint();
		// }
	}

	public void motionUpdate() {
		for (BallCreate ball : ballObjects) {
			double t = 0.1;
			ball.dX = ball.vX * t;
			ball.dY = (ball.vY + G * t) * t;
			ball.v2Y = G * t + ball.vY;
			// System.out.println("Ball v2y: " + ball.vY);
			ball.posX = ball.posX + ball.dX;
			ball.posY = ball.posY + ball.dY;
			ball.vY = ball.v2Y;
			ball.drawing();
			wallCollisionCheck();
			ballCollisionCheck();
		}
	}

	public void wallCollisionCheck() {
		for (BallCreate ball : ballObjects) {
			double botBound = 500-(ball.posY + ball.diam);
			if((botBound <=0 && ball.vY>=0)|| (ball.posY <= 0) && ball.vY <= 0 ){
					ball.vY = -0.65*ball.vY;
					if(botBound <= 0 && ball.vY <= 3 && !ball.hitBottom){
						ball.hitBottom = true;
						System.out.println("Stationary");
						ball.botBound = ball.posY + 1;
					}
					if(ball.hitBottom && (ball.posY - ball.botBound ) <= 1){
						ball.posY = ball.botBound;
					}
			}
			if((ball.posX<= 0 && ball.vX <= 0) || (500 - (ball.posX + ball.diam)) <=  0 && (ball.vX >=0)){
					ball.vX = -0.65*ball.vX;
			}
	}
}

	public class MovementUpdate implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			motionUpdate();
		}
	}// });
	/*
	 * public double quadradticSolver(int a, int b, int c){ int x = 0; int
	 * numerator = return x; }
	 */
}
// }