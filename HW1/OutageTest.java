package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * Tests for the Outage class
 * @author paige
 *
 */
public class OutageTest {
	//tests that if none of the other rules apply
	//any cell with 5 or more C neighbors becomes a S
	@Test
	public void addTest2() {
		Town cTown = new Town(3,3);
        cTown.grid[0][0] = new Casual(cTown,0,0);
        cTown.grid[0][1] = new Casual(cTown,0,1);
        cTown.grid[0][2] = new Outage(cTown, 0, 1);
        cTown.grid[1][0] = new Casual(cTown,1,0);
        cTown.grid[1][1] = new Casual(cTown,1,1); //middle of the grid
        cTown.grid[1][2] = new Outage(cTown, 1, 2);
        cTown.grid[2][2] = new Casual(cTown, 2, 2);
        cTown.grid[2][0] = new Casual(cTown, 2, 0);
        cTown.grid[2][1] = new Casual(cTown, 2, 1); 
        
        assertEquals(State.STREAMER, cTown.grid[1][1].next(cTown).who());
	
	}
		
		//tests that it changes the next cell to Empty
		@Test
		public void test1() {
			Town cTown = new Town(2,2);
	        cTown.grid[0][0] = new Outage(cTown,0,0);
	        cTown.grid[0][1] = new Streamer(cTown,0,1);
	        cTown.grid[1][0] = new Casual(cTown,1,0);
	        cTown.grid[1][1] = new Casual(cTown,1,1);
	        
	        assertEquals(State.EMPTY, cTown.grid[0][0].next(cTown).who());
		}
		
		@Test
		public void whoTest() {
			Town cTown = new Town(1,1);
	        cTown.grid[0][0] = new Outage(cTown,0,0);
			assertEquals(State.OUTAGE, cTown.grid[0][0].who());
		}
}
