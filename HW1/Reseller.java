package edu.iastate.cs228.hw1;

public class Reseller extends TownCell{

	/**
	 * constructor for a new Reseller cell
	 * @param p
	 * @param r
	 * @param c
	 */
	public Reseller(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.RESELLER;
	}

	@Override
	public TownCell next(Town tNew) {
		//reset and call census
		int[] newCensus = new int[NUM_CELL_TYPE];
		this.census(newCensus);
		// if census has <= 3 casual or >= 3 empty
		//if there are three or fewer casual users in the 
		//neighborhood, the reseller leaves making it empty
		//if there are three or more empty cells then the 
		//reseller leaves and it becomes empty
		if (newCensus[2] <= 3 || newCensus[1] >= 3)
			//next is empty
			return new Empty(tNew, this.row, this.col);
		//else next is reseller
		else
			return new Reseller(tNew, this.row, this.col); 
	}

}
