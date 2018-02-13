package BouncingBallsSimulation.src;

public class BouncingBalls {

	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.enableDoubleBuffering();
		Ball[] balls = new Ball[N];
		for (int i = 0; i < N; i++)
			balls[i] = new Ball();
		while (true) {
			StdDraw.clear();
			for (int i = 0; i < N; i++) {
				balls[i].move(0.5);
				balls[i].draw();
			}
			StdDraw.show();
			StdDraw.pause(50);
		}
	}
}
