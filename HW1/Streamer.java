package edu.iastate.cs228.hw1;

public class Streamer extends TownCell{

	/**
	 * constructor for a new Streamer cell
	 * @param p
	 * @param r
	 * @param c
	 */
	public Streamer(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.STREAMER;
	}

	@Override
	public TownCell next(Town tNew) {
		int[] newCensus = new int[NUM_CELL_TYPE];
		this.census(newCensus);
		
		if (newCensus[1] + newCensus[3] <= 1)
			//next is reseller
			return new Reseller(tNew, this.row, this.col);
		// if census has reseller
		else if (newCensus[0] >= 1)
			//next is Outage
			return new Outage(tNew, this.row, this.col);
		// else if census has outage 
		else if (newCensus[3] >= 1)
			//next is Empty
			return new Empty(tNew, this.row, this.col); 
		//else if num casual neighbors >= 5
		else if (newCensus[2] >= 5)
			//next is Streamer
			return new Streamer(tNew, this.row, this.col);
		//if nothing else next is Streamer
		else 
			return new Streamer(tNew, this.row, this.col); 
	}

}
