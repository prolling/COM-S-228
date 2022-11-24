package edu.iastate.cs228.hw1;

public class Casual extends TownCell {

	/**
	 * constructor to create a new casual cell
	 * @param p
	 * @param r
	 * @param c
	 */
	public Casual(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		// return the state of the casual cell as casual
		return State.CASUAL;
	}

	@Override
	public TownCell next(Town tNew) {
		//reset and call census
		int[] newCensus = new int[NUM_CELL_TYPE];
		this.census(newCensus);
		if (newCensus[1] + newCensus[3] <= 1)	
			//next is reseller 
			return new Reseller(tNew, this.row, this.col); 
		//if census has reseller
		//rule 1 for census
		if (newCensus[0] >= 1)
			//next is outage
			return new Outage(tNew, this.row, this.col);
		//else if there is a streamer
		//rule 2 for census
		else if (newCensus[4] >= 1)	
			//next is streamer	
			return new Streamer(tNew, this.row, this.col); 
		//else if num casual neighbors >= 5 
		//rule 6b
		else if (newCensus[2] >= 5)
			//next is Streamer
			return new Streamer(tNew, this.row, this.col);
		//else state is casual
		//otherwise return casual
		else
			return new Casual(tNew, this.row, this.col); 
	}

}
