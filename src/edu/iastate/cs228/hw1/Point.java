package edu.iastate.cs228.hw1;

/**
 * 
 * @author Taylor Bauer
 *
 */

public class Point implements Comparable<Point> {
	/** X-Coordinate */
	private int x;
	/** Y-Coordinate */
	private int y;
	/** Compare x or y coordinates */
	public static boolean xORy;
	/**
	 * Default Constructor
	 * 
	 * x and y get default value 0
	 */
	public Point() {
		
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Copy constructor
	 * @param p - Point Object
	 */
	public Point(Point p) {
		x = p.getX();
		y = p.getY();
	}
	
	/**
	 * Returns the x-coordinate
	 * @return the x-coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y-coordinate
	 * @return the y-coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set the value of the static instance variable xORy.
	 * 
	 * @param xORy
	 */
	public static void setXorY(boolean xORy) {
		Point.xORy = xORy;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}

	/**
	 * Compare this point with a second point q depending on the value of the static
	 * variable xORy
	 * 
	 * @param q
	 * @return -1 if (xORy == true && (this.x < q.x || (this.x == q.x && this.y <
	 *         q.y))) || (xORy == false && (this.y < q.y || (this.y == q.y && this.x
	 *         < q.x))) 0 if this.x == q.x && this.y == q.y) 1 otherwise
	 */
	public int compareTo(Point q) {
		if (xORy == true && (this.x < q.x || (this.x == q.x && this.y < q.y))
				|| (xORy == false && (this.y < q.y || (this.y == q.y && this.x < q.x)))) {
			return -1;
		} else if (this.x == q.x && this.y == q.y) {
			return 0;
		}
		return 1;
	}

	/**
	 * Output a point in the standard form (x, y).
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y+ ")";
	}
}