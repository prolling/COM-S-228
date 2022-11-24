package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * Tests for the Reseller class
 * @author paige
 *
 */
public class ResellerTest {
	//test that if there are 3 or fewer C
	//R leaves and becomes E
	@Test 
	public void test1(){
		Town cTown = new Town(2,2);
        cTown.grid[0][0] = new Reseller(cTown,0,0);
        cTown.grid[0][1] = new Empty(cTown,0,1);
        cTown.grid[1][0] = new Outage(cTown,1,0);
        cTown.grid[1][1] = new Casual(cTown,1,1);
        
        assertEquals(State.EMPTY, cTown.grid[0][0].next(cTown).who());
        
	}
	
	//test that if there are 3 or more empty cells
	//R leaves and becomes E
	public void test2() {
		Town cTown = new Town(2,2);
        cTown.grid[0][0] = new Reseller(cTown,0,0);
        cTown.grid[0][1] = new Empty(cTown,0,1);
        cTown.grid[1][0] = new Empty(cTown,1,0);
        cTown.grid[1][1] = new Empty(cTown,1,1);
        
        assertEquals(State.EMPTY, cTown.grid[0][0].next(cTown).who());
	}
	
	
	
	@Test
	public void whoTest() {
		Town cTown = new Town(1,1);
        cTown.grid[0][0] = new Reseller(cTown,0,0);
		assertEquals(State.RESELLER, cTown.grid[0][0].who());
	}
}
