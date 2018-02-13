package BouncingBallsSimulation.src;

public class AdvancedSimulation {
	public static void main(String[] args) {
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.enableDoubleBuffering();
		Particle[] particlesForSimulation = new Particle[Integer.parseInt(args[0])];
		int N = particlesForSimulation.length;
		for (int i = 0; i < N; i++)
			particlesForSimulation[i] = new Particle(N);
		CollisionSystem cs = new CollisionSystem(particlesForSimulation);
		cs.simulate();
	}
}
