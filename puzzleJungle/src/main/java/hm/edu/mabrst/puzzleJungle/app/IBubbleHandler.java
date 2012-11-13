package hm.edu.mabrst.puzzleJungle.app;

public interface IBubbleHandler {



	public Bubble[] getSameColoredNeighbors();
	public void delete();
	void setNeighbors(int[] id);
	
	
	
}
