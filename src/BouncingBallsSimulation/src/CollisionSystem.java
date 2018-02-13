package BouncingBallsSimulation.src;

public class CollisionSystem {
	private class Event implements Comparable<Event> {
		private final double time; // time of event
		private final Particle a, b; // particles involved in event
		private final int countA, countB; // collision counts for a and b

		public Event(double t, Particle a, Particle b) {
			time = t;
			this.a = a;
			this.b = b;
			if (a != null)
				countA = a.count();
			else
				countA = -1;
			if (b != null)
				countB = b.count();
			else
				countB = -1;
		}

		public int compareTo(Event that) {
			return Double.compare(this.time, that.time);
		}

		public boolean isValid() {
			if (a != null && a.count() != countA)
				return false;
			if (b != null && b.count() != countB)
				return false;
			return true;
		}
	}

	private MinPQ<Event> pq; // the priority queue
	private double t = 0.0; // simulation clock time
	private Particle[] particles; // the array of particles
	private int N;
	private final static double NUMBER_OF_REDRAW_EVENTS_PER_TICK = 5;

	public CollisionSystem(Particle[] particles) {
		this.particles = particles;
		N = particles.length;
	}

	private void predict(Particle a) {
		if (a == null)
			return;
		for (int i = 0; i < N; i++) {
			double dt = a.timeToHit(particles[i]);
			if (dt != Double.POSITIVE_INFINITY)
				pq.insert(new Event(t + dt, a, particles[i]));
		}
		pq.insert(new Event(t + a.timeToHitVerticalWall(), a, null));
		pq.insert(new Event(t + a.timeToHitHorizontalWall(), null, a));
	}

	private void redraw() {
		StdDraw.clear();
		for (int i = 0; i < N; i++) {
			particles[i].draw();
		}
		StdDraw.show();
		StdDraw.pause(20);
		pq.insert(new Event(t + 1.0 / NUMBER_OF_REDRAW_EVENTS_PER_TICK, null, null));
	}

	public void simulate() {
		pq = new MinPQ<Event>();
		for (int i = 0; i < N; i++)
			predict(particles[i]);
		pq.insert(new Event(0, null, null));
		while (!pq.isEmty()) {
			Event event = pq.delMin();
			if (!event.isValid())
				continue;
			Particle a = event.a;
			Particle b = event.b;
			for (int i = 0; i < N; i++)
				particles[i].move(event.time - t);
			t = event.time;
			if (a != null && b != null)
				a.bounceOff(b);
			else if (a != null && b == null)
				a.bounceOffVerticalWall();
			else if (a == null && b != null)
				b.bounceOffHorizontalWall();
			else if (a == null && b == null)
				redraw();
			predict(a);
			predict(b);
		}
	}
}