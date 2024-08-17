package edu.iastate.cs228.hw1;

/**
 *  
 * @author Taylor Bauer
 *
 */

import java.util.Comparator;
import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort,
 * and QuickSort. It stores the input (later the sorted) sequence.
 *
 */
public abstract class AbstractSorter {
	/** Array to store points operated on by a sorting algorithm */
	protected Point[] points;
	/** Type of sorting algorithm to be initialized by a child constructor */
	protected String algorithm = null;
	/** Comparator used for point comparison */
	protected Comparator<Point> pointComparator = null;

	/**
	 * This constructor accepts an array of points as input. Copy the points into
	 * the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException {
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}
		// new array of points, with length of array pts
		points = new Point[pts.length];
		// call to getPoints() to copy into array
		getPoints(pts);
	}

	/**
	 * Generates a comparator on the fly that compares by x-coordinate if order ==
	 * 0, by y-coordinate if order == 1. Assign the comparator to the variable
	 * pointComparator.
	 * 
	 * 
	 * 
	 * @param order 0 by x-coordinate 1 by y-coordinate
	 * 
	 * 
	 * @throws IllegalArgumentException if order is less than 0 or greater than 1
	 * 
	 */
	public void setComparator(int order) throws IllegalArgumentException {
		if (order < 0 || order > 1) {
			throw new IllegalArgumentException();
		} else if (order == 0) {
			Point.setXorY(true);
			pointComparator = new XOrYComp();
		} else if (order == 1) {
			Point.setXorY(false);
			pointComparator = new XOrYComp();
		}
	}
	
	protected static class XOrYComp implements Comparator<Point> {
		@Override
		public int compare(Point point1, Point point2) {
			return point1.compareTo(point2);
		}
	}
	
	/**
	 * Use the created pointComparator to conduct sorting. 
	 * setComparato() must be called before to generate an 
	 * appropriate comparator for sorting by the x or y coord.
	 * 
	 * Should be protected. Made public for testing.
	 */
	public abstract void sort();

	/**
	 * Obtain the point in the array points[] that has median index
	 * 
	 * @return median point
	 */
	public Point getMedian() {
		return points[points.length / 2];
	}

	/**
	 * Copys the array points[] onto the array pts[].
	 * 
	 * @param pts
	 */
	public void getPoints(Point[] pts) {
		for (int i = 0; i < pts.length; ++i) {
			points[i] = new Point(pts[i]);
		}
	}

	/**
	 * Swaps the two elements indexed at i and j respectively in the array points[].
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j) {
		Point temp = points[i];
		points[i] = points[j];
		points[j] = temp;
	}
}
