package edu.iastate.cs228.hw2;

/**
 *  
 * @author Paige
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


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		

		// 
		// Conducts multiple rounds of comparison of four sorting algorithms.  Within each round, 
		// set up scanning as follows: 
		// 
		//    a) If asked to scan random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		// 
		//    b) Reassigns to the array scanners[] (declared below) the references to four new 
		//       PointScanner objects, which are created using four different values  
		//       of the Algorithm type:  SelectionSort, InsertionSort, MergeSort and QuickSort. 
		// 
		// 	
		//PointScanner[] scanners = new PointScanner[4]; 
		
		// For each input of points, do the following. 
		// 
		//     a) Initialize the array scanners[].  
		//
		//     b) Iterate through the array scanners[], and have every scanner call the scan() 
		//        method in the PointScanner class.  
		//
		//     c) After all four scans are done for the input, print out the statistics table from
		//		  section 2.
		//
		// A sample scenario is given in Section 2 of the project description. 
		 
		
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
		System.out.println();
		Scanner scnr = new Scanner(System.in);
		System.out.println("keys:  1 (random integers)  2 (file input)  3 (exit)");
		
		//Variable to hold the amount of trials
		int trialNum = 1; 
		int key; 
		
		System.out.print("Trial " + trialNum + ": ");
		key = scnr.nextInt();
		
		// do while key != 3
		do {
			
			//if the key is random
			if (key == 1) {
				//ask the user for the number of points
				System.out.print("Enter number of points: "); 
				int numPoints = scnr.nextInt(); 
				Random generator = new Random(); 
				//generate random points
				Point[] rPoints = generateRandomPoints(numPoints, generator); 
				
				//create the scanners and put them into the array
				PointScanner[] scanners = new PointScanner[4];
				scanners[0] = new PointScanner(rPoints, Algorithm.InsertionSort); 
				scanners[1] = new PointScanner(rPoints, Algorithm.MergeSort); 
				scanners[2] = new PointScanner(rPoints, Algorithm.QuickSort); 
				scanners[3] = new PointScanner(rPoints, Algorithm.SelectionSort); 
				
				System.out.println(); 
				System.out.println("algorithm\tsize\ttime (ns)");
				System.out.println("----------------------------------");
				//scan the files
				for (PointScanner s : scanners) {
					s.scan(); 
					System.out.println(s.stats());
				}
				System.out.println("----------------------------------");
				System.out.println(); 
				
 
			}
			
			//if the key is from file
			else if (key == 2) {
				//ask the user for the file name
				System.out.print("File name: ");
				String fileName = scnr.next(); 
				
				//create scanners and put them into the array
				PointScanner[] scanners = new PointScanner[4];
				scanners[0] = new PointScanner(fileName, Algorithm.InsertionSort);
				scanners[1] = new PointScanner(fileName, Algorithm.MergeSort);
				scanners[2] = new PointScanner(fileName, Algorithm.QuickSort);
				scanners[3] = new PointScanner(fileName, Algorithm.SelectionSort);
				
				System.out.println(); 
				System.out.println("algorithm\tsize\ttime (ns)");
				System.out.println("----------------------------------");
				//scan the files
				for (PointScanner s : scanners) {
					s.scan(); 
					System.out.println(s.stats());
				}
				System.out.println("----------------------------------");
				System.out.println(); 
			}
			
			//if the key is to break or anything else
			else {
				break; 
			}
			
			//ask for user key again for the next round
			//Variable to hold the amount of trials
			trialNum ++;
			System.out.print("Trial " + trialNum + ": ");
			//System.out.println("keys:  1 (random integers)  2 (file input)  3 (exit)");
			key = scnr.nextInt();
			 
			
		} while (key != 3); 
 
		scnr.close(); 
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if (numPts < 1) {
			throw new IllegalArgumentException(); 
		}
		
		//array of points to hold the random points
		Point[] pts = new Point[numPts]; 
		//new random generator
		Random generator = rand; 
		int x = 0;
		int y = 0;
		//loop through the number of points 
		for (int i = 0; i < numPts; ++i) {
			x = generator.nextInt(101) - 50;
			y = generator.nextInt(101) - 50;
			Point pt = new Point(x, y);
			pts[i] = pt; 
		}
		return pts; 
	}
	
}
