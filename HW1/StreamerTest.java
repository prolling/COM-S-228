package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * tests for the Streamer class
 * @author paige
 *
 */
public class StreamerTest {

	
	@Test
	public void whoTest() {
		Town cTown = new Town(1,1);
        cTown.grid[0][0] = new Streamer(cTown,0,0);
		assertEquals(State.STREAMER, cTown.grid[0][0].who());
	}
}
