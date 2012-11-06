package hm.edu.mabrst.puzzleJungle.app;

import java.util.HashMap;



public class Handler implements IHandler, IShootHandler {

	
	private int[][] coordinates;
	public HashMap<Integer,Bubble> map;
	
	@Override
	public void shoot(int winkel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void newGame() {
		coordinates= new int[40][32];
		
		// TODO Auto-generated method stub

	}

	@Override
	public void addKugelToHashMap(Bubble b) {
		// TODO Auto-generated method stub

	}

	@Override
	public Bubble getBubble(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWall() {
		// TODO Auto-generated method stub
		return 0;
	}

}
