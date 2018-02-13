package BouncingBallsSimulation.src;
import java.awt.Color;
import java.util.Random;

public class Ball {
	private static Random r = new Random();
	private double rx, ry; // position
	private double vx, vy; // velocity
	private final double radius; // radius
	private Color color;

	public Ball() {
		/* initialize position and velocity */
		vx = 0;
		while (vx == 0)
			vx = r.nextInt(1500);
		vy = 0;
		while (vy == 0)
			vy = r.nextInt(1500);
		radius = 100 + r.nextInt(900);
		rx = r.nextInt(32768 - (int) (2 * radius)) + radius;
		ry = r.nextInt(32768 - (int) (2 * radius)) + radius;
		setRandomColor();
	}

	private void setRandomColor() {
		color = Color.getHSBColor(1 - r.nextFloat(), 1.0f, 1 - 0.7f * r.nextFloat());
	}

	public void move(double dt) {
		if ((rx + vx * dt < radius) || (rx + vx * dt > 32768 - radius)) {
			vx = -vx;
		}
		if ((ry + vy * dt < radius) || (ry + vy * dt > 32768 - radius)) {
			vy = -vy;
		}
		rx = rx + vx * dt;
		ry = ry + vy * dt;
	}

	public void draw() {
		StdDraw.setPenColor(color);
		StdDraw.filledCircle(rx, ry, radius);
	}
}