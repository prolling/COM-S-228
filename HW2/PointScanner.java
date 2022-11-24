package edu.iastate.cs228.hw2;

import java.io.File;
 

/**
 * 
 * @author 
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


/**
 * @author paige
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
	
	private static final String outputFileName = "MCP.txt"; //output file name for the MCPtoText
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{ 
		//throw illegal argument exception if pts is null
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException(); 
		}
		
		//set the points array to the size of pts
		points = new Point[pts.length]; 
		//copy the pts in Point into points		
		for (int i = 0; i < pts.length; ++i) {
			points[i] = pts[i]; 
		}
		

		
		//set the algorithm as algo
		sortingAlgorithm = algo; 
		
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		//set the sortingAlgorithm to the given algo
		sortingAlgorithm = algo; 
		//create new file from the file name
		File file = new File(inputFileName);
		
		//array to hold the amount of points 
		ArrayList<Point> numPts = new ArrayList<Point>(); 
		
		//create new scanner to scan the file 
		Scanner scnr = new Scanner(file);
		
		int numInts = 0; 
		
		while (scnr.hasNext()) {
			int x = scnr.nextInt();
			numInts ++; 
			if (scnr.hasNext()) {
				int y = scnr.nextInt();
				numInts ++; 
				Point p = new Point(x, y);
				numPts.add(p);
			}
		}
		
		//throw the exception if the amount of ints is not even 
		if (numInts % 2 != 0) {
			scnr.close();
			throw new InputMismatchException();  
		}
		//set the array points size to the size of the numPts arrayList
		points = new Point[numPts.size() - 1]; 
		
		
		//manually set all of the points in the array list to the array
		for (int i = 0; i < numPts.size() - 1; ++i) {
			points[i] = numPts.get(i);
		}

		
		scnr.close(); 
		
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
  
		AbstractSorter aSorter; 
		
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the two 
		// rounds of sorting, have aSorter do the following: 
		// 
		//     a) call setComparator() with an argument 0 or 1. 
		//
		//     b) call sort(). 		
		// 
		//     c) use a new Point object to store the coordinates of the medianCoordinatePoint
		//
		//     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
		//
		//     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime. 


		// create a object to be referred by aSorter according to sorting algorithm
		if (sortingAlgorithm == Algorithm.InsertionSort) 
			aSorter = new InsertionSorter(points);
		else if (sortingAlgorithm == Algorithm.MergeSort)
			aSorter = new MergeSorter(points);
		else if (sortingAlgorithm == Algorithm.QuickSort)
			aSorter = new QuickSorter(points);
		else if (sortingAlgorithm == Algorithm.SelectionSort)
			aSorter = new SelectionSorter(points);
		else
			return;

		// compare the x values and find the median x value
		//start the time
		long XstartTime = System.nanoTime(); 
		aSorter.setComparator(0);
		aSorter.sort();
		Point medianPointX = new Point(aSorter.getMedian());
		int medianX = medianPointX.getX();
		//time for x
		long timeX = System.nanoTime() - XstartTime; 

		// compare the y values and find the median y value
		//start the time
		long YstartTime = System.nanoTime();
		aSorter.setComparator(1);
		aSorter.sort();
		Point medianPointY = new Point(aSorter.getMedian());
		int medianY = medianPointY.getY();
		//time for y
		long timeY = System.nanoTime() - YstartTime; 

		// create a new point with the median x and y coordinates
		medianCoordinatePoint = new Point(medianX, medianY);
		//get the total time taken to scan X and Y
		scanTime = timeY + timeX;  
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		int size = points.length; 
		
		return sortingAlgorithm + "\t" + size + "\t" + scanTime; 
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		
		return "MCP: " + medianCoordinatePoint.toString(); 
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		File file = new File(outputFileName);
		PrintWriter p = new PrintWriter(file);
		p.write(this.toString());
		p.close(); 
	}	

	

		
}
