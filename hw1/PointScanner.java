package edu.iastate.cs228.hw1;

import java.io.File;
/**
 * 
 * @author Taylor Bauer
 *
 */
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * This class sorts all the points in an array of 2D points to determine a
 * reference point whose x and y coordinates are respectively the medians of the
 * x and y coordinates of the original points.
 * 
 * It records the employed sorting algorithm as well as the sorting time for
 * comparison.
 *
 */
public class PointScanner {
	/** Array to store points */
	private Point[] points;
	/** Median of x and y coordinates of the elements in the array points */
	private Point medianCoordinatePoint;
	/** The sorting algorithm */
	private Algorithm sortingAlgorithm;
	/** Execution time in nanoseconds */
	protected long scanTime;

	/**
	 * This constructor accepts an array of points and one of the four sorting
	 * algorithms as input. Copy the points into the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {
		sortingAlgorithm = algo;

		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}

		points = new Point[pts.length];
		// copy points into the array points[]
		for (int i = 0; i < pts.length; ++i) {
			points[i] = new Point(pts[i]);
		}

	}

	/**
	 * This constructor reads points from a file.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException if the input file contains an odd number of
	 *                                integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException {
		sortingAlgorithm = algo;
		// file object
		File f = new File(inputFileName);
		// scanner to read file contents
		Scanner s = new Scanner(f);
		// integer counter
		int n = 0;
		// read contents of file while there is still more
		while (s.hasNextInt()) {
			// reads next integer from next file
			s.nextInt();
			// increment counter when integer is read
			++n;
		}
		// if total number of integers is uneven
		if (n % 2 != 0) {
			// close scanner
			s.close();
			// throw exception
			throw new InputMismatchException();
		}
		
		// new point array with length of all file ints divided by 2
		points = new Point[n / 2];

		for (int i = 0; i < points.length; ++i) {
			// file x-coordinate
			int x = s.nextInt();
			// file y-coordinate
			int y = s.nextInt();
			// create new point in points array
			points[i] = new Point(x, y);
		}
		s.close();

	}

	/**
	 * Carry out two rounds of sorting using the algorithm designated by
	 * sortingAlgorithm as follows:
	 * 
	 * a) Sort points[] by the x-coordinate to get the median x-coordinate. b) Sort
	 * points[] again by the y-coordinate to get the median y-coordinate. c)
	 * Construct medianCoordinatePoint using the obtained median x- and
	 * y-coordinates.
	 * 
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter,
	 * InsertionSorter, MergeSorter, or QuickSorter to carry out sorting.
	 * 
	 * @param algo
	 * @return
	 */
	public void scan() {
		// reference to hold sorter object
		AbstractSorter aSorter;

		// sorting algorithm objects to hold aSorter reference
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
		} else if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(points);
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
		} else {
			aSorter = new QuickSorter(points);
		}

		// x comparison
		aSorter.setComparator(0);
		// start time for x
		long xBegin = System.nanoTime();
		// sort
		aSorter.sort();
		// x sort time
		long timeForX = System.nanoTime() - xBegin;
		// median x-coordinate
		int medianXPoint = aSorter.getMedian().getX();

		// y comparison
		aSorter.setComparator(1);
		// start time for y
		long yBegin = System.nanoTime();
		// sort
		aSorter.sort();
		// y sort time
		long timeForY = System.nanoTime() - yBegin;
		// median y-coordinate
		int medianYPoint = aSorter.getMedian().getY();

		// new point object to store coordinates
		medianCoordinatePoint = new Point(medianXPoint, medianYPoint);

		// sum of time spent on two sorting rounds
		scanTime = timeForX + timeForY;
	}

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description.
	 */
	public String stats() {
		return String.format("%-17s %-11d %-6d", sortingAlgorithm, points.length, scanTime);
	}

	/**
	 * Write MCP after a call to scan(), in the format "MCP: (x, y)" The x and y
	 * coordinates of the point are displayed on the same line with exactly one
	 * blank space in between.
	 */
	@Override
	public String toString() {
		return "MPC: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")";
	}

	/**
	 * 
	 * This method, called after scanning, writes point data into a file by
	 * outputFileName. The format of data in the file is the same as printed out
	 * from toString(). The file can help you verify the full correctness of a
	 * sorting result and debug the underlying algorithm.
	 * 
	 * @throws IOException
	 */
	public void writeMCPToFile() throws IOException {
		// scanner to grab output file name
		Scanner s = new Scanner(System.in);
		System.out.println("Please enter the name of the output file: ");
		// output file name
		String outputFileName = s.next();
		// file writer - idea from Geeks for Geeks
		FileWriter fWriter = new FileWriter(outputFileName);
		// write to file
		fWriter.write(this.toString());
		// close file writer
		fWriter.close();
		// close scanner
		s.close();

	}

}
