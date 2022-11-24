package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * Tests for the Empty class
 * @author paige
 *
 */
public class EmptyTest {
		//test that if the cell is not a reseller or outage 
		//and that num O + E neighbors are <= 1
		//it changes to a reseller
		@Test 
		public void test1(){
			Town cTown = new Town(2,2);
	        cTown.grid[0][0] = new Empty(cTown,0,0);
	        cTown.grid[0][1] = new Casual(cTown,0,1);
	        cTown.grid[1][0] = new Casual(cTown,1,0);
	        cTown.grid[1][1] = new Casual(cTown,1,1);
	        
	        assertEquals(State.RESELLER, cTown.grid[0][0].next(cTown).who());
	        
		}
		
		//test that if a cell was empty, a casual user takes it
		@Test
		public void test2() {
			Town cTown = new Town(2,2);
	        cTown.grid[0][0] = new Empty(cTown,0,0);
	        cTown.grid[0][1] = new Streamer(cTown,0,1);
	        cTown.grid[1][0] = new Empty(cTown,1,0);
	        cTown.grid[1][1] = new Outage(cTown,1,1);
	        
	        assertEquals(State.CASUAL, cTown.grid[0][0].next(cTown).who());
		}

		
		@Test
		public void whoTest() {
			Town cTown = new Town(1,1);
	        cTown.grid[0][0] = new Empty(cTown,0,0);
			assertEquals(State.EMPTY, cTown.grid[0][0].who());
		}
}
