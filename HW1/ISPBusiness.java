package edu.iastate.cs228.hw1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Paige Rolling
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth()); 
		//create a town object and populate the grid inside it
		for (int j = 0; j < tOld.getLength(); ++j) {
			for (int i = 0; i < tOld.getWidth(); ++i) {
				//get the next of each cell in the grid for the next grid
				tNew.grid[j][i] = tOld.grid[j][i].next(tNew); 
			}
		}
		 
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) {
		//num of casual users
		int c = 0; 
		//loop to count how many casual users there are on the grid
		for (int i = 0; i < town.getLength(); ++i) {
			for (int j = 0; j < town.getWidth(); ++j) {
				if (town.grid[i][j].who() == State.CASUAL)
					c++; 
			}
			
		}
 
		return c;
		
	}
	
	
	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * @throws FileNotFoundException 
	 * 
	 */
	public static void main(String []args) throws FileNotFoundException {

		//make new scanner to read user input
		Scanner scnr = new Scanner(System.in); 
		//ask the user about grid generation and takes in their response
		System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly with seed ");
		int type = scnr.nextInt();
		
		//if user chooses Option 1, take file path as input
		if (type == 1) {
			System.out.println("Please enter file path: ");
			//file format is given in ISP4x4.txt
			String textFileName = scnr.next();
			Town textTown = new Town(textFileName);
			//System.out.println(textTown); 
			
			int c = getProfit(textTown); 
			for (int i = 0; i < 11; ++i) {
				
				textTown = updatePlain(textTown); 
				//System.out.println(textTown);
				//System.out.println(c); 
				c += getProfit(textTown); 
			}
			
			//print the total profit for the whole 12 cycles
			int r = textTown.getLength() * textTown.getWidth();
			double totalProfit = (c*100.00)/(r*12.00); 
			System.out.println(String.format("%.2f", totalProfit) + "%");
			//have to look for ../Paige_Rolling_HW1/src/Testing.txt
			//System.out.println(textTown.toString());  
		//or if user chooses option 2
		} else {
			//ask the user for three integer values for number of row, cols, and seed for random num generator (in order)
			System.out.println("Provide rows, cols and seed integer separated by spaces: ");
			int rows = scnr.nextInt();
			int cols = scnr.nextInt();
			int seed = scnr.nextInt(); 
			
			Town randTown = new Town(rows, cols);
			randTown.randomInit(seed); 
			//System.out.println(randTown); 
			 
			//number of casual users
			int c = getProfit(randTown); 
			for (int i = 0; i < 11; ++i) {
				//updatePlain(randTown); 
				randTown = updatePlain(randTown); 
				//System.out.println(randTown); 
				//System.out.println(getProfit(randTown));
				c += getProfit(randTown);  
			}
			int r = randTown.getLength() * randTown.getWidth();
			double totalProfit =(c*100.00)/(r*12.00);
			System.out.println(String.format("%.2f", totalProfit) + "%");
			

			//File f = new File("../Paige_Rolling_HW1/src/Testing.txt");
			//System.out.println("Does it exist?: " + f.exists());
		}	
	}
}
