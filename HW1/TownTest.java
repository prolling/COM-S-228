package edu.iastate.cs228.hw1;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * tests for the Town class
 * @author paige
 *
 */
public class TownTest {
	
	Town t;
	
	//tests the print grid
	@Test
	public void testPrint() throws FileNotFoundException {
		t = new Town("../Paige_Rolling_HW1/src/Testing.txt");
		String grid = 
				    "O R O R \n"
				  + "E E C O \n"
				  + "E S O S \n"
				  + "E O R R \n";
		System.out.println(grid);
		System.out.println(t);
		assertEquals(grid, t.toString());
		
	}
	
	
	//tests the len of grid from random
	@Test
	public void length() {
		t = new Town(2, 2);
		t.randomInit(4);
		assertEquals(2, t.getLength());
	}
	
	//tests the wid of grid from random
	@Test
	public void width() {
		t = new Town(2, 2);
		t.randomInit(4);
		assertEquals(2, t.getWidth());
	}
	
	//tests the len of grid from file
	@Test
	public void testFileLen() throws FileNotFoundException {
		t = new Town("../Paige_Rolling_HW1/src/Testing.txt");
		assertEquals(4, t.getLength());
	}
	
	//tests the wid of grid from file
	@Test 
	public void testFileWid() throws FileNotFoundException {
		t = new Town("../Paige_Rolling_HW1/src/Testing.txt");
		assertEquals(4, t.getWidth());
	}
	
	//tests that it finds a file
	@Test
	public void fileFound() throws FileNotFoundException {
		File f = new File("../Paige_Rolling_HW1/src/Testing.txt");
		assertTrue(f.exists());
	}
}
