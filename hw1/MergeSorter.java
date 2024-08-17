package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 *  
 * @author Taylor Bauer
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.
 *
 */
public class MergeSorter extends AbstractSorter {

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		algorithm = "Merge Sort";
	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		mergeSortRec(points);
	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of
	 * points. One way is to make copies of the two halves of pts[], recursively
	 * call mergeSort on them, and merge the two sorted subarrays into pts[].
	 * 
	 * @param pts point array
	 */
	private void mergeSortRec(Point[] pts) {
		// base case
		if (pts.length <= 1) {
			return;
		}

		// middle of the array
		int mid = pts.length / 2;
		// copy array into two new arrays - left and right
		Point[] leftArr = Arrays.copyOf(pts, mid);
		Point[] rightArr = Arrays.copyOfRange(pts, mid, pts.length);

		mergeSortRec(leftArr);
		mergeSortRec(rightArr);
		merge(pts, leftArr, rightArr);
	}

	/**
	 * Merges two sorted subarrays into a single sorted array.
	 * 
	 * @param pts      an array where the final merged array will be put
	 * @param leftArr  sorted left subarray
	 * @param rightArr sorted right subarray
	 */
	private void merge(Point[] pts, Point[] leftArr, Point[] rightArr) {
		// index var of left array
		int left = 0;
		// index var of right array
		int right = 0;
		// index var of pts
		int i = 0;
		// merge elements from left and right sub arrays into pts
		while (left < leftArr.length && right < rightArr.length) {
			if (leftArr[left].compareTo(rightArr[right]) <= 0) {
				pts[i] = leftArr[left];
				++left;
			} else {
				pts[i] = rightArr[right];
				++right;
			}
			++i;
		}
		// copy leftover elements from left subarray
		while (left < leftArr.length) {
			pts[i] = leftArr[left];
			++i;
			++left;
		}
		// copy leftover elements from right subarray
		while (right < rightArr.length) {
			pts[i] = rightArr[right];
			++i;
			++right;
		}
	}
}
