package BouncingBallsSimulation.src;

public class MinPQ<Key extends Comparable<Key>> {

	private Key[] pq;
	private int n;

	public MinPQ() {
		pq = (Key[]) new Comparable[8];
		n = 0;
	}

	public boolean isEmty() {
		return n == 0;
	}

	public int size() {
		return n;
	}

	public void insert(Key key) {
		if (n == (pq.length - 1))
			resize(2 * pq.length);
		pq[++n] = key;
		swim(n);
	}

	public Key delMin() {
		Key min = pq[1];
		exch(1, n--);
		sink(1);
		pq[n + 1] = null;
		if (n > 0 && n == (pq.length / 4))
			resize(pq.length / 2);
		return min;
	}

	private void swim(int k) {
		while (k > 1 && less(k, k / 2)) {
			exch(k, k / 2);
			k = k / 2;
		}
	}

	private void sink(int k) {
		while (2 * k <= n) {
			int j = 2 * k;
			if (j < n && less(j + 1, j))
				j++;
			if (!less(j, k))
				break;
			exch(k, j);
			k = j;
		}
	}

	private boolean less(int i, int j) {
		return pq[i].compareTo(pq[j]) < 0;
	}

	private void exch(int i, int j) {
		Key t = pq[i];
		pq[i] = pq[j];
		pq[j] = t;
	}

	private void resize(int newSize) {
		Key[] temp = (Key[]) new Comparable[newSize];
		for (int i = 1; i <= n; i++)
			temp[i] = pq[i];
		pq = temp;
	}

}
