package BouncingBallsSimulation.src;

import java.awt.Color;
import java.util.Random;

public class Particle {
	private double rx, ry; // position
	private double vx, vy; // velocity
	private final double radius; // radius
	private final double mass; // mass
	private int count; // number of collisions
	private static Random r = new Random();
	private Color color;
	public static final int MIN_SPEED = 100;
	public static final int MAX_SPEED = 500;

	public Particle(int numberOfAllParticlesInSimulation) {
		double speed = MIN_SPEED + r.nextDouble() * (MAX_SPEED - MIN_SPEED);
		vx = speed * r.nextDouble();
		vy = Math.sqrt(speed * speed - vx * vx);
		if (r.nextBoolean())
			vy = -vy;
		if (r.nextBoolean())
			vx = -vx;
		radius = Math
				.max(Math.sqrt(32768 * 32768 / (numberOfAllParticlesInSimulation * numberOfAllParticlesInSimulation))
						* (1 - r.nextFloat() * 0.25), 50);
		rx = r.nextInt(32768 - (int) (2 * radius)) + radius;
		ry = r.nextInt(32768 - (int) (2 * radius)) + radius;
		mass = radius * 100;
		setRandomColor();
	}

	private void setRandomColor() {
		color = Color.getHSBColor(1 - r.nextFloat(), 1.0f, 1 - 0.7f * r.nextFloat());
	}

	public void setColor(Color c) {
		color = c;
	}

	public int count() {
		return count;
	}

	public double kineticEnergy() {
		return 0.5 * mass * (vx * vx + vy * vy);
	}

	public void move(double dt) {
		rx = rx + vx * dt;
		ry = ry + vy * dt;
	}

	public void draw() {
		StdDraw.setPenColor(color);
		StdDraw.filledCircle(rx, ry, radius);
	}

	public double timeToHit(Particle that) {
		if (this == that)
			return Double.POSITIVE_INFINITY;
		double dx = that.rx - this.rx, dy = that.ry - this.ry;
		double dvx = that.vx - this.vx;
		double dvy = that.vy - this.vy;
		double dvdr = dx * dvx + dy * dvy;
		if (dvdr > 0)
			return Double.POSITIVE_INFINITY;
		double dvdv = dvx * dvx + dvy * dvy;
		double drdr = dx * dx + dy * dy;
		double sigma = this.radius + that.radius;
		double d = (dvdr * dvdr) - dvdv * (drdr - sigma * sigma);
		if (d < 0)
			return Double.POSITIVE_INFINITY;
		return -(dvdr + Math.sqrt(d)) / dvdv;
	}

	public double timeToHitVerticalWall() {
		if (vx > 0) {
			return (32768 - (rx + radius)) / vx;
		}
		if (vx < 0)
			return (radius - rx) / vx;
		return Double.POSITIVE_INFINITY;
	}

	public double timeToHitHorizontalWall() {
		if (vy > 0) {
			return (32768 - (ry + radius)) / vy;
		}
		if (vy < 0)
			return (radius - ry) / vy;
		return Double.POSITIVE_INFINITY;
	}

	public void bounceOff(Particle that) {
		double dx = that.rx - this.rx, dy = that.ry - this.ry;
		double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
		double dvdr = dx * dvx + dy * dvy;
		double dist = this.radius + that.radius;
		double J = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
		double Jx = J * dx / dist;
		double Jy = J * dy / dist;
		this.vx += Jx / this.mass;
		this.vy += Jy / this.mass;
		that.vx -= Jx / that.mass;
		that.vy -= Jy / that.mass;
		this.setRandomColor();
		that.setRandomColor();
		this.count++;
		that.count++;
	}

	public void bounceOffVerticalWall() {
		vx = -vx;
		count++;

	}

	public void bounceOffHorizontalWall() {
		vy = -vy;
		count++;
	}
}