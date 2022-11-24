package edu.iastate.cs228.hw1;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * tests for the TownCell class
 * @author paige
 *
 */
public class TownCellTest {
	Town t; 
	
	//who and next methods are tested through all of the State tests
	//census is tested through all of the State tests
	
	//test to check that the file reader works
	@Test
	public void test() throws FileNotFoundException {
		t = new Town("../Paige_Rolling_HW1/src/Testing.txt"); 
		assertEquals(State.OUTAGE, t.grid[0][0].who());
	}
	

}
