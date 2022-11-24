package edu.iastate.cs228.hw1;

public class Outage extends TownCell {

	/**
	 * constructor for a new Outage cell
	 * @param p
	 * @param r
	 * @param c
	 */
	public Outage(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.OUTAGE;
	}

	@Override
	public TownCell next(Town tNew) {
		//reset and call census
		int[] newCensus = new int[NUM_CELL_TYPE];
		this.census(newCensus);
		//else next is empty
		//else 
			return new Empty(tNew, this.row, this.col); 
	}

}
