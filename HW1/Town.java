package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


/**
 *  @author Paige Rolling
 *
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		//create a new TownCell object 
		grid = new TownCell[length][width]; 
		//assign length and width of the grid
		this.length = length; 
		this.width = width; 
		
		//Town is a 2D array of TownCell
		//stores the state of each cell and holds the objects of 
		//subclasses of TownCell
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException { 
		File file = new File(inputFileName);
		
		Scanner scnr = new Scanner(file);
		length = Integer.parseInt(scnr.next());
		width = Integer.parseInt(scnr.next());  
		grid = new TownCell[length][width]; 
		
		//while the scanner has next line 
		do {
			scnr.nextLine(); 
			for (int i = 0; i < length; ++i) {
				for (int j = 0; j < width; ++j) {
					String next = scnr.next(); 
					if (next.equals("C ") || next.equals("C")) 
						grid[i][j] = new Casual(this, i , j);   
					else if (next.equals("S ") || next.equals("S"))
						grid[i][j] = new Streamer(this, i, j); 
					else if (next.equals("R ") || next.equals("R"))
						grid[i][j] = new Reseller(this, i, j);
					else if (next.equals("E ") || next.equals("E"))
						grid[i][j] = new Empty(this, i, j); 
					else if (next.equals("O ") || next.equals("O"))
						grid[i][j] = new Outage(this, i, j); 
				}	
			}
			
		} while (scnr.hasNextLine());
		
		scnr.close(); 
		
	}
	
	/**
	 * Returns width of the grid.
	 * @return
	 */
	public int getWidth() {
		//return the width of the grid
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return
	 */
	public int getLength() {
		//return the length of the grid
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		
		State[][] array = new State[length][width];
		//all of the possible states
		State[] states = {State.RESELLER, State.EMPTY, State.CASUAL, State.OUTAGE, State.STREAMER};
		
		//loop through the given length and width
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				//get a random state
				State state = states[rand.nextInt(5)];
				//set the grid at the current index to a new TownCell of the rand state
				if (state == State.CASUAL)
					grid[i][j] = new Casual(this, i, j); 
				else if (state == State.EMPTY)
					grid[i][j] = new Empty(this, i, j); 
				else if (state == State.OUTAGE)
					grid[i][j] = new Outage(this, i, j); 
				else if (state == State.RESELLER)
					grid[i][j] = new Reseller(this, i, j); 
				else if (state == State.STREAMER)
					grid[i][j] = new Streamer(this, i, j);  
			}
		}
		
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < length; ++i) {
			for (int j = 0; j < width; ++j) {
				if (grid[i][j].who()== State.CASUAL) 
					s += "C ";
				else if (grid[i][j].who() == State.STREAMER)
					s += "S ";
				else if (grid[i][j].who() == State.RESELLER) 
					s+= "R ";
				else if (grid[i][j].who() == State.EMPTY)
					s += "E ";
				else
					s+= "O "; 
			}

			s += "\n"; 
		}
		
		return s;
	}
	

}


