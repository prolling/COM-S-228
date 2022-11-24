package edu.iastate.cs228.hw1;

public class Empty extends TownCell {

	/**
	 * constructor to create a new empty cell
	 * @param p
	 * @param r
	 * @param c
	 */
	public Empty(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.EMPTY;
	}

	@Override
	public TownCell next(Town tNew) {
		//reset and call census
		int[] newCensus = new int[NUM_CELL_TYPE];
		this.census(newCensus);
		//if num empty neighbors + num outage neighbors <= 1
		if (newCensus[1] + newCensus[3] <= 1)
			//next is reseller
			return new Reseller(tNew, this.row, this.col);  
		//else next is casual
		else
			return new Casual(tNew, this.row, this.col); 
	}

}
