package edu.iastate.cs228.hw1;

/**
 *  
 * @author Taylor Bauer
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from
	 * files. Use them as coordinates to construct points. Scan these points with
	 * respect to their median coordinate point four times, each time using a
	 * different sorting algorithm.
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException {
		// array to hold PointScanner objects of sorting algorithms
		PointScanner[] scanners = new PointScanner[4];
		// trial tracker
		int t = 1;
		// array to hold points
		Point[] points;
		// initialize new scanner
		Scanner s = new Scanner(System.in);

		System.out.println("Performance of Four Sorting Algorithms in Point Scanning\n");

		System.out.println("Please choose one of the following: 1 (Random Integers) | 2 (File Input) | 3 (Exit)\n");

		// get input from the user
		int userInput = s.nextInt();

		while (userInput <= 3) {

			// generated random points
			if (userInput == 1) {
				System.out.println("\n" + "Trial: " + t + "\n");
				System.out.println("Please enter the number of random points: ");
				// number of random points
				int randomNumber = s.nextInt();
				// randomly generated points
				points = generateRandomPoints(randomNumber, new Random());
				// selection sort
				scanners[0] = new PointScanner(points, Algorithm.SelectionSort);
				// insertion sort
				scanners[1] = new PointScanner(points, Algorithm.InsertionSort);
				// merge sort
				scanners[2] = new PointScanner(points, Algorithm.MergeSort);
				// quick sort
				scanners[3] = new PointScanner(points, Algorithm.QuickSort);
			} else if (userInput == 2) {
				// input from a file
				System.out.println("\nTrial: " + t + "\n");
				System.out.println("Please enter the name of the file: ");
				// the file name
				String f = s.next();
				// selection sort
				scanners[0] = new PointScanner(f, Algorithm.SelectionSort);
				// insertion sort
				scanners[1] = new PointScanner(f, Algorithm.InsertionSort);
				// merge sort
				scanners[2] = new PointScanner(f, Algorithm.MergeSort);
				// quick sort
				scanners[3] = new PointScanner(f, Algorithm.QuickSort);
			} else {
				System.out.println("Goodbye!");
				// break loop if user wishes to exit
				break;
			}

			// statistics table!
			System.out.println("Algorithm        Size        Time(ns)");
			System.out.println("--------------------------------------");

			// iterate through scanners[] array
			for (int i = 0; i < scanners.length; ++i) {
				// each scanner calls scan() method
				scanners[i].scan();
				// print statistics table
				System.out.println(scanners[i].stats());
			}
			System.out.println("--------------------------------------");

			// increment trial counter
			++t;
		}
		// closing scanner
		s.close();
	}

	/**
	 * This method generates a given number of random points. The coordinates of
	 * these points are pseudo-random numbers within the range [-50,50] � [-50,50].
	 * Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param numPts number of points
	 * @param rand   Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	private static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException {
		// if number of points is less than 1
		if (numPts < 1) {
			throw new IllegalArgumentException();
		}
		// array to hold newly generated points
		Point[] points = new Point[numPts];

		for (int i = 0; i < points.length; ++i) {
			// randomly generated x coordinate
			int x = rand.nextInt(101) - 50;
			// randomly generated y coordinate
			int y = rand.nextInt(101) - 50;
			// creating new point at coordinates (x,y)
			points[i] = new Point(x, y);
		}
		return points;
	}

}
