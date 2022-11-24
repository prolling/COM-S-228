package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * tests the ISPBusiness Class
 * @author paige
 *
 */
public class ISPBusinessTest {
	
	Town t; 
	
	@Test
	public void testProfit() {
		Town t = new Town(2,2);
        t.grid[0][0] = new Casual(t,0,0);
        t.grid[0][1] = new Empty(t,0,1);
        t.grid[1][0] = new Casual(t,1,0);
        t.grid[1][1] = new Casual(t,1,1);
        
        assertEquals(3, ISPBusiness.getProfit(t));
	}
	
	//test the next grid from file
	@Test
	public void testNext() throws FileNotFoundException {
		t = new Town("../Paige_Rolling_HW1/src/Testing.txt");
		String nextTown = 
				  "E E E E \n"
				+ "C C O E \n"
				+ "C O E O \n"
				+ "C E E E \n";
		Town tNew = ISPBusiness.updatePlain(t);
		assertEquals(nextTown, tNew.toString()); 
	}
	
}
