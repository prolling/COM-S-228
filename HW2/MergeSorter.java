package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.reflect.Array;
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author paige
 *
 */

/**
 * 
 * This class implements the merge sort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{

	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{

		//invoke the superclass constructor
		super(pts); 
		//set the algorithm to Insertion sort
		super.algorithm = "merge sort"; 
		
	}


	/**
	 * Perform merge sort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		//base case
		if(pts.length <= 1)
			return;
		
		//mid point
		int mid = pts.length/2; 
		//left array
		Point[] l = new Point[mid]; 
		//right array
		Point[] r = new Point[pts.length - mid];
		for (int i = 0; i < l.length; i++) {
			l[i] = pts[i];
		}
		for (int j = 0; j < r.length; j++) {
			r[j] = pts[l.length + j]; 
		}
		
		mergeSortRec(l);
		mergeSortRec(r);
		merge(pts, l, r); 
	}

	

	/**
	 * private helper method to merge the two arrays together 
	 * @param pts
	 * @param left
	 * @param right
	 */
	private void merge(Point[] pts, Point[] left, Point[] right) {
		//left index
		int l = 0;
		//right index
		int r = 0;
		//index of points
		int i = 0; 
		
		while((l < left.length) && (r < right.length)) {
			if(pointComparator.compare(left[l], right[r]) < 0) {
				pts[i] = left[l++];
				i ++;
			}
			else {
				pts[i] = right[r++]; 
				i ++; 
			}
			
		}
		
		
		//leftover points
		while(l < left.length) {
			pts[i++] = left[l++];
		}
		while (r < right.length) {
			pts[i++] = right[r++]; 
		}
		
		
	}

}
